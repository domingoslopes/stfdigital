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
		Peticao peticao = new PeticaoImpl(new PeticaoId(1L), 5L, "PETICIONADOR");

	    assertNotNull(peticao);
	    assertEquals(new PeticaoId(1L), peticao.id());
	    assertEquals(new Long(5), peticao.numero());
	    assertEquals("PETICIONADOR", peticao.usuarioCadastramento());
	    
		// Atributos com valores são calculados
		assertFalse(peticao.isEletronica());
		assertFalse(peticao.hasRepresentacao());
		
	    // Estado inicial esperado para esses atributos
	    assertNull(peticao.classeSugerida());
		assertEquals(0, peticao.partesPoloAtivo().size());
		assertEquals(0, peticao.partesPoloPassivo().size());
		assertEquals(0, peticao.pecas().size());
		assertEquals(0, peticao.processosWorkflow().size());
		assertNull(peticao.classeProcessual());
		assertNull(peticao.motivoRejeicao());
	}
	
	@Test(expected = NullPointerException.class)
	public void tentaCriarPeticaoComUsuarioCadastramentoNulo() {
		new PeticaoImpl(new PeticaoId(1L), 1L, null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void tentaCriarPeticaoComUsuarioCadastramentoBranco() {
		new PeticaoImpl(new PeticaoId(1L), 1L, "");
	}
	
	@Test(expected = NullPointerException.class)
	public void tentaCriarPeticaoComIdNulo() {
		new PeticaoImpl(null, 1L, "PETICIONADOR");
	}
	
	@Test(expected = NullPointerException.class)
	public void tentaCriarPeticaoComNumeroNulo() {
		new PeticaoImpl(new PeticaoId(1L), null, "PETICIONADOR");
	}
	
	@Test
	public void sugeriClassePeticaoValida() {
		Peticao peticao = new PeticaoImpl(new PeticaoId(1L), 5L, "PETICIONADOR");
		
		peticao.sugerirClasse(new ClasseId("ADI"));
		
		assertEquals(new ClasseId("ADI"), peticao.classeSugerida());
	}
	
	@Test(expected = NullPointerException.class)
	public void tentaSugerirClassePeticaoSemInformarClasse() {
		Peticao peticao = new PeticaoImpl(new PeticaoId(1L), 5L, "PETICIONADOR");
		
		peticao.sugerirClasse(null);
	}
	
	@Test
	public void aceitaPeticaoValida() {
		Peticao peticao = new PeticaoImpl(new PeticaoId(1L), 5L, "PETICIONADOR");
		
		peticao.sugerirClasse(new ClasseId("ADI"));
		peticao.aceitar(new ClasseId("ADO"));
		
		assertEquals(new ClasseId("ADO"), peticao.classeProcessual());
	}
	
	@Test(expected = NullPointerException.class)
	public void tentaAceitarPeticaoSemClasseProcessual() {
		Peticao peticao = new PeticaoImpl(new PeticaoId(1L), 5L, "PETICIONADOR");
		
		peticao.sugerirClasse(new ClasseId("ADI"));
		peticao.aceitar(null);
	}
	
	@Test(expected = NullPointerException.class)
	public void tentaAceitarPeticaoSemSugerirClasse() {
		Peticao peticao = new PeticaoImpl(new PeticaoId(1L), 5L, "PETICIONADOR");
		
		peticao.aceitar(new ClasseId("ADO"));
	}
	
	@Test
	public void rejeitaPeticaoValida() {
		Peticao peticao = new PeticaoImpl(new PeticaoId(1L), 5L, "PETICIONADOR");
		
		peticao.sugerirClasse(new ClasseId("ADI"));
		peticao.rejeitar("Dados incompletos.");
		
		assertEquals("Dados incompletos.", peticao.motivoRejeicao());
	}
	
	@Test(expected = NullPointerException.class)
	public void tentaRejeitarPeticaoComMotivoNulo() {
		Peticao peticao = new PeticaoImpl(new PeticaoId(1L), 5L, "PETICIONADOR");
		
		peticao.sugerirClasse(new ClasseId("ADI"));
		peticao.rejeitar(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void tentaRejeitarPeticaoComMotivoBranco() {
		Peticao peticao = new PeticaoImpl(new PeticaoId(1L), 5L, "PETICIONADOR");
		
		peticao.sugerirClasse(new ClasseId("ADI"));
		peticao.rejeitar("");
	}
	
	@Test(expected = NullPointerException.class)
	public void tentaRejeitarPeticaoSemSugerirClasse() {
		Peticao peticao = new PeticaoImpl(new PeticaoId(1L), 5L, "PETICIONADOR");
		
		peticao.rejeitar("Dados incompletos.");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void tentaRejeitarPeticaoAceita() {
		Peticao peticao = new PeticaoImpl(new PeticaoId(1L), 5L, "PETICIONADOR");
		
		peticao.sugerirClasse(new ClasseId("ADI"));
		peticao.aceitar(new ClasseId("ADO"));
		peticao.rejeitar("Dados incompletos.");
	}

	@Test
	public void distribuiPeticaoValida() {
		ClasseId classeProcessual = new ClasseId("ADI");
		
		when(mockProcessoRepository.nextId()).thenReturn(new ProcessoId(1L));
		when(mockProcessoRepository.nextNumero(classeProcessual)).thenReturn(1L);
		
		Peticao peticao = new PeticaoImpl(new PeticaoId(1L), 5L, "PETICIONADOR");
		
		peticao.sugerirClasse(new ClasseId("ADO"));
		peticao.aceitar(classeProcessual);
		Processo processo = peticao.distribuir(new MinistroId(1L));
		
		assertNotNull(processo);
		assertEquals(new MinistroId(1L), processo.relator());
		assertEquals(new PeticaoId(1L), processo.peticao());
		assertEquals(classeProcessual, processo.classe());
		
		verify(mockProcessoRepository, times(1)).nextId();
		verify(mockProcessoRepository, times(1)).nextNumero(classeProcessual);
	}
	
	@Test(expected = NullPointerException.class)
	public void tentaDistribuirPeticaoSemRelator() {
		Peticao peticao = new PeticaoImpl(new PeticaoId(1L), 5L, "PETICIONADOR");
		
		peticao.sugerirClasse(new ClasseId("ADI"));
		peticao.aceitar(new ClasseId("ADI"));
		peticao.distribuir(null);
	}
	
	@Test(expected = NullPointerException.class)
	public void tentaDistribuirPeticaoSemAceitacao() {
		Peticao peticao = new PeticaoImpl(new PeticaoId(1L), 5L, "PETICIONADOR");
		
		peticao.distribuir(new MinistroId(1L));
	}
	
	@Test(expected = NullPointerException.class)
	public void tentaDistribuirPeticaoRejeitada() {
		Peticao peticao = new PeticaoImpl(new PeticaoId(1L), 5L, "PETICIONADOR");
		
		peticao.sugerirClasse(new ClasseId("ADI"));
		peticao.rejeitar("Dados incompletos.");
		peticao.distribuir(new MinistroId(1L));
	}
	
	@Test
	public void comparaPeticoesIguais() {
		Peticao peticao1 = new PeticaoImpl(new PeticaoId(1L), 5L, "PETICIONADOR");
		Peticao peticao2 = new PeticaoImpl(new PeticaoId(1L), 5L, "PETICIONADOR");
		
		assertTrue(peticao1.equals(peticao2));
	}
	
	@Test
	public void comparaPeticoesComIdentidadesIguais() {
		Peticao peticao1 = new PeticaoImpl(new PeticaoId(1L), 5L, "PETICIONADOR");
		Peticao peticao2 = new PeticaoImpl(new PeticaoId(1L), 5L, "PETICIONADOR");
		
		assertTrue(peticao1.sameIdentityAs(peticao2));
	}
	
	@Test
	public void comparaPeticoesComHashesIguais() {
		Peticao peticao1 = new PeticaoImpl(new PeticaoId(1L), 5L, "PETICIONADOR");
		Peticao peticao2 = new PeticaoImpl(new PeticaoId(1L), 5L, "PETICIONADOR");
		
		assertTrue(peticao1.hashCode() == peticao2.hashCode());
	}
	
	@Test
	public void comparaPeticoesDiferentes() {
		Peticao peticao1 = new PeticaoImpl(new PeticaoId(1L), 5L, "PETICIONADOR");
		Peticao peticao2 = new PeticaoImpl(new PeticaoId(2L), 4L, "PETICIONADOR");
		
		assertFalse(peticao1.equals(peticao2));
	}
	
	@Test
	public void comparaPeticoesComIdentidadesDiferentes() {
		Peticao peticao1 = new PeticaoImpl(new PeticaoId(1L), 5L, "PETICIONADOR");
		Peticao peticao2 = new PeticaoImpl(new PeticaoId(2L), 4L, "PETICIONADOR");
		
		assertFalse(peticao1.sameIdentityAs(peticao2));
	}
	
	@Test
	public void comparaPeticoesComHashesDiferentes() {
		Peticao peticao1 = new PeticaoImpl(new PeticaoId(1L), 5L, "PETICIONADOR");
		Peticao peticao2 = new PeticaoImpl(new PeticaoId(2L), 4L, "PETICIONADOR");
		
		assertFalse(peticao1.hashCode() == peticao2.hashCode());
	}
	
	@Test
	public void associaPeticaoComWorkflow() {
		Peticao peticao = new PeticaoImpl(new PeticaoId(1L), 5L, "PETICIONADOR");
		
		peticao.associarProcessoWorkflow(new ProcessoWorkflowId(1L));
		assertTrue(peticao.processosWorkflow().contains(new ProcessoWorkflowId(1L)));
	}
	
	@Test(expected = NullPointerException.class)
	public void tentaAssociarPeticaoComWorkflowSemInformarProcesso() {
		Peticao peticao = new PeticaoImpl(new PeticaoId(1L), 5L, "PETICIONADOR");
		
		peticao.associarProcessoWorkflow(null);
	}
	
	@Test
	public void adicionaPecaAPeticao() {
		Peticao peticao = new PeticaoImpl(new PeticaoId(1L), 5L, "PETICIONADOR");
		Peca peca = new PecaPeticao(new DocumentoId(1L), new TipoPeca(1L, "Petição inicial"), "Petição inicial");
		
		assertEquals(0, peticao.pecas().size());
		peticao.juntar(peca);
		assertEquals(1, peticao.pecas().size());
		assertTrue(peticao.pecas().contains(peca));
	}
	
	@Test(expected = NullPointerException.class)
	public void tentaAdicionarPecaAPeticaoInformandoNulo() {
		Peticao peticao = new PeticaoImpl(new PeticaoId(1L), 5L, "PETICIONADOR");
		
		peticao.juntar(null);
	}
	
	@Test
	public void removePecaDaPeticao() {
		Peticao peticao = new PeticaoImpl(new PeticaoId(1L), 5L, "PETICIONADOR");
		Peca peca = new PecaPeticao(new DocumentoId(1L), new TipoPeca(1L, "Petição inicial"), "Petição inicial");
		
		peticao.juntar(peca);
		assertEquals(1, peticao.pecas().size());
		peticao.removerPeca(peca);
		assertEquals(0, peticao.pecas().size());
		assertFalse(peticao.pecas().contains(peca));
	}
	
	@Test(expected = NullPointerException.class)
	public void tentaRemoverPecaDaPeticaoInformandoNulo() {
		Peticao peticao = new PeticaoImpl(new PeticaoId(1L), 5L, "PETICIONADOR");
		
		peticao.removerPeca(null);
	}
	
	@Test
	public void adicionaPartePoloAtivoAPeticao() {
		Peticao peticao = new PeticaoImpl(new PeticaoId(1L), 5L, "PETICIONADOR");
		
		assertEquals(0, peticao.partesPoloAtivo().size());
		Parte partePoloAtivo = new PartePeticao(new PessoaId(1L), TipoPolo.POLO_ATIVO);
		
		peticao.adicionarParte(partePoloAtivo);
		assertEquals(1, peticao.partesPoloAtivo().size());
		assertTrue(peticao.partesPoloAtivo().contains(partePoloAtivo));
	}
	
	@Test
	public void adicionaPartePoloPassivoAPeticao() {
		Peticao peticao = new PeticaoImpl(new PeticaoId(1L), 5L, "PETICIONADOR");
		
		assertEquals(0, peticao.partesPoloPassivo().size());
		Parte partePoloPassivo = new PartePeticao(new PessoaId(1L), TipoPolo.POLO_PASSIVO);
		
		peticao.adicionarParte(partePoloPassivo);
		assertEquals(1, peticao.partesPoloPassivo().size());
		assertTrue(peticao.partesPoloPassivo().contains(partePoloPassivo));
	}
	
	@Test(expected = NullPointerException.class)
	public void tentaAdicionarParteAPeticaoInformandoNulo() {
		Peticao peticao = new PeticaoImpl(new PeticaoId(1L), 5L, "PETICIONADOR");
		
		peticao.adicionarParte(null);
	}
	
	@Test
	public void removePartePoloAtivoDaPeticao() {
		Peticao peticao = new PeticaoImpl(new PeticaoId(1L), 5L, "PETICIONADOR");
		
		Parte partePoloAtivo = new PartePeticao(new PessoaId(1L), TipoPolo.POLO_ATIVO);
		peticao.adicionarParte(partePoloAtivo);
		
		peticao.removerParte(partePoloAtivo);
		assertEquals(0, peticao.partesPoloAtivo().size());
		assertFalse(peticao.partesPoloAtivo().contains(partePoloAtivo));
	}
	
	@Test
	public void removePartePoloPassivoDaPeticao() {
		Peticao peticao = new PeticaoImpl(new PeticaoId(1L), 5L, "PETICIONADOR");
		
		Parte partePoloPassivo = new PartePeticao(new PessoaId(2L), TipoPolo.POLO_PASSIVO);
		peticao.adicionarParte(partePoloPassivo);
		
		peticao.removerParte(partePoloPassivo);
		assertEquals(0, peticao.partesPoloPassivo().size());
		assertFalse(peticao.pecas().contains(partePoloPassivo));
	}
	
	@Test(expected = NullPointerException.class)
	public void tentaRemoverParteDaPeticaoInformandoNulo() {
		Peticao peticao = new PeticaoImpl(new PeticaoId(1L), 5L, "PETICIONADOR");
		
		peticao.removerParte(null);
	}
	
	// Innerclass que possibilita o teste dos métodos concretos em Peticao
	private class PeticaoImpl extends Peticao {
		PeticaoImpl(final PeticaoId id, final Long numero, final String usuarioCadastramento) {
			super(id, numero, usuarioCadastramento);
		}

		public boolean hasRepresentacao() {
			return false;
		}
	}
	
}
