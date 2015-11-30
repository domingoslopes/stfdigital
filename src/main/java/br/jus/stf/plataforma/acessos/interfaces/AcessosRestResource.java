package br.jus.stf.plataforma.acessos.interfaces;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.jus.stf.plataforma.acessos.application.AcessosApplicationService;
import br.jus.stf.plataforma.acessos.domain.model.Usuario;
import br.jus.stf.plataforma.acessos.interfaces.dto.PapelDto;
import br.jus.stf.plataforma.acessos.interfaces.dto.PapelDtoAssembler;
import br.jus.stf.plataforma.acessos.interfaces.dto.PermissaoDto;
import br.jus.stf.plataforma.acessos.interfaces.dto.PermissaoDtoAssembler;
import br.jus.stf.plataforma.acessos.interfaces.dto.UsuarioDto;
import br.jus.stf.plataforma.acessos.interfaces.dto.UsuarioDtoAssembler;
import br.jus.stf.plataforma.shared.security.SecurityContextUtil;

/**
 * @author Lucas.Rodrigues
 *
 */
@RestController
@RequestMapping("/api/acessos")
public class AcessosRestResource {
	
	@Autowired
	private AcessosApplicationService acessosApplicationService;
	
	@Autowired
	private PermissaoDtoAssembler permissaoDtoAssembler;
	
	@Autowired
	private UsuarioDtoAssembler usuarioDtoAssembler;
	
	@Autowired
	private PapelDtoAssembler papelDtoAssembler; 

	/*@RequestMapping("/usuario")
	public Principal usuario(Principal usuario) {
		return usuario;
	}*/
	
	@RequestMapping("/usuarios/permissoes")
	public Set<PermissaoDto> permissoes(@RequestParam("login") String login) {
		return acessosApplicationService.carregarPermissoesUsuario(login).stream()
				.map(permissao -> permissaoDtoAssembler.toDto(permissao))
				.collect(Collectors.toSet());
	}
	
	@RequestMapping("/recursos/permissoes")
	public Set<PermissaoDto> permissoes(@RequestParam("nome") String nome, @RequestParam("tipo") String tipo) {
		return acessosApplicationService.carregarPermissoesRecurso(nome, tipo).stream()
				.map(permissao -> permissaoDtoAssembler.toDto(permissao))
				.collect(Collectors.toSet());
	}

	@RequestMapping("/usuarios/papeis")
	public Set<String> papeis(@RequestParam("login") String login) {
		return acessosApplicationService.carregarPapeisUsuario(login);
	}
	
	/**
	 * Recupera as informações do usuário.
	 * 
	 * @param id Id do usuário.
	 * @return Informações do usuário.
	 */
	@RequestMapping(value="/usuario", method = RequestMethod.GET)
	public UsuarioDto recuperarInformacoes() {
		
		String login = SecurityContextUtil.getUsername();
		Set<GrantedAuthority> authorities = (Set<GrantedAuthority>) SecurityContextUtil.getAuthorities();
		Usuario usuario = this.acessosApplicationService.recuperarInformacoesUsuario(login);
		Set<PapelDto> papeis = usuario.papeis().stream().map(papel -> this.papelDtoAssembler.toDto(papel)).collect(Collectors.toSet());
		
		return this.usuarioDtoAssembler.toDto(usuario.nome(), "", papeis, authorities);
	}
	
}
