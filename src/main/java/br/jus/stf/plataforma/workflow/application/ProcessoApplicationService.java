package br.jus.stf.plataforma.workflow.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.plataforma.workflow.domain.ProcessoRepository;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 26.06.2015
 */
@Component
public class ProcessoApplicationService {

	@Autowired
	private ProcessoRepository processoRepository;

	public String iniciar(String id) {
		return processoRepository.criar(id);
	}

}
