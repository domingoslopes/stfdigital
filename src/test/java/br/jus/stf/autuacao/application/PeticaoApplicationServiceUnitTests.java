package br.jus.stf.autuacao.application;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.jus.stf.AbstractIntegrationTests;
import br.jus.stf.workflow.application.TarefaApplicationService;

/**
 * Teste do processo de peticionamento.
 * 
 * @author anderson.araujo
 * 
 * @since 1.0.0
 * @since 23.07.2015
 */
@Ignore
public class PeticaoApplicationServiceUnitTests extends AbstractIntegrationTests {
	
	@Autowired
	private PeticaoApplicationService peticaoApplicationService;
	
	@Autowired
	private TarefaApplicationService tarefaApllicationService;
	
	@Before
	public void iniciarPeticao() {
	}
	
	@Test(expected=RuntimeException.class)
	public void capturarExcecaoSeTipoRecebimentoNaoInformado(){
	}
	
	@Test(expected=RuntimeException.class)
	public void capturarExcecaoSeClasseProcessualNaoInformada(){
	}
	
	@Test(expected=RuntimeException.class)
	public void capturarExcecaoSePoloAtivoNaoInformado(){
	}
	
	@Test(expected=RuntimeException.class)
	public void capturarExcecaoSePoloPassivoNaoInformado(){
	}
	
	@Test
	public void registrarPeticao(){
	}
	
	@Test
	public void listarTarefasAutuador(){
	}
	
	@Test(expected=RuntimeException.class)
	public void capturarExcecaoAutuarPeticaoSeIdPeticaoNaoInformado(){
	}
	
	@Ignore
	public void autuarPeticaoValida(){
	}
}
