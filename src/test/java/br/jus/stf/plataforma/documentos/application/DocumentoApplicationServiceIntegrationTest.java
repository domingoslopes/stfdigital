package br.jus.stf.plataforma.documentos.application;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.Range;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;

import br.jus.stf.plataforma.documentos.domain.model.Documento;
import br.jus.stf.plataforma.documentos.domain.model.DocumentoRepository;
import br.jus.stf.plataforma.documentos.domain.model.DocumentoTemporario;
import br.jus.stf.plataforma.shared.tests.AbstractIntegrationTests;
import br.jus.stf.shared.DocumentoId;
import br.jus.stf.shared.DocumentoTemporarioId;

/**
 * Testes de integração.
 * 
 * @author Tomas.Godoi
 *
 */
public class DocumentoApplicationServiceIntegrationTest extends AbstractIntegrationTests {

	@Autowired
	private DocumentoApplicationService documentoApplicationService;
	
	@Autowired
	private DocumentoRepository documentoRepository;
	
	private MockMultipartFile mockMultiFile;

	@Test
	public void testDividirDocumentoUmIntervalo() throws Exception {
		List<DocumentoId> documentos = dividirDocumentoComIntervalos(Arrays.asList(Range.between(1, 17)));
		
		Assert.assertEquals("Deveria ter dividido o documento em 1.", 1, documentos.size());
		
		Documento documentoPersistido = documentoRepository.findOne(documentos.get(0));
		
		Assert.assertEquals("Documento dividido deveria ter 17 páginas.", new Integer(17), documentoPersistido.quantidadePaginas());
	}
	
	@Test
	public void testDividirDocumentoTresIntervalos() throws Exception {
		List<DocumentoId> documentos = dividirDocumentoComIntervalos(Arrays.asList(Range.between(1, 7), Range.between(8, 20), Range.between(21, 42)));
		
		Assert.assertEquals("Deveria ter dividido o documento em 3.", 3, documentos.size());
		
		Documento documentoPersistido = documentoRepository.findOne(documentos.get(0));
		Assert.assertEquals("Documento dividido deveria ter 7 páginas.", new Integer(7), documentoPersistido.quantidadePaginas());
		
		documentoPersistido = documentoRepository.findOne(documentos.get(1));
		Assert.assertEquals("Documento dividido deveria ter 13 páginas.", new Integer(13), documentoPersistido.quantidadePaginas());
		
		documentoPersistido = documentoRepository.findOne(documentos.get(2));
		Assert.assertEquals("Documento dividido deveria ter 22 páginas.", new Integer(22), documentoPersistido.quantidadePaginas());
	}

	private List<DocumentoId> dividirDocumentoComIntervalos(List<Range<Integer>> intervalos) throws IOException {
		mockMultiFile = new MockMultipartFile("file", "archimate.pdf", "application/pdf",
				getClass().getResourceAsStream("/pdf/archimate.pdf"));
		DocumentoTemporario documentoTemporario = new DocumentoTemporario(mockMultiFile);
		
		String idDocumentoTemporario = documentoApplicationService.salvarDocumentoTemporario(documentoTemporario);
		Map<String, DocumentoId> documentosSalvos = documentoApplicationService.salvarDocumentos(Arrays.asList(new DocumentoTemporarioId(idDocumentoTemporario)));
		
		DocumentoId docParaDividir = documentosSalvos.get(idDocumentoTemporario);
		
		List<DocumentoId> documentos = documentoApplicationService.dividirDocumento(docParaDividir, intervalos);
		return documentos;
	}
}
