package br.jus.stf.generico.interfaces;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.jus.stf.generico.interfaces.commands.CadastrarPessoasCommand;
import br.jus.stf.generico.interfaces.dto.PessoaDto;
import br.jus.stf.generico.interfaces.facade.GenericoServiceFacade;
import br.jus.stf.shared.domain.model.PessoaId;

/**
 * Api rest de consulta e cadastro de pessoa
 * 
 * @author Lucas.Rodrigues
 *
 */
@RestController
@RequestMapping("/api/pessoas")
public class PessoaRestResource {

	@Autowired
	private GenericoServiceFacade genericoServiceFacade;
	
	@Autowired
	private Validator validator;
	
	/**
	 * Retorna os ids na mesma ordem de envio dos nomes
	 * 
	 * @param command
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.POST)
	//TODO: Substituir a validação pelo @Valid e BindingResult
	public Set<PessoaId> cadastrar(@RequestBody CadastrarPessoasCommand command) {
		Set<ConstraintViolation<CadastrarPessoasCommand>> result = validator.validate(command);
		if (!result.isEmpty()) {
			throw new IllegalArgumentException(result.toString());
		}
		return genericoServiceFacade.cadastrarPessoas(command.getNomes());
	}
	
	/**
	 * Pesquisa uma pessoa pela identificacao
	 * 
	 * @param pessoaId
	 * @return
	 */
	@RequestMapping(value = "/{pessoaId}", method = RequestMethod.GET)
	public PessoaDto pesquisar(@PathVariable("pessoaId") Long pessoaId) {
		return genericoServiceFacade.pesquisarPessoa(pessoaId);
	}
	
}
