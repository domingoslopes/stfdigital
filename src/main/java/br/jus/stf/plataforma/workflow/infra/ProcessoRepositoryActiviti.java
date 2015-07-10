package br.jus.stf.plataforma.workflow.infra;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.jus.stf.plataforma.workflow.domain.ProcessoRepository;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 26.06.2015
 */
@Repository
public class ProcessoRepositoryActiviti implements ProcessoRepository {

	@Autowired
	private RuntimeService runtimeService;

	@Override
	public String criar(String id) {
		ProcessInstance processInstance = runtimeService
				.startProcessInstanceByKey(id);

		return processInstance.getId();
	}

}
