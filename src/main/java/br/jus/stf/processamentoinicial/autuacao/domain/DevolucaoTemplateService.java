package br.jus.stf.processamentoinicial.autuacao.domain;

import java.io.IOException;
import java.io.InputStream;

import br.jus.stf.processamentoinicial.autuacao.domain.model.TipoDevolucao;


/**
 * @author Lucas.Rodrigues
 *
 */
public interface DevolucaoTemplateService {
	
	/**
	 * Carrega o template de um tipo de devolução
	 * 
	 * @param tipoDevolucao
	 * @param extensao
	 * @return
	 * @throws IOException
	 */
	InputStream carregarTemplate(TipoDevolucao tipoDevolucao, String extensao) throws Exception;
	
}
