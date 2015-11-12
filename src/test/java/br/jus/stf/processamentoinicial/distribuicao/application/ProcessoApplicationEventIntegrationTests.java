package br.jus.stf.processamentoinicial.distribuicao.application;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import br.jus.stf.plataforma.shared.tests.AbstractIntegrationTests;
import br.jus.stf.processamentoinicial.autuacao.domain.model.FormaRecebimento;
import br.jus.stf.processamentoinicial.autuacao.domain.model.Parte;
import br.jus.stf.processamentoinicial.autuacao.domain.model.PartePeticao;
import br.jus.stf.processamentoinicial.autuacao.domain.model.PecaPeticao;
import br.jus.stf.processamentoinicial.autuacao.domain.model.Peticao;
import br.jus.stf.processamentoinicial.autuacao.domain.model.PeticaoFactory;
import br.jus.stf.processamentoinicial.autuacao.domain.model.TipoPeca;
import br.jus.stf.processamentoinicial.autuacao.domain.model.TipoPolo;
import br.jus.stf.processamentoinicial.distribuicao.domain.model.Processo;
import br.jus.stf.processamentoinicial.distribuicao.domain.model.ProcessoFactory;
import br.jus.stf.processamentoinicial.distribuicao.domain.model.ProcessoRepository;
import br.jus.stf.shared.ClasseId;
import br.jus.stf.shared.DocumentoId;
import br.jus.stf.shared.MinistroId;
import br.jus.stf.shared.PessoaId;

/**
 * @author Lucas Rodrigues
 */
public class ProcessoApplicationEventIntegrationTests extends AbstractIntegrationTests {

	@Autowired
	private ProcessoApplicationEvent processoApplicationEvent;
	
	@Autowired
	private PeticaoFactory peticaoFactory;
	
	@Autowired
	private ProcessoRepository processoRepository;
	
	@Before
	public void setUp() {
		Authentication auth = Mockito.mock(Authentication.class);
		
		Mockito.when(auth.getPrincipal()).thenReturn("PETICIONADOR");
		SecurityContextHolder.getContext().setAuthentication(auth);
	}
	
	@Test
	public void processoDistribuidoEvent() {
		Peticao peticao = peticaoFactory.criarPeticaoFisica(1, 1, FormaRecebimento.SEDEX, "123");
		TipoPeca tipo = new TipoPeca(1L, "Petição Inicial");
		peticao.juntar(new PecaPeticao(new DocumentoId(1L), tipo, tipo.nome()));
		peticao.adicionarParte(new PartePeticao(new PessoaId(1L), TipoPolo.POLO_ATIVO));
		
		new ProcessoFactory(processoRepository);
		Set<Parte> partes = new HashSet<Parte>(0);
		
		partes.addAll(peticao.partesPoloAtivo());
		partes.addAll(peticao.partesPoloPassivo());
		
		Processo processo = ProcessoFactory.criarProcesso(new ClasseId("HC"), new MinistroId(1L), partes, peticao.pecas(), peticao.id());
		processoApplicationEvent.processoDistribuido(processo);
	}
	
}
