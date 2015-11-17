package br.jus.stf.plataforma.acessos.interfaces;

import java.security.Principal;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Lucas.Rodrigues
 *
 */
@RestController
public class UsuarioRestResource {

	@RequestMapping("/api/usuario")
	public Principal usuario(Principal usuario) {
		return usuario;
	}
	
}
