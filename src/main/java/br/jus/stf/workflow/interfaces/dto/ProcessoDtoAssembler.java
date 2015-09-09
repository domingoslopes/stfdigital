package br.jus.stf.workflow.interfaces.dto;

import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.stereotype.Component;

/**
 * Classe montadora de dtos de processo de bpm
 */
@Component
public class ProcessoDtoAssembler {
	
	/**
	 * Converte uma instância do processo em dto
	 * 
	 * @param processInstance
	 * @return o dto
	 */
	public ProcessoDto toDto(ProcessInstance processInstance) {
		return new ProcessoDto(processInstance.getId(), processInstance.getProcessVariables());
	}
	
}
