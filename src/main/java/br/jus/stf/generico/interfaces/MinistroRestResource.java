package br.jus.stf.generico.interfaces;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.jus.stf.generico.interfaces.dto.MinistroDto;
import br.jus.stf.generico.interfaces.facade.GenericoServiceFacade;

import com.wordnik.swagger.annotations.ApiOperation;

/**
 * Api REST de consulta de Ministros
 * 
 * @author Lucas.Rodrigues
 *
 */
@RestController
@RequestMapping("/api/ministros")
public class MinistroRestResource {
	
	@Autowired
	private GenericoServiceFacade genericoServiceFacade;
	
	@ApiOperation(value = "Retorna a lista com todos o Ministros ativos")
	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<MinistroDto> listar() {
		return genericoServiceFacade.listarMinistros();
	}
	
}
