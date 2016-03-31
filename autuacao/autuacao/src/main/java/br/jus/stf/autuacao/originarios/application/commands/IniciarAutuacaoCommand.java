package br.jus.stf.autuacao.originarios.application.commands;

import javax.validation.constraints.NotNull;

import com.wordnik.swagger.annotations.ApiModelProperty;

import br.jus.stf.core.shared.protocolo.ProtocoloId;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 11.02.2016
 */
public class IniciarAutuacaoCommand {
    
    @NotNull
    @ApiModelProperty(value = "O identificador do protocolo gerado durante o peticionamento digital ou o registro de uma remessa físca.", required=true)
    private ProtocoloId protocoloId;
    
    public IniciarAutuacaoCommand(ProtocoloId protocoloId) {
        this.protocoloId = protocoloId;
    }

    public ProtocoloId getProtocoloId() {
        return protocoloId;
    }

}
