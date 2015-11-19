package br.jus.stf.processamentoinicial.distribuicao.application;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import br.jus.stf.plataforma.shared.tests.AbstractIntegrationTests;
import br.jus.stf.processamentoinicial.distribuicao.domain.model.Processo;

/**
 * @author Lucas Rodrigues
 */
public class ProcessoApplicationEventIntegrationTests extends AbstractIntegrationTests {

	@Autowired
	private ProcessoApplicationEvent processoApplicationEvent;
	
	@Mock
	private Processo processo;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void processoDistribuidoEvent() throws Exception {		
		processoApplicationEvent.processoDistribuido(processo);
	}
	
}
