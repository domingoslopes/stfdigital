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
import br.jus.stf.plataforma.documentos.domain.model.Documento;
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
		Documento documento = documentoRepository.findOne(id);
		return dividirDocumento(documento, intervalos);
	}
	
	private List<DocumentoId> dividirDocumento(Documento documento, List<Range<Integer>> intervalos) {
		if (!intervalosSaoContidosPeloDocumento(intervalos, documento)) {
			throw new IllegalArgumentException("Há intervalos não contidos no documento.");
		}
		ConteudoDocumento conteudo = documentoRepository.download(documento.id());
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
	
	private boolean intervalosSaoContidosPeloDocumento(List<Range<Integer>> intervalos, Documento documento) {
		for (Range<Integer> intervalo : intervalos) {
			if (intervalo.getMinimum() <= 0 || intervalo.getMaximum() > documento.quantidadePaginas()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Divide um documento contiguamente como especificado pelos intervalos.
	 * 
	 * @param id
	 * @param intervalos
	 * @return
	 */
	public List<DocumentoId> dividirDocumentoContiguamente(DocumentoId id, List<Range<Integer>> intervalos) {
		Documento documento = documentoRepository.findOne(id);
		if (intervalosSaoContiguos(intervalos, documento)) {
			return dividirDocumento(documento, intervalos);
		} else {
			throw new IllegalArgumentException("Intervalos não são contíguos");
		}
	}

	private boolean intervalosSaoContiguos(List<Range<Integer>> intervalos, Documento documento) {
		if (intervalos.get(0).getMinimum() <= 0 || intervalos.get(intervalos.size() - 1).getMaximum() != documento.quantidadePaginas()) {
			return false;
		} else {
			Range<Integer> anterior = intervalos.get(0);
			for (int i = 1; i < intervalos.size(); i++) {
				Range<Integer> proximo = intervalos.get(i);
				if (anterior.getMaximum() + 1 != proximo.getMinimum()) {
					return false;
				}
				anterior = proximo;
			}
		}
		return true;
	}

	/**
	 * Une os documentos especificados em um só.
	 * 
	 * @param documentos
	 * @return
	 */
	public DocumentoId unirDocumentos(List<DocumentoId> documentos) {
		List<ConteudoDocumento> conteudos = documentos.stream().map(d -> documentoRepository.download(d)).collect(Collectors.toList());
		DocumentoTemporario temp = documentoService.unirConteudos(conteudos);
		DocumentoTemporarioId tempId = new DocumentoTemporarioId(documentoRepository.storeTemp(temp));
		DocumentoId novoDocumento = documentoRepository.save(tempId);
		return novoDocumento;
	}

}
