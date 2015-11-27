package br.jus.stf.plataforma.acessos.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.plataforma.acessos.domain.model.UsuarioRepository;
import br.jus.stf.plataforma.acessos.interfaces.dto.UsuarioDto;

/**
 * Realiza operações sobre usuários.
 * 
 * @author Anderson.Araujo
 * @since 27.11.2015
 *
 */
@Component
public class UsuarioApplicationService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	/**
	 * Recupera as informações do usuário.
	 * 
	 * @param login Login do usuário.
	 * @return Informações do usuário.
	 */
	public UsuarioDto recuperarInformacoesUsuario(String login){
		
		return null;
	}
}
