package br.jus.stf.pesquisa.interfaces;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.jus.stf.pesquisa.interfaces.command.PesquisarCommand;
import br.jus.stf.pesquisa.interfaces.dto.ResultadoPesquisaDto;
import br.jus.stf.pesquisa.interfaces.facade.PesquisaServiceFacade;

import com.wordnik.swagger.annotations.ApiOperation;

/**
 * Interface Rest de pesquisa
 * 
 * @author Lucas.Rodrigues
 *
 */
@RestController
@RequestMapping("/api/pesquisas")
public class PesquisaRestResource {
	
	@Autowired
	private PesquisaServiceFacade pesquisaServiceFacade;

	@ApiOperation("Pesquisa genérica de objetos indexados")
	@RequestMapping(value = "", method = RequestMethod.POST)
	public List<ResultadoPesquisaDto> pesquisar(@RequestBody @Valid PesquisarCommand command, BindingResult result) {
		if (result.hasErrors()) {
			throw new IllegalArgumentException(result.getAllErrors().toString());
		}
		return pesquisaServiceFacade.pesquisar(command.getIndices(), command.getFiltros(), command.getCampos(), command.getOrdenador());
	}
	
}
