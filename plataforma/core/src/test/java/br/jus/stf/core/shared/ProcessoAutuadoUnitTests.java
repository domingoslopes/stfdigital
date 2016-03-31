package br.jus.stf.core.shared;

import org.junit.Assert;
import org.junit.Test;

import br.jus.stf.core.shared.eventos.ProcessoAutuado;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 27.02.2016
 */
public class ProcessoAutuadoUnitTests {
	
	@Test
	public void devemSerConsideradosIguais() {
		ProcessoAutuado evento1 = new ProcessoAutuado("1", "01/2016");
		ProcessoAutuado evento2 = new ProcessoAutuado("1", "01/2016");
		
		Assert.assertTrue(evento1.sameEventAs(evento2));
	}
	

	@Test
	public void devemSerConsideradosDiferentes() {
		ProcessoAutuado evento1 = new ProcessoAutuado("1", "01/2016");
		ProcessoAutuado evento2 = new ProcessoAutuado("2", "02/2016");
		
		Assert.assertFalse(evento1.sameEventAs(evento2));
	}
	
}
