package br.jus.stf.generico.domain.model;

import br.jus.stf.shared.domain.model.DocumentoId;
import br.jus.stf.shared.domain.stereotype.Entity;

/**
 * @author Rafael.Alencar
 * @version 1.0
 * @created 14-ago-2015 18:34:02
 */
public class Documento implements Entity<Documento> {

	private DocumentoId documentoId;
	private byte[] conteudo;

	public Documento(final DocumentoId documentoId, final byte[] conteudo){
		this.documentoId = documentoId;
		this.conteudo = conteudo;
	}

	public DocumentoId documentoId(){
		return documentoId;
	}

	public byte[] conteudo(){
		return conteudo;
	}
	
	public boolean sameIdentityAs(final Documento other) {
		return other != null && this.documentoId.sameValueAs(other.documentoId);
	}

}