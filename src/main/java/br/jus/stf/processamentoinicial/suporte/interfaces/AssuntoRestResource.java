package br.jus.stf.processamentoinicial.suporte.interfaces;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.jus.stf.jurisprudencia.controletese.domain.model.AssuntoRepository;
import br.jus.stf.processamentoinicial.suporte.interfaces.dto.AssuntoDto;
import br.jus.stf.processamentoinicial.suporte.interfaces.dto.AssuntoDtoAssembler;

/**
 * Api REST de consulta de assuntos.
 * 
 * @author Anderson.Araújo
 */
@RestController
@RequestMapping("/api/assuntos")
public class AssuntoRestResource {
	
	@Autowired
	private AssuntoRepository assuntoRepository;
	
	@Autowired
	private AssuntoDtoAssembler assuntoDtoAssembler;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<AssuntoDto> listar() {
		return assuntoRepository.findAll().stream().map(assunto -> assuntoDtoAssembler.toDto(assunto)).collect(Collectors.toList());
	}
}
