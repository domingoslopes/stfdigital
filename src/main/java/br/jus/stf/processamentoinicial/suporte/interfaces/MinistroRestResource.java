package br.jus.stf.processamentoinicial.suporte.interfaces;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.jus.stf.processamentoinicial.suporte.domain.model.MinistroRepository;
import br.jus.stf.processamentoinicial.suporte.interfaces.dto.MinistroDto;
import br.jus.stf.processamentoinicial.suporte.interfaces.dto.MinistroDtoAssembler;

import com.wordnik.swagger.annotations.ApiOperation;

/**
 * Api REST de consulta de Ministros
 * 
 * @author Lucas Rodrigues
 * @author Rodrigo Barreiros
 */
@RestController
@RequestMapping("/api/ministros")
public class MinistroRestResource {
	
	@Autowired
	private MinistroDtoAssembler ministroDtoAssembler;
	
	@Autowired
	private MinistroRepository ministroRepository;
	
	@ApiOperation(value = "Retorna a lista com todos o Ministros ativos")
	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<MinistroDto> listar() {
		return ministroRepository.findAll().stream().map(ministro -> ministroDtoAssembler.toDto(ministro)).collect(Collectors.toList());
	}
	
}
