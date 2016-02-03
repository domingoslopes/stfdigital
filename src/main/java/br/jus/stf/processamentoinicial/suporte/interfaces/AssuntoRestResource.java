package br.jus.stf.processamentoinicial.suporte.interfaces;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.jus.stf.jurisprudencia.controletese.domain.model.AssuntoRepository;
import br.jus.stf.processamentoinicial.suporte.interfaces.dto.AssuntoDto;
import br.jus.stf.processamentoinicial.suporte.interfaces.dto.AssuntoDtoAssembler;
import br.jus.stf.shared.AssuntoId;

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
	public List<AssuntoDto> listar(@RequestParam("termo") String termo) {
		List<AssuntoDto> assuntos = new ArrayList<AssuntoDto>();
		
		if (NumberUtils.isNumber(termo)) {
			assuntos.add(assuntoDtoAssembler.toDto(assuntoRepository.findOne(new AssuntoId(termo))));
		} else {
			assuntos = assuntoRepository.findAssuntoByDescricao(termo.toUpperCase()).stream().map(assunto -> assuntoDtoAssembler.toDto(assunto)).collect(Collectors.toList());
		}
		
		return assuntos;
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public AssuntoDto consultar(@PathVariable String id) {
		return assuntoDtoAssembler.toDto(assuntoRepository.findOne(new AssuntoId(id)));
	}
}
