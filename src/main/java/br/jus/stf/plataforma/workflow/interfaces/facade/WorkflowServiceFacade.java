package br.jus.stf.plataforma.workflow.interfaces.facade;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.plataforma.workflow.application.WorkflowApplicationService;
import br.jus.stf.plataforma.workflow.domain.model.Metadado;
import br.jus.stf.plataforma.workflow.domain.model.ProcessoWokflowRepository;
import br.jus.stf.plataforma.workflow.interfaces.dto.ProcessoDto;
import br.jus.stf.plataforma.workflow.interfaces.dto.ProcessoDtoAssembler;
import br.jus.stf.shared.ProcessoWorkflow;
import br.jus.stf.shared.ProcessoWorkflowId;

/**
 * @author Lucas.Rodrigues
 */
@Component
public class WorkflowServiceFacade {
	
	@Autowired
	private ProcessoWokflowRepository processoWorkflowRepository;

	@Autowired
	private WorkflowApplicationService processoApplicationService;
	
	@Autowired
	private ProcessoDtoAssembler processoDtoAssembler;
	
	public Long iniciar(String chave, Long informacao, String tipoInformacao, String status, String descricao) {
		Metadado metadado = new Metadado(informacao, tipoInformacao, status, descricao);
		ProcessoWorkflowId id = processoApplicationService.iniciar(chave, metadado);
		return id.toLong();
	}
	
	public Long iniciarPorMensagem(String mensagem, Long informacao, String tipoInformacao, String status, String descricao) {
		Metadado metadado = new Metadado(informacao, tipoInformacao, status, descricao);
		ProcessoWorkflowId id = processoApplicationService.iniciarPorMensagem(mensagem, metadado);
		return id.toLong();
	}
	
	public ProcessoDto consultar(Long id) {
		ProcessoWorkflowId processoId = new ProcessoWorkflowId(id);
		return Optional.ofNullable(processoWorkflowRepository.findOne(processoId)).map(processoDtoAssembler::toDto).orElseThrow(IllegalArgumentException::new);
	}
	
	public void sinalizar(Long id, String sinal, String status, String descricao) {
		Metadado metadado = new Metadado(status, descricao);
		ProcessoWorkflowId processoId = new ProcessoWorkflowId(id);
		ProcessoWorkflow processo = Optional.ofNullable(processoWorkflowRepository.findOne(processoId)).orElseThrow(IllegalArgumentException::new);
		processoApplicationService.sinalizar(processo , sinal, metadado);
	}
	
}
