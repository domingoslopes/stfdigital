package br.jus.stf.plataforma.documentos.interfaces.commands;

import org.springframework.web.multipart.MultipartFile;

import br.jus.stf.plataforma.shared.certification.interfaces.validators.SignedDocument;

public class UploadDocumentoAssinadoCommand {

//	@SignedDocument // TODO Benchmark-Tomas Remover comentário depois do benchmark
	private MultipartFile file;

	public UploadDocumentoAssinadoCommand() {
		
	}
	
	public UploadDocumentoAssinadoCommand(MultipartFile file) {
		this.file = file;
	}
	
	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}
	
}
