package br.jus.stf.processamentoinicial.autuacao.domain.model;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.jus.stf.processamentoinicial.suporte.domain.model.Situacao;
import br.jus.stf.processamentoinicial.suporte.domain.model.TipoPeca;
import br.jus.stf.processamentoinicial.suporte.domain.model.TipoPolo;
import br.jus.stf.processamentoinicial.suporte.domain.model.TipoProcesso;
import br.jus.stf.processamentoinicial.suporte.domain.model.Visibilidade;
import br.jus.stf.shared.ClasseId;
import br.jus.stf.shared.DocumentoId;
import br.jus.stf.shared.PessoaId;
import br.jus.stf.shared.PeticaoId;
import br.jus.stf.shared.TipoDocumentoId;

public class PeticaoRepositoryUnitTests {
	
	@Mock
	private PeticaoRepository peticaoRepository;
	
	private Set<PartePeticao> partes;
	private List<PecaPeticao> pecas;
	private TipoPeca tipo;
	private List<TipoPeca> tipos;
	private Orgao orgao;
	private List<Orgao> orgaos;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		
		tipo = new TipoPeca(new TipoDocumentoId(1L), "Petição inicial");
		
		tipos = new ArrayList<TipoPeca>(0);
		tipos.add(tipo);
		tipos.add(new TipoPeca(new TipoDocumentoId(2L), "Documentos comprobatórios"));
		
		orgao = new Orgao(1L, "STF");
		
		orgaos = new ArrayList<Orgao>(0);
		orgaos.add(orgao);
		orgaos.add(new Orgao(2L, "AGU"));
		
		partes = new HashSet<PartePeticao>(0);
		partes.add(new PartePeticao(new PessoaId(1L), TipoPolo.POLO_ATIVO));
		partes.add(new PartePeticao(new PessoaId(2L), TipoPolo.POLO_PASSIVO));

		pecas = new ArrayList<PecaPeticao>(0);
		pecas.add(new PecaPeticao(new DocumentoId(1L), tipo, "Petição inicial", Visibilidade.PUBLICO, Situacao.JUNTADA));
		
		Peticao peticao = peticao();
		List<Peticao> peticoes = new ArrayList<Peticao>();
		
		peticoes.add(peticao);
		
		when(peticaoRepository.save(peticao)).thenReturn(peticao);
		when(peticaoRepository.findOne(new PeticaoId(1L))).thenReturn(peticao);
		when(peticaoRepository.findAll(null)).thenReturn(peticoes);
		when(peticaoRepository.nextId()).thenReturn(new PeticaoId(2L));
		when(peticaoRepository.nextNumero()).thenReturn(3L);
		when(peticaoRepository.findOneTipoPeca(new TipoDocumentoId(1L))).thenReturn(tipo);
		when(peticaoRepository.findAllTipoPeca()).thenReturn(tipos);
		when(peticaoRepository.findOneOrgao(1L)).thenReturn(orgao);
		when(peticaoRepository.findAllOrgao()).thenReturn(orgaos);
	}

	private Peticao peticao() {
		return new PeticaoEletronica(new PeticaoId(1L), 1L, "RECEBEDOR",
				new ClasseId("HC"), partes, pecas, TipoProcesso.ORIGINARIO);
	}
	
	@Test
	public void salvarPeticao() {
		Peticao peticao = peticao();
		
		assertEquals(peticao(), peticaoRepository.save(peticao));
		verify(peticaoRepository, times(1)).save(peticao);
	}
	
	@Test
	public void encontraPeticao() {
		assertEquals(peticao(), peticaoRepository.findOne(new PeticaoId(1L)));
		verify(peticaoRepository, times(1)).findOne(new PeticaoId(1L));
	}
	
	@Test
	public void encontraPeticoes() {
		Peticao peticao = peticao();
		List<Peticao> peticoes = new ArrayList<Peticao>();
		
		peticoes.add(peticao);
		assertEquals(peticoes, peticaoRepository.findAll(null));
		verify(peticaoRepository, times(1)).findAll(null);
	}
	
	@Test
	public void proximoIdPeticao() {
		assertEquals(new PeticaoId(2L), peticaoRepository.nextId());
		verify(peticaoRepository, times(1)).nextId();
	}
	
	@Test
	public void proximoNumeroAnoPeticao() {
		assertEquals(new Long(3), peticaoRepository.nextNumero());
		verify(peticaoRepository, times(1)).nextNumero();
	}
	
	@Test
	public void encontraTipoPeca() {
		assertEquals(tipo, peticaoRepository.findOneTipoPeca(new TipoDocumentoId(1L)));
		verify(peticaoRepository, times(1)).findOneTipoPeca(new TipoDocumentoId(1L));
	}
	
	@Test
	public void encontraTiposPeca() {
		assertEquals(tipos, peticaoRepository.findAllTipoPeca());
		verify(peticaoRepository, times(1)).findAllTipoPeca();
	}
	
	@Test
	public void encontraOrgao() {
		assertEquals(orgao, peticaoRepository.findOneOrgao(1L));
		verify(peticaoRepository, times(1)).findOneOrgao(1L);
	}
	
	@Test
	public void encontraOrgaos() {
		assertEquals(orgaos, peticaoRepository.findAllOrgao());
		verify(peticaoRepository, times(1)).findAllOrgao();
	}
	
}
