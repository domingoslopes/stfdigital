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
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.jus.stf.autuacao.interfaces.commands.AutuarPeticaoCommand;
import br.jus.stf.autuacao.interfaces.commands.DistribuirPeticaoCommand;
import br.jus.stf.autuacao.interfaces.commands.RegistrarPeticaoCommand;
import br.jus.stf.autuacao.interfaces.commands.RegistrarPeticaoFisicaCommand;
import br.jus.stf.autuacao.interfaces.dto.PeticaoDto;
import br.jus.stf.autuacao.interfaces.dto.ProcessoDistribuidoDto;

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
public class PeticaoMockRestResource {
	
	@Autowired
	private ProcessEngineFactoryBean processEngine;

	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private HistoryService historyService;  
	
    @SuppressWarnings({ "unchecked"})
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
			String primeiraPartePoloAtivo = (String) ((List<String>) tarefa.getProcessVariables().get("partesPoloAtivo")).get(0);
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
				String primeiraPartePoloAtivo = (String) ((List<String>) variable.getValue()).get(0);
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

	@ApiOperation(value = "Registra uma nova petição digital")
	@ApiResponses(value = {@ApiResponse(code = 400, message = "Petição Inválida")})
	@RequestMapping(value = "/api/peticao", method = RequestMethod.POST)
	public String peticionar(@RequestBody @Valid RegistrarPeticaoCommand command, BindingResult binding) {
    	Map<String, Object> parameters = new LinkedHashMap<String, Object>();
    	parameters.put("classe", command.getClasse());
    	parameters.put("partesPoloAtivo", command.getPartesPoloAtivo());
    	parameters.put("partesPoloPassivo", command.getPartesPoloPassivo());
    	
    	ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("autuarOriginarios", parameters);
    	
    	return processInstance.getId();
	}

    @SuppressWarnings("unchecked")
	@ApiOperation(value = "Recupera as informações de uma determinada petição")
	@RequestMapping(value = "/api/peticao/{id}", method = RequestMethod.GET)
	public PeticaoDto consultar(@PathVariable String id) {
    	String processInstanceId = taskService.createTaskQuery().taskId(id).singleResult().getProcessInstanceId();
    	
    	ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).includeProcessVariables().singleResult();
    	
    	Map<String, Object> parameters = processInstance.getProcessVariables();
    	
    	Map<String, List<String>> partes = new LinkedHashMap<String, List<String>>();
    	partes.put("poloAtivo", (List<String>) parameters.get("partesPoloAtivo"));
    	partes.put("poloPassivo", (List<String>) parameters.get("partesPoloPassivo"));
    	
    	return new PeticaoDto(parameters.get("classe").toString(), partes);
	}

    @ApiOperation(value = "Registra uma nova petição física", hidden = true)
	@RequestMapping(value = "/api/peticao/fisica", method = RequestMethod.POST)
	public String registrar(@Valid @RequestBody RegistrarPeticaoFisicaCommand command, BindingResult binding) throws Exception {
    	ProcessInstance processInstance = runtimeService.startProcessInstanceByMessage("Remessa de Petições Físicas", "autuarOriginarios");
    	
    	return processInstance.getId();
	}

    @ApiOperation(value = "Conclui a pré-autuação de uma determinada petição física", hidden = true)
	@RequestMapping(value = "/api/peticao/{id}/preautuacao", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void preautuar(@PathVariable String id) {
    	taskService.complete(id);
	}

    @ApiOperation(value = "Conclui a autuação de uma determinada petição")
	@RequestMapping(value = "/api/peticao/{id}/autuacao", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
    @Transactional
	public void autuar(@PathVariable String id, @RequestBody AutuarPeticaoCommand command) {
    	
    	Task tarefa = taskService.createTaskQuery().taskId(id).singleResult();
    	
		String processInstanceId = tarefa.getProcessInstanceId();
    	
		runtimeService.setVariable(processInstanceId, "classe", command.getClasse());
		
		if (!command.isValida()) {
			runtimeService.setVariable(processInstanceId, "motivo", command.getMotivo());
			runtimeService.signalEventReceived("Petição Inválida", tarefa.getExecutionId());
		} else {
			taskService.complete(id);
		}
    	
    	System.out.println(command);
	}

    @ApiOperation(value = "Conclui a devolução de uma determinada petição recebida incorretamente", hidden = true)
	@RequestMapping(value = "/api/peticao/{id}/devolucao", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void devolver(@PathVariable String id) {
    	taskService.complete(id);
	}

    @ApiOperation(value = "Conclui a distribuição de uma determinada petição")
	@RequestMapping(value = "/api/peticao/{id}/distribuicao", method = RequestMethod.POST)
	public ProcessoDistribuidoDto distribuir(@PathVariable String id, @RequestBody DistribuirPeticaoCommand command) {
    	Task task = taskService.createTaskQuery().taskId(id).includeProcessVariables().singleResult();
    	if (task == null) {
    		throw new IllegalArgumentException("Petição Inválida! Não será possível distribuir essa petição.");
    	}
    	
    	String processInstanceId = task.getProcessInstanceId();
    	
		runtimeService.setVariable(processInstanceId, "relator", command.getRelator());
		
    	taskService.complete(id);
    	
		String classe = task.getProcessVariables().get("classe").toString();
		
		return new ProcessoDistribuidoDto(classe, "0001", command.getRelator());
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
