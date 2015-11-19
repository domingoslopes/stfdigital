package br.jus.stf.shared;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ProcessoWorkflowUnitTests {
	
	@Test
	public void criaProcessoWorkflowValido() {
		ProcessoWorkflow ProcessoWorkflow = new ProcessoWorkflow(new ProcessoWorkflowId(1L), "DISTRIBUIDA");
		
		assertNotNull(ProcessoWorkflow);
		assertEquals(ProcessoWorkflow.id(), new ProcessoWorkflowId(1L));
		assertEquals(ProcessoWorkflow.status(), "DISTRIBUIDA");
	}
	
	@Test(expected=NullPointerException.class)
	public void criaProcessoWorkflowComIdNulo() {
		new ProcessoWorkflow(null, "DISTRIBUIDA");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void criaProcessoWorkflowComNomeVazio() {
		new ProcessoWorkflow(new ProcessoWorkflowId(1L), "");
	}
	
	@Test(expected=NullPointerException.class)
	public void criaProcessoWorkflowComNomeNulo() {
		new ProcessoWorkflow(new ProcessoWorkflowId(1L), null);
	}
	
	@Test
	public void comparaProcessoWorkflowIguais() {
		ProcessoWorkflow ProcessoWorkflow1 = new ProcessoWorkflow(new ProcessoWorkflowId(1L), "DISTRIBUIDA");
		ProcessoWorkflow ProcessoWorkflow2 = new ProcessoWorkflow(new ProcessoWorkflowId(1L), "DISTRIBUIDA");
		
		assertTrue(ProcessoWorkflow1.equals(ProcessoWorkflow2));
	}
	
	@Test
	public void comparaProcessoWorkflowComIdentidadesIguais() {
		ProcessoWorkflow ProcessoWorkflow1 = new ProcessoWorkflow(new ProcessoWorkflowId(1L), "DISTRIBUIDA");
		ProcessoWorkflow ProcessoWorkflow2 = new ProcessoWorkflow(new ProcessoWorkflowId(1L), "DISTRIBUIDA");
		
		assertTrue(ProcessoWorkflow1.sameIdentityAs(ProcessoWorkflow2));
	}
	
	@Test
	public void comparaProcessoWorkflowComHashesIguais() {
		ProcessoWorkflow ProcessoWorkflow1 = new ProcessoWorkflow(new ProcessoWorkflowId(1L), "DISTRIBUIDA");
		ProcessoWorkflow ProcessoWorkflow2 = new ProcessoWorkflow(new ProcessoWorkflowId(1L), "DISTRIBUIDA");
		
		assertTrue(ProcessoWorkflow1.hashCode() == ProcessoWorkflow2.hashCode());
	}
	
	@Test
	public void comparaProcessoWorkflowDiferentes() {
		ProcessoWorkflow ProcessoWorkflow1 = new ProcessoWorkflow(new ProcessoWorkflowId(1L), "DISTRIBUIDA");
		ProcessoWorkflow ProcessoWorkflow2 = new ProcessoWorkflow(new ProcessoWorkflowId(2L), "ACEITA");
		
		assertFalse(ProcessoWorkflow1.equals(ProcessoWorkflow2));
	}
	
	@Test
	public void comparaProcessoWorkflowComIdentidadesDiferentes() {
		ProcessoWorkflow ProcessoWorkflow1 = new ProcessoWorkflow(new ProcessoWorkflowId(1L), "DISTRIBUIDA");
		ProcessoWorkflow ProcessoWorkflow2 = new ProcessoWorkflow(new ProcessoWorkflowId(2L), "ACEITA");
		
		assertFalse(ProcessoWorkflow1.sameIdentityAs(ProcessoWorkflow2));
	}
	
	@Test
	public void comparaProcessoWorkflowComHashesDiferentes() {
		ProcessoWorkflow ProcessoWorkflow1 = new ProcessoWorkflow(new ProcessoWorkflowId(1L), "DISTRIBUIDA");
		ProcessoWorkflow ProcessoWorkflow2 = new ProcessoWorkflow(new ProcessoWorkflowId(2L), "ACEITA");
		
		assertFalse(ProcessoWorkflow1.hashCode() == ProcessoWorkflow2.hashCode());
	}

}
