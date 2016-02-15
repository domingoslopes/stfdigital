package br.jus.stf.plataforma.documentos.infra;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Testes da service
 * 
 * @author Tomas.Godoi
 *
 */
public class DocumentoServiceImplTest {

	private DocumentoServiceImpl documentoPdfService;
	private RandomAccessFile pdfFile;

	@Before
	public void setUp() throws IOException {
		documentoPdfService = new DocumentoServiceImpl();
		pdfFile = criarPdfFile();
	}

	private RandomAccessFile criarPdfFile() throws IOException {
		InputStream is = getClass().getResourceAsStream("/pdf/archimate.pdf");
		File tempFile = File.createTempFile("_DocTemp_", "pdf");
		FileUtils.copyInputStreamToFile(is, tempFile);
		return new RandomAccessFile(tempFile, "r");
	}

	@Test
	public void testContarPaginas() {
		Integer quantidadePaginas = documentoPdfService.contarPaginas(pdfFile);
		Assert.assertEquals("Quantidade de páginas deveria ser 42.", new Integer(42), quantidadePaginas);
	}
}
