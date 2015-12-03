package br.jus.stf.plataforma.acessos.domain.model;

import java.util.List;



public interface UsuarioRepository {
	
	public Usuario findOne(Long sequencial);
	
	public Usuario findOne(String login);
	
	public Usuario save(Usuario principal);
	
	public List<Permissao> findByPermissaoUsuario(String login);

}