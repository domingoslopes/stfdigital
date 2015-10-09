package br.jus.stf.processamentoinicial.autuacao.interfaces;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.validation.Valid;

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

import br.jus.stf.plataforma.documentos.interfaces.facade.DocumentoServiceFacade;
import br.jus.stf.plataforma.identidades.domain.model.Pessoa;
import br.jus.stf.plataforma.identidades.domain.model.PessoaRepository;
import br.jus.stf.processamentoinicial.autuacao.application.PeticaoApplicationService;
import br.jus.stf.processamentoinicial.autuacao.domain.model.Peticao;
import br.jus.stf.processamentoinicial.autuacao.interfaces.commands.AutuarPeticaoCommand;
import br.jus.stf.processamentoinicial.autuacao.interfaces.commands.PreautuarPeticaoFisicaCommand;
import br.jus.stf.processamentoinicial.autuacao.interfaces.commands.RegistrarPeticaoCommand;
import br.jus.stf.processamentoinicial.autuacao.interfaces.commands.RegistrarPeticaoFisicaCommand;
import br.jus.stf.processamentoinicial.autuacao.interfaces.dto.PeticaoDto;
import br.jus.stf.processamentoinicial.autuacao.interfaces.facade.PeticaoServiceFacade;
import br.jus.stf.shared.ProcessoWorkflow;
import br.jus.stf.shared.ProcessoWorkflowRepository;

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
	private EntityManager entityManager;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private ProcessoWorkflowRepository processoWorkflowRepository;
	
	@Autowired
	private DocumentoServiceFacade documentoServiceFacade;
	
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
			peticao.put("id", p.id().toString());
			if (!p.partesPoloAtivo().isEmpty()) {
				Pessoa parte = pessoaRepository.findOne(p.partesPoloAtivo().iterator().next().pessoaId());
				peticao.put("descricao", String.format("Petição %s. Autor: %s", p.identificacao(), parte.nome()));
			} else {
				peticao.put("descricao", String.format("Petição %s. Autor: %s", p.identificacao(), ""));
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
		if (binding.hasErrors()) {
			throw new IllegalArgumentException("Petição Inválida: " + binding.getAllErrors());
		}
		return peticaoServiceFacade.peticionar(command.getClasseId(), command.getPartesPoloAtivo(), command.getPartesPoloPassivo(), command.getPecas());
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

}
