package br.jus.stf.plataforma.acessos.domain.model;

import java.util.List;

import br.jus.stf.shared.SegmentoId;
import br.jus.stf.shared.TipoInformacaoId;

/**
 * @author Rafael.Alencar
 *
 */
public interface SegmentoRepository {
	
	/**
	 * @param id
	 * @return
	 */
	public Segmento findOne(SegmentoId id);
	
	/**
	 * @param nome
	 * @param tipo
	 * @return
	 */
	public Segmento findOne(String nome, TipoInformacaoId tipo);
	
	/**
	 * @return
	 */
	public SegmentoId nextId();
	
	/**
	 * @return
	 */
	public List<Segmento> findAll();
	
	/**
	 * @param tipo
	 * @return
	 */
	public List<Segmento> findByTipoInformacao(TipoInformacaoId tipo);
	
	/**
	 * @param segmento
	 * @return
	 */
	public Segmento save(Segmento segmento);
	
	/**
	 * @param tipo
	 * @return
	 */
	public TipoInformacao findOneTipoInformacao(TipoInformacaoId tipo);
	
	/**
	 * @return
	 */
	public List<TipoInformacao> findAllTipoInformacao();	
	
}