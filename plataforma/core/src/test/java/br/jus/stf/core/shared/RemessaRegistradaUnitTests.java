package br.jus.stf.core.shared;

import org.junit.Assert;
import org.junit.Test;

import br.jus.stf.core.shared.eventos.RemessaRegistrada;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 20.02.2016
 */
public class RemessaRegistradaUnitTests {
	
	@Test
	public void devemSerConsideradosIguais() {
		RemessaRegistrada evento1 = new RemessaRegistrada(1L, "01/2016");
		RemessaRegistrada evento2 = new RemessaRegistrada(1L, "01/2016");
		
		Assert.assertTrue(evento1.sameEventAs(evento2));
	}
	

	@Test
	public void devemSerConsideradosDiferentes() {
		RemessaRegistrada evento1 = new RemessaRegistrada(1L, "01/2016");
		RemessaRegistrada evento2 = new RemessaRegistrada(2L, "02/2016");
		
		Assert.assertFalse(evento1.sameEventAs(evento2));
	}
	
}
