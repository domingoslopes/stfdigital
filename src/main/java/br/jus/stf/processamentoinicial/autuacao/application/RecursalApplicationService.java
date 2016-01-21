package br.jus.stf.processamentoinicial.autuacao.application;

import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import br.jus.stf.processamentoinicial.autuacao.domain.model.PeticaoFisica;

/**
 * @author Rafael Alencar
 * 
 * @since 13.01.2016
 */
/**
 * @author Rafael.Alencar
 *
 */
@Component
@Transactional
public class RecursalApplicationService {
	
	/**
	 * Realiza a preautuação de recursais.
	 * 
	 * @param peticao Dados da petição física.
	 */
	public void preautuar(PeticaoFisica peticao) {
		// TODO: Verificar restante da história para implementação.
	}
	
}
