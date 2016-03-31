package br.jus.stf.core.framework.workflow;

import java.util.Optional;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.ReadOnlyProcessDefinition;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.ImmutableMap;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 06.02.2016
 */
public abstract class StatusAdapterSupport<I, T> {
    
    @Autowired
    private RepositoryService repositoryService;
    
    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private TaskService taskService;
    
    public T nextStatus(I informationId, String transition) {
        String processId = processId(informationId);
        // Primeiro devemos verificar se existe algum processo ativo relacionado à remessa em questão
        if (runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(processId).count() == 0) {
            // Se não exite, devemos iniciar o processo...
            runtimeService.startProcessInstanceByKey(processKey(), processId, ImmutableMap.of("transition", transition, "informationId", informationId));
        } else {
            // Caso contrário, devemos completar a tarefa corrente
            Task task = taskService.createTaskQuery().processInstanceBusinessKey(processId).singleResult();
            
            taskService.complete(task.getId(), ImmutableMap.of("transition", transition, "informationId", informationId));
        }

        // Para ambos os casos acima, deve então selecionar a tarefa final gerada
        Optional<Task> task = Optional.ofNullable(taskService.createTaskQuery().processInstanceBusinessKey(processId).singleResult());
        if (task.isPresent()) {
            // Por fim, apenas recuperamos a descrição do status
            String statusDescription = statusFromTaskname(task.get().getProcessDefinitionId(), task.get().getTaskDefinitionKey());
            
            return statusFromDescription(statusDescription);
        }
        
        // Caso não exista nenhuma task ativa, então devemos procurar a remessa no histórico de execução (o processo BPM já foi concluído)
        HistoricProcessInstance process = historyService.createHistoricProcessInstanceQuery().processInstanceBusinessKey(processId).singleResult();
        
        // Após, devemos identificar qual o último evento executado.
        HistoricActivityInstance result = historyService.createHistoricActivityInstanceQuery().processInstanceId(process.getId()).activityType("endEvent").singleResult();
        
        // Por fim, apenas recuperamos a descrição do status
        String statusDescription = statusFromTaskname(process.getProcessDefinitionId(), result.getActivityId());
        
        return statusFromDescription(statusDescription);
    }

    public T nextStatus(I informationId) {
        return nextStatus(informationId, "");
    }
    
    private String statusFromTaskname(String processDefinitionId, String taskDefinitionKey) {
        ReadOnlyProcessDefinition deployedProcessDefinition = ((RepositoryServiceImpl) repositoryService).getDeployedProcessDefinition(processDefinitionId);
        PvmActivity activity = deployedProcessDefinition.getActivities().stream().filter(a -> a.getId().equals(taskDefinitionKey)).findFirst().get();
        return (String) activity.getProperty("documentation");
    }
    
    protected abstract T statusFromDescription(String description);
    
    protected abstract String processId(I informationId);
    
    protected abstract String processKey();
    
}
