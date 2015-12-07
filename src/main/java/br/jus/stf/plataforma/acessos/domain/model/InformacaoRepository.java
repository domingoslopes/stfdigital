package br.jus.stf.plataforma.acessos.domain.model;

import br.jus.stf.shared.InformacaoId;


/**
 * @author Rafael.Alencar
 *
 */
public interface InformacaoRepository {
	
	/**
	 * @param id
	 * @return
	 */
	public Informacao findOne(InformacaoId id);
	
	/**
	 * @param tipo
	 * @param segmento
	 * @param identidade
	 * @return
	 */
	public Informacao findOne(TipoInformacao tipo, Segmento segmento, String identidade);
	
	/**
	 * @return
	 */
	public InformacaoId nextId();
	
	/**
	 * @param informacao
	 * @return
	 */
	public Informacao save(Informacao informacao);
	
	/**
	 * @param id
	 * @return
	 */
	public void delete(InformacaoId id);

}