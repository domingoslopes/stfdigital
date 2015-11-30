package br.jus.stf.plataforma.acessos.domain.model;

import java.util.List;



public interface UsuarioRepository {
	
	public <T extends Usuario> T findOne(Long sequencial);
	
	public <T extends Usuario> T findOne(String login);
	
	public <T extends Usuario> T save(Usuario principal);
	
	public List<Permissao> findByPermissaoUsuario(String login);

}