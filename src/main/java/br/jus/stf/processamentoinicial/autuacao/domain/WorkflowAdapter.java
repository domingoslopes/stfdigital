package br.jus.stf.processamentoinicial.autuacao.domain;

import org.springframework.stereotype.Component;

import br.jus.stf.processamentoinicial.autuacao.domain.model.Peticao;
import br.jus.stf.processamentoinicial.autuacao.domain.model.PeticaoEletronica;
import br.jus.stf.processamentoinicial.autuacao.domain.model.PeticaoFisica;

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
	void iniciarWorkflow(PeticaoEletronica peticaoEletronica);
	
	/**
	 * Iniciar uma nova instância do processo de autuação de petição eletrônica
	 * @param peticaoFisica
	 */
	void iniciarWorkflow(PeticaoFisica peticaoFisica);
	
	/**
	 * Emite um sinal para o processo que rejeita a autução da petição
	 * 
	 * @param peticao
	 */
	void rejeitarAutuacao(Peticao peticao);
	
	/**
	 * Emite um sinal para o processo que devolve autuação da petição
	 * 
	 * @param peticao
	 */
	void devolver(Peticao peticao);
	
	/**
	 * Emite um sinal para o processo que libera o documento de devolução da petiação para assinatura
	 * 
	 * @param peticao
	 */
	void assinar(Peticao peticao);
	
}
