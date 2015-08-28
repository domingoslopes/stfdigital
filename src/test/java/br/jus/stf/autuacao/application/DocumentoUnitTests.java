package br.jus.stf.autuacao.application;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;

import br.jus.stf.autuacao.domain.PeticaoService;

public class DocumentoUnitTests {

	private PeticaoService peticaoService = new PeticaoService();
	
	@Test
	public void enviarArquivo() throws IOException{
		String nomeArquivo = "teste_arq_temp.pdf";
		String mime = "application/pdf";
		String idDocumento = "";
		String caminho = "pdf/archimate.pdf";
		
		String teste = new ClassPathResource(caminho).getPath();
		
		idDocumento = teste;
		
		byte[] arquivo=  IOUtils.toByteArray(new ClassPathResource(caminho).getInputStream());

	    MockMultipartFile mockArquivo = new MockMultipartFile("file", nomeArquivo, mime, arquivo);
		idDocumento = this.peticaoService.gravarArquivo(mockArquivo);
		
		Assert.assertEquals(false, idDocumento.isEmpty());
	}
}
