package br.jus.stf.processamentoinicial.autuacao.infra;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;

import br.jus.stf.plataforma.identidades.interfaces.PessoaRestResource;
import br.jus.stf.plataforma.identidades.interfaces.commands.CadastrarPessoasCommand;
import br.jus.stf.processamentoinicial.autuacao.domain.PessoaAdapter;
import br.jus.stf.shared.PessoaId;

/**
 * @author Lucas Rodrigues
 */
@Component
public class PessoaRestAdapter implements PessoaAdapter {
	
	@Autowired
	private PessoaRestResource pessoaRestResource;

	@Override
	public Set<PessoaId> cadastrarPessoas(List<String> pessoas) {
		CadastrarPessoasCommand command = new CadastrarPessoasCommand();
		command.setNomes(pessoas);
		return pessoaRestResource.cadastrar(command, new BeanPropertyBindingResult(command, "cadastrarPessoasCommand")).stream()
				.map(dto -> new PessoaId(dto.getId()))
				.collect(Collectors.toCollection(
						() -> new LinkedHashSet<PessoaId>()));
	}

}
