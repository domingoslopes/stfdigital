package br.jus.stf.plataforma.identidades.application;

import br.jus.stf.plataforma.acessos.domain.model.Usuario;

/**
 * Registra eventos associados a usuários.
 * 
 * @author Anderson.Araujo
 * 
 * @since 14/12/2015
 *
 */
public interface UsuarioApplicationEvent {
	public void usuarioCadastrado(Usuario usuario);
}