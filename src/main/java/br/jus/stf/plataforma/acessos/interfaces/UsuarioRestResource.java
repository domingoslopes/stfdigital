package br.jus.stf.plataforma.acessos.interfaces;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.jus.stf.plataforma.acessos.interfaces.dto.UsuarioDto;

/**
 * @author Lucas.Rodrigues
 *
 */
@RestController
public class UsuarioRestResource {

	@Autowired
	private UsuarioServiceFacade usuarioServiceFacade; 
	
	@RequestMapping("/api/usuario")
	public Principal usuario(Principal usuario) {
		return usuario;
	}

	/**
	 * Recupera as informações do usuário.
	 * 
	 * @param id Id do usuário.
	 * @return Informações do usuário.
	 */
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public UsuarioDto recuperarInformacoes(@PathVariable Long id) {
		return this.usuarioServiceFacade.recuperarInformacoesUsuario();
	}
}
