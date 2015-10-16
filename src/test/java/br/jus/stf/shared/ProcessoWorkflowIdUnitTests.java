package br.jus.stf.shared;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ProcessoWorkflowIdUnitTests {
	
	@Test
	public void criaProcessoWorkflowIdValido() {
		ProcessoWorkflowId id = new ProcessoWorkflowId(1L);
		
		assertNotNull(id);
		assertTrue(id.toLong() == 1L);
	}
	
	@Test(expected=NullPointerException.class)
	public void criaProcessoWorkflowIdComSequencialNula() {
		new ProcessoWorkflowId(null);
	}
	
	@Test
	public void comparaProcessoWorkflowIdIguais() {
		ProcessoWorkflowId id1 = new ProcessoWorkflowId(1L);
		ProcessoWorkflowId id2 = new ProcessoWorkflowId(1L);
		
		assertTrue(id1.equals(id2));
	}
	
	@Test
	public void comparaProcessoWorkflowIdComValoresIguais() {
		ProcessoWorkflowId id1 = new ProcessoWorkflowId(1L);
		ProcessoWorkflowId id2 = new ProcessoWorkflowId(1L);
		
		assertTrue(id1.sameValueAs(id2));
	}
	
	@Test
	public void comparaProcessoWorkflowIdComHashesIguais() {
		ProcessoWorkflowId id1 = new ProcessoWorkflowId(1L);
		ProcessoWorkflowId id2 = new ProcessoWorkflowId(1L);
		
		assertTrue(id1.hashCode() == id2.hashCode());
	}
	
	@Test
	public void comparaProcessoWorkflowIdDiferentes() {
		ProcessoWorkflowId id1 = new ProcessoWorkflowId(1L);
		ProcessoWorkflowId id2 = new ProcessoWorkflowId(2L);
		
		assertFalse(id1.equals(id2));
	}
	
	@Test
	public void comparaProcessoWorkflowIdComValoresDiferentes() {
		ProcessoWorkflowId id1 = new ProcessoWorkflowId(1L);
		ProcessoWorkflowId id2 = new ProcessoWorkflowId(2L);
		
		assertFalse(id1.sameValueAs(id2));
	}
	
	@Test
	public void comparaProcessoWorkflowIdComHashesDiferentes() {
		ProcessoWorkflowId id1 = new ProcessoWorkflowId(1L);
		ProcessoWorkflowId id2 = new ProcessoWorkflowId(2L);
		
		assertFalse(id1.hashCode() == id2.hashCode());
	}
	
	@Test
	public void testaToString() {
		ProcessoWorkflowId id1 = new ProcessoWorkflowId(1L);
		
		assertEquals(id1.toString(), "1");
	}

}
