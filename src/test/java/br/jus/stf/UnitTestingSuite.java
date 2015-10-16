package br.jus.stf;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import br.jus.stf.plataforma.documentos.domain.model.DocumentoTemporarioUnitTests;
import br.jus.stf.plataforma.documentos.domain.model.DocumentoUnitTests;
import br.jus.stf.plataforma.identidades.domain.model.PessoaUnitTests;
import br.jus.stf.plataforma.shared.web.CorsFilterUnitTests;
import br.jus.stf.plataforma.shared.web.Html5FilterUnitTests;
import br.jus.stf.plataforma.workflow.domain.model.TarefaUnitTests;
import br.jus.stf.processamentoinicial.autuacao.application.PeticaoApplicationServiceUnitTests;
import br.jus.stf.processamentoinicial.autuacao.domain.model.PeticaoEletronicaUnitTests;
import br.jus.stf.processamentoinicial.autuacao.domain.model.PeticaoFisicaUnitTests;
import br.jus.stf.processamentoinicial.autuacao.domain.model.PeticaoUnitTests;
import br.jus.stf.processamentoinicial.suporte.domain.model.ClasseUnitTests;
import br.jus.stf.processamentoinicial.suporte.domain.model.MinistroUnitTests;
import br.jus.stf.shared.ClasseIdUnitTests;
import br.jus.stf.shared.DocumentoIdUnitTests;
import br.jus.stf.shared.DocumentoTemporarioIdUnitTests;
import br.jus.stf.shared.MinistroIdUnitTests;
import br.jus.stf.shared.PessoaIdUnitTests;
import br.jus.stf.shared.PeticaoIdUnitTests;
import br.jus.stf.shared.ProcessoIdUnitTests;
import br.jus.stf.shared.ProcessoWorkflowIdUnitTests;
import br.jus.stf.shared.ProcessoWorkflowUnitTests;
import br.jus.stf.shared.TarefaIdUnitTests;

@RunWith(Suite.class)
@SuiteClasses({ 
	PeticaoApplicationServiceUnitTests.class,
	Html5FilterUnitTests.class,
	CorsFilterUnitTests.class,
	DocumentoTemporarioUnitTests.class,
	DocumentoUnitTests.class,
	PessoaUnitTests.class,
	TarefaUnitTests.class,
	PeticaoUnitTests.class,
	PeticaoFisicaUnitTests.class,
	PeticaoEletronicaUnitTests.class,
	ClasseUnitTests.class,
	MinistroUnitTests.class,
	ClasseIdUnitTests.class,
	DocumentoIdUnitTests.class,
	DocumentoTemporarioIdUnitTests.class,
	MinistroIdUnitTests.class,
	PessoaIdUnitTests.class,
	PeticaoIdUnitTests.class,
	ProcessoIdUnitTests.class,
	ProcessoWorkflowIdUnitTests.class,
	ProcessoWorkflowUnitTests.class,
	TarefaIdUnitTests.class
})
public class UnitTestingSuite {

}
