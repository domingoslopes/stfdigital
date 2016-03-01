package br.jus.stf.plataforma.shared.security;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

/**
 * Este provedor de autenticação apenas carrega as permissões do usuário que acessou
 * com base no mescanismo de acessos. 
 * TODO A verificação de senha deverá ser implementada posteriormente com integração LDAP.
 * 
 * @author Lucas.Rodrigues
 *
 */
@Component
public class AuthenticationProviderImpl implements AuthenticationProvider {
	
	private static final String PASS = "N/A";
	
	@Autowired
	private AcessosRestAdapter acessosRestAdapter;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String login = authentication.getName();
        Optional<UserDetails> userDetails = acessosRestAdapter.recuperarUsuario(login);
        
        if (userDetails.isPresent()) {
        	Set<GrantedAuthority> permissoes = acessosRestAdapter.carregarPermissoesUsuario(login);
        	userDetails.get().getPapeis().addAll(acessosRestAdapter.carregarPapeisUsuario(login));
        	userDetails.get().getRecursos().addAll(acessosRestAdapter.carregarRecursosUsuario(login));
        	
        	UserImpl user = new UserImpl(login, PASS, userDetails.get(), permissoes);
            return new UsernamePasswordAuthenticationToken(user, PASS, permissoes);
        }
        return null;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
