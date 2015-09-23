package br.jus.stf.autuacao.interfaces;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.validation.Valid;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.jus.stf.autuacao.application.PeticaoApplicationService;
import br.jus.stf.autuacao.domain.model.Peticao;
import br.jus.stf.autuacao.interfaces.commands.AutuarPeticaoCommand;
import br.jus.stf.autuacao.interfaces.commands.DistribuirPeticaoCommand;
import br.jus.stf.autuacao.interfaces.commands.PreautuarPeticaoFisicaCommand;
import br.jus.stf.autuacao.interfaces.commands.RegistrarPeticaoCommand;
import br.jus.stf.autuacao.interfaces.commands.RegistrarPeticaoFisicaCommand;
import br.jus.stf.autuacao.interfaces.dto.PeticaoDto;
import br.jus.stf.autuacao.interfaces.dto.ProcessoDto;
import br.jus.stf.autuacao.interfaces.facade.PeticaoServiceFacade;
import br.jus.stf.generico.domain.model.Pessoa;
import br.jus.stf.generico.domain.model.PessoaRepository;
import br.jus.stf.generico.interfaces.facade.GenericoServiceFacade;
import br.jus.stf.shared.domain.model.ProcessoWorkflow;
import br.jus.stf.shared.domain.model.ProcessoWorkflowRepository;

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
@RequestMapping("/api/peticoes")
public class PeticaoRestResource {

	@Autowired
	private PeticaoServiceFacade peticaoServiceFacade;
	
	@Autowired
	private PeticaoApplicationService peticaoApplicationService;
	
	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private HistoryService historyService;
	
	@Autowired
	private EntityManager entityManager;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private ProcessoWorkflowRepository processoWorkflowRepository;
	
	@Autowired
	private GenericoServiceFacade genericoServiceFacade;
	
	@ApiOperation("Lista as petições associadas ao usuário corrente")
	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<Map<String, String>> peticoes(@RequestHeader(value="papel") String papel) {
		// ----------------------------------------------------------------------------------------------
		// Petiçãoes em processamento...
		List<Map<String, String>> peticoesAtivas = new LinkedList<Map<String, String>>();
		
		@SuppressWarnings("unchecked")
		List<Peticao> peticoes = entityManager.createQuery("from Peticao").getResultList();
		for (Peticao p : peticoes) {
			Map<String, String> peticao = new LinkedHashMap<String, String>();
			if (!p.partesPoloAtivo().isEmpty()) {
				Pessoa parte = pessoaRepository.findOne(p.partesPoloAtivo().iterator().next().pessoaId());
				peticao.put("id", String.format("Petição #%s. Autor: %s", p.id().toLong(), parte.nome()));
			} else {
				peticao.put("id", String.format("Petição #%s. Autor: %s", p.id().toLong(), ""));
			}
			if (!p.processosWorkflow().isEmpty()) {
				ProcessoWorkflow workflow = processoWorkflowRepository.findOne(p.processosWorkflow().iterator().next());
				peticao.put("detalhes",  workflow.status());
			} else {
				peticao.put("detalhes",  "");
			}
			peticoesAtivas.add(peticao);
		}
		
		return peticoesAtivas;
	}

	@ApiOperation("Registra uma nova petição eletrônica")
	@ApiResponses(value = {@ApiResponse(code = 400, message = "Petição Inválida")})
	@RequestMapping(value = "", method = RequestMethod.POST)
	public Long peticionar(@RequestBody @Valid RegistrarPeticaoCommand command, BindingResult binding) throws IOException {
		
		// [TODO] Remover assim que o frond-end for capaz de enviar a lista real
		byte[] conteudo = IOUtils.toByteArray(new ClassPathResource("pdf/archimate.pdf").getInputStream());
	    MockMultipartFile mockArquivo = new MockMultipartFile("file", "teste_arq_temp.pdf", "application/pdf", conteudo);
		command.setDocumentos(Arrays.asList(genericoServiceFacade.salvarDocumentoTemporario(mockArquivo)));
		
		if (binding.hasErrors()) {
			throw new IllegalArgumentException("Petição Inválida: " + binding.getAllErrors());
		}
		return peticaoServiceFacade.peticionar(command.getClasseId(), command.getPartesPoloAtivo(), command.getPartesPoloPassivo(), command.getDocumentos());
	}

    @ApiOperation("Registra uma nova petição física")
	@RequestMapping(value = "/fisicas", method = RequestMethod.POST)
	public Long registrar(@Valid @RequestBody RegistrarPeticaoFisicaCommand command, BindingResult binding) {
    	if (binding.hasErrors()) {
			throw new IllegalArgumentException("Petição Inválida: " + binding.getAllErrors());
		}
    	return peticaoServiceFacade.registrar(command.getQuantidadeVolumes(), command.getQuantidadeApensos(), command.getFormaRecebimento(), command.getNumeroSedex());
	}

    @ApiOperation("Recupera as informações de uma determinada petição")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public PeticaoDto consultar(@PathVariable Long id) {
		return peticaoServiceFacade.consultar(id);
	}

    @ApiOperation("Conclui a pré-autuação de uma determinada petição física")
	@RequestMapping(value = "/fisicas/{id}/preautuar", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public void preautuar(@PathVariable Long id, @RequestBody PreautuarPeticaoFisicaCommand command) {
		peticaoServiceFacade.preautuar(id, command.getClasseId()); 
	}

    @ApiOperation("Conclui a autuação de uma determinada petição")
	@RequestMapping(value = "/{id}/autuar", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public void autuar(@PathVariable Long id, @RequestBody AutuarPeticaoCommand command) {
		peticaoServiceFacade.autuar(id, command.getClasseId(), command.isValida(), command.getMotivo());
	}

    @ApiOperation(value = "Conclui a devolução de uma determinada petição recebida incorretamente", hidden = true)
	@RequestMapping(value = "/{id}/devolver", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public void devolver(@PathVariable Long id) {
		peticaoServiceFacade.devolver(id);
	}

    @ApiOperation("Conclui a distribuição de uma determinada petição")
	@RequestMapping(value = "/{id}/distribuir", method = RequestMethod.POST)
	public ProcessoDto distribuir(@PathVariable Long id, @RequestBody DistribuirPeticaoCommand command) {
    	return this.peticaoServiceFacade.distribuir(id, command.getMinistroId());
    }

}
