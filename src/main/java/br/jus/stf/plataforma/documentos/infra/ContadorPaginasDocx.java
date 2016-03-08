package br.jus.stf.plataforma.documentos.infra;

import java.io.IOException;

import org.apache.poi.xwpf.usermodel.XWPFDocument;

import br.jus.stf.plataforma.documentos.domain.ContadorPaginas;
import br.jus.stf.plataforma.documentos.domain.model.DocumentoTemporario;

public class ContadorPaginasDocx implements ContadorPaginas {

	@Override
	public Integer contarPaginas(DocumentoTemporario docTemp) {
		try (XWPFDocument document = new XWPFDocument(docTemp.stream())) {
			return document.getProperties().getExtendedProperties().getPages();
		} catch (IOException e) {
			throw new RuntimeException("Erro ao contar a quantidade de páginas do documento.", e);
		}
	}

}
