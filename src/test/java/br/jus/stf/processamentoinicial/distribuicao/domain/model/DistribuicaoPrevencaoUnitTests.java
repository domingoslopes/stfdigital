package br.jus.stf.processamentoinicial.distribuicao.domain.model;

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

public class DistribuicaoPrevencaoUnitTests {
	
	@InjectMocks
	private ProcessoFactory mockProcessoFactory;
	
	@Mock
	private ProcessoRepository mockProcessoRepository;
	
	@Mock
	private Processo mockProcesso;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		
		when(mockProcessoRepository.nextId()).thenReturn(new ProcessoId(2L));
		when(mockProcessoRepository.nextNumero(new ClasseId("ADI"))).thenReturn(2L);
		
		when(mockProcesso.relator()).thenReturn(new MinistroId(1L));
	}
	
	@Test
	public void realizaDistribuicaoPrevencaoValida() {
		Set<Processo> processosPreventos = new HashSet<Processo>();
		PeticaoFisica peticao = new PeticaoFisica(new PeticaoId(1L), 1L, "DISTRIBUIDOR", 1, 1, FormaRecebimento.SEDEX, "BR123456789AD");
		TipoPeca tipo = new TipoPeca(1L, "Petição Inicial");
		
		peticao.juntar(new PecaPeticao(new DocumentoId(1L), tipo, tipo.nome()));
		peticao.adicionarParte(new PartePeticao(new PessoaId(1L), TipoPolo.POLO_ATIVO));
		peticao.preautuar(new ClasseId("ADI"));
		peticao.aceitar(new ClasseId("ADI"));
		peticao.associarProcessoWorkflow(new ProcessoWorkflowId(1L));
		processosPreventos.add(mockProcesso);
		
		Peticao peticaoVO = new Peticao(peticao.id(), peticao.classeProcessual(), peticao.partesPoloAtivo(), peticao.pecas(), peticao.processosWorkflow().iterator().next());
		ParametroDistribuicao parametros = new ParametroDistribuicao(peticaoVO, "Assuntos correlatos.", "DISTRIBUIDOR", null, null, processosPreventos);
		Distribuicao distribuicao = new DistribuicaoPrevencao(parametros);
		
		Assert.assertEquals(mockProcesso.relator(), distribuicao.executar().relator());
	}
	
}
