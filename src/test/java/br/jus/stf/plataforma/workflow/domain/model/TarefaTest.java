package br.jus.stf.plataforma.workflow.domain.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
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

	private Task taskNula = null;
	private TarefaId idValido = new TarefaId(1L);
	private ProcessoWorkflowId processoWorkflowValido = new ProcessoWorkflowId(1L);
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		when(taskValida.getId()).thenReturn(idValido.toString());
		when(taskValida.getProcessInstanceId()).thenReturn(processoWorkflowValido.toLong().toString());
	}
	
	@Test
	public void criaTarefaValida() {
		Tarefa tarefa = new Tarefa(taskValida);
		
		assertNotNull(tarefa);
		assertEquals(tarefa.id(), idValido);
		assertEquals(tarefa.definicao(), taskValida);
		assertEquals(tarefa.processo(), processoWorkflowValido);
	}
	
	@Test(expected=NullPointerException.class)
	public void criaTarefaComTaskNula() {
		new Tarefa(taskNula);
	}
	
	@Test
	public void tarefasIguais() {
		Tarefa tarefa1 = new Tarefa(taskValida);
		Tarefa tarefa2 = new Tarefa(taskValida);
		
		assertTrue(tarefa1.sameIdentityAs(tarefa2));
	}
	
	@Test
	public void tarefasDiferentes() {
		Tarefa tarefa1 = new Tarefa(taskValida);

		when(taskValida.getId()).thenReturn("2");
		when(taskValida.getProcessInstanceId()).thenReturn("2");
		Tarefa tarefa2 = new Tarefa(taskValida);
		
		assertFalse(tarefa1.sameIdentityAs(tarefa2));
	}

}
