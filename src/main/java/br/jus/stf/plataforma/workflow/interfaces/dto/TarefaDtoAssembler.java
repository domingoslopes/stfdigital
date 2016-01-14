package br.jus.stf.plataforma.workflow.interfaces.dto;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.plataforma.shared.security.AcessosRestAdapter;
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
	
	@Autowired
	private AcessosRestAdapter acessosRestAdapter;

	public TarefaDto toDto(Tarefa tarefa) {
		Long id = tarefa.id().toLong();
		Long processo = tarefa.processo().toLong();
		String responsavel = Optional.ofNullable(tarefa.reponsavel()).map(r -> r.nome()).orElse("");
		return new TarefaDto(id, tarefa.nome(), tarefa.descricao(), responsavel, tarefa.isDono(), processo, toDto(tarefa.metadado()));
	}
	
	private MetadadoDto toDto(Metadado metadado) {
		return new MetadadoDto(metadado.informacao(), metadado.tipoInformacao(), metadado.status(), metadado.descricao());
	}

}
