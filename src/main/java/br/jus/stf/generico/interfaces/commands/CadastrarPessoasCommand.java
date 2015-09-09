package br.jus.stf.generico.interfaces.commands;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Comando para cadastro de pessoa
 * 
 * @author Lucas.Rodrigues
 *
 */
public class CadastrarPessoasCommand {

	@NotEmpty
	private List<String> nomes;

	/**
	 * @return the nomes
	 */
	public List<String> getNomes() {
		return nomes;
	}

	/**
	 * @param nomes the nomes to set
	 */
	public void setNomes(List<String> nomes) {
		this.nomes = nomes;
	}
	
}
