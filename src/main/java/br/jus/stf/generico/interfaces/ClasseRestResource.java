package br.jus.stf.generico.interfaces;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.jus.stf.generico.interfaces.dto.ClasseDto;
import br.jus.stf.generico.interfaces.dto.ClasseDtoAssembler;
import br.jus.stf.generico.interfaces.facade.GenericoServiceFacade;

/**
 * Api REST de consulta de classes
 * 
 * @author Lucas.Rodrigues
 *
 */
@RestController
@RequestMapping("/api/classes")
public class ClasseRestResource {

	@Autowired
	private GenericoServiceFacade genericoServiceFacade;
	
	@Autowired
	private ClasseDtoAssembler classeDtoAssembler;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<ClasseDto> listar() {
		
		return genericoServiceFacade.listarClasses();
	}
	
}
