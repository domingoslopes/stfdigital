package br.jus.stf;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import br.jus.stf.plataforma.acessos.domain.model.PermissaoGrupoUnitTests;
import br.jus.stf.plataforma.acessos.domain.model.PermissaoPapelUnitTests;
import br.jus.stf.plataforma.acessos.domain.model.PermissaoUsuarioUnitTests;
import br.jus.stf.plataforma.actions.ActionMappingInfoUnitTests;
import br.jus.stf.plataforma.actions.ActionMappingRegistryUnitTests;
import br.jus.stf.plataforma.actions.ActionServiceUnitTests;
import br.jus.stf.plataforma.dashboards.interfaces.dto.DashboardDtoAssemblerUnitTests;
import br.jus.stf.plataforma.dashboards.interfaces.facade.DashboardServiceFacadeUnitTests;
import br.jus.stf.plataforma.documentos.domain.model.DocumentoTemporarioUnitTests;
import br.jus.stf.plataforma.documentos.domain.model.DocumentoUnitTests;
import br.jus.stf.plataforma.identidades.domain.model.AssociadoUnitTests;
import br.jus.stf.plataforma.identidades.domain.model.PessoaUnitTests;
import br.jus.stf.plataforma.shared.web.CorsFilterUnitTests;
import br.jus.stf.plataforma.shared.web.Html5FilterUnitTests;
import br.jus.stf.plataforma.workflow.domain.model.TarefaUnitTests;
import br.jus.stf.processamentoinicial.autuacao.application.PecaDevolucaoBuilderUnitTests;
import br.jus.stf.processamentoinicial.autuacao.domain.model.OrgaoUnitTests;
import br.jus.stf.processamentoinicial.autuacao.domain.model.PeticaoEletronicaUnitTests;
import br.jus.stf.processamentoinicial.autuacao.domain.model.PeticaoFisicaUnitTests;
import br.jus.stf.processamentoinicial.autuacao.domain.model.PeticaoUnitTests;
import br.jus.stf.processamentoinicial.distribuicao.domain.model.DistribuicaoComumUnitTests;
import br.jus.stf.processamentoinicial.distribuicao.domain.model.DistribuicaoPrevencaoUnitTests;
import br.jus.stf.processamentoinicial.distribuicao.domain.model.ProcessoRepositoryUnitTests;
import br.jus.stf.processamentoinicial.distribuicao.domain.model.ProcessoUnitTests;
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
	PecaDevolucaoBuilderUnitTests.class,
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
	TarefaIdUnitTests.class,
	ActionMappingInfoUnitTests.class,
	ActionMappingRegistryUnitTests.class,
	ActionServiceUnitTests.class,
	DashboardServiceFacadeUnitTests.class,
	DashboardDtoAssemblerUnitTests.class,
	OrgaoUnitTests.class,
	ProcessoUnitTests.class,
	ProcessoRepositoryUnitTests.class,
	DistribuicaoComumUnitTests.class,
	DistribuicaoPrevencaoUnitTests.class,
	PermissaoUsuarioUnitTests.class,
	PermissaoGrupoUnitTests.class,
	PermissaoPapelUnitTests.class,
	AssociadoUnitTests.class
})
public class UnitTestingSuite {

}