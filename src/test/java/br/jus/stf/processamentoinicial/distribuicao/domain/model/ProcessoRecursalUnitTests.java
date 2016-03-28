package br.jus.stf.processamentoinicial.distribuicao.domain.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import br.jus.stf.processamentoinicial.recursaledistribuicao.domain.model.MotivoInaptidao;
import br.jus.stf.processamentoinicial.recursaledistribuicao.domain.model.MotivoInaptidaoProcesso;
import br.jus.stf.processamentoinicial.recursaledistribuicao.domain.model.Origem;
import br.jus.stf.processamentoinicial.recursaledistribuicao.domain.model.ParteProcesso;
import br.jus.stf.processamentoinicial.recursaledistribuicao.domain.model.ProcessoRecursal;
import br.jus.stf.processamentoinicial.recursaledistribuicao.domain.model.ProcessoSituacao;
import br.jus.stf.processamentoinicial.suporte.domain.model.Classificacao;
import br.jus.stf.processamentoinicial.suporte.domain.model.MeioTramitacao;
import br.jus.stf.processamentoinicial.suporte.domain.model.Pais;
import br.jus.stf.processamentoinicial.suporte.domain.model.Sigilo;
import br.jus.stf.processamentoinicial.suporte.domain.model.TipoPolo;
import br.jus.stf.processamentoinicial.suporte.domain.model.TipoProcesso;
import br.jus.stf.processamentoinicial.suporte.domain.model.TribunalJuizo;
import br.jus.stf.processamentoinicial.suporte.domain.model.UnidadeFederacao;
import br.jus.stf.shared.AssuntoId;
import br.jus.stf.shared.ClasseId;
import br.jus.stf.shared.PessoaId;
import br.jus.stf.shared.PeticaoId;
import br.jus.stf.shared.PreferenciaId;
import br.jus.stf.shared.ProcessoId;
import br.jus.stf.shared.TeseId;

public class ProcessoRecursalUnitTests {
	
	@Test
	public void criaProcessoNaoEleitoralNemCriminalValido() {
		ProcessoRecursal processo = processo();

	    assertNotNull(processo);
	    assertEquals(TipoProcesso.RECURSAL, processo.tipoProcesso());
	    assertEquals(ProcessoSituacao.A_ANALISAR, processo.situacao());
	    assertEquals(new ProcessoId(5L), processo.id());
	    assertEquals(new ClasseId("ADI"), processo.classe());
	    assertEquals(new Long(10), processo.numero());
	    assertEquals(new PeticaoId(12L), processo.peticao());
	    assertEquals(new Long(1), processo.quantidadeRecursos());
	    assertEquals(new Date(), processo.dataRecebimento());
	}
	
	@Test
	public void criaProcessoEleitoralCriminalValido() {
		Set<PreferenciaId> preferencias = new HashSet<PreferenciaId>(0);
		
		preferencias.add(new PreferenciaId(2L));
		preferencias.add(new PreferenciaId(3L));
		
		ProcessoRecursal processo = new ProcessoRecursal(new ProcessoId(9L), new ClasseId("ADI"), 18L, new PeticaoId(15L),
				preferencias, new Date(), MeioTramitacao.FISICO,
				Sigilo.PUBLICO, 0L);

	    assertNotNull(processo);
	    assertEquals(ProcessoSituacao.A_AUTUAR, processo.situacao());
	}

	@Test(expected = IllegalArgumentException.class)
	public void tentaCriarProcessoComQuantidadeRecursoInvalida() {
		new ProcessoRecursal(new ProcessoId(5L), new ClasseId("ADI"), 10L, new PeticaoId(12L),
				null, new Date(), MeioTramitacao.FISICO,
				Sigilo.PUBLICO, -1L);
	}
	
	@Test(expected = NullPointerException.class)
	public void tentaAtribuirAssuntosAProcessoInformandoNulo() {
		ProcessoRecursal processo = processo();
		
		processo.atribuirAssuntos(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void tentaAtribuirAssuntosAProcessoInformandoVazio() {
		ProcessoRecursal processo = processo();
		
		processo.atribuirAssuntos(new HashSet<AssuntoId>(0));
	}
	
	@Test(expected = NullPointerException.class)
	public void tentaAtribuirTesesAProcessoInformandoNulo() {
		ProcessoRecursal processo = processo();
		
		processo.atribuirTeses(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void tentaAtribuirTesesAProcessoInformandoVazio() {
		ProcessoRecursal processo = processo();
		
		processo.atribuirTeses(new HashSet<TeseId>(0));
	}
	
	@Test(expected = NullPointerException.class)
	public void tentaAtribuirOrigensAProcessoInformandoNulo() {
		ProcessoRecursal processo = processo();
		
		processo.atribuirOrigens(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void tentaAtribuirOrigensAProcessoInformandoVazio() {
		ProcessoRecursal processo = processo();
		
		processo.atribuirOrigens(new HashSet<Origem>(0));
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void tentaAtribuirOrigensAProcessoSemUmaPrincipal() {
		ProcessoRecursal processo = processo();
		Set<Origem> origens = new HashSet<Origem>(0);
		
		origens.add(new Origem(1L, new UnidadeFederacao(16L, "Pernambuco", "PE", new Pais(16L, "Brasil")), new TribunalJuizo(1L, "TJ-PE"), 65423L, false));
		processo.atribuirOrigens(origens);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void tentaAtribuirOrigensAProcessoComMaisDeUmaPrincipal() {
		ProcessoRecursal processo = processo();
		Set<Origem> origens = new HashSet<Origem>(0);
		
		origens.add(new Origem(1L, new UnidadeFederacao(16L, "Pernambuco", "PE", new Pais(16L, "Brasil")), new TribunalJuizo(1L, "TJ-PE"), 65423L, true));
		origens.add(new Origem(2L, new UnidadeFederacao(1L, "Acre", "AC", new Pais(16L, "Brasil")), new TribunalJuizo(2L, "TJ-AC"), 65420L, true));
		processo.atribuirOrigens(origens);
	}
	
	@Test
	public void atribuiOrigensAProcesso() {
		ProcessoRecursal processo = processo();
		Set<Origem> origens = new HashSet<Origem>(0);
		
		origens.add(new Origem(1L, new UnidadeFederacao(16L, "Pernambuco", "PE", new Pais(16L, "Brasil")), new TribunalJuizo(1L, "TJ-PE"), 65423L, true));
		processo.atribuirOrigens(origens);
		
		assertEquals(origens, processo.origens());
	}
	
	@Test
	public void autuaUmProcessoRecursal() {
		ProcessoRecursal processo = processo();
		Set<ParteProcesso> poloAtivo = new HashSet<ParteProcesso>(0);
		Set<ParteProcesso> poloPassivo = new HashSet<ParteProcesso>(0);
		Set<AssuntoId> assuntos = new HashSet<AssuntoId>(0);
		
		poloAtivo.add(new ParteProcesso(new PessoaId(1L), TipoPolo.POLO_ATIVO));
		poloPassivo.add(new ParteProcesso(new PessoaId(2L), TipoPolo.POLO_PASSIVO));
		assuntos.add(new AssuntoId("120"));
		processo.autuar(assuntos, poloAtivo, poloPassivo);
		
		assertEquals(new Date(), processo.dataAutuacao());
		assertEquals(poloAtivo, processo.partesPoloAtivo());
		assertEquals(poloPassivo, processo.partesPoloPassivo());
		assertEquals(assuntos, processo.assuntos());
	}
	
	@Test(expected = NullPointerException.class)
	public void tentaAnalisarPressupostosFormaisSemClassificacao() {
		ProcessoRecursal processo = processo();
		
		processo.analisarPressupostosFormais(null, "", null);
	}
	
	@Test
	public void realizaAnalisePressupostosFormaisComoApto() {
		ProcessoRecursal processo = processo();
		
		processo.analisarPressupostosFormais(Classificacao.APTO, "Apto após análise.", null);
		
		assertEquals(Classificacao.APTO, processo.classificacao());
		assertEquals("Apto após análise.", processo.observacaoAnalise());
	}
	
	@Test
	public void realizaAnalisePressupostosFormaisComoInapto() {
		ProcessoRecursal processo = processo();
		Set<MotivoInaptidaoProcesso> motivos = new HashSet<MotivoInaptidaoProcesso>(0);
		
		motivos.add(new MotivoInaptidaoProcesso(new MotivoInaptidao(9L, "Outro"), "Falta de comprovação documental."));
		processo.analisarPressupostosFormais(Classificacao.INAPTO, "Inapto após análise.", motivos);
		
		assertEquals(Classificacao.INAPTO, processo.classificacao());
		assertEquals("Inapto após análise.", processo.observacaoAnalise());
		assertEquals(motivos, processo.motivosInaptidao());
	}
	
	@Test(expected = NullPointerException.class)
	public void tentaAnalisarPressupostosFormaisInaptoSemMotivos() {
		ProcessoRecursal processo = processo();
		
		processo.analisarPressupostosFormais(Classificacao.INAPTO, "", null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void tentaAnalisarRepercussaoGeralComTesesVazio() {
		ProcessoRecursal processo = processo();
		Set<AssuntoId> assuntos = new HashSet<AssuntoId>(0);
		
		assuntos.add(new AssuntoId("3215"));
		processo.analisarRepercussaoGeral(assuntos, new HashSet<TeseId>(0), "");
	}
	
	@Test(expected = NullPointerException.class)
	public void tentaAnalisarRepercussaoGeralComTesesNulo() {
		ProcessoRecursal processo = processo();
		Set<AssuntoId> assuntos = new HashSet<AssuntoId>(0);
		
		assuntos.add(new AssuntoId("3215"));
		processo.analisarRepercussaoGeral(assuntos, null, "");
	}
	
	@Test
	public void realizaAnaliseRepercussaoGeral() {
		ProcessoRecursal processo = processo();
		Set<AssuntoId> assuntos = new HashSet<AssuntoId>(0);
		Set<TeseId> teses = new HashSet<TeseId>(0);
		
		assuntos.add(new AssuntoId("3215"));
		teses.add(new TeseId(140L));
		processo.analisarRepercussaoGeral(assuntos, teses, "Processo com RG.");
		
		assertEquals(assuntos, processo.assuntos());
		assertEquals(teses, processo.teses());
		assertEquals("Processo com RG.", processo.observacaoAnalise());
	}
	
	private ProcessoRecursal processo() {
		return new ProcessoRecursal(new ProcessoId(5L), new ClasseId("ADI"), 10L, new PeticaoId(12L),
				null, new Date(), MeioTramitacao.FISICO,
				Sigilo.PUBLICO, 1L);
	}
	
}
