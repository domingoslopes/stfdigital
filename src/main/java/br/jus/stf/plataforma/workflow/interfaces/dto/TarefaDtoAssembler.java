package br.jus.stf.plataforma.workflow.interfaces.dto;

import org.springframework.stereotype.Component;

import br.jus.stf.plataforma.workflow.domain.model.Metadado;
import br.jus.stf.plataforma.workflow.domain.model.Tarefa;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 23.06.2015
 */
@Component
public class TarefaDtoAssembler {

	public TarefaDto toDto(Tarefa tarefa) {
		Long id = tarefa.id().toLong();
		Long processo = tarefa.processo().toLong();
		return new TarefaDto(id, tarefa.nome(), tarefa.descricao(), processo, toDto(tarefa.metadado()), tarefa.metadado().tipoInformacao());
	}
	
	private MetadadoDto toDto(Metadado metadado) {
		return new MetadadoDto(metadado.informacao(), metadado.tipoInformacao(), metadado.status(), metadado.descricao());
	}

}
