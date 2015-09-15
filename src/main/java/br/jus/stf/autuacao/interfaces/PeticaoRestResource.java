package br.jus.stf.autuacao.interfaces;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.history.HistoricVariableInstanceQuery;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.jus.stf.autuacao.application.PeticaoApplicationService;
import br.jus.stf.autuacao.domain.model.FormaRecebimento;
import br.jus.stf.autuacao.interfaces.commands.AutuarPeticaoCommand;
import br.jus.stf.autuacao.interfaces.commands.DistribuirPeticaoCommand;
import br.jus.stf.autuacao.interfaces.commands.PreautuarPeticaoFisicaCommand;
import br.jus.stf.autuacao.interfaces.commands.RegistrarPeticaoCommand;
import br.jus.stf.autuacao.interfaces.commands.RegistrarPeticaoFisicaCommand;
import br.jus.stf.autuacao.interfaces.dto.PeticaoDto;
import br.jus.stf.autuacao.interfaces.dto.ProcessoDistribuidoDto;
import br.jus.stf.autuacao.interfaces.facade.PeticaoServiceFacade;

import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 22.06.2015
 */
@RestController
public class PeticaoRestResource {

	@Autowired
	private PeticaoServiceFacade peticaoSerivceFacade;
	
	@Autowired
	private PeticaoApplicationService peticaoApplicationService;

	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private HistoryService historyService;  
	
	@ApiOperation(value = "Lista as petições associadas ao usuário corrente")
	@RequestMapping(value = "/api/peticoes", method = RequestMethod.GET)
	public List<Map<String, String>> peticoes(@RequestHeader(value="papel") String papel) {
		// ----------------------------------------------------------------------------------------------
		// Petiçãoes em processamento...
		
		List<Map<String, String>> peticoesAtivas = new LinkedList<Map<String, String>>();
		
    	List<Task> tarefas = null;
    	
		if (papel.equals("peticionador")) {
	    	// O peticionador pode ver todas as petições ativas
			tarefas = taskService.createTaskQuery().includeProcessVariables().list();
		} else {
			// Os demais papeis (autuador e distribuidor) só podem ver as petições associadas ao seu perfil
			tarefas = taskService.createTaskQuery().taskCandidateGroup(papel).includeProcessVariables().list();
		}
		
		for (Task tarefa : tarefas) {
			String[] partes = (String[]) tarefa.getProcessVariables().get("partesPoloAtivo");
			String primeiraPartePoloAtivo = partes[0];
			Map<String, String> peticao = new LinkedHashMap<String, String>();
			peticao.put("id", String.format("Petição Digital #%s. Autor: %s", tarefa.getId(), primeiraPartePoloAtivo));
			peticao.put("detalhes",  String.format("%s", status(tarefa.getTaskDefinitionKey())));
			peticoesAtivas.add(peticao);
		}
		
		// ----------------------------------------------------------------------------------------------
		// Petiçãoes já finalizadas...
		
		// Analisando as 3 histórias com listagens (dashlets), concluimos que somente o peticionador pode
		// ver petições já finalizadas (rejeitadas ou distribuídas)
		if (papel.equals("peticionador")) {
			List<Map<String, String>> peticoesFinalizadas = new LinkedList<Map<String, String>>();
			
			List<HistoricProcessInstance> processos = historyService.createHistoricProcessInstanceQuery().finished().list();
			
			for (HistoricProcessInstance processo : processos) {
				HistoricVariableInstanceQuery variableQuery = historyService.createHistoricVariableInstanceQuery().processInstanceId(processo.getId());
				HistoricVariableInstance variable = variableQuery.variableName("partesPoloAtivo").singleResult();
				String primeiraPartePoloAtivo = ((String[]) variable.getValue())[0];
				Map<String, String> peticao = new LinkedHashMap<String, String>();
				peticao.put("id", String.format("Petição Digital #%s. Autor: %s", processo.getId(), primeiraPartePoloAtivo));
				
				 HistoricActivityInstance historicActivity = historyService.createHistoricActivityInstanceQuery().processInstanceId(processo.getId()).activityId("distribuido").singleResult();
				 if (historicActivity != null) {
					 Object relator = variableQuery.variableName("relator").singleResult().getValue();
					 peticao.put("detalhes", String.format("Distribuído. Relator: %s", relator));
				 } else {
					 Object motivo = variableQuery.variableName("motivo").singleResult().getValue();
					 peticao.put("detalhes", String.format("Rejeitada. Motivo: %s", motivo));
				 }
				 
				peticoesAtivas.add(peticao);
			}
			
			peticoesAtivas.addAll(peticoesFinalizadas);
		}
		
		return peticoesAtivas;
	}

	@ApiOperation(value = "Registra uma nova petição eletrônica")
	@ApiResponses(value = {@ApiResponse(code = 400, message = "Petição Inválida")})
	@RequestMapping(value = "/api/peticao", method = RequestMethod.POST)
	public Long peticionar(@RequestBody @Valid RegistrarPeticaoCommand command, BindingResult binding) {
		
		if (binding.hasErrors()) {
			throw new IllegalArgumentException("Petição Inválida: " + binding.getAllErrors());
		}
		
		String classeSugerida = command.getClasse();
		List<String> poloAtivo = new LinkedList<String>();
		List<String> poloPassivo = new LinkedList<String>();
		List<String> documentos = new LinkedList<String>();
				
		for(int i = 0; i < command.getPartesPoloAtivo().size(); i++) {
			poloAtivo.add(command.getPartesPoloAtivo().get(i));
		}
				
		for(int i = 0; i < command.getPartesPoloPassivo().size(); i++){
			poloPassivo.add(command.getPartesPoloPassivo().get(i));
		}
		
		for(int i = 0; i < command.getDocumentos().size(); i++){
			documentos.add(command.getDocumentos().get(i));
		}
				
		return this.peticaoSerivceFacade.peticionar(classeSugerida, poloAtivo, poloPassivo, documentos);
	}

    @ApiOperation(value = "Registra uma nova petição física", hidden = true)
	@RequestMapping(value = "/api/peticao/fisica", method = RequestMethod.POST)
	public Long registrar(@Valid @RequestBody RegistrarPeticaoFisicaCommand command, BindingResult binding) {
    	
    	if (binding.hasErrors()) {
			throw new IllegalArgumentException("Petição Inválida: " + binding.getAllErrors());
		}
    	
    	Integer volumes = command.getQuantidadeVolumes();
    	Integer apensos = command.getQuantidadeApensos();
    	String numeroSedex = command.getNumeroSedex();
    	FormaRecebimento formaRecebimento = null;
    	
    	switch(command.getFormaRecebimento()){
    	case "1": //Balcão.
    		formaRecebimento = FormaRecebimento.BALCAO;
    		break;
    	case "2": //Sedex
    		formaRecebimento = FormaRecebimento.SEDEX;
    		break;
    	case "3": //Malote
    		formaRecebimento = FormaRecebimento.MALOTE;
    		break;
    	case "4": //Fax
    		formaRecebimento = FormaRecebimento.FAX;
    		break;
    	case "5": //Email
    		formaRecebimento = FormaRecebimento.EMAIL;
    		break;
    	}
    	
    	return this.peticaoSerivceFacade.registrar(volumes, apensos, formaRecebimento, numeroSedex);
	}

    @ApiOperation(value = "Recupera as informações de uma determinada petição")
	@RequestMapping(value = "/api/peticao/{id}", method = RequestMethod.GET)
	public PeticaoDto consultar(@PathVariable Long id) {
		return this.peticaoSerivceFacade.consultar(id);
	}

    @ApiOperation(value = "Conclui a pré-autuação de uma determinada petição física", hidden = true)
	@RequestMapping(value = "/api/peticao/{id}/preautuacao", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void preautuar(@PathVariable Long id, @RequestBody PreautuarPeticaoFisicaCommand command) {
		this.peticaoSerivceFacade.preautuar(id, command.getClasseSugerida()); 
	}

    @ApiOperation(value = "Conclui a autuação de uma determinada petição")
	@RequestMapping(value = "/api/peticao/{id}/autuacao", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void autuar(@PathVariable Long id, @RequestBody AutuarPeticaoCommand command) {
		this.peticaoSerivceFacade.autuar(id, command.getClasse(), command.isValida(), command.getMotivo());
	}

    @ApiOperation(value = "Conclui a devolução de uma determinada petição recebida incorretamente", hidden = true)
	@RequestMapping(value = "/api/peticao/{id}/devolucao", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void devolver(@PathVariable Long id) {
		this.peticaoSerivceFacade.devolver(id);
	}

    @ApiOperation(value = "Conclui a distribuição de uma determinada petição")
	@RequestMapping(value = "/api/peticao/{id}/distribuicao", method = RequestMethod.POST)
	public ProcessoDistribuidoDto distribuir(@PathVariable Long id, @RequestBody DistribuirPeticaoCommand command) {
    	
    	return this.peticaoSerivceFacade.distribuir(id, command.getIdRelator());
    }

    private String status(String id) {
    	switch (id) {
			case "autuacao": return "Em Autuação";
			case "distribuicao": return "Em Distribuição";
			case "devolucao": return "Em Devolução";
    	}
		return null;
	}

}
