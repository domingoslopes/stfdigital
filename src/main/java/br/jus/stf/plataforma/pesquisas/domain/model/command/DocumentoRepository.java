package br.jus.stf.plataforma.pesquisas.domain.model.command;

/**
 * @author Lucas.Rodrigues
 *
 */
public interface DocumentoRepository {
	
	/**
	 * Criar um índice com as configurações informadas, caso já exista
	 * apenas atualiza os mapeamentos
	 * 
	 * @param indice
	 */
	void criar(Indice indice);

	/**
	 * Salva um objeto indexavel no índice
	 * 
	 * @param objeto
	 */
	void salvar(Documento documento);
	
	/**
	 * Realiza uma atualização parcial do documento
	 * 
	 * @param documento
	 */
	void atualizar(Documento documento);
	
}
