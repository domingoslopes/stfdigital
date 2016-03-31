package br.jus.stf.autuacao.originarios.application;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.jus.stf.autuacao.originarios.application.commands.AutuarProcessoCommand;
import br.jus.stf.autuacao.originarios.application.commands.IniciarAutuacaoCommand;
import br.jus.stf.autuacao.originarios.domain.NumeroProcessoAdapter;
import br.jus.stf.autuacao.originarios.domain.ProcessoFactory;
import br.jus.stf.autuacao.originarios.domain.StatusAdapter;
import br.jus.stf.autuacao.originarios.domain.model.Processo;
import br.jus.stf.autuacao.originarios.domain.model.ProcessoOriginarioRepository;
import br.jus.stf.autuacao.originarios.domain.model.Status;
import br.jus.stf.autuacao.originarios.infra.RabbitConfiguration;
import br.jus.stf.core.shared.eventos.ProcessoAutuado;
import br.jus.stf.core.shared.eventos.ProcessoRegistrado;
import br.jus.stf.core.shared.processo.Identificacao;
import br.jus.stf.core.shared.processo.ProcessoId;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 04.01.2016
 */
@Component
public class AutuacaoDeOriginariosApplicationService {
    
    @Autowired
    private ProcessoOriginarioRepository processoRepository;
    
    @Autowired
    private NumeroProcessoAdapter numeroProcessoAdapter;
    
    @Autowired
    private ProcessoFactory processoFactory;
    
    @Autowired
    private StatusAdapter statusAdapter;

    @Autowired
	private RabbitTemplate rabbitTemplate;
    
    @Transactional(propagation = REQUIRES_NEW)
    public void handle(IniciarAutuacaoCommand command) {
        ProcessoId processoId = processoRepository.nextProcessoId();
        
        Status status = statusAdapter.nextStatus(processoId);
        
        Processo processo = processoFactory.novoProcesso(processoId, command.getProtocoloId(), status);
        
        processoRepository.save(processo);
        
        // TODO Rodrigo Barreiros Substituir o RabbitTemplate por um EventPublisher e remover a necessidade de informar a fila
        rabbitTemplate.convertAndSend(RabbitConfiguration.PROCESSO_REGISTRADO_QUEUE, new ProcessoRegistrado(command.getProtocoloId().toLong(), processoId.toString()));
    }

    @Transactional
    public void handle(AutuarProcessoCommand command) {
        Processo processo = processoRepository.findOne(command.getProcessoId());
        
        Status status = statusAdapter.nextStatus(processo.identity(), "distribuir");
        
        Identificacao numero = numeroProcessoAdapter.novoNumeroProcesso(command.getClasseId());
        
        processo.classificar(command.getClasseId(), status);
        
        processoRepository.save(processo);
        
        // TODO Rodrigo Barreiros Substituir o RabbitTemplate por um EventPublisher e remover a necessidade de informar a fila
        rabbitTemplate.convertAndSend(RabbitConfiguration.PROCESSO_AUTUADO_QUEUE, new ProcessoAutuado(processo.identity().toString(), numero.toString()));
    }

}
