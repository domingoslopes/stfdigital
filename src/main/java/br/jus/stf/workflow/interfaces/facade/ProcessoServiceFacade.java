package br.jus.stf.workflow.interfaces.facade;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.shared.domain.model.ProcessoWorkflow;
import br.jus.stf.shared.domain.model.ProcessoWorkflowId;
import br.jus.stf.workflow.application.ProcessoApplicationService;
import br.jus.stf.workflow.domain.model.ProcessoWokflowRepository;
import br.jus.stf.workflow.interfaces.dto.ProcessoDto;
import br.jus.stf.workflow.interfaces.dto.ProcessoDtoAssembler;

/**
 * @author Lucas.Rodrigues
 */
@Component
public class ProcessoServiceFacade {
	
	@Autowired
	private ProcessoWokflowRepository processoWorkflowRepository;

	@Autowired
	private ProcessoApplicationService processoApplicationService;
	
	@Autowired
	private ProcessoDtoAssembler processoDtoAssembler;

	public Long iniciar(String mensagem, String status) {
		ProcessoWorkflowId id = processoApplicationService.iniciar(mensagem, status);
		return id.toLong();
	}
	
	public ProcessoDto consultar(Long id) {
		ProcessoWorkflowId processoId = new ProcessoWorkflowId(id);
		return Optional.ofNullable(processoWorkflowRepository.findOne(processoId))
				.map(processoDtoAssembler::toDto)
				.orElseThrow(IllegalArgumentException::new);
	}
	
	public void sinalizar(Long id, String sinal, String status) {
		ProcessoWorkflowId processoId = new ProcessoWorkflowId(id);
		ProcessoWorkflow processo = Optional.ofNullable(processoWorkflowRepository.findOne(processoId))
										.orElseThrow(IllegalArgumentException::new);
		processoApplicationService.sinalizar(processo , sinal, status);
	}
	
}
