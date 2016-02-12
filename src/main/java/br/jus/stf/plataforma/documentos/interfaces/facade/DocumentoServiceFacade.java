package br.jus.stf.plataforma.documentos.interfaces.facade;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import br.jus.stf.plataforma.documentos.application.DocumentoApplicationService;
import br.jus.stf.plataforma.documentos.domain.model.ConteudoDocumento;
import br.jus.stf.plataforma.documentos.domain.model.DocumentoRepository;
import br.jus.stf.plataforma.documentos.domain.model.DocumentoTemporario;
import br.jus.stf.plataforma.documentos.interfaces.dto.DocumentoDto;
import br.jus.stf.plataforma.documentos.interfaces.dto.DocumentoDtoAssembler;
import br.jus.stf.shared.DocumentoId;
import br.jus.stf.shared.DocumentoTemporarioId;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 25.09.2015
 */
@Component
public class DocumentoServiceFacade {
	
	@Autowired
	private DocumentoApplicationService documentoApplicationService;

	@Autowired
	private DocumentoDtoAssembler documentoDtoAssembler;
	
	@Autowired
	private DocumentoRepository documentoRepository;

	public List<DocumentoDto> salvarDocumentos(List<DocumentoTemporarioId> documentosTemporarios) {
		return documentoApplicationService.salvarDocumentos(documentosTemporarios).entrySet().stream()
				.map(entry -> documentoDtoAssembler.toDto(entry.getKey(), entry.getValue()))
				.collect(Collectors.toList());
	}

	public String salvarDocumentoTemporario(MultipartFile file) {
		DocumentoTemporario documentoTemporario = new DocumentoTemporario(file);
		return documentoApplicationService.salvarDocumentoTemporario(documentoTemporario);
	}

	public ConteudoDocumento pesquisaDocumento(Long documentoId) {
		return documentoRepository.download(new DocumentoId(documentoId));
	}

	public void apagarDocumentosTemporarios(List<String> files) {
		documentoApplicationService.apagarDocumentosTemporarios(files);
	}

	public List<DocumentoId> dividirDocumento(DocumentoId id, List<Range<Integer>> intervalos) {
		return documentoApplicationService.dividirDocumentoContiguamente(id, intervalos);
	}

	public DocumentoId unirDocumentos(List<DocumentoId> documentos) {
		return documentoApplicationService.unirDocumentos(documentos);
	}

}
