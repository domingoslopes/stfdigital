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
import br.jus.stf.processamentoinicial.autuacao.domain.model.TipoPeca;
import br.jus.stf.processamentoinicial.autuacao.domain.model.TipoPolo;
import br.jus.stf.shared.ClasseId;
import br.jus.stf.shared.DocumentoId;
import br.jus.stf.shared.MinistroId;
import br.jus.stf.shared.PessoaId;
import br.jus.stf.shared.PeticaoId;
import br.jus.stf.shared.ProcessoId;
import br.jus.stf.shared.ProcessoWorkflowId;

public class DistribuicaoComumUnitTests {
	
	@InjectMocks
	private ProcessoFactory mockProcessoFactory;
	
	@Mock
	private ProcessoRepository mockProcessoRepository;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		
		when(mockProcessoRepository.nextId()).thenReturn(new ProcessoId(1L));
		when(mockProcessoRepository.nextNumero(new ClasseId("ADI"))).thenReturn(1L);
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
		
		PeticaoFisica peticao = new PeticaoFisica(new PeticaoId(1L), 1L, "DISTRIBUIDOR", 1, 1, FormaRecebimento.SEDEX, "BR123456789AD");
		TipoPeca tipo = new TipoPeca(1L, "Petição Inicial");
		
		peticao.juntar(new PecaPeticao(new DocumentoId(1L), tipo, tipo.nome()));
		peticao.adicionarParte(new PartePeticao(new PessoaId(1L), TipoPolo.POLO_ATIVO));
		peticao.preautuar(new ClasseId("ADI"));
		peticao.aceitar(new ClasseId("ADI"));
		peticao.associarProcessoWorkflow(new ProcessoWorkflowId(1L));
		
		Peticao peticaoVO = new Peticao(peticao.id(), peticao.classeProcessual(), peticao.partesPoloAtivo(), peticao.pecas(), peticao.processosWorkflow().iterator().next());
		ParametroDistribuicao parametros = new ParametroDistribuicao(peticaoVO, "Familiares ou amigos relacionados aos ministros impedidos.", "DISTRIBUIDOR", ministrosCanditatos, ministrosImpedidos, null);
		Distribuicao distribuicao = new DistribuicaoComum(parametros);
		MinistroId relator = distribuicao.executar().relator();
		
		Assert.assertTrue(ministrosCanditatos.contains(relator));
		Assert.assertFalse(ministrosImpedidos.contains(relator));
		
		verify(mockProcessoRepository, times(1)).nextId();
		verify(mockProcessoRepository, times(1)).nextNumero(new ClasseId("ADI"));
	}
	
}
