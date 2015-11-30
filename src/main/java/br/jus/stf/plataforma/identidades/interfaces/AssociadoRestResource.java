package br.jus.stf.plataforma.identidades.interfaces;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.jus.stf.plataforma.identidades.application.AssociadoApplicationService;
import br.jus.stf.plataforma.identidades.interfaces.commands.AssociadoCommand;

/**
 * API responsável por operações relacioandas aos associados de órgãos.
 * 
 * @author Anderson.Araujo
 *
 * @since 27.11.2015
 */
@RestController
@RequestMapping("/api/associado")
public class AssociadoRestResource {
	
	@Autowired
	private AssociadoApplicationService associadoApplicationService;
	
	/**
	 * Cadastra um novo associado a  um detrrminado órgão.
	 */
	@RequestMapping(value="", method = RequestMethod.POST)
	public void cadastrar(@RequestBody @Valid AssociadoCommand command, BindingResult result ){
		
		if (result.hasErrors()){
			throw new IllegalArgumentException(result.getAllErrors().toString());
		}
		
		this.associadoApplicationService.cadastrar(command.getIdOrgao(), command.getCPF(), command.getNome(), command.getTipoAssociacao(), command.getCargo());
	}
}
