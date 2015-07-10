package br.jus.stf.autuacao.interfaces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.jus.stf.autuacao.application.PeticaoApplicationService;
import br.jus.stf.autuacao.interfaces.commands.AutuarPeticaoCommand;
import br.jus.stf.autuacao.interfaces.commands.RegistrarPeticaoFisicaCommand;

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

	@RequestMapping(value = "/api/peticao/fisica", method = RequestMethod.POST)
	public String registrar(@RequestBody RegistrarPeticaoFisicaCommand command) {
		return peticaoApplicationService.registrar(command.getTipoRecebimento());
	}

	@RequestMapping(value = "/api/peticao/{id}/preautuacao", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void preautuar(@PathVariable String id) {
		peticaoApplicationService.preautuar(id);
	}

	@RequestMapping(value = "/api/peticao/{id}/autuacao", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void autuar(@PathVariable String id, @RequestBody AutuarPeticaoCommand command) {
		peticaoApplicationService.autuar(id, command.getClassificacao());
	}

	@RequestMapping(value = "/api/peticao/{id}/devolucao", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void devolver(@PathVariable String id) {
		peticaoApplicationService.devolver(id);
	}

	@RequestMapping(value = "/api/peticao/{id}/distribuicao", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void distribuir(@PathVariable String id) {
		peticaoApplicationService.distribuir(id);
	}

}
