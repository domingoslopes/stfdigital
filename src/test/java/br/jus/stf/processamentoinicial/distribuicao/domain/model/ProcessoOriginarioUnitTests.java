package br.jus.stf.processamentoinicial.distribuicao.domain.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Before;
import org.junit.Test;

import br.jus.stf.processamentoinicial.autuacao.domain.model.PecaPeticao;
import br.jus.stf.processamentoinicial.recursaledistribuicao.domain.model.ParteProcesso;
import br.jus.stf.processamentoinicial.recursaledistribuicao.domain.model.PecaProcesso;
import br.jus.stf.processamentoinicial.recursaledistribuicao.domain.model.Processo;
import br.jus.stf.processamentoinicial.recursaledistribuicao.domain.model.ProcessoOriginario;
import br.jus.stf.processamentoinicial.suporte.domain.model.Peca;
import br.jus.stf.processamentoinicial.suporte.domain.model.Situacao;
import br.jus.stf.processamentoinicial.suporte.domain.model.TipoPeca;
import br.jus.stf.processamentoinicial.suporte.domain.model.TipoPolo;
import br.jus.stf.processamentoinicial.suporte.domain.model.Visibilidade;
import br.jus.stf.shared.ClasseId;
import br.jus.stf.shared.DocumentoId;
import br.jus.stf.shared.MinistroId;
import br.jus.stf.shared.PessoaId;
import br.jus.stf.shared.PeticaoId;
import br.jus.stf.shared.ProcessoId;
import br.jus.stf.shared.TipoDocumentoId;

public class ProcessoOriginarioUnitTests {
	
	private Set<ParteProcesso> partes;
	private Set<PecaProcesso> pecas;
	private PecaProcesso peca;
	
	@Before
	public void setUp() {
		partes = new HashSet<ParteProcesso>(0);
		partes.add(new ParteProcesso(new PessoaId(1L), TipoPolo.POLO_ATIVO));
		partes.add(new ParteProcesso(new PessoaId(2L), TipoPolo.POLO_PASSIVO));
		partes.add(new ParteProcesso(new PessoaId(3L), TipoPolo.POLO_PASSIVO));

		pecas = new LinkedHashSet<PecaProcesso>(0);
		
		peca = new PecaProcesso(new DocumentoId(1L), new TipoPeca(new TipoDocumentoId(1L), "Petição inicial"), "Petição inicial", Visibilidade.PUBLICO, Situacao.JUNTADA);
		
		peca.atribuirSequencial(1L);
		pecas.add(peca);
	}
	
	@Test
	public void criaProcessoValido() {
		Processo processo = processo();

	    assertNotNull(processo);
	    assertEquals(new ProcessoId(1L), processo.id());
	    assertEquals(new ClasseId("HD"), processo.classe());
	    assertEquals(new Long(1), processo.numero());
	    assertEquals(null, processo.relator());
	    assertEquals(new PeticaoId(1L), processo.peticao());
	    assertEquals(1, processo.partesPoloAtivo().size());
		assertEquals(2, processo.partesPoloPassivo().size());
		assertTrue("Peças deveriam ter sido adicionadas.", CollectionUtils.isEqualCollection(pecas, processo.pecas()));
	    
		// Atributos cujos valores são calculados
		assertEquals("HD 1", processo.identificacao());
	}

	private Processo processo() {
		return new ProcessoOriginario(new ProcessoId(1L), new ClasseId("HD"), 1L, new PeticaoId(1L), partes, pecas, null);
	}
	
	@Test(expected = NullPointerException.class)
	public void tentaCriarProcessoComIdNulo() {
		new ProcessoOriginario(null, new ClasseId("HD"), 1L, new PeticaoId(1L), partes, pecas, null);
	}
	
	@Test(expected = NullPointerException.class)
	public void tentaCriarProcessoComClasseNula() {
		new ProcessoOriginario(new ProcessoId(1L), null, 1L, new PeticaoId(1L), partes, pecas, null);
	}
	
	@Test(expected = NullPointerException.class)
	public void tentaCriarProcessoComNumeroNulo() {
		new ProcessoOriginario(new ProcessoId(1L), new ClasseId("HD"), null, new PeticaoId(1L), partes, pecas, null);
	}
	
	@Test(expected = NullPointerException.class)
	public void tentaCriarProcessoComPeticaoNula() {
		new ProcessoOriginario(new ProcessoId(1L), new ClasseId("HD"), 1L, null, partes, pecas, null);
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
		Processo processo2 = new ProcessoOriginario(new ProcessoId(2L), new ClasseId("HC"), 1L, new PeticaoId(2L), partes, pecas, null);
		
		assertFalse(processo1.equals(processo2));
	}
	
	@Test
	public void comparaProcessosComIdentidadesDiferentes() {
		Processo processo1 = processo();
		Processo processo2 = new ProcessoOriginario(new ProcessoId(2L), new ClasseId("HC"), 1L, new PeticaoId(2L), partes, pecas, null);
		
		assertFalse(processo1.sameIdentityAs(processo2));
	}
	
	@Test
	public void comparaProcessosComHashesDiferentes() {
		Processo processo1 = processo();
		Processo processo2 = new ProcessoOriginario(new ProcessoId(2L), new ClasseId("HC"), 1L, new PeticaoId(2L), partes, pecas, null);
		
		assertFalse(processo1.hashCode() == processo2.hashCode());
	}
	
	@Test
	public void adicionaPecaAProcesso() {
		Processo processo = processo();
		Peca peca = new PecaPeticao(new DocumentoId(1L), new TipoPeca(new TipoDocumentoId(1L), "Petição inicial"), "Petição inicial", Visibilidade.PUBLICO, Situacao.JUNTADA);
		
		processo.adicionarPeca(peca);
		assertEquals(2, processo.pecas().size());
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
		Peca peca = new PecaProcesso(new DocumentoId(1L), new TipoPeca(new TipoDocumentoId(1L), "Petição inicial"), "Petição inicial", Visibilidade.PUBLICO, Situacao.JUNTADA);
		
		processo.adicionarPeca(peca);
		processo.removerPeca(peca);
		assertEquals(Situacao.EXCLUIDA, processo.pecas().get(processo.pecas().indexOf(peca)).situacao());
	}
	
	@Test(expected = NullPointerException.class)
	public void tentaRemoverPecaDaProcessoInformandoNulo() {
		Processo processo = processo();
		
		processo.removerPeca(null);
	}
	
	@Test
	public void adicionaPartePoloAtivoAProcesso() {
		Processo processo = processo();
		ParteProcesso partePoloAtivo = new ParteProcesso(new PessoaId(1L), TipoPolo.POLO_ATIVO);
		
		processo.adicionarParte(partePoloAtivo);
		assertEquals(1, processo.partesPoloAtivo().size());
		assertTrue(processo.partesPoloAtivo().contains(partePoloAtivo));
	}
	
	@Test
	public void adicionaPartePoloPassivoAProcesso() {
		Processo processo = processo();
		ParteProcesso partePoloPassivo = new ParteProcesso(new PessoaId(1L), TipoPolo.POLO_PASSIVO);
		
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
	public void removerPartePoloAtivoDoProcesso() {
		Processo processo = processo();
		ParteProcesso partePoloAtivo = new ParteProcesso(new PessoaId(1L), TipoPolo.POLO_ATIVO);
		
		processo.adicionarParte(partePoloAtivo);
		processo.removerParte(partePoloAtivo);
		assertEquals(0, processo.partesPoloAtivo().size());
		assertFalse(processo.partesPoloAtivo().contains(partePoloAtivo));
	}
	
	@Test
	public void removerPartePoloPassivoDoProcesso() {
		Processo processo = processo();
		ParteProcesso partePoloPassivo = new ParteProcesso(new PessoaId(2L), TipoPolo.POLO_PASSIVO);
		
		processo.adicionarParte(partePoloPassivo);
		processo.removerParte(partePoloPassivo);
		assertEquals(1, processo.partesPoloPassivo().size());
		assertFalse(processo.partesPoloAtivo().contains(partePoloPassivo));
	}
	
	@Test
	public void atribuirPoloAtivoDoProcesso() {
		Processo processo = processo();
		ParteProcesso partePoloPassivo = new ParteProcesso(new PessoaId(2L), TipoPolo.POLO_ATIVO);
		
		processo.adicionarParte(partePoloPassivo);
		
		Set<ParteProcesso> novoPoloAtivo = new HashSet<ParteProcesso>();
		novoPoloAtivo.add(new ParteProcesso(new PessoaId(3L), TipoPolo.POLO_ATIVO));
		novoPoloAtivo.add(new ParteProcesso(new PessoaId(4L), TipoPolo.POLO_ATIVO));
		
		processo.atribuirPartes(novoPoloAtivo, TipoPolo.POLO_ATIVO);
		
		assertEquals(2, processo.partesPoloAtivo().size());
		assertTrue(processo.partesPoloAtivo().contains(new ParteProcesso(new PessoaId(3L), TipoPolo.POLO_ATIVO)));
	}
	
	@Test
	public void atribuirPoloPassivoDoProcesso() {
		Processo processo = processo();
		ParteProcesso partePoloPassivo = new ParteProcesso(new PessoaId(4L), TipoPolo.POLO_PASSIVO);
		
		processo.adicionarParte(partePoloPassivo);
		
		Set<ParteProcesso> novoPoloPassivo = new HashSet<ParteProcesso>();
		novoPoloPassivo.add(new ParteProcesso(new PessoaId(5L), TipoPolo.POLO_PASSIVO));
		novoPoloPassivo.add(new ParteProcesso(new PessoaId(6L), TipoPolo.POLO_PASSIVO));
		
		processo.atribuirPartes(novoPoloPassivo, TipoPolo.POLO_PASSIVO);
		
		assertEquals(2, processo.partesPoloPassivo().size());
		assertTrue(processo.partesPoloPassivo().contains(new ParteProcesso(new PessoaId(5L), TipoPolo.POLO_PASSIVO)));
	}
	
	@Test(expected = NullPointerException.class)
	public void tentaRemoverParteDoProcessoInformandoNulo() {
		Processo processo = processo();
		
		processo.removerParte(null);
	}
	
	@Test
	public void associarRelator() {
		Processo processo = processo();
		MinistroId relator = new MinistroId(1L);
		processo.associarRelator(relator);
		
		assertEquals(processo.relator(), relator);
	}
	
	@Test(expected = NullPointerException.class)
	public void associarRelatorNulo() {
		Processo processo = processo();
		processo.associarRelator(null);
	}
	
	@Test
	public void modificaOrdemDasPecas() {
		Processo processo = processo();
		PecaProcesso peca2 = new PecaProcesso(new DocumentoId(2L), new TipoPeca(new TipoDocumentoId(2L), "Documento"), "Documento", Visibilidade.PUBLICO, Situacao.PENDENTE_JUNTADA);
		
		peca2.atribuirSequencial(2L);
		processo.adicionarPeca(peca2);
		
		List<Long> pecasOrganizadas = new ArrayList<Long>();
		pecasOrganizadas.add(2L);
		pecasOrganizadas.add(1L);
		processo.organizarPecas(pecasOrganizadas);
		
		assertEquals(peca.toLong(), processo.pecas().get(1).toLong());
		assertEquals(peca2.toLong(), processo.pecas().get(0).toLong());
	}
	
	@Test
	public void juntaPecaAoProcesso() {
		Processo processo = processo();
		PecaProcesso peca2 = new PecaProcesso(new DocumentoId(2L), new TipoPeca(new TipoDocumentoId(2L), "Documento"), "Documento", Visibilidade.PUBLICO, Situacao.PENDENTE_JUNTADA);
		
		processo.adicionarPeca(peca2);
		processo.juntarPeca(peca2);
		
		assertEquals(Situacao.JUNTADA, peca2.situacao());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void tentaJuntarPecaComSituacaoJuntada() {
		Processo processo = processo();
		
		processo.juntarPeca(peca);
	}
	
	@Test
	public void fazDivisaoDeUmaPecaEmDuas() {
		Processo processo = processo();
		PecaProcesso pecaDividida1 = new PecaProcesso(new DocumentoId(2L), new TipoPeca(new TipoDocumentoId(2L), "Custas"), "Custas", Visibilidade.PUBLICO, Situacao.JUNTADA);
		PecaProcesso pecaDividida2 = new PecaProcesso(new DocumentoId(3L), new TipoPeca(new TipoDocumentoId(5L), "Ato coator"), "Ato coator", Visibilidade.PUBLICO, Situacao.JUNTADA);
		List<Peca> pecasDivididas = new ArrayList<Peca>(2);
		
		pecaDividida1.atribuirSequencial(2L);
		pecaDividida2.atribuirSequencial(3L);
		
		pecasDivididas.add(pecaDividida1);
		pecasDivididas.add(pecaDividida2);
		processo.dividirPeca((PecaProcesso)processo.pecas().get(0), pecasDivididas);
		
		assertEquals("Processo deveria ter 2 peças.", 2L, processo.pecas().size());
		assertEquals("Processo deveria ter 1 peça original vinculada.", 1L, processo.pecasOriginaisVinculadas().size());
	}
	
	@Test
	public void fazUniaoDeDuasPecasEmUma() {
		Processo processo = processo();
		PecaProcesso peca1 = new PecaProcesso(new DocumentoId(2L), new TipoPeca(new TipoDocumentoId(2L), "Custas"), "Custas", Visibilidade.PUBLICO, Situacao.JUNTADA);
		PecaProcesso peca2 = new PecaProcesso(new DocumentoId(3L), new TipoPeca(new TipoDocumentoId(5L), "Ato coator"), "Ato coator", Visibilidade.PUBLICO, Situacao.JUNTADA);
		PecaProcesso pecaUnida = new PecaProcesso(new DocumentoId(4L), new TipoPeca(new TipoDocumentoId(3L), "Documentos diversos"), "Documentos diversos", Visibilidade.PUBLICO, Situacao.JUNTADA);
		List<PecaProcesso> pecasUnidas = new ArrayList<PecaProcesso>(2);
		
		peca1.atribuirSequencial(2L);
		peca2.atribuirSequencial(3L);
		pecaUnida.atribuirSequencial(4L);
		
		processo.adicionarPeca(peca1);
		processo.adicionarPeca(peca2);
		pecasUnidas.add(peca1);
		pecasUnidas.add(peca2);
		processo.unirPecas(pecasUnidas, pecaUnida);
		
		assertEquals("Processo deveria ter 2 peças.", 2L, processo.pecas().size());
		assertEquals("Processo deveria ter 2 peças originais vinculadas.", 2L, processo.pecasOriginaisVinculadas().size());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void tentaEditarPecaParaOrdemInvalida() {
		Processo processo = processo();
		
		processo.editarPeca(peca, peca.tipo(), peca.descricao(), 2L, peca.visibilidade());
	}
	
	@Test
	public void editaUmaPecaAlterandoNumeroOrdem() {
		Processo processo = processo();
		PecaProcesso peca2 = new PecaProcesso(new DocumentoId(2L), new TipoPeca(new TipoDocumentoId(2L), "Documento"), "Documento", Visibilidade.PUBLICO, Situacao.PENDENTE_JUNTADA);
		
		processo.adicionarPeca(peca2);
		processo.editarPeca(peca2, peca2.tipo(), peca2.descricao(), 1L, peca2.visibilidade());
		
		assertEquals("Peça 1 deveria ter número de ordem 2.", 2L, peca.numeroOrdem().longValue());
		assertEquals("Peça 2 deveria ter número de ordem 1.", 1L, peca2.numeroOrdem().longValue());
	}
	
	@Test
	public void editaUmaPecaMantendoNumeroOrdem() {
		Processo processo = processo();
		
		processo.editarPeca(peca, peca.tipo(), "Nova descrição.", 1L, peca.visibilidade());
		
		assertEquals("Peça 1 deveria ter descrição nova.", "Nova descrição.", peca.descricao());
	}
	
}
