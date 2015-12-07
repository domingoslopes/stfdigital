package br.jus.stf.plataforma.acessos.domain.model;

import java.util.List;

import br.jus.stf.shared.PapelId;


/**
 * @author Rafael.Alencar
 *
 */
public interface PapelRepository {
	
	/**
	 * @param id
	 * @return
	 */
	public Papel findOne(PapelId id);
	
	/**
	 * @param nome
	 * @return
	 */
	public Papel findOne(String nome);
	
	/**
	 * @return
	 */
	public List<Papel> findAll();
	
	/**
	 * @param id
	 * @return
	 */
	public List<Permissao> findPermissaoByPapel(PapelId id);
	
	/**
	 * @param papel
	 * @return
	 */
	public Papel save(Papel papel);

	/**
	 * @return
	 */
	public PapelId nextId();

}