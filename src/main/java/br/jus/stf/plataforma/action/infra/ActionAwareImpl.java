package br.jus.stf.plataforma.action.infra;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import br.jus.stf.plataforma.action.domain.Action;
import br.jus.stf.plataforma.action.domain.ActionAuthority;
import br.jus.stf.plataforma.action.domain.ActionAware;
import br.jus.stf.plataforma.action.domain.ActionRepository;
import br.jus.stf.plataforma.action.domain.SearchSpecification;
import br.jus.stf.plataforma.action.domain.exception.ActionUnavailableException;
import br.jus.stf.plataforma.component.action.service.ActionModuleService;

import com.fasterxml.jackson.databind.node.ArrayNode;


/**
 * @author Lucas.Rodrigues
 *
 */
@Component
public class ActionAwareImpl implements ActionAware {
	
	@Autowired
	private ActionRepository actionRepository;

	@Autowired
	private ActionModuleService actionModuleService;
	
	@Override
	public Set<Action> search(SearchSpecification spec) throws Exception {
		Set<Action> actions = new TreeSet<Action>((a1, a2) -> a1.description().compareTo(a2.description()));
		Set<ActionAuthority> actionAuths = getUserActionAuthorities();
		
		actionRepository.findAll(spec)
			.stream()
			.filter(action -> action.hasGrantedAccess(actionAuths))
			.forEach(action -> actions.add(action));
		
		removeActionsNotAllowed(spec, actions);
		
		return actions;
	}

	@Override
	public void execute(Action action, ArrayNode resources) throws ActionUnavailableException {
		try {
			actionModuleService.executeAction(action.id().toString(), resources);
		} catch (Exception e) {
			throw new ActionUnavailableException();
		}
	}
	
	/**
	 * Carrega os papéis do usuário a partir do contexto de segurança 
	 * 
	 * @return os papéis do usuário
	 */
	//TODO Lucas.Rodrigues: as authorities deve ser checado no módulo de segurança
	private Set<ActionAuthority> getUserActionAuthorities() {
		Set<ActionAuthority> actionAuths = new HashSet<ActionAuthority>();
		Authentication userAuth = SecurityContextHolder.getContext().getAuthentication();
		
		if (userAuth != null) {
			userAuth.getAuthorities()
				.forEach(auth -> actionAuths.add(
					new ActionAuthority(auth.getAuthority())));
		}
		return actionAuths;
	}
	
	/**
	 * Remove as ações previamente permitidas que foram permitidas
	 * no módulo de origem
	 * 
	 * @param spec
	 * @param actions
	 * @throws Exception
	 */
	private void removeActionsNotAllowed(SearchSpecification spec, Set<Action> actions) throws Exception {
		Set<String> actionsNotAllowed = new HashSet<String>(0);
		actions.stream()
			.filter(action -> action.hasConditionHandlers())
			.forEach(action -> actionsNotAllowed.add(action.id().toString()));
		
		if (actionsNotAllowed.size() > 0) {
			actionsNotAllowed.removeAll(actionModuleService
					.verifyActionsAllowed(actionsNotAllowed, spec.resourcesType(), spec.resources()));
			
			actions.removeIf(action -> actionsNotAllowed.contains(action.id().toString()));
		}
	}

}
