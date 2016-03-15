package br.jus.stf.processamentoinicial.autuacao.domain;

import java.io.IOException;
import java.io.InputStream;

import br.jus.stf.processamentoinicial.autuacao.domain.model.MotivoDevolucao;


/**
 * @author Lucas.Rodrigues
 *
 */
public interface DevolucaoTemplateService {
	
	/**
	 * Carrega o template de um tipo de devolução
	 * 
	 * @param motivoDevolucao
	 * @param extensao
	 * @return
	 * @throws IOException
	 */
	InputStream carregarTemplate(MotivoDevolucao motivoDevolucao, String extensao) throws Exception;
	
}
