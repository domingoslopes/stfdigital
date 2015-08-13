package br.jus.stf;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import br.jus.stf.autuacao.interfaces.AutuacaoOriginariosIntegrationTests;
import br.jus.stf.plataforma.action.interfaces.AcoesIntegrationTests;

@RunWith(Suite.class)
@SuiteClasses({ AutuacaoOriginariosIntegrationTests.class, AcoesIntegrationTests.class })
public class IntegrationTestsSuite {

}
