package br.jus.stf.plataforma.shared.actions.resources;

import java.util.Optional;

import org.springframework.stereotype.Component;

import br.jus.stf.plataforma.shared.actions.support.ActionMappingInfo;

/**
 * @author Lucas.Rodrigues
 *
 */
@Component
public class ActionDtoAssembler {

	public ActionDto toDto(ActionMappingInfo actionInfo) {
		ActionDto dto = new ActionDto();
		dto.setId(actionInfo.getId());
		dto.setDescription(actionInfo.getDescription());
		dto.setResourcesMode(actionInfo.getResourcesMode());
		dto.setNeededAuthorities(actionInfo.getNeededAuthorities());
		String resourcesType = Optional.ofNullable(actionInfo.getResourcesClass())
			.map(resourcesClass -> resourcesClass.getSimpleName())
			.orElse("");
		dto.setResourcesType(resourcesType);
		dto.setHasConditionHandlers(!actionInfo.getActionHandlersInfo().isEmpty());
		return dto;
	}
	
}
