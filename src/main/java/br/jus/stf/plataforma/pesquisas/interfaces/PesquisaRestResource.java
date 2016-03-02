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

import br.jus.stf.plataforma.pesquisas.application.PesquisaApplicationService;
import br.jus.stf.plataforma.pesquisas.interfaces.command.PesquisarAvancadoCommand;
import br.jus.stf.plataforma.pesquisas.interfaces.command.PesquisarCommand;
import br.jus.stf.plataforma.pesquisas.interfaces.command.SalvarPesquisaAvancadaCommand;
import br.jus.stf.plataforma.pesquisas.interfaces.dto.PesquisaAvancadaDto;
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
	
	@Autowired
	private PesquisaApplicationService pesquisaApplicationService;

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
				command.getFiltros(), command.getOrdenadores(), command.getPage(), command.getSize());
	}
	
	@ApiOperation("Pesquisa genérica de objetos indexados")
	@RequestMapping(value = "/sugestoes", method = RequestMethod.POST)
	public List<ResultadoDto> sugerir(@RequestBody @Valid PesquisarCommand command, BindingResult result) {
		if (result.hasErrors()) {
			throw new IllegalArgumentException(result.getAllErrors().toString());
		}
		return pesquisaServiceFacade.sugerir(command.getCampos(), command.getTipos(), command.getIndices(), command.getFiltros(), command.getOrdenadores());
	}
	
	@ApiOperation("Pesquisa avançada")
	@RequestMapping(value = "/avancada", method = RequestMethod.POST)
	public PagedResources<Resource<ResultadoDto>> pesquisarAvancado(@RequestBody @Valid PesquisarAvancadoCommand command, BindingResult result) {
		if (result.hasErrors()) {
			throw new IllegalArgumentException(result.getAllErrors().toString());
		}		
		return pesquisaServiceFacade.pesquisarAvancado(command.getConsulta(), command.getIndices(), command.getPage(), command.getSize());
	}
	
	@ApiOperation("Pesquisa avançada")
	@RequestMapping(value = "/avancadas", method = RequestMethod.GET)
	public List<PesquisaAvancadaDto> recuperarAvancadas() {		
		return pesquisaServiceFacade.recuperarAvancadas();
	}
	
	@ApiOperation("Pesquisa avançada")
	@RequestMapping(value = "/avancadas", method = RequestMethod.POST)
	public void salvarAvancada(@RequestBody @Valid SalvarPesquisaAvancadaCommand command, BindingResult result) {
		if (result.hasErrors()) {
			throw new IllegalArgumentException(result.getAllErrors().toString());
		}		
		pesquisaApplicationService.salvar(command.getNome(), command.getConsulta(), command.getIndices());
	}
	
	@ApiOperation("Pesquisa avançada")
	@RequestMapping(value = "/criterios", method = RequestMethod.GET)
	public List<PesquisaAvancadaDto> recuperarCriterios() {		
		return pesquisaServiceFacade.recuperarAvancadas();
	}
	
}
