package br.jus.stf.processamentoinicial.autuacao.domain.model;

import br.jus.stf.shared.stereotype.ValueObject;


/**
 * @author Lucas.Rodrigues
 *
 */
public enum FormaRecebimento implements ValueObject<FormaRecebimento> {

	BALCAO("Balcão"),
	SEDEX("Sedex"),
	MALOTE("Malote"),
	FAX("Fax"),
	EMAIL("E-mail");
	
	private String descricao;
	
	private FormaRecebimento(final String descricao) {
		this.descricao = descricao;
	}
	
	public String descricao() {
		return descricao;
	}
	
	public boolean sameValueAs(final FormaRecebimento other) {
		return this.equals(other);
	}
	
}
