package br.jus.stf.workflow.interfaces.dto;

import org.springframework.stereotype.Component;

import br.jus.stf.shared.domain.model.ProcessoWorkflow;

/**
 * Classe montadora de dtos de processo de bpm
 */
@Component("processoWorkflowDtoAssembler")
public class ProcessoDtoAssembler {
	
	/**
	 * Converte uma instância do processo em dto
	 * 
	 * @param processInstance
	 * @return o dto
	 */
	public ProcessoDto toDto(ProcessoWorkflow processo) {
		return new ProcessoDto(processo.id().toLong(), processo.status());
	}
	
}
