package br.jus.stf.plataforma.acessos.interfaces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.plataforma.acessos.application.UsuarioApplicationService;
import br.jus.stf.plataforma.acessos.interfaces.dto.UsuarioDto;
import br.jus.stf.plataforma.shared.security.SecurityContextUtil;

/**
 * @author Anderson.Araujo
 *
 */
@Component
public class UsuarioServiceFacade {
	
	@Autowired
	private UsuarioApplicationService usuarioApplicationService;
	
	public UsuarioDto recuperarInformacoesUsuario(){
		return this.usuarioApplicationService.recuperarInformacoesUsuario(SecurityContextUtil.getNomeUsuario());
	}
}
