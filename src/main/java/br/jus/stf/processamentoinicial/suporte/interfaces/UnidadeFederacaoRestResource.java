package br.jus.stf.processamentoinicial.suporte.interfaces;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.jus.stf.processamentoinicial.suporte.interfaces.dto.UnidadeFederacaoDto;

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
	
	@ApiOperation("Retorna uma lista contendo todas as unidades da federação.")
	@RequestMapping(value = "", method = RequestMethod.GET )
	public List<UnidadeFederacaoDto> listar(){
		return null;
	}
}
