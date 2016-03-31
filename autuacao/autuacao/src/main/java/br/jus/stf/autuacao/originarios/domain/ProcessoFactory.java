package br.jus.stf.autuacao.originarios.domain;

import org.springframework.stereotype.Component;

import br.jus.stf.autuacao.originarios.domain.model.Processo;
import br.jus.stf.autuacao.originarios.domain.model.Status;
import br.jus.stf.core.shared.processo.ProcessoId;
import br.jus.stf.core.shared.protocolo.ProtocoloId;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 04.02.2016
 */
@Component
public class ProcessoFactory {

    public Processo novoProcesso(ProcessoId processoId, ProtocoloId protocoloId, Status status) {
        return new Processo(processoId, protocoloId, status);
    }

}
