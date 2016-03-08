package br.jus.stf.plataforma.documentos.domain.model;

import org.apache.commons.lang3.Validate;

import br.jus.stf.shared.stereotype.ValueObject;

/**
 * 
 * @author Tomas.Godoi
 *
 */
public class SubstituicaoTag implements ValueObject<SubstituicaoTag> {

	private static final long serialVersionUID = 1L;

	private String nome;

	private String valor;

	public SubstituicaoTag(final String nome, final String valor) {
		Validate.notBlank(nome, "substituicaoTag.nome.required");
		Validate.notBlank(valor, "substituicaoTag.valor.required");

		this.nome = nome;
		this.valor = valor;
	}

	public String nome() {
		return nome;
	}

	public String valor() {
		return valor;
	}

	@Override
	public boolean sameValueAs(SubstituicaoTag other) {
		return other != null && nome.equals(other.nome) && valor.equals(other.valor);
	}

}
