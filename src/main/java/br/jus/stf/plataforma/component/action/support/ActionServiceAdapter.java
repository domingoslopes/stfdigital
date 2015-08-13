package br.jus.stf.plataforma.component.action.support;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.plataforma.action.interfaces.commands.RegisterActionCommand;
import br.jus.stf.plataforma.action.interfaces.facade.ActionServiceFacade;

/**
 * Adapter para requisições ao mecanismo de ações
 * TODO A chamada ao facade do mecanismo deve ser alterado por um mecanismo de integração
 * 
 * @author Lucas.Rodrigues
 *
 */
@Component
public class ActionServiceAdapter {

	@Autowired
	private ActionServiceFacade actionServiceFacade;
	
	/**
	 * Invoca o registro das ações no mecanismo principal
	 * 
	 * @param actions
	 */
	public void register(Map<String, ActionMappingInfo> actions) {
		List<RegisterActionCommand> commands = new ArrayList<RegisterActionCommand>();	
		actions.forEach((actionId, actionInfo) -> commands.add(getRegisterActionCommand(actionInfo)));
		
		actionServiceFacade.register(commands);
	}


	/**
	 * Monta os comandos de registro das ações
	 * 
	 * @param actionInfo
	 * @return o comando de registro
	 */
	private RegisterActionCommand getRegisterActionCommand(ActionMappingInfo actionInfo) {
		RegisterActionCommand command = new RegisterActionCommand();
		command.setId(actionInfo.getId());
		command.setDescription(actionInfo.getDescription());
		command.setContext(actionInfo.getContext());
		command.setResourcesType(actionInfo.getResourcesType());
		command.setResourcesMode(actionInfo.getResourcesMode().name());
		command.getGrantedAuthorities().addAll(actionInfo.getGrantedAuthorities());
		command.setHasConditionHandlers(actionInfo.getActionHandlersInfo().size() > 0);
		return command;
	}
	
}
