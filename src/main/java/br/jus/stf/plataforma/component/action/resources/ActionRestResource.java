package br.jus.stf.plataforma.component.action.resources;

import java.util.Collection;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.jus.stf.plataforma.component.action.resources.commands.ResourcesCommand;
import br.jus.stf.plataforma.component.action.resources.commands.VerifyActionsCommand;
import br.jus.stf.plataforma.component.action.resources.dto.ActionDTO;
import br.jus.stf.plataforma.component.action.resources.dto.ActionDTOAssembler;
import br.jus.stf.plataforma.component.action.service.ActionService;

import com.wordnik.swagger.annotations.ApiOperation;

/**
 * @author Lucas.Rodrigues
 *
 */
@RestController
@RequestMapping("/api/actions")
public class ActionRestResource {
	
	@Autowired
	private ActionService actionService;
	
	@Autowired
	private ActionDTOAssembler actionDTOAssembler;
	
    @ApiOperation(value = "Lista as ações de um determinado contexto.")
	@RequestMapping(value = "", method = RequestMethod.GET)
	public Collection<ActionDTO> list() {

    	return actionService.listActions()
    			.stream()
				.map(action -> actionDTOAssembler.toDTO(action))
				.sorted((a1, a2) -> a1.getDescription().compareTo(a2.getDescription()))
				.collect(Collectors.toList());
	}
    
    @ApiOperation(value = "Verifica se uma ação pode ser executada.")
	@RequestMapping(value = "/{actionId}/verify", method = RequestMethod.POST)
	public boolean verify(@PathVariable("actionId") String actionId, @RequestBody ResourcesCommand command) {
    	return actionService.isAllowed(actionId, command.getResources());
	}
    
    @ApiOperation(value = "Verifica se as ações podem ser executadas.")
	@RequestMapping(value = "/verify", method = RequestMethod.POST)
	public Collection<String> verify(@RequestBody @Valid VerifyActionsCommand command, BindingResult result) {
    	
    	if (result.hasErrors()) {
    		throw new IllegalArgumentException("Verificação inválida: " + result.getAllErrors());
    	}
    	return command.getIds()
    		.stream()
    		.filter(id -> actionService.isAllowed(id, command.getResources()))
    		.collect(Collectors.toList());
	}
    
    @ApiOperation(value = "Executa a ação de um determinado contexto.")
	@RequestMapping("/{actionId}/execute")
	public Object execute(@PathVariable("actionId") String actionId, @RequestBody ResourcesCommand command) {
    	return actionService.executeAction(actionId, command.getResources());
	}
	
}
