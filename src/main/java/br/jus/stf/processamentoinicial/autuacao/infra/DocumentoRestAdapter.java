package br.jus.stf.processamentoinicial.autuacao.infra;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import br.jus.stf.plataforma.documentos.interfaces.DocumentoRestResource;
import br.jus.stf.plataforma.documentos.interfaces.commands.SalvarDocumentosCommand;
import br.jus.stf.plataforma.documentos.interfaces.commands.UploadDocumentoCommand;
import br.jus.stf.plataforma.shared.util.PDFMultipartFile;
import br.jus.stf.processamentoinicial.autuacao.domain.DocumentoAdapter;
import br.jus.stf.shared.DocumentoId;
import br.jus.stf.shared.DocumentoTemporarioId;

/**
 * @author Lucas Rodrigues
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 25.09.2015
 */
@Component
public class DocumentoRestAdapter implements DocumentoAdapter {
	
	@Autowired
	private DocumentoRestResource documentoRestResource;

	@Override
	public DocumentoId salvar(DocumentoTemporarioId documentoTemporario) {
		SalvarDocumentosCommand command = new SalvarDocumentosCommand();
		command.setDocumentos(Arrays.asList(documentoTemporario));
		LinkedHashSet<DocumentoId> docs = documentoRestResource.salvar(command).stream()
				.map(dto -> new DocumentoId(dto.getDocumentoId()))
				.collect(Collectors.toCollection(
						() -> new LinkedHashSet<DocumentoId>()));
		return docs.iterator().next();
	}

	@Override
	public Set<DocumentoId> salvar(List<DocumentoTemporarioId> documentosTemporarios) {
		SalvarDocumentosCommand command = new SalvarDocumentosCommand();
		command.setDocumentos(documentosTemporarios);
		return documentoRestResource.salvar(command).stream()
				.map(dto -> new DocumentoId(dto.getDocumentoId()))
				.collect(Collectors.toCollection(
						() -> new LinkedHashSet<DocumentoId>()));
	}

	@Override
	public DocumentoTemporarioId upload(String nome, byte[] documento) {
		MultipartFile file = new PDFMultipartFile(nome, documento);
		return new DocumentoTemporarioId(documentoRestResource.upload(new UploadDocumentoCommand(file)));
	}

}
