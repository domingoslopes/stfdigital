package br.jus.stf.processamentoinicial.distribuicao.application;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;

import br.jus.stf.plataforma.shared.indexacao.IndexadorRestAdapter;
import br.jus.stf.plataforma.shared.tests.AbstractIntegrationTests;
import br.jus.stf.processamentoinicial.distribuicao.domain.model.Processo;
import br.jus.stf.processamentoinicial.distribuicao.infra.eventbus.ProcessoIndexadorConsumer;

/**
 * @author Lucas Rodrigues
 */
public class ProcessoApplicationEventIntegrationTests extends AbstractIntegrationTests {

	@Autowired
	private ProcessoApplicationEvent processoApplicationEvent;
	
	@Mock
	private Processo processo;
	
	@Spy
	private IndexadorRestAdapter indexadorRestAdapter;
	
	@Autowired
	@InjectMocks
	private ProcessoIndexadorConsumer processoIndexadorConsumer;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		doNothing().when(indexadorRestAdapter).indexar(any(), any());
	}
	
	@Test
	public void processoDistribuidoEvent() throws Exception {		
		processoApplicationEvent.processoDistribuido(processo);
	}
	
}
