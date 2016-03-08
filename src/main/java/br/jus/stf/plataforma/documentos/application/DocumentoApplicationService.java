package br.jus.stf.plataforma.documentos.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.commons.lang3.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.jus.stf.plataforma.documentos.domain.ControladorEdicaoDocumento;
import br.jus.stf.plataforma.documentos.domain.DocumentoService;
import br.jus.stf.plataforma.documentos.domain.model.ConteudoDocumento;
import br.jus.stf.plataforma.documentos.domain.model.Documento;
import br.jus.stf.plataforma.documentos.domain.model.DocumentoRepository;
import br.jus.stf.plataforma.documentos.domain.model.DocumentoTemporario;
import br.jus.stf.plataforma.documentos.infra.persistence.ConteudoDocumentoRepository;
import br.jus.stf.plataforma.documentos.infra.persistence.DocumentoTempRepository;
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
	
	@Autowired
	private DocumentoTempRepository documentoTempRepository;
	
	@Autowired
	private ConteudoDocumentoRepository conteudoDocumentoRepository;
	
	@Autowired
	private ControladorEdicaoDocumento controladorEdicaoDocumento;

	/**
	 * Salva os documentos temporários no repositório
	 * 
	 * @param documentosTemporarios
	 * @return
	 */
	public Map<String, DocumentoId> salvarDocumentos(List<DocumentoTemporarioId> documentosTemporarios) {
		return documentosTemporarios.stream()
				.collect(Collectors.toMap(docTemp -> docTemp.toString(), docTemp -> salvar(docTemp)));
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
	 * Divide um documento.
	 * 
	 * @param id
	 * @param intervalos
	 * @return
	 */
	public List<DocumentoId> dividirDocumento(DocumentoId id, List<Range<Integer>> intervalos) {
		List<DocumentoTemporarioId> documentosTemporarios = documentoService.dividirDocumento(id, intervalos);
		return salvar(documentosTemporarios);
	}
	
	/**
	 * Divide um documento completamente.
	 * 
	 * @param id
	 * @param intervalos
	 * @return
	 */
	public List<DocumentoId> dividirDocumentoCompletamente(DocumentoId id, List<Range<Integer>> intervalos) {
		List<DocumentoTemporarioId> documentosTemporarios = documentoService.dividirDocumentoCompletamente(id, intervalos);
		return salvar(documentosTemporarios);
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
		DocumentoId novoDocumento = salvar(tempId);
		return novoDocumento;
	}

	private List<DocumentoId> salvar(List<DocumentoTemporarioId> documentosTemporarios) {
		List<DocumentoId> documentosSalvos = new ArrayList<>();
		for (DocumentoTemporarioId docTempId : documentosTemporarios) {
			DocumentoId novoDocumento = salvar(docTempId);
			documentosSalvos.add(novoDocumento);
		}
		return documentosSalvos;
	}
	
	private DocumentoId salvar(DocumentoTemporarioId docTempId) {
		DocumentoTemporario docTemp = documentoTempRepository.recoverTemp(docTempId);
		
		DocumentoId id = documentoRepository.nextId();
		
		String numeroConteudo = conteudoDocumentoRepository.save(id, docTemp);
		
		Documento documento = new Documento(id, numeroConteudo, documentoService.contarPaginas(docTemp));
		documento = documentoRepository.save(documento);
		
		documentoTempRepository.removeTemp(docTempId.toString());
		docTemp.delete();
		return documento.id();
	}

	public void concluirEdicaoDocumento(String numeroEdicao, DocumentoId documentoId, DocumentoTemporario documentoTemporario) {
		Documento documento = documentoRepository.findOne(documentoId);
		conteudoDocumentoRepository.deleteConteudo(documento.numeroConteudo());
		String numeroConteudo = conteudoDocumentoRepository.save(documentoId, documentoTemporario);
		documento.alterarConteudo(numeroConteudo);
		documentoRepository.save(documento);
		controladorEdicaoDocumento.excluirEdicao(numeroEdicao);
	}
	
}
