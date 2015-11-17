/**
 * 
 */
package br.jus.stf.processamentoinicial.autuacao.infra.eventbus;

import static reactor.bus.selector.Selectors.$;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import reactor.bus.Event;
import reactor.bus.EventBus;
import reactor.fn.Consumer;
import br.jus.stf.plataforma.shared.indexacao.IndexadorRestAdapter;
import br.jus.stf.processamentoinicial.autuacao.domain.model.Peticao;
import br.jus.stf.processamentoinicial.autuacao.infra.configuration.IndicadoresAutuacaoConfiguration;
import br.jus.stf.shared.stereotype.Entity;

/**
 * Registra a autuação de uma petição no elastic search.
 * 
 * @author Anderson Araújo
 * 
 * @since 09.11.2015
 */
@Component
public class PeticaoIndicadorConsumer implements Consumer<Event<PeticaoAutuada>>, InitializingBean {
	
	@Autowired
	private EventBus eventBus;
	
	@Autowired
	private IndexadorRestAdapter indexadorRestAdapter;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		eventBus.on($("indicadorEventBus"), this);
	}

	@Override
	public void accept(Event<PeticaoAutuada> evento) {
		Peticao peticao = evento.getData().peticao();
		
		QuantidadeAtuacoes quantidadeAtuacoes = new QuantidadeAtuacoes(peticao.identificacao(), peticao.classeProcessual().toString(), LocalDate.now().getMonthValue());
		
		try {
			this.indexadorRestAdapter.indexar(IndicadoresAutuacaoConfiguration.INDICE, quantidadeAtuacoes);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	class QuantidadeAtuacoes implements Entity<QuantidadeAtuacoes, String> {
		
		private String identificadorPeticao;
		
		private String classeProcessual;
		
		private int mesAutuacao;

		private String id;
		
		public QuantidadeAtuacoes(String identificadorPeticao, String classeProcessual, int mesAutuacao) {
			this.id = UUID.randomUUID().toString(); 
			this.identificadorPeticao = identificadorPeticao;
			this.classeProcessual = classeProcessual;
			this.mesAutuacao = mesAutuacao;
		}

		public String getIdentificadorPeticao() {
			return identificadorPeticao;
		}

		public String getClasseProcessual() {
			return classeProcessual;
		}

		public int getMesAutuacao() {
			return mesAutuacao;
		}

		@Override
		public String id() {
			return id;
		}

		@Override
		public boolean sameIdentityAs(QuantidadeAtuacoes other) {
			return id.equals(other.id);
		}
		
	}
	
}
