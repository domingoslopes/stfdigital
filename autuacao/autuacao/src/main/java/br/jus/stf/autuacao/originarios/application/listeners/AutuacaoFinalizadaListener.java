package br.jus.stf.autuacao.originarios.application.listeners;

import org.activiti.engine.delegate.DelegateExecution;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.autuacao.originarios.infra.RabbitConfiguration;
import br.jus.stf.core.shared.eventos.AutuacaoFinalizada;

/**
 * TODO Rodrigo Barreiros Analisar se esse listener está no lugar certo
 * 
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 03.01.2016
 */
@Component
public class AutuacaoFinalizadaListener {

	/** 
	 * TODO Rodrigo Barreiros Analisar se é melhor uma interface com implementação do Rabbit
	 * TODO Rodrigo Barreiros Passar o ProcessoId como variável do processo, como em {@link RecebimentoFinalizadoListener}
	 */
	@Autowired
	private RabbitTemplate rabbitTemplate;
    
    public void handle(DelegateExecution execution) {
    	rabbitTemplate.convertAndSend(RabbitConfiguration.AUTUACAO_FINALIZADA_QUEUE, new AutuacaoFinalizada(Long.valueOf(execution.getProcessBusinessKey().substring(3))));
    }
    
}
