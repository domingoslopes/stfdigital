package br.jus.stf.autuacao.domain;

import java.util.List;
import java.util.Set;

import br.jus.stf.shared.domain.model.DocumentoId;
import br.jus.stf.shared.domain.model.PessoaId;

/**
 * Interface para comunicação com o domínio genérico
 * 
 * @author Lucas.Rodrigues
 *
 */
public interface GenericoAdapter {

	/**
	 * Salva os documentos e recupera os ids na ordem de envio
	 * 
	 * @param documentosTemporarios
	 * @return a lista de ids dos documentos
	 */
	public Set<DocumentoId> salvarDocumentos(List<String> documentosTemporarios);
	
	/**
	 * Cadastra as pessoas e recupera os ids na ordem de envio
	 * 
	 * @param pessoas
	 * @return a lista de ids de pessoas
	 */
	public Set<PessoaId> cadastrarPessoas(List<String> pessoas);
	
}
