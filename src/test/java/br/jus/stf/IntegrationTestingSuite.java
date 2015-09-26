package br.jus.stf;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import br.jus.stf.plataforma.actions.AcoesIntegrationTests;
import br.jus.stf.plataforma.documentos.DocumentoIntegrationTests;
import br.jus.stf.plataforma.pesquisas.PesquisaIntegrationTests;
import br.jus.stf.processamentoinicial.autuacao.application.PeticaoApplicationEventIntegrationTests;
import br.jus.stf.processamentoinicial.autuacao.interfaces.AutuacaoOriginariosIntegrationTests;

@RunWith(Suite.class)
@SuiteClasses({ 
	PeticaoApplicationEventIntegrationTests.class,
	AutuacaoOriginariosIntegrationTests.class,
	DocumentoIntegrationTests.class,
	PesquisaIntegrationTests.class, 
	AcoesIntegrationTests.class 
})
public class IntegrationTestingSuite {

}
