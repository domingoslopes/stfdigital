package br.jus.stf.plataforma.documentos.infra.persistence;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import br.jus.stf.plataforma.documentos.domain.model.ConteudoDocumentoDownload;
import br.jus.stf.plataforma.documentos.domain.model.DocumentoTemporario;
import br.jus.stf.plataforma.shared.settings.Profiles;
import br.jus.stf.shared.DocumentoId;

/**
 * Implementação do repositório de conteúdo com armazenamento em File System.
 * 
 * @author Tomas.Godoi
 *
 */
@Repository
@Profile(Profiles.DOCUMENTO_FS)
public class FSConteudoDocumentoRepositoryImpl implements ConteudoDocumentoRepository {

	@Autowired
	@Qualifier("documentosDirPath")
	private String documentosDirPath;

	@Override
	public ConteudoDocumentoDownload downloadConteudo(String numeroConteudo) {
		File arquivoConteudo = createArquivoConteudo(numeroConteudo);
		try {
			byte[] conteudo = FileUtils.readFileToByteArray(arquivoConteudo);
			return new ConteudoDocumentoDownload(new ByteArrayInputStream(conteudo), new Long(conteudo.length));
		} catch (IOException e) {
			throw new RuntimeException("Erro ao recuperar o documento.", e);
		}
	}

	@Override
	public void deleteConteudo(String numeroConteudo) {
		File arquivoConteudo = createArquivoConteudo(numeroConteudo);
		if (!arquivoConteudo.delete()) {
			throw new RuntimeException("Erro ao excluir documento.");
		}
	}

	@Override
	public String save(DocumentoId documentoId, DocumentoTemporario documentoTemporario) {
		String numeroConteudo = RandomStringUtils.randomAlphanumeric(24).toLowerCase();
		File arquivoConteudo = createArquivoConteudo(numeroConteudo);
		InputStream in = documentoTemporario.stream();
		OutputStream out = null;
		try {
			out = new FileOutputStream(arquivoConteudo);
			IOUtils.copy(in, out);
			return numeroConteudo;
		} catch (IOException e) {
			throw new RuntimeException("Erro ao gravar documento.", e);
		} finally {
			IOUtils.closeQuietly(in);
			IOUtils.closeQuietly(out);
		}
	}

	private File createArquivoConteudo(String numeroConteudo) {
		return new File(documentosDirPath, numeroConteudo);
	}

}
