package br.jus.stf.processamentoinicial.autuacao.domain.model;

import org.apache.commons.lang3.Validate;

import br.jus.stf.shared.DocumentoTemporarioId;
import br.jus.stf.shared.stereotype.ValueObject;

public class PecaTemporaria implements ValueObject<PecaTemporaria> {
	
	private static final long serialVersionUID = 1L;
	
	private DocumentoTemporarioId documentoTemporario;
	
	private TipoPeca tipo;
	
	private String descricao;
	
	public PecaTemporaria(final DocumentoTemporarioId documentoTemporario, final TipoPeca tipo, final String descricao) {
		Validate.notNull(documentoTemporario, "pecaTemporaria.documentoTemporario.required");
		Validate.notNull(tipo, "pecaTemporaria.tipo.required");
		Validate.notBlank(descricao, "pecaTemporaria.descricao.required");
		
		this.documentoTemporario = documentoTemporario;
		this.tipo = tipo;
		this.descricao = descricao;
	}
	
	public TipoPeca tipo() {
		return this.tipo;
	}
	
	public String descricao() {
		return this.descricao;
	}
	
	public DocumentoTemporarioId documentoTemporario() {
		return this.documentoTemporario;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((documentoTemporario == null) ? 0 : documentoTemporario.hashCode());
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
	    if (this == obj) return true;
	    if (obj == null || !(obj instanceof PecaTemporaria)) return false;
	    
	    final PecaTemporaria other = (PecaTemporaria) obj;
	    return sameValueAs(other);
	}
	
	@Override
	public boolean sameValueAs(final PecaTemporaria other){
		return other != null && this.documentoTemporario.sameValueAs(other.documentoTemporario) && this.tipo.sameValueAs(other.tipo);
	}
	
	PecaTemporaria() {
		
	}

}
