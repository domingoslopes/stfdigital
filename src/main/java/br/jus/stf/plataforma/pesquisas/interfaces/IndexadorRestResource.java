package br.jus.stf.plataforma.pesquisas.interfaces;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
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
	@RequestMapping(value = "/documentos", method = RequestMethod.POST)
	public void indexar(@RequestBody @Valid IndexarCommand command, BindingResult result) throws Exception {
		
		if (result.hasErrors()) {
			throw new IllegalArgumentException(result.getAllErrors().toString());
		}
		indexadorServiceFacade.indexar(command.getId(), command.getTipo(), command.getIndice(), command.getObjeto());
	}

}
