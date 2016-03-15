package br.jus.stf.plataforma.pesquisas.interfaces;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.jus.stf.plataforma.pesquisas.interfaces.command.AtualizarColecaoCommand;
import br.jus.stf.plataforma.pesquisas.interfaces.command.AtualizarCommand;
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
	@ResponseStatus(HttpStatus.CREATED)
	public void indexar(@RequestBody @Valid IndexarCommand command, BindingResult result) throws Exception {
		
		if (result.hasErrors()) {
			throw new IllegalArgumentException(result.getAllErrors().toString());
		}
		indexadorServiceFacade.indexar(command.getId(), command.getTipo(), command.getIndice(), command.getObjeto());
	}
	
	@ApiOperation("Atualiza objetos para pesquisa")
	@RequestMapping(value = "/documentos", method = RequestMethod.PUT)
	public void atualizar(@RequestBody @Valid AtualizarCommand command, BindingResult result) throws Exception {
		
		if (result.hasErrors()) {
			throw new IllegalArgumentException(result.getAllErrors().toString());
		}
		indexadorServiceFacade.atualizar(command.getId(), command.getTipo(), command.getIndice(), command.getObjeto());
	}
	
	@ApiOperation("Atualiza campos de um item de uma coleção")
	@RequestMapping(value = "/documentos/colecao", method = RequestMethod.PUT)
	public void atualizarColecao(@RequestBody @Valid AtualizarColecaoCommand command, BindingResult result) throws Exception {
		
		if (result.hasErrors()) {
			throw new IllegalArgumentException(result.getAllErrors().toString());
		}
		indexadorServiceFacade.atualizarColecao(command.getId(), command.getTipo(), command.getIndice(),
				command.getCampoColecao(), command.getExpressaoId(), command.getIdItem(), command.getObjeto());
	}

}
