package br.jus.stf.autuacao.interfaces;

import java.util.List;

import javax.validation.Valid;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.jus.stf.autuacao.interfaces.commands.AutuarPeticaoCommand;
import br.jus.stf.autuacao.interfaces.commands.DistribuirPeticaoCommand;
import br.jus.stf.autuacao.interfaces.commands.RegistrarPeticaoFisicaCommand;
import br.jus.stf.autuacao.interfaces.dto.ProcessoDistribuidoDto;

import com.wordnik.swagger.annotations.ApiOperation;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 22.06.2015
 */
@RestController
public class PeticaoMockRestResource {
	
	@Autowired
	private ProcessEngineFactoryBean processEngine;

	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	private TaskService taskService;
	
    @ApiOperation(value = "Registra uma nova petição física", hidden = true)
	@RequestMapping(value = "/api/peticao/fisica", method = RequestMethod.POST)
	public String registrar(@Valid @RequestBody RegistrarPeticaoFisicaCommand command, BindingResult binding) throws Exception {
    	List<Deployment> list = processEngine.getObject().getRepositoryService().createDeploymentQuery().list();
    	
    	System.out.println(list);
    	
    	ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("autuarOriginarios");
    	
    	return processInstance.getId();
	}

    @ApiOperation(value = "Conclui a pré-autuação de uma determinada petição física", hidden = true)
	@RequestMapping(value = "/api/peticao/{id}/preautuacao", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void preautuar(@PathVariable String id) {
    	taskService.complete(id);
	}

    @ApiOperation(value = "Conclui a autuação de uma determinada petição")
	@RequestMapping(value = "/api/peticao/{id}/autuacao", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void autuar(@PathVariable String id, @RequestBody AutuarPeticaoCommand command) {
    	taskService.complete(id);
	}

    @ApiOperation(value = "Conclui a devolução de uma determinada petição recebida incorretamente", hidden = true)
	@RequestMapping(value = "/api/peticao/{id}/devolucao", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void devolver(@PathVariable String id) {
    	taskService.complete(id);
	}

    @ApiOperation(value = "Conclui a distribuição de uma determinada petição")
	@RequestMapping(value = "/api/peticao/{id}/distribuicao", method = RequestMethod.POST)
	public ProcessoDistribuidoDto distribuir(@PathVariable String id, @RequestBody DistribuirPeticaoCommand command) {
    	Task task = taskService.createTaskQuery().taskId(id).singleResult();
    	if (task == null) {
    		throw new IllegalArgumentException("Petição Inválida! Não será possível distribuir essa petição.");
    	}
    	
    	taskService.complete(id);
    	
		return new ProcessoDistribuidoDto("AP", "0001", "Min. Dias Toffoli");
	}
    
}
