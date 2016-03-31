package br.jus.stf.autuacao.originarios.domain.model;

import br.jus.stf.core.shared.processo.ProcessoId;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 04.02.2016
 */
public interface ProcessoOriginarioRepository {

    <P extends Processo> P save(P processo);
    
    Processo findOne(ProcessoId id);
    
    ProcessoId nextProcessoId();

}
