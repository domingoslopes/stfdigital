package br.jus.stf.plataforma.workflow.interfaces.dto;

import org.activiti.engine.runtime.ProcessInstance;

public class ProcessoDtoAssembler {
	
	public ProcessoDto toDto(ProcessInstance process) {
		return new ProcessoDto(process.getId(), process.getProcessVariables());
	}
	
}
