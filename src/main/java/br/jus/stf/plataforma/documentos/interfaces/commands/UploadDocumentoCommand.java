package br.jus.stf.plataforma.documentos.interfaces.commands;

import org.springframework.web.multipart.MultipartFile;

import br.jus.stf.plataforma.shared.certification.interfaces.validators.SignedDocument;

public class UploadDocumentoCommand {

	@SignedDocument
	private MultipartFile file;

	public UploadDocumentoCommand() {
		
	}
	
	public UploadDocumentoCommand(MultipartFile file) {
		this.file = file;
	}
	
	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}
	
}
