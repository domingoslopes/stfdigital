package br.jus.stf.plataforma.acessos.interfaces;

import java.security.Principal;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.jus.stf.plataforma.acessos.application.AcessosApplicationService;
import br.jus.stf.plataforma.acessos.interfaces.dto.PermissaoDto;
import br.jus.stf.plataforma.acessos.interfaces.dto.PermissaoDtoAssembler;

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

	@RequestMapping("/usuario")
	public Principal usuario(Principal usuario) {
		return usuario;
	}
	
	@RequestMapping("/usuarios/permissoes")
	public Set<PermissaoDto> permissoes(@RequestParam("login") String login) {
		return acessosApplicationService.carregarPermissoesUsuario(login).stream()
				.map(permissao -> permissaoDtoAssembler.toDto(permissao))
				.collect(Collectors.toSet());
	}
	
	@RequestMapping("/recursos/permissoes")
	public Set<PermissaoDto> permissoes(@RequestParam("nome") String nome, @RequestParam("tipoRecurso") String tipoRecurso) {
		return acessosApplicationService.carregarPermissoesRecurso(nome, tipoRecurso).stream()
				.map(permissao -> permissaoDtoAssembler.toDto(permissao))
				.collect(Collectors.toSet());
	}

	@RequestMapping("/usuarios/papeis")
	public Set<String> papeis(@RequestParam("login") String login) {
		return acessosApplicationService.carregarPapeisUsuario(login);
	}
	
}
