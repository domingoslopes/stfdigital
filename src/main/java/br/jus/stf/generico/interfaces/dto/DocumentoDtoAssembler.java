package br.jus.stf.generico.interfaces.dto;

import org.springframework.stereotype.Component;

import br.jus.stf.shared.domain.model.DocumentoId;

/**
 * Converte um documento em um dto
 * 
 * @author Lucas.Rodrigues
 *
 */
@Component
public class DocumentoDtoAssembler {

	public DocumentoDto toDto(String tempId, DocumentoId documentoId) {
		return new DocumentoDto(tempId, documentoId.toLong());
	}
	
}
