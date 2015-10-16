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

public class TarefaTest {

	@Mock
	private Task taskValida;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void criaTarefaValida() {
		when(taskValida.getId()).thenReturn("1");
		when(taskValida.getProcessInstanceId()).thenReturn("1");
		
		Tarefa tarefa = new Tarefa(taskValida);
		
		assertNotNull(tarefa);
		assertEquals(tarefa.id(), new TarefaId(1L));
		assertEquals(tarefa.definicao(), taskValida);
		assertEquals(tarefa.processo(), new ProcessoWorkflowId(1L));
		
		verify(taskValida, times(1)).getId();
		verify(taskValida, times(1)).getProcessInstanceId();
	}
	
	@Test(expected=NullPointerException.class)
	public void criaTarefaComTaskNula() {
		new Tarefa(null);
	}
	
	@Test
	public void comparaTarefasComIdentidadesIguais() {
		when(taskValida.getId()).thenReturn("1");
		when(taskValida.getProcessInstanceId()).thenReturn("1");
		
		Tarefa tarefa1 = new Tarefa(taskValida);
		Tarefa tarefa2 = new Tarefa(taskValida);
		
		assertTrue(tarefa1.sameIdentityAs(tarefa2));
		
		verify(taskValida, times(2)).getId();
		verify(taskValida, times(2)).getProcessInstanceId();
	}
	
	@Test
	public void comparaTarefasIdentidadesDiferentes() {
		when(taskValida.getId()).thenReturn("1");
		when(taskValida.getProcessInstanceId()).thenReturn("1");
		
		Tarefa tarefa1 = new Tarefa(taskValida);

		when(taskValida.getId()).thenReturn("2");
		when(taskValida.getProcessInstanceId()).thenReturn("2");
		Tarefa tarefa2 = new Tarefa(taskValida);
		
		assertFalse(tarefa1.sameIdentityAs(tarefa2));
		
		verify(taskValida, times(2)).getId();
		verify(taskValida, times(2)).getProcessInstanceId();
	}

}
