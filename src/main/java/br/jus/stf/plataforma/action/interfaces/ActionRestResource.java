package br.jus.stf.plataforma.action.interfaces;

import java.util.Collection;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.jus.stf.plataforma.action.application.ActionApplicationService;
import br.jus.stf.plataforma.action.domain.exception.ActionUnavailableException;
import br.jus.stf.plataforma.action.interfaces.assembler.ActionDTOAssembler;
import br.jus.stf.plataforma.action.interfaces.commands.ExecuteActionCommand;
import br.jus.stf.plataforma.action.interfaces.commands.ListActionCommand;
import br.jus.stf.plataforma.action.interfaces.dto.ActionDTO;

import com.wordnik.swagger.annotations.ApiOperation;

/**
 * @author Lucas.Rodrigues
 *
 */
@RestController
@RequestMapping("/api/acoes")
public class ActionRestResource {
	
	@Autowired
	private ActionApplicationService actionApplicationService;
	
	@Autowired
	private ActionDTOAssembler actionDTOAssembler;
	
	@Autowired
	private HttpMessageConverters converters;

    @ApiOperation(value = "Lista as ações de um determinado contexto.")
	@RequestMapping(value = "/listar", method = RequestMethod.POST)
	public Collection<ActionDTO> list(@RequestBody @Valid ListActionCommand command) throws Exception {

    	return actionApplicationService
    			.listActions(command.getContext(), command.getResourcesType(), command.getResources())
    			.stream()
				.map(action -> actionDTOAssembler.toDTO(action))
				.collect(Collectors.toList());
	}
    
    @ApiOperation(value = "Executa a ação de um determinado contexto.")
	@RequestMapping("/executar")
	public void execute(@RequestBody @Valid ExecuteActionCommand command) throws ActionUnavailableException {
    	actionApplicationService
    		.executeAction(command.getActionId(), command.getResources());
	}
	
}
