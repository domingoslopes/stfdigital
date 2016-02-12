package br.jus.stf.plataforma.documentos.infra;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.io.RandomAccessSourceFactory;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.RandomAccessFileOrArray;

import br.jus.stf.plataforma.documentos.domain.DocumentoService;
import br.jus.stf.plataforma.documentos.domain.model.ConteudoDocumento;
import br.jus.stf.plataforma.documentos.domain.model.Documento;
import br.jus.stf.plataforma.documentos.domain.model.DocumentoRepository;
import br.jus.stf.plataforma.documentos.domain.model.DocumentoTemporario;
import br.jus.stf.plataforma.shared.util.PDFMultipartFile;
import br.jus.stf.shared.DocumentoId;
import br.jus.stf.shared.DocumentoTemporarioId;

/**
 * Service para manipulação de documentos do tipo Pdf.
 * 
 * @author Tomas.Godoi
 *
 */
@Component
public class DocumentoServiceImpl implements DocumentoService {

	@Autowired
	private DocumentoRepository documentoRepository;
	
	/**
	 * Realiza a contagem da quantidade de páginas em um arquivo PDF.
	 * 
	 * @param file
	 * @return
	 */
	@Override
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

	/**
	 * Divide o conteúdo do documento.
	 * 
	 */
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

	/**
	 * Une vários conteúdos em um só.
	 * 
	 */
	@Override
	public DocumentoTemporario unirConteudos(List<ConteudoDocumento> conteudos) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream(1000);
			Document document = new Document();
			PdfCopy copy = new PdfCopy(document, baos);
			document.open();
			for (ConteudoDocumento conteudo : conteudos) {
				PdfReader reader = new PdfReader(conteudo.stream());
				copy.addDocument(reader);
				reader.close();
			}
			document.close();
			return new DocumentoTemporario(new PDFMultipartFile("pdf-unido", baos.toByteArray()));
		} catch (IOException | DocumentException e) {
			throw new RuntimeException("Erro ao unir os conteúdos dos documentos.", e);
		}
	}
	
	/**
	 * Divide um documento nos intervalos especificados.
	 * 
	 * @param id
	 * @param intervalos
	 * @return
	 */
	@Override
	public List<DocumentoTemporarioId> dividirDocumento(DocumentoId id, List<Range<Integer>> intervalos) {
		Documento documento = documentoRepository.findOne(id);
		return dividirDocumento(documento, intervalos);
	}
	
	/**
	 * Divide um documento contiguamente como especificado pelos intervalos.
	 * 
	 * @param id
	 * @param intervalos
	 * @return
	 */
	@Override
	public List<DocumentoTemporarioId> dividirDocumentoContiguamente(DocumentoId id, List<Range<Integer>> intervalos) {
		Documento documento = documentoRepository.findOne(id);
		if (intervalosSaoContiguos(intervalos, documento)) {
			return dividirDocumento(documento, intervalos);
		} else {
			throw new IllegalArgumentException("Intervalos não são contíguos");
		}
	}
	
	private List<DocumentoTemporarioId> dividirDocumento(Documento documento, List<Range<Integer>> intervalos) {
		if (!intervalosSaoContidosPeloDocumento(intervalos, documento)) {
			throw new IllegalArgumentException("Há intervalos não contidos no documento.");
		}
		ConteudoDocumento conteudo = documentoRepository.download(documento.id());
		List<DocumentoTemporarioId> documentosTemporariosDivididos = new ArrayList<>();
		for (Range<Integer> intervalo : intervalos) {
			DocumentoTemporario temp = dividirConteudo(conteudo, intervalo.getMinimum(),
					intervalo.getMaximum());
			DocumentoTemporarioId tempId = new DocumentoTemporarioId(documentoRepository.storeTemp(temp));
			documentosTemporariosDivididos.add(tempId);
		}
		return documentosTemporariosDivididos;
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
	
	private boolean intervalosSaoContidosPeloDocumento(List<Range<Integer>> intervalos, Documento documento) {
		for (Range<Integer> intervalo : intervalos) {
			if (intervalo.getMinimum() <= 0 || intervalo.getMaximum() > documento.quantidadePaginas()) {
				return false;
			}
		}
		return true;
	}
	
}
