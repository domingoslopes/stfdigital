package br.jus.stf.generico.domain.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Objects;

import org.apache.commons.lang3.Validate;

import br.jus.stf.shared.domain.stereotype.ValueObject;

/**
 * Documento ainda não persistido
 * 
 * @author Lucas.Rodrigues
 *
 */
public class DocumentoTemporario implements ValueObject<DocumentoTemporario> {

	private static final long serialVersionUID = -3725370010702512231L;
	
	private String tempId;
	private FileInputStream stream;
	private Long tamanho;
	
	public DocumentoTemporario(String tempId) {
		Validate.notBlank(tempId);
		
		this.tempId = tempId;
		File arquivo = new File(tempId);
		try {
			this.stream = new FileInputStream(arquivo);
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException(e);
		}
		this.tamanho = arquivo.length();
	}
	
	public String tempId() {
		return tempId;
	}
	
	public Long tamanho() {
		return tamanho;
	}
	
	public FileInputStream stream() {
		return stream;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tempId == null) ? 0 : tempId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		
		DocumentoTemporario other = (DocumentoTemporario) obj;
		return sameValueAs(other);
	}
	
	@Override
	public boolean sameValueAs(DocumentoTemporario other) {
		return other != null && Objects.equals(this.tempId, other.tempId);
	}

}
