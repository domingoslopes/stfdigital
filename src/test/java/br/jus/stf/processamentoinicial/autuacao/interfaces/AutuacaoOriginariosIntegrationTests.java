package br.jus.stf.processamentoinicial.autuacao.interfaces;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.cert.CertificateEncodingException;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MvcResult;

import com.jayway.jsonpath.JsonPath;

import br.jus.stf.plataforma.shared.certification.infra.pki.CustomKeyStore;
import br.jus.stf.plataforma.shared.certification.support.pki.PlataformaUnitTestingUser;
import br.jus.stf.plataforma.shared.indexacao.IndexadorRestAdapter;
import br.jus.stf.plataforma.shared.tests.AbstractIntegrationTests;
import br.jus.stf.processamentoinicial.autuacao.infra.eventbus.PeticaoIndexadorConsumer;
import br.jus.stf.processamentoinicial.autuacao.infra.eventbus.PeticaoStatusIndexadorConsumer;
import br.jus.stf.processamentoinicial.distribuicao.infra.eventbus.ProcessoIndexadorConsumer;

/**
 * @author Rodrigo Barreiros
 * @author Anderson.Araujo
 * 
 * @since 1.0.0
 * @since 17.06.2015
 */
public class AutuacaoOriginariosIntegrationTests extends AbstractIntegrationTests {
	
	@Spy
	private IndexadorRestAdapter indexadorRestAdapter;
	
	@Autowired
	@InjectMocks
	private ProcessoIndexadorConsumer processoIndexadorConsumer;
	
	@Autowired
	@InjectMocks
	private PeticaoIndexadorConsumer peticaoIndexadorConsumer;
	
	@Autowired
	@InjectMocks
	private PeticaoStatusIndexadorConsumer peticaoStatusIndexadorConsumer;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		doNothing().when(indexadorRestAdapter).indexar(any(), any());
		doNothing().when(indexadorRestAdapter).atualizarItemDeColecao(any(), any(), any(), any(), any(), any(), any());
	}
	
	@Test
	public void recuperarListaStatusPeticao() throws Exception {
		//Recupera as informações do usuário.
		this.mockMvc.perform(get("/api/peticoes/status")
			.header("login", "autuador")).andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(8)));
	}
}
