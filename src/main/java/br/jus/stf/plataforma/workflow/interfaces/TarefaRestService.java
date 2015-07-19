package br.jus.stf.plataforma.workflow.interfaces;

import java.util.List;
import java.util.stream.Collectors;

import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.jus.stf.plataforma.workflow.application.TarefaApplicationService;
import br.jus.stf.plataforma.workflow.interfaces.commands.CompletarTarefaCommand;
import br.jus.stf.plataforma.workflow.interfaces.commands.SinalizarCommand;
import br.jus.stf.plataforma.workflow.interfaces.dto.TarefaDto;
import br.jus.stf.plataforma.workflow.interfaces.dto.TarefaDtoAssembler;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 23.06.2015
 */
@RestController
@Api(basePath = "/api/tarefas", value = "Tarefas", description = "Operações sobre Tarefas", produces = "application/json")
public class TarefaRestService {
	
	private TarefaDtoAssembler dtoAssembler = new TarefaDtoAssembler();
	
    @Autowired 
    private TarefaApplicationService tarefaApplicationService;
    
    @ApiOperation(value = "Lista todas as tarefas associadas ao papel do usuário corrente")
	@RequestMapping(value = "/api/tarefas", method = RequestMethod.GET)
	public List<TarefaDto> tarefas(@RequestHeader(value="papel") String papel) {
    	List<Task> tarefas = tarefaApplicationService.tarefas(papel);
    	
        return tarefas.stream().map(tarefa -> dtoAssembler.toDto(tarefa)).collect(Collectors.toList()); 
	}
	
    @ApiOperation(value = "Completa uma dada tarefa")
    @RequestMapping(value = "/api/tarefa", method = RequestMethod.POST)
	public void completar(@RequestBody CompletarTarefaCommand command) {
        tarefaApplicationService.completar(command.getIdTarefa());
	}

	public void sinalizar(SinalizarCommand command) {
        tarefaApplicationService.sinalizar(command.getSinal());
	}

}
