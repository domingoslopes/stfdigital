package br.jus.stf.processamentoinicial.autuacao.interfaces;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.jus.stf.processamentoinicial.autuacao.domain.model.TipoDevolucao;
import br.jus.stf.processamentoinicial.autuacao.interfaces.commands.AutuarPeticaoCommand;
import br.jus.stf.processamentoinicial.autuacao.interfaces.commands.DevolverPeticaoCommand;
import br.jus.stf.processamentoinicial.autuacao.interfaces.commands.PreautuarPeticaoFisicaCommand;
import br.jus.stf.processamentoinicial.autuacao.interfaces.commands.RegistrarPeticaoCommand;
import br.jus.stf.processamentoinicial.autuacao.interfaces.commands.RegistrarPeticaoFisicaCommand;
import br.jus.stf.processamentoinicial.autuacao.interfaces.dto.PeticaoDto;
import br.jus.stf.processamentoinicial.autuacao.interfaces.facade.PeticaoServiceFacade;

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

	private PeticaoServiceFacade peticaoServiceFacade;
	
	@Autowired
	public PeticaoRestResource(PeticaoServiceFacade peticaoServiceFacade) {
		this.peticaoServiceFacade = peticaoServiceFacade;
	}

	@ApiOperation("Registra uma nova petição eletrônica")
	@ApiResponses(value = {@ApiResponse(code = 400, message = "Petição Inválida")})
	@RequestMapping(value = "", method = RequestMethod.POST)
	public Long peticionar(@RequestBody @Valid RegistrarPeticaoCommand command, BindingResult binding) throws IOException {	    		
		if (binding.hasErrors()) {
			throw new IllegalArgumentException("Petição Inválida: " + binding.getAllErrors());
		}
		return peticaoServiceFacade.peticionar(command.getClasseId(), command.getPartesPoloAtivo(), command.getPartesPoloPassivo(), command.getPecas(), command.getOrgaoId());
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
		peticaoServiceFacade.preautuar(id, command.getClasseId(), command.isValida(), command.getMotivo()); 
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
	public void devolver(@PathVariable Long id, @RequestBody DevolverPeticaoCommand command) {
		peticaoServiceFacade.devolver(id, TipoDevolucao.valueOf(command.getTipoDevolucao()), command.getNumeroOficio());
	}

    @ApiOperation(value = "Retorna as partes de uma petição", hidden = true)
	@RequestMapping(value = "/{id}/partes", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public Map<String, List<Long>> consultarPartes(@PathVariable Long id) {
		PeticaoDto peticao = this.peticaoServiceFacade.consultar(id);
		return peticao.getPartes();
	}
}
