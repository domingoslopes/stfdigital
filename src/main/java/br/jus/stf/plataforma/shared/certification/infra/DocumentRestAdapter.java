package br.jus.stf.plataforma.shared.certification.infra;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import br.jus.stf.plataforma.documentos.interfaces.DocumentoRestResource;
import br.jus.stf.plataforma.documentos.interfaces.commands.SalvarDocumentosCommand;
import br.jus.stf.plataforma.shared.certification.domain.DocumentAdapter;
import br.jus.stf.plataforma.shared.certification.domain.model.SignedDocument;
import br.jus.stf.plataforma.shared.certification.domain.model.SigningDocument;
import br.jus.stf.plataforma.shared.util.PDFMultipartFile;
import br.jus.stf.shared.DocumentoId;
import br.jus.stf.shared.DocumentoTemporarioId;

@Component
public class DocumentRestAdapter implements DocumentAdapter {

	@Autowired
	private DocumentoRestResource docRestResource;

	@Override
	public SigningDocument retrieve(DocumentoId id) throws IOException {
		MockHttpServletResponse response = new MockHttpServletResponse();
		docRestResource.recuperar(id.toLong(), response);
		return new PdfTempDocument(new ByteArrayInputStream(response.getContentAsByteArray()));
	}

	@Override
	public DocumentoTemporarioId upload(String name, SignedDocument document) {
		MultipartFile file = new PDFMultipartFile(name, document.bytes());
		return new DocumentoTemporarioId(docRestResource.upload(file));
	}

	@Override
	public DocumentoId save(DocumentoTemporarioId tempDocument) {
		SalvarDocumentosCommand command = new SalvarDocumentosCommand();
		command.setDocumentos(Arrays.asList(tempDocument));
		LinkedHashSet<DocumentoId> docs = docRestResource.salvar(command).stream()
				.map(dto -> new DocumentoId(dto.getDocumentoId()))
				.collect(Collectors.toCollection(
						() -> new LinkedHashSet<DocumentoId>()));
		return docs.iterator().next();
	}

}
