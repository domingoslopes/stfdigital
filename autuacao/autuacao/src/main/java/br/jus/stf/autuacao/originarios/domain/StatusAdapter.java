package br.jus.stf.autuacao.originarios.domain;

import org.springframework.stereotype.Component;

import br.jus.stf.autuacao.originarios.domain.model.Status;
import br.jus.stf.core.framework.workflow.StatusAdapterSupport;
import br.jus.stf.core.shared.processo.ProcessoId;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 07.02.2016
 */
@Component("statusProcessoAdapter")
public class StatusAdapter extends StatusAdapterSupport<ProcessoId, Status> {

    private static final String ORIGINARIOS_PROCESS_KEY = "originarios";
    
    private static final String ORIGINARIOS_ID_PATTERN = "OR:%s";

    @Override
    protected Status statusFromDescription(String description) {
        return Status.valueOf(description);
    }

    @Override
    protected String processId(ProcessoId informationId) {
        return String.format(ORIGINARIOS_ID_PATTERN, informationId);
    }
    
    @Override
    protected String processKey() {
        return ORIGINARIOS_PROCESS_KEY;
    }

}
