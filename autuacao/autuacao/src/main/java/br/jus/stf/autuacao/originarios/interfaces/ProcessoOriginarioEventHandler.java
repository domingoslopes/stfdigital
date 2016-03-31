package br.jus.stf.autuacao.originarios.interfaces;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.autuacao.originarios.application.AutuacaoDeOriginariosApplicationService;
import br.jus.stf.autuacao.originarios.application.commands.IniciarAutuacaoCommand;
import br.jus.stf.autuacao.originarios.infra.RabbitConfiguration;
import br.jus.stf.core.shared.eventos.PeticaoRegistrada;
import br.jus.stf.core.shared.eventos.RecebimentoFinalizado;
import br.jus.stf.core.shared.protocolo.ProtocoloId;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 26.12.2015
 */
@Component
public class ProcessoOriginarioEventHandler {
    
    @Autowired
    private AutuacaoDeOriginariosApplicationService originariosApplicationService;
    
    @RabbitListener(queues = RabbitConfiguration.REMESSA_RECEBIDA_QUEUE)
    public void handle(RecebimentoFinalizado event) {
        originariosApplicationService.handle(new IniciarAutuacaoCommand(new ProtocoloId(event.getProtocoloId())));
    }

    @RabbitListener(queues = RabbitConfiguration.PETICAO_ORIGINARIO_QUEUE)
    public void handle(PeticaoRegistrada event) {
        originariosApplicationService.handle(new IniciarAutuacaoCommand(new ProtocoloId(event.getProtocoloId())));
    }

}
