package br.jus.stf.core.shared;

import org.junit.Assert;
import org.junit.Test;

import br.jus.stf.core.shared.eventos.RecebimentoFinalizado;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 26.12.2015
 */
public class RecebimentoFinalizadoUnitTests {
	
	@Test
	public void devemSerConsideradosIguais() {
		RecebimentoFinalizado evento1 = new RecebimentoFinalizado(1L);
		RecebimentoFinalizado evento2 = new RecebimentoFinalizado(1L);
		
		Assert.assertTrue(evento1.sameEventAs(evento2));
	}
	

	@Test
	public void devemSerConsideradosDiferentes() {
		RecebimentoFinalizado evento1 = new RecebimentoFinalizado(1L);
		RecebimentoFinalizado evento2 = new RecebimentoFinalizado(2L);
		
		Assert.assertFalse(evento1.sameEventAs(evento2));
	}
	
}
