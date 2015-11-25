package br.jus.stf.plataforma.shared.certification.infra;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;

import br.jus.stf.plataforma.shared.certification.domain.model.DocumentSignature;
import br.jus.stf.plataforma.shared.certification.domain.model.SignedDocument;

public class PdfTempSignedDocument implements SignedDocument {

	private File file;
	private DocumentSignature signature;

	public PdfTempSignedDocument(File file, DocumentSignature signature) {
		this.file = file;
		this.signature = signature;
	}
	
	@Override
	public InputStream stream() {
		try {
			return new FileInputStream(file);
		} catch (FileNotFoundException e) {
			throw new IllegalStateException("Arquivo de documento assinado não encontrado.", e);
		}
	}

	@Override
	public byte[] bytes() {
		try {
			return FileUtils.readFileToByteArray(file);
		} catch (IOException e) {
			throw new IllegalStateException("Erro ao ler documento assinado.", e);
		}
	}

	@Override
	public DocumentSignature signature() {
		return signature;
	}

}
