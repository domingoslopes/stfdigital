package br.jus.stf.plataforma.pesquisas.interfaces;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.jus.stf.plataforma.pesquisas.interfaces.command.CriarIndiceCommand;
import br.jus.stf.plataforma.pesquisas.interfaces.command.IndexarCommand;
import br.jus.stf.plataforma.pesquisas.interfaces.facade.IndexadorServiceFacade;

import com.wordnik.swagger.annotations.ApiOperation;

/**
 * Controlador com API Rest para indexação
 * 
 * @author Lucas.Rodrigues
 *
 */
@RestController
@RequestMapping("/api/indices")
public class IndexadorRestResource {
	
	@Autowired
	private IndexadorServiceFacade indexadorServiceFacade;
	
	@ApiOperation("Indexa objetos para pesquisa")
	@RequestMapping(value = "", method = RequestMethod.POST)
	public void criarIndice(@RequestBody @Valid CriarIndiceCommand command, BindingResult result) throws Exception {
		
		if (result.hasErrors()) {
			throw new IllegalArgumentException(result.getAllErrors().toString());
		}
		indexadorServiceFacade.criarIndice(command.getIndice(), command.getConfiguracao());
	}
	
	@ApiOperation("Indexa objetos para pesquisa")
	@RequestMapping(value = "/{indice}/existe", method = RequestMethod.POST)
	public boolean existeIndice(@PathVariable String indice) {
		return indexadorServiceFacade.existeIndice(indice);
	}

	@ApiOperation("Indexa objetos para pesquisa")
	@RequestMapping(value = "/{indice}", method = RequestMethod.POST)
	public void indexar(@PathVariable String indice, @RequestBody @Valid IndexarCommand command, BindingResult result) throws Exception {
		
		if (result.hasErrors()) {
			throw new IllegalArgumentException(result.getAllErrors().toString());
		}
		indexadorServiceFacade.indexar(indice, command.getTipo(), command.getId(), command.getObjeto());
	}

}
