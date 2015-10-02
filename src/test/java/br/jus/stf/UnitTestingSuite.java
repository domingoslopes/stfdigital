package br.jus.stf;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import br.jus.stf.plataforma.shared.web.CorsFilterUnitTests;
import br.jus.stf.plataforma.shared.web.Html5FilterUnitTests;
import br.jus.stf.processamentoinicial.autuacao.application.PeticaoApplicationServiceUnitTests;

@RunWith(Suite.class)
@SuiteClasses({ 
	PeticaoApplicationServiceUnitTests.class,
	Html5FilterUnitTests.class,
	CorsFilterUnitTests.class
})
public class UnitTestingSuite {

}
