package br.jus.stf.workflow.interfaces;

import java.util.List;
import java.util.stream.Collectors;

import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.jus.stf.workflow.application.TarefaApplicationService;
import br.jus.stf.workflow.interfaces.commands.CompletarTarefaCommand;
import br.jus.stf.workflow.interfaces.commands.SinalizarCommand;
import br.jus.stf.workflow.interfaces.dto.TarefaDto;
import br.jus.stf.workflow.interfaces.dto.TarefaDtoAssembler;

import com.wordnik.swagger.annotations.ApiOperation;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 23.06.2015
 */
@RestController
public class TarefaRestResource {
	
	@Autowired
	private TarefaDtoAssembler tarefaDtoAssembler;
	
    @Autowired 
    private TarefaApplicationService tarefaApplicationService;
    
    @ApiOperation(value = "Lista todas as tarefas associadas ao papel do usuário corrente")
	@RequestMapping(value = "/api/tarefas", method = RequestMethod.GET)
	public List<TarefaDto> tarefas(@RequestHeader(value="papel") String papel) {
    	List<Task> tarefas = tarefaApplicationService.tarefas(papel);
    	
        return tarefas.stream().map(tarefa -> tarefaDtoAssembler.toDto(tarefa)).collect(Collectors.toList());
	}
	
	public void completar(@PathVariable String id, @RequestBody CompletarTarefaCommand command) {
        tarefaApplicationService.completar(command.getIdTarefa());
	}

	public void sinalizar(SinalizarCommand command) {
        tarefaApplicationService.sinalizar(command.getSinal(), command.getExecutionId());
	}
	
	public TarefaDto consultar(@PathVariable String id){
		return tarefaDtoAssembler.toDto(tarefaApplicationService.consultar(id));
	}

}
