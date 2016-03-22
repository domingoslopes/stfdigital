package br.jus.stf.processamentoinicial.suporte.interfaces;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.jus.stf.processamentoinicial.suporte.domain.model.ProcedenciaGeograficaRepository;
import br.jus.stf.processamentoinicial.suporte.domain.model.TribunalJuizoRepository;
import br.jus.stf.processamentoinicial.suporte.domain.model.UnidadeFederacao;
import br.jus.stf.processamentoinicial.suporte.interfaces.dto.TribunalJuizoDto;
import br.jus.stf.processamentoinicial.suporte.interfaces.dto.TribunalJuizoDtoAssembler;

import com.wordnik.swagger.annotations.ApiOperation;

/**
 * Serviço REST usado para retornar informações sobre juízo de origem.
 * 
 * @author Anderson.Araujo
 * @since 17.03.2016
 *
 */
@RestController
@RequestMapping("/api/tribunais-juizos")
public class TribunalJuizoRestResource {
	
	@Autowired
	private TribunalJuizoRepository tribunalJuizoRepository;
	
	@Autowired
	ProcedenciaGeograficaRepository procedenciaGeograficaRepository;
	
	@Autowired
	private TribunalJuizoDtoAssembler tribunalJuizoDtoAssembler; 
	
	@ApiOperation("Retorna uma lista de juízos de origem")
	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<TribunalJuizoDto> listar(){
		return Optional.ofNullable(tribunalJuizoRepository.findAll().stream().map(t -> tribunalJuizoDtoAssembler.toDo(t))
				.collect(Collectors.toList())).orElseThrow(IllegalArgumentException::new);
	}
	
	@ApiOperation("Retorna uma lista de juízos de origem")
	@RequestMapping(value = "/origens/{id}", method = RequestMethod.GET)
	public List<TribunalJuizoDto> consultarPorOrigem(@PathVariable("id") Long id){
		UnidadeFederacao uf = procedenciaGeograficaRepository.findOneUnidadeFederacao(id);
		return Optional.ofNullable(tribunalJuizoRepository.findByUnidadeFederacao(uf) .stream().map(t -> tribunalJuizoDtoAssembler.toDo(t))
				.collect(Collectors.toList())).orElseThrow(IllegalArgumentException::new);
	}
}
