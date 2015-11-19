package br.jus.stf.processamentoinicial.autuacao.domain;

import java.util.List;
import java.util.Set;

import br.jus.stf.shared.PessoaId;

/**
 * Interface para comunicação com o domínio genérico
 * 
 * @author Lucas Rodrigues
 */
public interface PessoaAdapter {

	/**
	 * Cadastra as pessoas e recupera os ids na ordem de envio
	 * 
	 * @param pessoas
	 * @return a lista de ids de pessoas
	 */
	public Set<PessoaId> cadastrarPessoas(List<String> pessoas);
	
}
