package br.jus.stf.plataforma.documentos.interfaces.dto;

import org.springframework.stereotype.Component;

import br.jus.stf.shared.DocumentoId;

/**
 * Converte um documento em um dto
 * 
 * @author Lucas.Rodrigues
 *
 */
@Component
public class DocumentoTemporarioDtoAssembler {

	public DocumentoTemporarioDto toDto(String tempId, DocumentoId documentoId) {
		return new DocumentoTemporarioDto(tempId, documentoId.toLong());
	}
	
}
