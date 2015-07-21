package br.jus.stf.autuacao.interfaces;

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

import br.jus.stf.autuacao.application.PeticaoApplicationService;
import br.jus.stf.autuacao.interfaces.commands.AutuarPeticaoCommand;
import br.jus.stf.autuacao.interfaces.commands.RegistrarPeticaoCommand;
import br.jus.stf.autuacao.interfaces.commands.RegistrarPeticaoFisicaCommand;

import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 22.06.2015
 */
@RestController
public class PeticaoRestService {

	@Autowired
	private PeticaoApplicationService peticaoApplicationService;

    @ApiOperation(value = "Registra uma nova petição digital")
	@ApiResponses(value = {@ApiResponse(code = 400, message = "Petição Inválida")})
	@RequestMapping(value = "/api/peticao", method = RequestMethod.POST)
	public String peticionar(@RequestBody @Valid @ApiParam(value = "A nova petição", required = true) RegistrarPeticaoCommand command, BindingResult binding) {
		
		if (binding.hasErrors()) {
			throw new IllegalArgumentException("Petição Inválida: " + binding.getAllErrors());
		}
		
		return "";
	}

    @ApiOperation(value = "Registra uma nova petição física", hidden = true)
	@RequestMapping(value = "/api/peticao/fisica", method = RequestMethod.POST)
	public String registrar(@Valid @RequestBody RegistrarPeticaoFisicaCommand command, BindingResult binding) {
		if (binding.hasErrors()) {
			throw new IllegalArgumentException("Petição Inválida: " + binding.getAllErrors());
		}
		
		return peticaoApplicationService.registrar(command.getTipoRecebimento());
	}

    @ApiOperation(value = "Conclui a pré-autuação de uma determinada petição física", hidden = true)
	@RequestMapping(value = "/api/peticao/{id}/preautuacao", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void preautuar(@PathVariable String id) {
		peticaoApplicationService.preautuar(id);
	}

    @ApiOperation(value = "Conclui a autuação de uma determinada petição")
	@RequestMapping(value = "/api/peticao/{id}/autuacao", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void autuar(@PathVariable String id, @RequestBody AutuarPeticaoCommand command) {
		peticaoApplicationService.autuar(id, command.getClassificacao());
	}

    @ApiOperation(value = "Conclui a devolução de uma determinada petição recebida incorretamente", hidden = true)
	@RequestMapping(value = "/api/peticao/{id}/devolucao", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void devolver(@PathVariable String id) {
		peticaoApplicationService.devolver(id);
	}

    @ApiOperation(value = "Conclui a distribuição de uma determinada petição")
	@RequestMapping(value = "/api/peticao/{id}/distribuicao", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void distribuir(@PathVariable String id) {
		peticaoApplicationService.distribuir(id);
	}

}
