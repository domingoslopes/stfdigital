package br.jus.stf.core.shared;

import org.junit.Assert;
import org.junit.Test;

import br.jus.stf.core.shared.eventos.ProcessoRegistrado;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 27.02.2016
 */
public class ProcessoRegistradoUnitTests {
	
	@Test
	public void devemSerConsideradosIguais() {
		ProcessoRegistrado evento1 = new ProcessoRegistrado(1L, "1");
		ProcessoRegistrado evento2 = new ProcessoRegistrado(1L, "1");
		
		Assert.assertTrue(evento1.sameEventAs(evento2));
	}
	

	@Test
	public void devemSerConsideradosDiferentes() {
		ProcessoRegistrado evento1 = new ProcessoRegistrado(1L, "1");
		ProcessoRegistrado evento2 = new ProcessoRegistrado(2L, "2");
		
		Assert.assertFalse(evento1.sameEventAs(evento2));
	}
	
}
