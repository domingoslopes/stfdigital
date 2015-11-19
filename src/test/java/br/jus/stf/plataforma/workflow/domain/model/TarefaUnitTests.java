package br.jus.stf.plataforma.workflow.domain.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.activiti.engine.task.Task;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.jus.stf.shared.ProcessoWorkflowId;
import br.jus.stf.shared.TarefaId;

public class TarefaUnitTests {

	@Mock
	private Task taskValida;
	@Mock
	private Metadado metadado;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		
		when(taskValida.getId()).thenReturn("1");
		when(taskValida.getProcessInstanceId()).thenReturn("1");
		when(taskValida.getTaskDefinitionKey()).thenReturn("teste");
		when(taskValida.getName()).thenReturn("Teste");
	}
	
	@Test
	public void criaTarefaValida() {
		Tarefa tarefa = tarefa();

		assertNotNull(tarefa);
		assertEquals(tarefa.id(), new TarefaId(1L));
		assertEquals(tarefa.nome(), "teste");
		assertEquals(tarefa.descricao(), "Teste");
		assertEquals(tarefa.processo(), new ProcessoWorkflowId(1L));
		
		verify(taskValida, times(1)).getId();
		verify(taskValida, times(1)).getProcessInstanceId();
	}
		
	@Test
	public void comparaTarefasComIdentidadesIguais() {
		when(taskValida.getId()).thenReturn("1");
		when(taskValida.getProcessInstanceId()).thenReturn("1");
		
		Tarefa tarefa1 = tarefa();
		Tarefa tarefa2 = tarefa();
		
		assertTrue(tarefa1.sameIdentityAs(tarefa2));
		
		verify(taskValida, times(2)).getId();
		verify(taskValida, times(2)).getProcessInstanceId();
	}
	
	@Test
	public void comparaTarefasIdentidadesDiferentes() {
		Tarefa tarefa1 = tarefa();

		when(taskValida.getId()).thenReturn("2");
		when(taskValida.getProcessInstanceId()).thenReturn("2");
		Tarefa tarefa2 = tarefa();
		
		assertFalse(tarefa1.sameIdentityAs(tarefa2));
		
		verify(taskValida, times(2)).getId();
		verify(taskValida, times(2)).getProcessInstanceId();
	}
	
	private Tarefa tarefa() {
		TarefaId id = new TarefaId(Long.parseLong(taskValida.getId()));
		ProcessoWorkflowId processo = new ProcessoWorkflowId(Long.parseLong(taskValida.getProcessInstanceId()));
		return new Tarefa(id, taskValida.getTaskDefinitionKey(), taskValida.getName(), processo, metadado);
	}

}
