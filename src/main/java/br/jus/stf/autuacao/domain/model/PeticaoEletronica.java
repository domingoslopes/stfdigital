package br.jus.stf.autuacao.domain.model;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.CollectionTable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;

import org.apache.commons.lang3.Validate;

import br.jus.stf.shared.domain.model.ClasseId;
import br.jus.stf.shared.domain.model.DocumentoId;

/**
 * @author Lucas.Rodrigues
 *
 */
@Entity
@DiscriminatorValue("ELETRONICA")
public class PeticaoEletronica extends Peticao {

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "PETICAO_DOCUMENTO", schema = "AUTUACAO",
			joinColumns = @JoinColumn(name = "SEQ_PETICAO"))
	private Set<DocumentoId> documentos = new TreeSet<DocumentoId>(
			(d1, d2) -> d1.toLong().compareTo(d2.toLong()));
	
	PeticaoEletronica(final ClasseId classeSugerida, final Set<PartePeticao> partes, final Set<DocumentoId> documentos) {
		
		Validate.notNull(classeSugerida, "peticao.classeSugerida.required");
		Validate.notEmpty(partes, "peticao.partes.notEmpty");
		Validate.notEmpty(documentos, "peticao.documentos.notEmpty");
	
		this.classeSugerida = classeSugerida;
		this.partes.addAll(partes);
		this.documentos.addAll(documentos);
	}
	
	public Set<DocumentoId> documentos(){
		return Collections.unmodifiableSet(this.documentos);
	}

	/**
	 * 
	 * @param documento
	 */
	public boolean adicionarDocumento(final DocumentoId documento) {
		Validate.notNull(documento, "peticao.documento.required");
	
		return this.documentos.add(documento);
	}
	
	/**
	 * 
	 * @param documento
	 */
	public boolean removerDocumento(final DocumentoId documento) {
		Validate.notNull(documento, "peticao.documento.required");
	
		return this.documentos.remove(documento);
	}
	
}
