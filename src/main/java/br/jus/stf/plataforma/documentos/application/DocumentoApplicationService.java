package br.jus.stf.plataforma.documentos.application;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.plataforma.documentos.domain.model.DocumentoRepository;
import br.jus.stf.plataforma.documentos.domain.model.DocumentoTemporario;
import br.jus.stf.shared.DocumentoId;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 25.09.2015
 */
@Component
public class DocumentoApplicationService {

	@Autowired
	private DocumentoRepository documentoRepository;

	/**
	 * Salva os documentos temporários no repositório
	 * 
	 * @param documentosTemporarios
	 * @return
	 */
	public Map<String, DocumentoId> salvarDocumentos(List<String> documentosTemporarios) {
		return documentosTemporarios.stream()
				.collect(Collectors.toMap(docTemp -> docTemp, docTemp -> documentoRepository.save(docTemp)));
	}

	/**
	 * @param documentoTemporario
	 * @return
	 */
	public String salvarDocumentoTemporario(DocumentoTemporario documentoTemporario) {
		return documentoRepository.storeTemp(documentoTemporario);
	}

}
