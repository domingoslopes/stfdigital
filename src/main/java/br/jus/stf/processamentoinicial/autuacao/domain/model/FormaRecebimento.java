package br.jus.stf.processamentoinicial.autuacao.domain.model;

import br.jus.stf.shared.stereotype.ValueObject;


/**
 * @author Lucas.Rodrigues
 *
 */
public enum FormaRecebimento implements ValueObject<FormaRecebimento> {

	BALCAO("Balcão", false),
	SEDEX("Sedex", true),
	MALOTE("Malote", false),
	FAX("Fax", false),
	EMAIL("E-mail", false);
	
	private String descricao;
	private boolean exigeNumero;
	
	private FormaRecebimento(final String descricao, final boolean exigeNumero) {
		this.descricao = descricao;
		this.exigeNumero = exigeNumero;
	}
	
	public String descricao() {
		return descricao;
	}
	
	public boolean exigeNumero() {
		return exigeNumero;
	}
	
	@Override
	public boolean sameValueAs(final FormaRecebimento other) {
		return this.equals(other);
	}
	
}
