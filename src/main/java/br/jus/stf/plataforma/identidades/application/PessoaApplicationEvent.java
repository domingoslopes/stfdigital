package br.jus.stf.plataforma.identidades.application;

import br.jus.stf.plataforma.identidades.domain.model.Pessoa;

/**
 * Interface que define os eventos publicados pela aplicação
 * 
 * @author Lucas Rodrigues
 */
public interface PessoaApplicationEvent {
	
	public void pessoaCadastrada(Pessoa pessoa);
	
}
