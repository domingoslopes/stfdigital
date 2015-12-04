package br.jus.stf.plataforma.acessos.interfaces;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.jus.stf.plataforma.acessos.application.AcessosApplicationService;
import br.jus.stf.plataforma.acessos.domain.model.Usuario;
import br.jus.stf.plataforma.acessos.interfaces.commands.CadastrarUsuarioCommand;
import br.jus.stf.plataforma.acessos.interfaces.commands.ConfigurarPermissoesUsuarioCommand;
import br.jus.stf.plataforma.acessos.interfaces.dto.GrupoDto;
import br.jus.stf.plataforma.acessos.interfaces.dto.GrupoDtoAssembler;
import br.jus.stf.plataforma.acessos.interfaces.dto.PapelDto;
import br.jus.stf.plataforma.acessos.interfaces.dto.PapelDtoAssembler;
import br.jus.stf.plataforma.acessos.interfaces.dto.PermissaoDto;
import br.jus.stf.plataforma.acessos.interfaces.dto.PermissaoDtoAssembler;
import br.jus.stf.plataforma.acessos.interfaces.dto.UsuarioDto;
import br.jus.stf.plataforma.acessos.interfaces.dto.UsuarioDtoAssembler;
import br.jus.stf.plataforma.shared.security.SecurityContextUtil;

import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

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
	private GrupoDtoAssembler grupoDtoAssembler;
	
	@Autowired
	private PapelDtoAssembler papelDtoAssembler; 
	
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
	public Set<PapelDto> papeis(@RequestParam("login") String login) {
		return acessosApplicationService.carregarPapeisUsuario(login).stream()
				.map(papel -> this.papelDtoAssembler.toDto(papel))
				.sorted((p1, p2) -> p1.getNome().compareTo(p2.getNome()))
				.collect(Collectors.toCollection(LinkedHashSet::new));
	}
	
	@RequestMapping("/usuarios/grupos")
	public Set<GrupoDto> grupos(@RequestParam("login") String login) {
		return acessosApplicationService.carregarGruposUsuario(login).stream()
				.map(grupo -> this.grupoDtoAssembler.toDto(grupo))
				.sorted((g1, g2) -> g1.getNome().compareTo(g2.getNome()))
				.collect(Collectors.toCollection(LinkedHashSet::new)); 
	}
	
	@ApiOperation("Configura as permissões a grupos e papéis de um usuário.")
	@RequestMapping(value = "/permissoes/configuracao", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public void configurarPermissoesUsuario(@RequestBody ConfigurarPermissoesUsuarioCommand command ){
		this.acessosApplicationService.configurarPermissoesUsuario(command.getIdUsuario(), command.getPapeisAdicionados(), 
				command.getGruposAdicionados(), command.getPapeisRemovidos(), command.getGruposRemovidos());
	}
	
	/**
	 * Recupera as informações do usuário logado.
	 * 
	 * @param id Id do usuário.
	 * @return Informações do usuário.
	 */
	@ApiOperation("Recupera as informações do usuário logado.")
	@RequestMapping(value="/usuario", method = RequestMethod.GET)
	public UsuarioDto recuperarInformacoes() {
		
		String login = SecurityContextUtil.getUsername();
		Set<GrantedAuthority> authorities = SecurityContextUtil.getAuthorities();
		Usuario usuario = this.acessosApplicationService.recuperarInformacoesUsuario(login);
		
		return this.usuarioDtoAssembler.toDto(usuario, authorities);
	}
	

	/**
	 * Cadastra um novo usuário
	 * 
	 * @param CadastrarUsuarioCommand command
	 * @param BindingResult binding
	 * @return Long ID do usuário criado
	 */
	@ApiOperation("Cadastra um novo usuário")
	@ApiResponses(value = {@ApiResponse(code = 400, message = "Usuário Inválido")})
	@RequestMapping(value="/usuarios", method = RequestMethod.POST)
	public Long cadastrarUsuario(@RequestBody @Valid CadastrarUsuarioCommand command, BindingResult binding) {
		if (binding.hasErrors()) {
			throw new IllegalArgumentException("Usuário inválido: " + binding.getAllErrors());
		}
		
		Usuario usuario = this.acessosApplicationService.cadastrarUsuario(
			command.getLogin(), 
			command.getNome(), 
			command.getCpf(), 
			command.getOab(), 
			command.getEmail(), 
			command.getTelefone()
		);
		
		return usuario.id().toLong();
	}
}