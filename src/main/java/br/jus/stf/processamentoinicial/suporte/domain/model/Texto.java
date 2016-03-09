package br.jus.stf.processamentoinicial.suporte.domain.model;

import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Table;

import org.apache.commons.lang3.Validate;

import br.jus.stf.shared.DocumentoId;
import br.jus.stf.shared.TextoId;
import br.jus.stf.shared.stereotype.Entity;

@javax.persistence.Entity
@Table(name = "TEXTO", schema = "CORPORATIVO")
public class Texto implements Entity<Texto, TextoId> {

	@EmbeddedId
	private TextoId id;

	@Embedded
	private DocumentoId documento;

	Texto() {

	}

	public Texto(final TextoId id, final DocumentoId documento) {
		Validate.notNull(id, "texto.id.required");
		Validate.notNull(documento, "texto.documento.required");

		this.id = id;
		this.documento = documento;
	}

	@Override
	public TextoId id() {
		return id;
	}

	public DocumentoId documento() {
		return documento;
	}

	@Override
	public boolean sameIdentityAs(Texto other) {
		return other != null && this.id.sameValueAs(other.id);
	}

}
