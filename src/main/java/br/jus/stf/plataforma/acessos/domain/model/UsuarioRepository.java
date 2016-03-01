package br.jus.stf.plataforma.acessos.domain.model;

import java.util.List;

import br.jus.stf.shared.UsuarioId;

/**
 * Interface para repositório de usuário
 * 
 * @author Rafael Esdras
 *
 */
public interface UsuarioRepository {
	
	public List<Usuario> findAll();
	
	public Usuario findOne(UsuarioId id);
	
	public Usuario findOne(String login);
	
	public Usuario save(Usuario principal);
	
	public List<Recurso> findRecursoByUsuario(String login);

}
