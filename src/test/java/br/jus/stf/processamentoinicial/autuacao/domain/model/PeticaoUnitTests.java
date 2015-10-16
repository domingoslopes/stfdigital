package br.jus.stf.processamentoinicial.autuacao.domain.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.jus.stf.processamentoinicial.distribuicao.domain.model.Processo;
import br.jus.stf.processamentoinicial.distribuicao.domain.model.ProcessoFactory;
import br.jus.stf.processamentoinicial.distribuicao.domain.model.ProcessoRepository;
import br.jus.stf.shared.ClasseId;
import br.jus.stf.shared.DocumentoId;
import br.jus.stf.shared.MinistroId;
import br.jus.stf.shared.PessoaId;
import br.jus.stf.shared.PeticaoId;
import br.jus.stf.shared.ProcessoId;
import br.jus.stf.shared.ProcessoWorkflowId;

public class PeticaoUnitTests {
	
	@InjectMocks
	private ProcessoFactory mockProcessoFactory;
	
	@Mock
	private ProcessoRepository mockProcessoRepository;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void criaPeticaoValida() {
		Peticao peticao = new PeticaoImpl(new PeticaoId(1L), 5L);

	    assertNotNull(peticao);
	    assertEquals(peticao.id(), new PeticaoId(1L));
	    assertEquals(peticao.numero(), new Long(5));
	    
		// Atributos com valores são calculados
		assertFalse(peticao.isEletronica());
		assertFalse(peticao.hasRepresentacao());
		assertEquals(peticao.ano(), new Short("2015"));
	    assertEquals(peticao.identificacao(), "5/2015");
	    
	    // Estado inicial esperado para esses atributos
	    assertNull(peticao.classeSugerida());
		assertTrue(peticao.partesPoloAtivo().size() == 0);
		assertTrue(peticao.partesPoloPassivo().size() == 0);
		assertTrue(peticao.pecas().size() == 0);
		assertTrue(peticao.processosWorkflow().size() == 0);
		assertNull(peticao.classeProcessual());
		assertNull(peticao.motivoRejeicao());
	}
	
	@Test(expected = NullPointerException.class)
	public void tentaCriarPeticaoComIdNulo() {
		new PeticaoImpl(null, 1L);
	}
	
	@Test(expected = NullPointerException.class)
	public void tentaCriarPeticaoComNumeroNulo() {
		new PeticaoImpl(new PeticaoId(1L), null);
	}
	
	@Test
	public void sugeriClassePeticaoValida() {
		Peticao peticao = new PeticaoImpl(new PeticaoId(1L), 5L);
		
		peticao.sugerirClasse(new ClasseId("ADI"));
		
		assertEquals(peticao.classeSugerida(), new ClasseId("ADI"));
	}
	
	@Test(expected = NullPointerException.class)
	public void tentaSugerirClassePeticaoSemInformarClasse() {
		Peticao peticao = new PeticaoImpl(new PeticaoId(1L), 5L);
		
		peticao.sugerirClasse(null);
	}
	
	@Test
	public void aceitaPeticaoValida() {
		Peticao peticao = new PeticaoImpl(new PeticaoId(1L), 5L);
		
		peticao.sugerirClasse(new ClasseId("ADI"));
		peticao.aceitar(new ClasseId("ADO"));
		
		assertEquals(peticao.classeProcessual(), new ClasseId("ADO"));
	}
	
	@Test(expected = NullPointerException.class)
	public void tentaAceitarPeticaoSemClasseProcessual() {
		Peticao peticao = new PeticaoImpl(new PeticaoId(1L), 5L);
		
		peticao.sugerirClasse(new ClasseId("ADI"));
		peticao.aceitar(null);
	}
	
	@Test(expected = NullPointerException.class)
	public void tentaAceitarPeticaoSemSugerirClasse() {
		Peticao peticao = new PeticaoImpl(new PeticaoId(1L), 5L);
		
		peticao.aceitar(new ClasseId("ADO"));
	}
	
	@Test
	public void rejeitaPeticaoValida() {
		Peticao peticao = new PeticaoImpl(new PeticaoId(1L), 5L);
		
		peticao.sugerirClasse(new ClasseId("ADI"));
		peticao.rejeitar("Dados incompletos.");
		
		assertEquals(peticao.motivoRejeicao(), "Dados incompletos.");
	}
	
	@Test(expected = NullPointerException.class)
	public void tentaRejeitarPeticaoComMotivoNulo() {
		Peticao peticao = new PeticaoImpl(new PeticaoId(1L), 5L);
		
		peticao.sugerirClasse(new ClasseId("ADI"));
		peticao.rejeitar(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void tentaRejeitarPeticaoComMotivoBranco() {
		Peticao peticao = new PeticaoImpl(new PeticaoId(1L), 5L);
		
		peticao.sugerirClasse(new ClasseId("ADI"));
		peticao.rejeitar("");
	}
	
	@Test(expected = NullPointerException.class)
	public void tentaRejeitarPeticaoSemSugerirClasse() {
		Peticao peticao = new PeticaoImpl(new PeticaoId(1L), 5L);
		
		peticao.rejeitar("Dados incompletos.");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void tentaRejeitarPeticaoAceita() {
		Peticao peticao = new PeticaoImpl(new PeticaoId(1L), 5L);
		
		peticao.sugerirClasse(new ClasseId("ADI"));
		peticao.aceitar(new ClasseId("ADO"));
		peticao.rejeitar("Dados incompletos.");
	}

	@Test
	public void distribuiPeticaoValida() {
		ClasseId classeProcessual = new ClasseId("ADI");
		
		when(mockProcessoRepository.nextId()).thenReturn(new ProcessoId(1L));
		when(mockProcessoRepository.nextNumero(classeProcessual)).thenReturn(1L);
		
		Peticao peticao = new PeticaoImpl(new PeticaoId(1L), 5L);
		
		peticao.sugerirClasse(new ClasseId("ADO"));
		peticao.aceitar(classeProcessual);
		Processo processo = peticao.distribuir(new MinistroId(1L));
		
		assertNotNull(processo);
		assertEquals(processo.relator(), new MinistroId(1L));
		assertEquals(processo.peticao(), peticao.id());
		assertEquals(processo.classe(), classeProcessual);
		
		verify(mockProcessoRepository, times(1)).nextId();
		verify(mockProcessoRepository, times(1)).nextNumero(classeProcessual);
	}
	
	@Test(expected = NullPointerException.class)
	public void tentaDistribuirPeticaoSemRelator() {
		Peticao peticao = new PeticaoImpl(new PeticaoId(1L), 5L);
		
		peticao.sugerirClasse(new ClasseId("ADI"));
		peticao.aceitar(new ClasseId("ADI"));
		peticao.distribuir(null);
	}
	
	@Test(expected = NullPointerException.class)
	public void tentaDistribuirPeticaoSemAceitacao() {
		Peticao peticao = new PeticaoImpl(new PeticaoId(1L), 5L);
		
		peticao.distribuir(new MinistroId(1L));
	}
	
	@Test(expected = NullPointerException.class)
	public void tentaDistribuirPeticaoRejeitada() {
		Peticao peticao = new PeticaoImpl(new PeticaoId(1L), 5L);
		
		peticao.sugerirClasse(new ClasseId("ADI"));
		peticao.rejeitar("Dados incompletos.");
		peticao.distribuir(new MinistroId(1L));
	}
	
	@Test
	public void comparaPeticoesIguais() {
		Peticao peticao1 = new PeticaoImpl(new PeticaoId(1L), 5L);
		Peticao peticao2 = new PeticaoImpl(new PeticaoId(1L), 5L);
		
		assertTrue(peticao1.equals(peticao2));
	}
	
	@Test
	public void comparaPeticoesComIdentidadesIguais() {
		Peticao peticao1 = new PeticaoImpl(new PeticaoId(1L), 5L);
		Peticao peticao2 = new PeticaoImpl(new PeticaoId(1L), 5L);
		
		assertTrue(peticao1.sameIdentityAs(peticao2));
	}
	
	@Test
	public void comparaPeticoesComHashesIguais() {
		Peticao peticao1 = new PeticaoImpl(new PeticaoId(1L), 5L);
		Peticao peticao2 = new PeticaoImpl(new PeticaoId(1L), 5L);
		
		assertTrue(peticao1.hashCode() == peticao2.hashCode());
	}
	
	@Test
	public void comparaPeticoesDiferentes() {
		Peticao peticao1 = new PeticaoImpl(new PeticaoId(1L), 5L);
		Peticao peticao2 = new PeticaoImpl(new PeticaoId(2L), 4L);
		
		assertFalse(peticao1.equals(peticao2));
	}
	
	@Test
	public void comparaPeticoesComIdentidadesDiferentes() {
		Peticao peticao1 = new PeticaoImpl(new PeticaoId(1L), 5L);
		Peticao peticao2 = new PeticaoImpl(new PeticaoId(2L), 4L);
		
		assertFalse(peticao1.sameIdentityAs(peticao2));
	}
	
	@Test
	public void comparaPeticoesComHashesDiferentes() {
		Peticao peticao1 = new PeticaoImpl(new PeticaoId(1L), 5L);
		Peticao peticao2 = new PeticaoImpl(new PeticaoId(2L), 4L);
		
		assertFalse(peticao1.hashCode() == peticao2.hashCode());
	}
	
	@Test
	public void associaPeticaoComWorkflow() {
		Peticao peticao = new PeticaoImpl(new PeticaoId(1L), 5L);
		
		peticao.associarProcessoWorkflow(new ProcessoWorkflowId(1L));
		assertTrue(peticao.processosWorkflow().contains(new ProcessoWorkflowId(1L)));
	}
	
	@Test(expected = NullPointerException.class)
	public void tentaAssociarPeticaoComWorkflowSemInformarProcesso() {
		Peticao peticao = new PeticaoImpl(new PeticaoId(1L), 5L);
		
		peticao.associarProcessoWorkflow(null);
	}
	
	@Test
	public void adicionaPecaAPeticao() {
		Peticao peticao = new PeticaoImpl(new PeticaoId(1L), 5L);
		Peca peca = new PecaPeticao(new DocumentoId(1L), new TipoPeca(1L, "Petição inicial"), "Petição inicial");
		
		assertTrue(peticao.pecas().size() == 0);
		peticao.adicionarPeca(peca);
		assertTrue(peticao.pecas().size() == 1);
		assertTrue(peticao.pecas().contains(peca));
	}
	
	@Test(expected = NullPointerException.class)
	public void tentaAdicionarPecaAPeticaoInformandoNulo() {
		Peticao peticao = new PeticaoImpl(new PeticaoId(1L), 5L);
		
		peticao.adicionarPeca(null);
	}
	
	@Test
	public void removePecaDaPeticao() {
		Peticao peticao = new PeticaoImpl(new PeticaoId(1L), 5L);
		Peca peca = new PecaPeticao(new DocumentoId(1L), new TipoPeca(1L, "Petição inicial"), "Petição inicial");
		
		peticao.adicionarPeca(peca);
		assertTrue(peticao.pecas().size() == 1);
		peticao.removerPeca(peca);
		assertTrue(peticao.pecas().size() == 0);
		assertFalse(peticao.pecas().contains(peca));
	}
	
	@Test(expected = NullPointerException.class)
	public void tentaRemoverPecaDaPeticaoInformandoNulo() {
		Peticao peticao = new PeticaoImpl(new PeticaoId(1L), 5L);
		
		peticao.removerPeca(null);
	}
	
	@Test
	public void adicionaPartePoloAtivoAPeticao() {
		Peticao peticao = new PeticaoImpl(new PeticaoId(1L), 5L);
		
		assertTrue(peticao.partesPoloAtivo().size() == 0);
		Parte partePoloAtivo = new PartePeticao(new PessoaId(1L), TipoPolo.POLO_ATIVO);
		
		peticao.adicionarParte(partePoloAtivo);
		assertTrue(peticao.partesPoloAtivo().size() == 1);
		assertTrue(peticao.partesPoloAtivo().contains(partePoloAtivo));
	}
	
	@Test
	public void adicionaPartePoloPassivoAPeticao() {
		Peticao peticao = new PeticaoImpl(new PeticaoId(1L), 5L);
		
		assertTrue(peticao.partesPoloPassivo().size() == 0);
		Parte partePoloPassivo = new PartePeticao(new PessoaId(1L), TipoPolo.POLO_PASSIVO);
		
		peticao.adicionarParte(partePoloPassivo);
		assertTrue(peticao.partesPoloPassivo().size() == 1);
		assertTrue(peticao.partesPoloPassivo().contains(partePoloPassivo));
	}
	
	@Test(expected = NullPointerException.class)
	public void tentaAdicionarParteAPeticaoInformandoNulo() {
		Peticao peticao = new PeticaoImpl(new PeticaoId(1L), 5L);
		
		peticao.adicionarParte(null);
	}
	
	@Test
	public void removePartePoloAtivoDaPeticao() {
		Peticao peticao = new PeticaoImpl(new PeticaoId(1L), 5L);
		
		Parte partePoloAtivo = new PartePeticao(new PessoaId(1L), TipoPolo.POLO_ATIVO);
		peticao.adicionarParte(partePoloAtivo);
		
		peticao.removerParte(partePoloAtivo);
		assertTrue(peticao.partesPoloAtivo().size() == 0);
		assertFalse(peticao.partesPoloAtivo().contains(partePoloAtivo));
	}
	
	@Test
	public void removePartePoloPassivoDaPeticao() {
		Peticao peticao = new PeticaoImpl(new PeticaoId(1L), 5L);
		
		Parte partePoloPassivo = new PartePeticao(new PessoaId(2L), TipoPolo.POLO_PASSIVO);
		peticao.adicionarParte(partePoloPassivo);
		
		peticao.removerParte(partePoloPassivo);
		assertTrue(peticao.partesPoloPassivo().size() == 0);
		assertFalse(peticao.pecas().contains(partePoloPassivo));
	}
	
	@Test(expected = NullPointerException.class)
	public void tentaRemoverParteDaPeticaoInformandoNulo() {
		Peticao peticao = new PeticaoImpl(new PeticaoId(1L), 5L);
		
		peticao.removerParte(null);
	}
	
	// Innerclass que possibilita o teste dos métodos concretos em Peticao
	private class PeticaoImpl extends Peticao {
		PeticaoImpl(final PeticaoId id, final Long numero) {
			super(id, numero);
		}

		public boolean hasRepresentacao() {
			return false;
		}
	}
	
}
