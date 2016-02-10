package br.jus.stf.plataforma.documentos.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.commons.lang3.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.jus.stf.plataforma.documentos.domain.DocumentoService;
import br.jus.stf.plataforma.documentos.domain.model.ConteudoDocumento;
import br.jus.stf.plataforma.documentos.domain.model.DocumentoRepository;
import br.jus.stf.plataforma.documentos.domain.model.DocumentoTemporario;
import br.jus.stf.shared.DocumentoId;
import br.jus.stf.shared.DocumentoTemporarioId;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 25.09.2015
 */
@Service
@Transactional
public class DocumentoApplicationService {

	@Autowired
	private DocumentoRepository documentoRepository;
	
	@Autowired
	private DocumentoService documentoService;

	/**
	 * Salva os documentos temporários no repositório
	 * 
	 * @param documentosTemporarios
	 * @return
	 */
	public Map<String, DocumentoId> salvarDocumentos(List<DocumentoTemporarioId> documentosTemporarios) {
		return documentosTemporarios.stream()
				.collect(Collectors.toMap(docTemp -> docTemp.toString(), docTemp -> documentoRepository.save(docTemp)));
	}

	/**
	 * @param documentoTemporario
	 * @return
	 */
	public String salvarDocumentoTemporario(DocumentoTemporario documentoTemporario) {
		return documentoRepository.storeTemp(documentoTemporario);
	}

	public void apagarDocumentosTemporarios(List<String> documentoTemporarioIds) {
		documentoTemporarioIds.stream()
			.forEach(tempId -> documentoRepository.removeTemp(tempId));
	}

	/**
	 * Divide um documento em documentos especificados pelos intervalos.
	 * 
	 * @param id
	 * @param intervalos
	 * @return
	 */
	public List<DocumentoId> dividirDocumento(DocumentoId id, List<Range<Integer>> intervalos) {
		ConteudoDocumento conteudo = documentoRepository.download(id);
		List<DocumentoId> documentosDivididos = new ArrayList<>();
		for (Range<Integer> intervalo : intervalos) {
			DocumentoTemporario temp = documentoService.dividirConteudo(conteudo, intervalo.getMinimum(),
			        intervalo.getMaximum());
			DocumentoTemporarioId tempId = new DocumentoTemporarioId(documentoRepository.storeTemp(temp));
			DocumentoId novoDocumento = documentoRepository.save(tempId);
			documentosDivididos.add(novoDocumento);
		}
		return documentosDivididos;
	}

}
