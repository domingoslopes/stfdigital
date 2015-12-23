package br.jus.stf.plataforma.workflow.interfaces.facade;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.plataforma.workflow.application.TarefaApplicationService;
import br.jus.stf.plataforma.workflow.domain.model.Metadado;
import br.jus.stf.plataforma.workflow.domain.model.Tarefa;
import br.jus.stf.plataforma.workflow.domain.model.TarefaRepository;
import br.jus.stf.plataforma.workflow.interfaces.dto.TarefaDto;
import br.jus.stf.plataforma.workflow.interfaces.dto.TarefaDtoAssembler;
import br.jus.stf.shared.ProcessoWorkflowId;
import br.jus.stf.shared.TarefaId;

/**
 * @author Lucas.Rodrigues
 * 
 */
@Component
public class TarefaServiceFacade {
	
	@Autowired
	private TarefaRepository tarefaRepository;
	
	@Autowired
	private TarefaDtoAssembler tarefaDtoAssembler;
	
    @Autowired 
    private TarefaApplicationService tarefaApplicationService;
    
	public List<TarefaDto> tarefas() {
        return tarefaRepository.listarMinhas().stream()
        		.map(tarefa -> tarefaDtoAssembler.toDto(tarefa))
        		.collect(Collectors.toList());
	}
	
	public List<TarefaDto> tarefasPorMeusPapeis() {
		return tarefaRepository.listarPorMeusPapeis().stream()
        		.map(tarefa -> tarefaDtoAssembler.toDto(tarefa))
        		.collect(Collectors.toList());
	}
	
	public void completar(Long id, String status, String descricao) {
		Metadado metadado = new Metadado(status, descricao);
		TarefaId tarefaId = new TarefaId(id);
		Tarefa tarefa = Optional.ofNullable(tarefaRepository.consultar(tarefaId))
							.orElseThrow(IllegalArgumentException::new);
        tarefaApplicationService.completar(tarefa, metadado);
	}
	
	public TarefaDto consultar(Long id) {
		TarefaId tarefaId = new TarefaId(id);
		return Optional.ofNullable(tarefaRepository.consultar(tarefaId))
				.map(tarefaDtoAssembler::toDto)
				.orElse(null);
	}
	
	public TarefaDto consultarPorProcesso(Long id) {
		ProcessoWorkflowId processoId = new ProcessoWorkflowId(id);
		return Optional.ofNullable(tarefaRepository.consultarPorProcesso(processoId))
				.map(tarefaDtoAssembler::toDto)
				.orElse(null);
	}

}
