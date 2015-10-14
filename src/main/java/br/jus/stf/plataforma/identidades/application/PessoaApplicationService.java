package br.jus.stf.plataforma.identidades.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.plataforma.identidades.domain.model.Pessoa;
import br.jus.stf.plataforma.identidades.domain.model.PessoaRepository;
import br.jus.stf.shared.PessoaId;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 25.09.2015
 */
@Component
public class PessoaApplicationService {

	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private PessoaApplicationEvent pessoaApplicationEvent;

	/**
	 * Cadastra pessoas
	 * 
	 * @param pessoas
	 * @return
	 */
	public List<Pessoa> cadastrarPessoas(List<String> pessoas) {
		return pessoas.stream()
				.map(nome -> {
					PessoaId id = pessoaRepository.nextId();
					Pessoa pessoa = new Pessoa(id, nome);
					pessoaRepository.save(pessoa);
					pessoaApplicationEvent.pessoaCadastrada(pessoa);
					return pessoa;
				})
				.collect(Collectors.toList());
	}

}
