package br.jus.stf.plataforma.action.interfaces.facade;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.metamodel.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import br.jus.stf.plataforma.action.application.ActionApplicationService;
import br.jus.stf.plataforma.action.domain.Action;
import br.jus.stf.plataforma.action.domain.ActionBuilder;
import br.jus.stf.plataforma.action.interfaces.commands.RegisterActionCommand;

/**
 * Facade que permite o registro de ações
 * 
 * @author Lucas.Rodrigues
 *
 */
@Service
public class ActionServiceFacade {
	
	@Autowired
	private ActionApplicationService actionApplicationService;
	
	@Autowired
	private Validator validator;

	/**
	 * Recebe os comandos para o registro de ações
	 * 
	 * @param commands
	 */
	public void register(List<RegisterActionCommand> commands) {
		validate(commands);
    	List<Action> actions = new ArrayList<Action>();
    	commands.forEach(command -> actions.add(buildActionFrom(command)));
    	actionApplicationService.registerActions(actions);
	}
	
	/**
	 * Constrói uma ação a partir de um comando de registro
	 * 
	 * @param command
	 * @return a ação
	 */
	private Action buildActionFrom(RegisterActionCommand command) {
		return new ActionBuilder(command.getId(), command.getDescription(), command.getContext())
			.withResourcesInfo(command.getResourcesType(), command.getResourcesMode())
			.withAuthorithies(command.getGrantedAuthorities())
			.withConditionHandlers(command.hasConditionHandlers())
			.build();
	}
	
	/**
	 * Valida os comandos de registro
	 * 
	 * @param commands
	 * @throws ValidationException
	 */
	private void validate(List<RegisterActionCommand> commands) throws ValidationException {
		BindingResult errors = new BeanPropertyBindingResult(commands, "command");
		commands.forEach(command -> validator.validate(command, errors));

		if (errors.hasErrors()) {
			throw new ValidationException("Comando inválido!\n" + errors);
		}
	}
	
}
