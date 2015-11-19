package br.jus.stf.plataforma.shared.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.elasticsearch.common.lang3.Validate;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Lucas.Rodrigues
 *
 */
public class PDFMultipartFile implements MultipartFile {

	private static final String EXT = ".pdf";
	private static final String CONTENT_TYPE = "application/pdf";
	private final String nome;
	private final byte[] conteudo;

	/**
	 * @param nome
	 * @param conteudo
	 */
	public PDFMultipartFile(String nome, byte[] conteudo) {
		Validate.notBlank(nome, "Nome não pode ser nulo ou vazio!");
		
		this.nome = nome;
		this.conteudo = conteudo != null ? conteudo : new byte[0];
	}

	@Override
	public String getName() {
		return this.nome;
	}

	@Override
	public String getOriginalFilename() {
		return this.nome + EXT;
	}

	@Override
	public String getContentType() {
		return CONTENT_TYPE;
	}

	@Override
	public boolean isEmpty() {
		return this.conteudo.length == 0;
	}

	@Override
	public long getSize() {
		return this.conteudo.length;
	}

	@Override
	public byte[] getBytes() {
		return this.conteudo;
	}

	@Override
	public InputStream getInputStream() {
		return new ByteArrayInputStream(this.conteudo);
	}

	@Override
	public void transferTo(File dest) throws IOException {
		FileCopyUtils.copy(this.conteudo, dest);
	}

}

