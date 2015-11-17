package br.jus.stf.plataforma.pesquisas.interfaces;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.jus.stf.plataforma.pesquisas.interfaces.command.PesquisarCommand;
import br.jus.stf.plataforma.pesquisas.interfaces.dto.ResultadoDto;
import br.jus.stf.plataforma.pesquisas.interfaces.facade.PesquisaServiceFacade;

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
	public List<ResultadoDto> pesquisar(@RequestBody @Valid PesquisarCommand command, BindingResult result) {
		
		if (result.hasErrors()) {
			throw new IllegalArgumentException(result.getAllErrors().toString());
		}
		
		return pesquisaServiceFacade.pesquisar(command.getCampos(), command.getTipos(), command.getIndices(), command.getFiltros(), command.getOrdenadores(), command.getCampoAgregacao());
	}
	
	@ApiOperation("Pesquisa genérica de objetos indexados")
	@RequestMapping(value = "/paginadas", method = RequestMethod.POST)
	public PagedResources<Resource<ResultadoDto>> pesquisarPaginado(@RequestBody @Valid PesquisarCommand command, BindingResult result) {
		if (result.hasErrors()) {
			throw new IllegalArgumentException(result.getAllErrors().toString());
		}		
		return pesquisaServiceFacade.pesquisarPaginado(command.getCampos(), command.getTipos(), command.getIndices(), 
				command.getFiltros(), command.getOrdenadores(), command.getPagina(), command.getTamanho());
	}
	
	@ApiOperation("Pesquisa genérica de objetos indexados")
	@RequestMapping(value = "/sugestoes", method = RequestMethod.POST)
	public List<ResultadoDto> sugerir(@RequestBody @Valid PesquisarCommand command, BindingResult result) {
		if (result.hasErrors()) {
			throw new IllegalArgumentException(result.getAllErrors().toString());
		}
		return pesquisaServiceFacade.sugerir(command.getCampos(), command.getTipos(), command.getIndices(), command.getFiltros(), command.getOrdenadores());
	}
	
}
