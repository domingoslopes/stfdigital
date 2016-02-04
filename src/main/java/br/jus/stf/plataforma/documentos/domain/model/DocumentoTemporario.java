package br.jus.stf.plataforma.documentos.domain.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.web.multipart.MultipartFile;

import br.jus.stf.plataforma.shared.util.HashGeneratorUtils;
import br.jus.stf.shared.stereotype.ValueObject;

/**
 * Documento ainda não persistido
 * 
 * @author Lucas Rodrigues
 */
public class DocumentoTemporario implements ValueObject<DocumentoTemporario> {

	private static final long serialVersionUID = -3725370010702512231L;

	private static String FILE_NAME_PREFFIX = "_DocTemp_";
	
	private Long tamanho;
	private File arquivo;
	
	public DocumentoTemporario(MultipartFile file) {
		Validate.notNull(file);
		
		arquivo = createTempFile(file);
		tamanho = arquivo.length();
	}
	
	private File createTempFile(MultipartFile file) {
		File tempFile = null;
		try {
			tempFile = File.createTempFile(FILE_NAME_PREFFIX, extractExtension(file.getOriginalFilename()));
			file.transferTo(tempFile);
		} catch (IllegalStateException | IOException e) {
			throw new DocumentoTempRuntimeException(e);		
		}
		return tempFile;
	}
	
	public String tempId() {
		return arquivo.getName();
	}
	
	public Long tamanho() {
		return tamanho;
	}
	
	public String contentType() {
		try {
			return Files.probeContentType(arquivo.toPath());
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	public FileInputStream stream() {
		try {
			return new FileInputStream(arquivo);
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	/**
	 * Recupera um objeto para acesso aleatório ao arquivo. 
	 * 
	 * @return
	 */
	public RandomAccessFile randomAccessFile() {
		try {
			return new RandomAccessFile(arquivo, "r");
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Erro ao recuperar o arquivo temporário.", e);
		}
	}
	
	public void delete() {
		arquivo.delete();
	}
	
	private String extractExtension(String name) {
		return name.substring(name.lastIndexOf("."));
	}

	@Override
	public int hashCode() {
		String hashFile = HashGeneratorUtils.generateSHA256(this.arquivo);
		return new HashCodeBuilder().append(hashFile).hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		
		DocumentoTemporario other = (DocumentoTemporario) obj;
		return sameValueAs(other);
	}
	
	@Override
	public boolean sameValueAs(DocumentoTemporario other) {
		return other != null && HashGeneratorUtils.generateSHA256(this.arquivo).equals(HashGeneratorUtils.generateSHA256(other.arquivo));
	}

}
