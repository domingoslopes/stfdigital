package br.jus.stf.plataforma.documentos.infra;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

import org.springframework.stereotype.Component;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.io.RandomAccessSourceFactory;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.RandomAccessFileOrArray;

import br.jus.stf.plataforma.documentos.domain.DocumentoService;
import br.jus.stf.plataforma.documentos.domain.model.ConteudoDocumento;
import br.jus.stf.plataforma.documentos.domain.model.DocumentoTemporario;
import br.jus.stf.plataforma.shared.util.PDFMultipartFile;

/**
 * Service para manipulação de documentos do tipo Pdf.
 * 
 * @author Tomas.Godoi
 *
 */
@Component
public class DocumentoServiceImpl implements DocumentoService {

	/**
	 * Realiza a contagem da quantidade de páginas em um arquivo PDF.
	 * 
	 * @param file
	 * @return
	 */
	public Integer contarPaginas(RandomAccessFile file) {
		try {
			RandomAccessFileOrArray pdfFile = new RandomAccessFileOrArray(
			        new RandomAccessSourceFactory().createSource(file));
			PdfReader reader = new PdfReader(pdfFile, new byte[0]);
			int paginas = reader.getNumberOfPages();
			reader.close();
			return paginas;
		} catch (IOException e) {
			throw new RuntimeException("Erro ao contar a quantidade de páginas do documento.", e);
		}
	}

	@Override
	public DocumentoTemporario dividirConteudo(ConteudoDocumento conteudo, Integer paginaInicial,
	        Integer paginaFinal) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream(1000);
			Document document = new Document();
			PdfReader reader = new PdfReader(conteudo.stream());
			PdfCopy copy = new PdfCopy(document, baos);
			document.open();
			reader.selectPages(paginaInicial + "-" + paginaFinal);
			copy.addDocument(reader);
			document.close();
			reader.close();
			
			return new DocumentoTemporario(new PDFMultipartFile("pdf-dividido", baos.toByteArray()));
		} catch (IOException | DocumentException e) {
			throw new RuntimeException("Erro ao dividir o conteúdo do documento.", e);
		}
	}
}
