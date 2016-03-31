package br.jus.stf.core.processos.infra;

import static br.jus.stf.core.processos.infra.RabbitConfiguration.PARTE_REGISTRADA_QUEUE;
import static br.jus.stf.core.processos.infra.RabbitConfiguration.PETICAO_PROCESSO_QUEUE;
import static br.jus.stf.core.processos.infra.RabbitConfiguration.PROCESSO_AUTUADO_QUEUE;
import static br.jus.stf.core.processos.infra.RabbitConfiguration.PROCESSO_REGISTRADO_QUEUE;
import static br.jus.stf.core.processos.infra.RabbitConfiguration.REMESSA_REGISTRADA_QUEUE;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.core.processos.domain.Processo;
import br.jus.stf.core.processos.domain.ProcessoRepository;
import br.jus.stf.core.shared.eventos.EnvolvidoRegistrado;
import br.jus.stf.core.shared.eventos.PeticaoRegistrada;
import br.jus.stf.core.shared.eventos.ProcessoAutuado;
import br.jus.stf.core.shared.eventos.ProcessoRegistrado;
import br.jus.stf.core.shared.eventos.RemessaRegistrada;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 20.02.2016
 */
@Component
public class ProcessoEventHandler {
	
	@Autowired
	private ProcessoRepository processoRepository;
	
    @RabbitListener(queues = REMESSA_REGISTRADA_QUEUE)
	public void handle(RemessaRegistrada event) {
		processoRepository.save(new Processo(event.getProtocoloId(), event.getProtocolo()));
	}

    @RabbitListener(queues = PETICAO_PROCESSO_QUEUE)
	public void handle(PeticaoRegistrada event) {
		processoRepository.save(new Processo(event.getProtocoloId(), event.getProtocolo()));
	}

	@RabbitListener(queues = PARTE_REGISTRADA_QUEUE)
	public void handle(EnvolvidoRegistrado event) {
		doIndexByProtocoloId(event.getProtocoloId(), processo -> processo.getPartes().add(event.getNome()));
	}

    @RabbitListener(queues = PROCESSO_REGISTRADO_QUEUE)
	public void handle(ProcessoRegistrado event) {
		doIndexByProtocoloId(event.getProtocoloId(), processo -> processo.setProcessoId(event.getProcessoId()));
		
		Processo processo = processoRepository.findByProcessoId(event.getProcessoId());
		
		// Ao lançar a exceção abaixo forçamos uma nova tentativa no futuro, já que o Rabbit só vai remover a mensagem da fila se o processamento ocorrer sem problemas
        if (processo == null) {
        	throw new IllegalStateException(String.format("Processo [%s] ainda não foi indexado.", event.getProcessoId()));
        }
    }

	@RabbitListener(queues = PROCESSO_AUTUADO_QUEUE)
	public void handle(ProcessoAutuado event) {
		doIndexByProcessoId(event.getProcessoId(), processo -> processo.setNumero(event.getNumero()));
	}
	
	// ----------------------------------------------------------------------------------------------------------------------------------------------------------------
	// Classes e Métodos Utilitários...
	
	private void doIndexByProtocoloId(Long protocoloId, IndexAction action) {
		Processo processo = processoRepository.findByProtocoloId(protocoloId);
		
        if (processo == null) {
        	throw new IllegalStateException(String.format("Processo de Protocolo ID [%s] ainda não foi indexado.", protocoloId));
        }
        
		action.execute(processo);
		
		processoRepository.save(processo);
	}
	
	private void doIndexByProcessoId(String processoId, IndexAction action) {
		Processo processo = processoRepository.findByProcessoId(processoId);
		
		// Ao lançar a exceção abaixo forçamos uma nova tentativa no futuro, já que o Rabbit só vai remover a mensagem da fila se o processamento ocorrer sem problemas
        if (processo == null) {
        	throw new IllegalStateException(String.format("Processo [%s] ainda não foi indexado.", processoId));
        }
        
		action.execute(processo);
		
		processoRepository.save(processo);
	}
	
	@FunctionalInterface
	private interface IndexAction {

		void execute(Processo processo);
		
	}

}
