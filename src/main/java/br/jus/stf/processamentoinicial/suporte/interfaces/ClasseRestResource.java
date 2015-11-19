package br.jus.stf.processamentoinicial.suporte.interfaces;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.jus.stf.processamentoinicial.suporte.domain.model.ClasseRepository;
import br.jus.stf.processamentoinicial.suporte.interfaces.dto.ClasseDto;
import br.jus.stf.processamentoinicial.suporte.interfaces.dto.ClasseDtoAssembler;

/**
 * Api REST de consulta de classes
 * 
 * @author Lucas Rodrigues
 * @author Rodrigo Barreiros
 */
@RestController
@RequestMapping("/api/classes")
public class ClasseRestResource {

	@Autowired
	private ClasseDtoAssembler classeDtoAssembler;

	@Autowired
	private ClasseRepository classeRepository;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<ClasseDto> listar() {
		return classeRepository.findAll().stream().map(classe -> classeDtoAssembler.toDto(classe)).collect(Collectors.toList());
	}
	
}
