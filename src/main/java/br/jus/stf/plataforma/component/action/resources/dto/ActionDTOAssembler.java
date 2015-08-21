package br.jus.stf.plataforma.component.action.resources.dto;

import org.springframework.stereotype.Component;

import br.jus.stf.plataforma.component.action.support.ActionMappingInfo;

/**
 * @author Lucas.Rodrigues
 *
 */
@Component
public class ActionDTOAssembler {

	public ActionDTO toDTO(ActionMappingInfo actionInfo) {
		ActionDTO actionDTO = new ActionDTO();
		actionDTO.setId(actionInfo.getId());
		actionDTO.setDescription(actionInfo.getDescription());
		actionDTO.setResourcesType(actionInfo.getResourcesClass().getSimpleName());
		actionDTO.setHasConditionHandlers(actionInfo.getActionHandlersInfo().size() > 0);
		actionDTO.setResourcesMode(actionInfo.getResourcesMode().name());
		actionDTO.getNeededAuthorithies().addAll(actionInfo.getNeededAuthorities());
		return actionDTO;
	}
	
}
