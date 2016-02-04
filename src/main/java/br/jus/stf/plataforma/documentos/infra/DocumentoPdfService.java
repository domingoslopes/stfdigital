package br.jus.stf.plataforma.documentos.infra;

import java.io.IOException;
import java.io.RandomAccessFile;

import org.springframework.stereotype.Component;

import com.itextpdf.text.io.RandomAccessSourceFactory;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.RandomAccessFileOrArray;

/**
 * Service para manipulação de documentos do tipo Pdf.
 * 
 * @author Tomas.Godoi
 *
 */
@Component
public class DocumentoPdfService {

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
}
