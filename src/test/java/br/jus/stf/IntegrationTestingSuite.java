package br.jus.stf;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import br.jus.stf.plataforma.actions.AcoesIntegrationTests;
import br.jus.stf.plataforma.dashboards.DashboardIntegrationTests;
import br.jus.stf.plataforma.documentos.DocumentoIntegrationTests;
import br.jus.stf.plataforma.pesquisas.PesquisaIntegrationTests;
import br.jus.stf.plataforma.shared.certification.application.DocumentValidatorApplicationServiceTest;
import br.jus.stf.plataforma.shared.certification.application.SignatureApplicationServiceTest;
import br.jus.stf.processamentoinicial.autuacao.interfaces.AutuacaoOriginariosIntegrationTests;
import br.jus.stf.processamentoinicial.autuacao.interfaces.actions.PeticionamentoActionIntegrationTests;
import br.jus.stf.processamentoinicial.distribuicao.application.ProcessoApplicationEventIntegrationTests;
import br.jus.stf.processamentoinicial.distribuicao.interfaces.ConsultaProcessosIntegrationTests;

@RunWith(Suite.class)
@SuiteClasses({ 
	ProcessoApplicationEventIntegrationTests.class,
	AutuacaoOriginariosIntegrationTests.class,
	ConsultaProcessosIntegrationTests.class,
	DocumentoIntegrationTests.class,
	PesquisaIntegrationTests.class,
	ProcessoApplicationEventIntegrationTests.class,
	AcoesIntegrationTests.class,
	DashboardIntegrationTests.class,
	PeticionamentoActionIntegrationTests.class,
	SignatureApplicationServiceTest.class,
	DocumentValidatorApplicationServiceTest.class
})
public class IntegrationTestingSuite {

}
