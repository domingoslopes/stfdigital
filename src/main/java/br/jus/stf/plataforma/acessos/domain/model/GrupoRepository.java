package br.jus.stf.plataforma.acessos.domain.model;

import java.util.List;

import br.jus.stf.shared.GrupoId;


/**
 * @author Rafael.Alencar
 *
 */
public interface GrupoRepository {
	
	/**
	 * @param id
	 * @return
	 */
	public Grupo findOne(GrupoId id);
	
	/**
	 * @param nome
	 * @param tipo
	 * @return
	 */
	public Grupo findOne(String nome, TipoGrupo tipo);
	
	/**
	 * @return
	 */
	public List<Grupo> findAll();
	
	/**
	 * @param id
	 * @return
	 */
	public List<Recurso> findRecursoByGrupo(GrupoId id);
	
	/**
	 * @return
	 */
	public GrupoId nextId();
	
	/**
	 * @param grupo
	 * @return
	 */
	public Grupo save(Grupo grupo);

}