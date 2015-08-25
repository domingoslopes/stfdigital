package br.jus.stf.autuacao.application;

import java.io.IOException;
//import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import br.jus.stf.autuacao.domain.PeticaoService;
import br.jus.stf.plataforma.ApplicationContextInitializer;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ApplicationContextInitializer.class)
@WebAppConfiguration
public class DocumentoUnitTests {

	@Autowired
	private PeticaoApplicationService peticaoAppService;
	
	@Autowired
	private PeticaoService peticaoService;
	
	@Test
	public void enviarArquivo() throws IOException{
		String nomeArquivo = "teste_arq_temp.pdf";
		String mime = "application/pdf";
		String idDocumento = "";
		String caminho = "static/tmp/Spring Persistence with Hibernate.pdf";
		
		String teste = new ClassPathResource(caminho).getPath();
		
		idDocumento = teste;
		
		byte[] arquivo=  null;//IOUtils.toByteArray(new ClassPathResource(caminho).getInputStream());

	    MockMultipartFile mockArquivo = new MockMultipartFile("file", nomeArquivo, mime, arquivo);
		idDocumento = this.peticaoService.gravarArquivo(mockArquivo);
		
		Assert.assertEquals(false, idDocumento.isEmpty());
	}
}
