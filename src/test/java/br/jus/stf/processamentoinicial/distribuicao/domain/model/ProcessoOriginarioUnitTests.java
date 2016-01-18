package br.jus.stf.processamentoinicial.distribuicao.domain.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import br.jus.stf.processamentoinicial.autuacao.domain.model.PartePeticao;
import br.jus.stf.processamentoinicial.autuacao.domain.model.PecaPeticao;
import br.jus.stf.processamentoinicial.suporte.domain.model.Parte;
import br.jus.stf.processamentoinicial.suporte.domain.model.Peca;
import br.jus.stf.processamentoinicial.suporte.domain.model.TipoPeca;
import br.jus.stf.processamentoinicial.suporte.domain.model.TipoPolo;
import br.jus.stf.shared.ClasseId;
import br.jus.stf.shared.DocumentoId;
import br.jus.stf.shared.MinistroId;
import br.jus.stf.shared.PessoaId;
import br.jus.stf.shared.PeticaoId;
import br.jus.stf.shared.ProcessoId;

public class ProcessoOriginarioUnitTests {
	
	private Set<ParteProcesso> partes;
	private Set<PecaProcesso> pecas;
	
	@Before
	public void setUp() {
		partes = new HashSet<ParteProcesso>(0);
		partes.add(new ParteProcesso(new PessoaId(1L), TipoPolo.POLO_ATIVO));
		partes.add(new ParteProcesso(new PessoaId(2L), TipoPolo.POLO_PASSIVO));
		partes.add(new ParteProcesso(new PessoaId(3L), TipoPolo.POLO_PASSIVO));

		pecas = new LinkedHashSet<PecaProcesso>(0);
		pecas.add(new PecaProcesso(new DocumentoId(1L), new TipoPeca(1L, "Petição inicial"), "Petição inicial"));
	}
	
	@Test
	public void criaProcessoValido() {
		Processo processo = processo();

	    assertNotNull(processo);
	    assertEquals(new ProcessoId(1L), processo.id());
	    assertEquals(new ClasseId("HD"), processo.classe());
	    assertEquals(new Long(1), processo.numero());
	    assertEquals(new MinistroId(1L), processo.relator());
	    assertEquals(new PeticaoId(1L), processo.peticao());
	    assertEquals(1, processo.partesPoloAtivo().size());
		assertEquals(2, processo.partesPoloPassivo().size());
		assertEquals(pecas, processo.pecas());
	    
		// Atributos cujos valores são calculados
		assertEquals("HD 1", processo.identificacao());
	}

	private Processo processo() {
		return new ProcessoOriginario(new ProcessoId(1L), new ClasseId("HD"), 1L, new MinistroId(1L), new PeticaoId(1L), partes, pecas, ProcessoSituacao.DISTRIBUIDO, null);
	}
	
	@Test(expected = NullPointerException.class)
	public void tentaCriarProcessoComIdNulo() {
		new ProcessoOriginario(null, new ClasseId("HD"), 1L, new MinistroId(1L), new PeticaoId(1L), partes, pecas, ProcessoSituacao.DISTRIBUIDO, null);
	}
	
	@Test(expected = NullPointerException.class)
	public void tentaCriarProcessoComClasseNula() {
		new ProcessoOriginario(new ProcessoId(1L), null, 1L, new MinistroId(1L), new PeticaoId(1L), partes, pecas, ProcessoSituacao.DISTRIBUIDO, null);
	}
	
	@Test(expected = NullPointerException.class)
	public void tentaCriarProcessoComNumeroNulo() {
		new ProcessoOriginario(new ProcessoId(1L), new ClasseId("HD"), null, new MinistroId(1L), new PeticaoId(1L), partes, pecas, ProcessoSituacao.DISTRIBUIDO, null);
	}
	
	@Test(expected = NullPointerException.class)
	public void tentaCriarProcessoComRelatorNulo() {
		new ProcessoOriginario(new ProcessoId(1L), new ClasseId("HD"), 1L, null, new PeticaoId(1L), partes, pecas, ProcessoSituacao.DISTRIBUIDO, null);
	}
	
	@Test(expected = NullPointerException.class)
	public void tentaCriarProcessoComPeticaoNula() {
		new ProcessoOriginario(new ProcessoId(1L), new ClasseId("HD"), 1L, new MinistroId(1L), null, partes, pecas, ProcessoSituacao.DISTRIBUIDO, null);
	}
	
	@Test
	public void comparaProcessosIguais() {
		Processo processo1 = processo();
		Processo processo2 = processo();
		
		assertTrue(processo1.equals(processo2));
	}
	
	@Test
	public void comparaProcessosComIdentidadesIguais() {
		Processo processo1 = processo();
		Processo processo2 = processo();
		
		assertTrue(processo1.sameIdentityAs(processo2));
	}
	
	@Test
	public void comparaProcessosComHashesIguais() {
		Processo processo1 = processo();
		Processo processo2 = processo();
		
		assertTrue(processo1.hashCode() == processo2.hashCode());
	}
	
	@Test
	public void comparaProcessosDiferentes() {
		Processo processo1 = processo();
		Processo processo2 = new ProcessoOriginario(new ProcessoId(2L), new ClasseId("HC"), 1L, new MinistroId(2L), new PeticaoId(2L), partes, pecas, ProcessoSituacao.DISTRIBUIDO, null);
		
		assertFalse(processo1.equals(processo2));
	}
	
	@Test
	public void comparaProcessosComIdentidadesDiferentes() {
		Processo processo1 = processo();
		Processo processo2 = new ProcessoOriginario(new ProcessoId(2L), new ClasseId("HC"), 1L, new MinistroId(2L), new PeticaoId(2L), partes, pecas, ProcessoSituacao.DISTRIBUIDO, null);
		
		assertFalse(processo1.sameIdentityAs(processo2));
	}
	
	@Test
	public void comparaProcessosComHashesDiferentes() {
		Processo processo1 = processo();
		Processo processo2 = new ProcessoOriginario(new ProcessoId(2L), new ClasseId("HC"), 1L, new MinistroId(2L), new PeticaoId(2L), partes, pecas, ProcessoSituacao.DISTRIBUIDO, null);
		
		assertFalse(processo1.hashCode() == processo2.hashCode());
	}
	
	@Test
	public void adicionaPecaAProcesso() {
		Processo processo = processo();
		Peca peca = new PecaPeticao(new DocumentoId(1L), new TipoPeca(1L, "Petição inicial"), "Petição inicial");
		
		processo.adicionarPeca(peca);
		assertEquals(1, processo.pecas().size());
		assertTrue(processo.pecas().contains(peca));
	}
	
	@Test(expected = NullPointerException.class)
	public void tentaAdicionarPecaAProcessoInformandoNulo() {
		Processo processo = processo();
		
		processo.adicionarPeca(null);
	}
	
	@Test
	public void removePecaDaProcesso() {
		Processo processo = processo();
		Peca peca = new PecaPeticao(new DocumentoId(1L), new TipoPeca(1L, "Petição inicial"), "Petição inicial");
		
		processo.adicionarPeca(peca);
		processo.removerPeca(peca);
		assertEquals(0, processo.pecas().size());
		assertFalse(processo.pecas().contains(peca));
	}
	
	@Test(expected = NullPointerException.class)
	public void tentaRemoverPecaDaProcessoInformandoNulo() {
		Processo processo = processo();
		
		processo.removerPeca(null);
	}
	
	@Test
	public void adicionaPartePoloAtivoAProcesso() {
		Processo processo = processo();
		Parte partePoloAtivo = new PartePeticao(new PessoaId(1L), TipoPolo.POLO_ATIVO);
		
		processo.adicionarParte(partePoloAtivo);
		assertEquals(1, processo.partesPoloAtivo().size());
		assertTrue(processo.partesPoloAtivo().contains(partePoloAtivo));
	}
	
	@Test
	public void adicionaPartePoloPassivoAProcesso() {
		Processo processo = processo();
		Parte partePoloPassivo = new PartePeticao(new PessoaId(1L), TipoPolo.POLO_PASSIVO);
		
		processo.adicionarParte(partePoloPassivo);
		assertEquals(3, processo.partesPoloPassivo().size());
		assertTrue(processo.partesPoloPassivo().contains(partePoloPassivo));
	}
	
	@Test(expected = NullPointerException.class)
	public void tentaAdicionarParteAProcessoInformandoNulo() {
		Processo processo = processo();
		
		processo.adicionarParte(null);
	}
	
	@Test
	public void removePartePoloAtivoDaProcesso() {
		Processo processo = processo();
		Parte partePoloAtivo = new PartePeticao(new PessoaId(1L), TipoPolo.POLO_ATIVO);
		
		processo.adicionarParte(partePoloAtivo);
		processo.removerParte(partePoloAtivo);
		assertEquals(0, processo.partesPoloAtivo().size());
		assertFalse(processo.partesPoloAtivo().contains(partePoloAtivo));
	}
	
	@Test
	public void removePartePoloPassivoDaProcesso() {
		Processo processo = processo();
		Parte partePoloPassivo = new PartePeticao(new PessoaId(2L), TipoPolo.POLO_PASSIVO);
		
		processo.adicionarParte(partePoloPassivo);
		processo.removerParte(partePoloPassivo);
		assertEquals(1, processo.partesPoloPassivo().size());
		assertFalse(processo.pecas().contains(partePoloPassivo));
	}
	
	@Test(expected = NullPointerException.class)
	public void tentaRemoverParteDaProcessoInformandoNulo() {
		Processo processo = processo();
		
		processo.removerParte(null);
	}
	
}
