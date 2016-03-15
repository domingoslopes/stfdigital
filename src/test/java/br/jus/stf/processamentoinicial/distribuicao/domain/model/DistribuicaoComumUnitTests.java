package br.jus.stf.processamentoinicial.distribuicao.domain.model;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.jus.stf.processamentoinicial.autuacao.domain.model.FormaRecebimento;
import br.jus.stf.processamentoinicial.autuacao.domain.model.PartePeticao;
import br.jus.stf.processamentoinicial.autuacao.domain.model.PecaPeticao;
import br.jus.stf.processamentoinicial.autuacao.domain.model.PeticaoFisica;
import br.jus.stf.processamentoinicial.recursaledistribuicao.domain.PeticaoAdapter;
import br.jus.stf.processamentoinicial.recursaledistribuicao.domain.model.Distribuicao;
import br.jus.stf.processamentoinicial.recursaledistribuicao.domain.model.ParametroDistribuicao;
import br.jus.stf.processamentoinicial.recursaledistribuicao.domain.model.Peticao;
import br.jus.stf.processamentoinicial.recursaledistribuicao.domain.model.ProcessoFactory;
import br.jus.stf.processamentoinicial.recursaledistribuicao.domain.model.ProcessoRepository;
import br.jus.stf.processamentoinicial.recursaledistribuicao.domain.model.TipoDistribuicao;
import br.jus.stf.processamentoinicial.suporte.domain.model.Situacao;
import br.jus.stf.processamentoinicial.suporte.domain.model.TipoPeca;
import br.jus.stf.processamentoinicial.suporte.domain.model.TipoPolo;
import br.jus.stf.processamentoinicial.suporte.domain.model.TipoProcesso;
import br.jus.stf.processamentoinicial.suporte.domain.model.Visibilidade;
import br.jus.stf.shared.ClasseId;
import br.jus.stf.shared.DocumentoId;
import br.jus.stf.shared.MinistroId;
import br.jus.stf.shared.PessoaId;
import br.jus.stf.shared.PeticaoId;
import br.jus.stf.shared.ProcessoId;
import br.jus.stf.shared.ProcessoWorkflow;
import br.jus.stf.shared.ProcessoWorkflowId;
import br.jus.stf.shared.TipoDocumentoId;

public class DistribuicaoComumUnitTests {
	
	@InjectMocks
	private ProcessoFactory mockProcessoFactory;
	
	@Mock
	private ProcessoRepository mockProcessoRepository;
	
	@Mock
	private PeticaoAdapter mockPeticaoAdapter;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		
		when(mockProcessoRepository.nextId()).thenReturn(new ProcessoId(1L));
		when(mockProcessoRepository.nextNumero(new ClasseId("ADI"))).thenReturn(1L);
		PeticaoFisica peticao = preparaPeticao();
		Peticao peticaoVO = new Peticao(peticao.id(), peticao.classeProcessual(), peticao.getClass().getSimpleName(), peticao.partesPoloAtivo(), peticao.pecas(), peticao.processosWorkflow().iterator().next().id(), TipoProcesso.ORIGINARIO, peticao.preferencias());
		when(mockPeticaoAdapter.consultar(1L)).thenReturn(peticaoVO);
	}
	
	@Test
	public void realizaDistribuicaoComumValida() {
		Set<MinistroId> ministrosCanditatos = new HashSet<MinistroId>();
		
		ministrosCanditatos.add(new MinistroId(42L));
		ministrosCanditatos.add(new MinistroId(28L));
		ministrosCanditatos.add(new MinistroId(44L));
		ministrosCanditatos.add(new MinistroId(49L));
		ministrosCanditatos.add(new MinistroId(36L));
		ministrosCanditatos.add(new MinistroId(45L));
		ministrosCanditatos.add(new MinistroId(30L));
		ministrosCanditatos.add(new MinistroId(48L));
		
		Set<MinistroId> ministrosImpedidos = new HashSet<MinistroId>();
		
		ministrosImpedidos.add(new MinistroId(46L));
		ministrosImpedidos.add(new MinistroId(47L));
		ministrosImpedidos.add(new MinistroId(1L));
		
		PeticaoFisica peticao = preparaPeticao();
		ParametroDistribuicao parametros = new ParametroDistribuicao(TipoDistribuicao.COMUM, peticao.id(), "Familiares ou amigos relacionados aos ministros impedidos.", "DISTRIBUIDOR", ministrosCanditatos, ministrosImpedidos, null);
		Distribuicao distribuicao = Distribuicao.criar(parametros);
		MinistroId relator = distribuicao.executar().relator();
		
		Assert.assertTrue(ministrosCanditatos.contains(relator));
		Assert.assertFalse(ministrosImpedidos.contains(relator));
		
		verify(mockProcessoRepository, times(1)).nextId();
		verify(mockProcessoRepository, times(1)).nextNumero(new ClasseId("ADI"));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void tentaDistribuirComIntersecaoEntreListasMinistros() {
		Set<MinistroId> ministrosCanditatos = new HashSet<MinistroId>();
		
		ministrosCanditatos.add(new MinistroId(42L));
		ministrosCanditatos.add(new MinistroId(28L));
		ministrosCanditatos.add(new MinistroId(44L));
		ministrosCanditatos.add(new MinistroId(49L));
		ministrosCanditatos.add(new MinistroId(36L));
		ministrosCanditatos.add(new MinistroId(45L));
		ministrosCanditatos.add(new MinistroId(30L));
		ministrosCanditatos.add(new MinistroId(48L));
		
		Set<MinistroId> ministrosImpedidos = new HashSet<MinistroId>();
		
		ministrosImpedidos.add(new MinistroId(46L));
		ministrosImpedidos.add(new MinistroId(47L));
		ministrosImpedidos.add(new MinistroId(48L));
		
		PeticaoFisica peticao = preparaPeticao();
		ParametroDistribuicao parametros = new ParametroDistribuicao(TipoDistribuicao.COMUM, peticao.id(), "Familiares ou amigos relacionados aos ministros impedidos.", "DISTRIBUIDOR", ministrosCanditatos, ministrosImpedidos, null);
		Distribuicao.criar(parametros);
	}
	
	@Test(expected = NullPointerException.class)
	public void tentaDistribuirComMinistrosImpedidosNulo() {
		Set<MinistroId> ministrosCanditatos = new HashSet<MinistroId>();
		
		ministrosCanditatos.add(new MinistroId(42L));
		ministrosCanditatos.add(new MinistroId(28L));
		ministrosCanditatos.add(new MinistroId(44L));
		ministrosCanditatos.add(new MinistroId(49L));
		ministrosCanditatos.add(new MinistroId(36L));
		ministrosCanditatos.add(new MinistroId(45L));
		ministrosCanditatos.add(new MinistroId(30L));
		ministrosCanditatos.add(new MinistroId(48L));
		ministrosCanditatos.add(new MinistroId(46L));
		ministrosCanditatos.add(new MinistroId(47L));
		ministrosCanditatos.add(new MinistroId(1L));
		
		PeticaoFisica peticao = preparaPeticao();
		ParametroDistribuicao parametros = new ParametroDistribuicao(TipoDistribuicao.COMUM, peticao.id(), "Familiares ou amigos relacionados aos ministros impedidos.", "DISTRIBUIDOR", ministrosCanditatos, null, null);
		Distribuicao.criar(parametros);
	}

	private PeticaoFisica preparaPeticao() {
		PeticaoFisica peticao = new PeticaoFisica(new PeticaoId(1L), 1L, "DISTRIBUIDOR", 1, 1, FormaRecebimento.SEDEX, "BR123456789AD", TipoProcesso.ORIGINARIO);
		TipoPeca tipo = new TipoPeca(new TipoDocumentoId(1L), "Petição Inicial");
		
		peticao.adicionarPeca(new PecaPeticao(new DocumentoId(1L), tipo, tipo.nome(), Visibilidade.PUBLICO, Situacao.JUNTADA));
		peticao.adicionarParte(new PartePeticao(new PessoaId(1L), TipoPolo.POLO_ATIVO));
		peticao.preautuar(new ClasseId("ADI"), null);
		peticao.aceitar(new ClasseId("ADI"));
		peticao.associarProcessoWorkflow(new ProcessoWorkflow(new ProcessoWorkflowId(1L), "A_PREAUTUAR"));
		return peticao;
	}
	
}
