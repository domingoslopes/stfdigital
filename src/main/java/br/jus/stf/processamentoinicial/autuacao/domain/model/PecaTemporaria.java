package br.jus.stf.processamentoinicial.autuacao.domain.model;

import java.util.Optional;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import br.jus.stf.processamentoinicial.suporte.domain.model.Situacao;
import br.jus.stf.processamentoinicial.suporte.domain.model.TipoPeca;
import br.jus.stf.processamentoinicial.suporte.domain.model.Visibilidade;
import br.jus.stf.shared.DocumentoTemporarioId;
import br.jus.stf.shared.stereotype.ValueObject;

public class PecaTemporaria implements ValueObject<PecaTemporaria> {
	
	private static final long serialVersionUID = 1L;
	
	private DocumentoTemporarioId documentoTemporario;
	
	private TipoPeca tipo;
	
	private String descricao;
	
	private Visibilidade visibilidade;
	
	private Situacao situacao;
	
	public PecaTemporaria(final DocumentoTemporarioId documentoTemporario, final TipoPeca tipo, final String descricao,
			final Visibilidade visibilidade, final Situacao situacao) {
		Validate.notNull(documentoTemporario, "pecaTemporaria.documentoTemporario.required");
		Validate.notNull(tipo, "pecaTemporaria.tipo.required");
		Validate.notBlank(descricao, "pecaTemporaria.descricao.required");
		Validate.notNull(visibilidade, "pecaTemporaria.visibilidade.required");
		Validate.notNull(situacao, "pecaTemporaria.situacao.required");
		
		this.documentoTemporario = documentoTemporario;
		this.tipo = tipo;
		this.descricao = descricao;
		this.visibilidade = Optional.ofNullable(visibilidade).orElse(Visibilidade.PUBLICO);
		this.situacao = Optional.ofNullable(situacao).orElse(Situacao.JUNTADA);
	}
	
	public TipoPeca tipo() {
		return tipo;
	}
	
	public String descricao() {
		return descricao;
	}
	
	public DocumentoTemporarioId documentoTemporario() {
		return documentoTemporario;
	}
	
	public Visibilidade visibilidade() {
		return visibilidade;
	}
	
	public Situacao situacao() {
		return situacao;
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(descricao).append(documentoTemporario).append(tipo)
				.append(visibilidade).hashCode();
	}

	@Override
	public boolean equals(Object obj) {
	    if (this == obj) {
	    	return true;
	    }
	    
	    if (obj == null || !(obj instanceof PecaTemporaria)) {
	    	return false;
	    }
	    
	    final PecaTemporaria other = (PecaTemporaria) obj;
	    return sameValueAs(other);
	}
	
	@Override
	public boolean sameValueAs(final PecaTemporaria other){
		return other != null && descricao.equals(other.descricao) && documentoTemporario.sameValueAs(other.documentoTemporario)
				&& tipo.sameValueAs(other.tipo) && visibilidade.sameValueAs(other.visibilidade);
	}

}
