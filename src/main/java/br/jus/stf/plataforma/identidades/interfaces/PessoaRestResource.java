package br.jus.stf.plataforma.identidades.interfaces;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.jus.stf.plataforma.identidades.interfaces.commands.CadastrarPessoasCommand;
import br.jus.stf.plataforma.identidades.interfaces.dto.PessoaDto;
import br.jus.stf.plataforma.identidades.interfaces.facade.PessoaServiceFacade;

/**
 * Api rest de consulta e cadastro de pessoa
 * 
 * @author Lucas Rodrigues
 */
@RestController
@RequestMapping("/api/pessoas")
public class PessoaRestResource {

	@Autowired
	private PessoaServiceFacade pessoaServiceFacade;
	
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
	public List<PessoaDto> cadastrar(@RequestBody CadastrarPessoasCommand command) {
		Set<ConstraintViolation<CadastrarPessoasCommand>> result = validator.validate(command);
		if (!result.isEmpty()) {
			throw new IllegalArgumentException(result.toString());
		}
		return pessoaServiceFacade.cadastrarPessoas(command.getNomes());
	}
	
	/**
	 * Pesquisa uma pessoa pela identificacao
	 * 
	 * @param pessoaId
	 * @return
	 */
	@RequestMapping(value = "/{pessoaId}", method = RequestMethod.GET)
	public PessoaDto pesquisar(@PathVariable("pessoaId") Long pessoaId) {
		return pessoaServiceFacade.pesquisarPessoa(pessoaId);
	}
	
}