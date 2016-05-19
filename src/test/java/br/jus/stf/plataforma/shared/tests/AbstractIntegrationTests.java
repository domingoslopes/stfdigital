package br.jus.stf.plataforma.shared.tests;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import br.jus.stf.plataforma.shared.bootstrap.ApplicationContextInitializer;
import br.jus.stf.plataforma.shared.settings.Profiles;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ApplicationContextInitializer.class)
@WebIntegrationTest({"server.port=57443", "management.port=0", "port=57443"})
@ActiveProfiles({Profiles.DESENVOLVIMENTO, Profiles.DOCUMENTO_MONGO})
public abstract class AbstractIntegrationTests {

	@Autowired
	private AuthoritiesMockFilter authoritiesMockFilter;  

	@Autowired
	private WebApplicationContext wac;

	protected MockMvc mockMvc;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).addFilters(authoritiesMockFilter).build();
	}
	
}
