package br.jus.stf.processamentoinicial.autuacao.domain;

import org.springframework.stereotype.Component;

import br.jus.stf.processamentoinicial.autuacao.domain.model.PeticaoEletronica;
import br.jus.stf.processamentoinicial.autuacao.domain.model.PeticaoFisica;
import br.jus.stf.shared.ProcessoWorkflow;

/**
 * @author Rodrigo Barreiros
 * @author Anderson Araújo
 * 
 * @since 1.0.0
 * @since 26.06.2015
 */
@Component
public interface WorkflowAdapter {

	/**
	 * Iniciar uma nova instância do processo de autuação de petição eletrônica
	 * @param peticaoEletronica
	 */
	ProcessoWorkflow iniciarWorkflow(PeticaoEletronica peticaoEletronica);
	
	/**
	 * Iniciar uma nova instância do processo de autuação de petição eletrônica
	 * @param peticaoFisica
	 */
	ProcessoWorkflow iniciarWorkflow(PeticaoFisica peticaoFisica);
	
}
