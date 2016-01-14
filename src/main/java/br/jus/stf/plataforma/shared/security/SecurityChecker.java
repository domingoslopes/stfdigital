package br.jus.stf.plataforma.shared.security;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import br.jus.stf.plataforma.shared.security.stereotype.Secured;

/**
 * Classe responsável por verificar se o usuário possui as permissões necessárias
 * para acessar um recurso
 * 
 * @author Lucas.Rodrigues
 *
 */
@Component("securityChecker")
public class SecurityChecker {
	
	@Autowired
	private AcessosRestAdapter acessosRestAdapter;
	
	public boolean hasPermission(Object targetObject) {
		
		Class<?> clazz = targetObject.getClass();
		
		if (Optional.class.isAssignableFrom(clazz)) {
			targetObject = ((Optional<?>) targetObject).get();
			clazz = targetObject.getClass();
		}
		
		if (Secured.class.isAssignableFrom(clazz)) {
			return SecurityContextUtil.userContainsAll(neededAuthorities((Secured) targetObject));
		}
		//TODO O mecanismo deve testar permissões sobre os dados também. As permissões exigidas pela
		// ação executada deverá ser confrontada com as permissões exigidas pelo dado
		return true;
	}
	
	private Set<GrantedAuthority> neededAuthorities(Secured targetObject) {
		return acessosRestAdapter
				.carregarPermissoesRecurso(targetObject.resourceId(), targetObject.type());
	}
}
