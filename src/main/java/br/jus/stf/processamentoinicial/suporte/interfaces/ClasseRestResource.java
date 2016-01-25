package br.jus.stf.processamentoinicial.suporte.interfaces;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.jus.stf.processamentoinicial.suporte.domain.model.ClasseRepository;
import br.jus.stf.processamentoinicial.suporte.interfaces.dto.ClasseDto;
import br.jus.stf.processamentoinicial.suporte.interfaces.dto.ClasseDtoAssembler;
import br.jus.stf.processamentoinicial.suporte.interfaces.dto.PreferenciaDto;
import br.jus.stf.processamentoinicial.suporte.interfaces.dto.PreferenciaDtoAssembler;
import br.jus.stf.shared.ClasseId;

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
	private PreferenciaDtoAssembler preferenciaDtoAssembler;

	@Autowired
	private ClasseRepository classeRepository;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<ClasseDto> listar() {
		return classeRepository.findAll().stream().map(classe -> classeDtoAssembler.toDto(classe)).collect(Collectors.toList());
	}
	
	@RequestMapping(value = "/{id}/preferencias", method = RequestMethod.GET)
	public List<PreferenciaDto> consultarPorClasse(@PathVariable String id) {
		ClasseId classeId = new ClasseId(id);
		return classeRepository.findPreferenciaByClasse(classeId).stream().map(pref -> preferenciaDtoAssembler.toDto(pref)).collect(Collectors.toList());
	}
	
}
