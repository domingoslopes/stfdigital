package br.jus.stf.plataforma.acessos.domain.model;

import java.util.List;

import br.jus.stf.shared.UsuarioId;



public interface UsuarioRepository {
	
	public <T extends Usuario> T findOne(UsuarioId id);
	
	public <T extends Usuario> T findOne(String login);
	
	public <T extends Usuario> T save(Usuario principal);
	
	public List<Permissao> findByPermissaoUsuario(String login);

}