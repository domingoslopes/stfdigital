package br.jus.stf.plataforma.action.interfaces.assembler;

import org.springframework.stereotype.Component;

import br.jus.stf.plataforma.action.domain.Action;
import br.jus.stf.plataforma.action.interfaces.dto.ActionDTO;

/**
 * @author Lucas.Rodrigues
 *
 */
@Component
public class ActionDTOAssembler {

	public ActionDTO toDTO(Action action) {
		return new ActionDTO(action.id().toString(), action.description());
	}
	
}
