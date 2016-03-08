package br.jus.stf.plataforma.documentos.domain.model;

import org.apache.commons.lang3.Validate;

import br.jus.stf.shared.stereotype.ValueObject;

public class Tag implements ValueObject<Tag> {

	private static final long serialVersionUID = 1L;

	private String nome;

	public Tag(final String nome) {
		Validate.notBlank(nome, "tag.nome.required");
		
		this.nome = nome;
	}

	public String nome() {
		return nome;
	}

	@Override
	public boolean sameValueAs(Tag other) {
		return other != null && nome.equals(other.nome);
	}

}
