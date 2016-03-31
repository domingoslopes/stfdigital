package br.jus.stf.autuacao.originarios.application.commands;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import br.jus.stf.core.shared.processo.ProcessoId;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 23.06.2015
 */
@ApiModel(value = "Contém as informações necessárias para autuar um processo originário")
public class AutuarProcessoCommand {

    @NotNull
    @ApiModelProperty(value = "O identificador do processo originário criado ao iniciar o fluxo de autuação.", required=true)
    private ProcessoId processoId;
    
    @NotBlank
	@ApiModelProperty(value = "O identificador da classe processual definitiva, selecionada pelo autuador", required=true)
    private String classeId;
    
	public ProcessoId getProcessoId() {
	    return processoId;
	}

	public String getClasseId() {
		return classeId;
	}

}
