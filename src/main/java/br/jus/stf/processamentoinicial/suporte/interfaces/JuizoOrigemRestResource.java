package br.jus.stf.processamentoinicial.suporte.interfaces;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.jus.stf.processamentoinicial.suporte.interfaces.dto.JuizoOrigemDto;

import com.wordnik.swagger.annotations.ApiOperation;

/**
 * Serviço REST usado para retornar informações sobre juízo de origem.
 * 
 * @author Anderson.Araujo
 * @since 17.03.2016
 *
 */
@RestController
@RequestMapping("/api/juizo-origem")
public class JuizoOrigemRestResource {
	@ApiOperation("Retorna uma lista de juízos de origem")
	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<JuizoOrigemDto> listar(){
		return null;
	}
}
