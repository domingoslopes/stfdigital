package br.jus.stf.autuacao.interfaces.commands;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 23.06.2015
 */
public class AutuarPeticaoCommand {

	private String classificacao;

	public AutuarPeticaoCommand() {
	}

	public AutuarPeticaoCommand(String classificacao) {
		this.classificacao = classificacao;
	}

	public String getClassificacao() {
		return classificacao;
	}

}
