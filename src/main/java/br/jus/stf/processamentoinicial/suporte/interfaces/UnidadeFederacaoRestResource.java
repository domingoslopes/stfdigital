package br.jus.stf.processamentoinicial.suporte.interfaces;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.jus.stf.processamentoinicial.suporte.domain.model.ProcedenciaGeograficaRepository;
import br.jus.stf.processamentoinicial.suporte.interfaces.dto.UnidadeFederacaoDto;
import br.jus.stf.processamentoinicial.suporte.interfaces.dto.UnidadeFederacaoDtoAssembler;

import com.wordnik.swagger.annotations.ApiOperation;

/**
 * Serviço REST para recuperar informações sobre as unidades da federação.
 * 
 * @author Anderson.Araujo
 * @since 17.03.2016
 *
 */
@RestController
@RequestMapping("/api/unidades-federacao")
public class UnidadeFederacaoRestResource {
	
	@Autowired
	private ProcedenciaGeograficaRepository procedenciaGeograficaRepository;
	
	@Autowired
	private UnidadeFederacaoDtoAssembler unidadeFederacaoDtoAssembler;
	
	@ApiOperation("Retorna uma lista contendo todas as unidades da federação.")
	@RequestMapping(value = "", method = RequestMethod.GET )
	public List<UnidadeFederacaoDto> listar(){
		return Optional.ofNullable(procedenciaGeograficaRepository.findAllUnidadeFederacao().stream().map(uf -> unidadeFederacaoDtoAssembler.toDo(uf))
				.collect(Collectors.toList())).orElseThrow(IllegalArgumentException::new);
	}
}
