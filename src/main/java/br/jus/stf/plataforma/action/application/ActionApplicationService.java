package br.jus.stf.plataforma.action.application;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.jus.stf.plataforma.action.domain.Action;
import br.jus.stf.plataforma.action.domain.ActionAware;
import br.jus.stf.plataforma.action.domain.ActionId;
import br.jus.stf.plataforma.action.domain.ActionRepository;
import br.jus.stf.plataforma.action.domain.exception.ActionUnavailableException;
import br.jus.stf.plataforma.action.domain.specification.ActionSpecification;

import com.fasterxml.jackson.databind.node.ArrayNode;

/**
 * @author Lucas.Rodrigues
 *
 */
@Service
public class ActionApplicationService {
	
	@Autowired
	private ActionAware actionAware;
	
	private ActionRepository actionRepository;
	
	@Autowired
	public ActionApplicationService(ActionRepository actionRepository) {
		this.actionRepository = actionRepository;
	}
	
	@Transactional
	public void registerActions(List<Action> actions) {
		
		List<ActionId> ids = new ArrayList<ActionId>();
		actions.forEach(action -> ids.add(action.id()));
		
		List<Action> registeredActions = actionRepository.findAll(ids);
		registeredActions.forEach(action -> action.updateFrom(actions));
		actions.removeAll(registeredActions);
		
		actionRepository.save(registeredActions);
		actionRepository.save(actions);
	}
	
	@Transactional
	public void unregisterActions(List<ActionId> actionIds) {
		//TODO Lucas.Rodrigues: desregistrar as ações de ids que não foram enviados
		//para registro dado um contexto, pode ser necessário confirmar no módulo
		//fazer exclusão lógica, etc
	}
	
	public Collection<Action> listActions(String context, String resourcesType, ArrayNode resources) throws Exception {
		ActionSpecification spec = new ActionSpecification(context, resourcesType, resources);
		return actionAware.search(spec);
	}

	public void executeAction(String actionId, ArrayNode resources) throws ActionUnavailableException {
		Action action = actionRepository.findOne(new ActionId(actionId));
		actionAware.execute(action, resources);
	}
	
}
