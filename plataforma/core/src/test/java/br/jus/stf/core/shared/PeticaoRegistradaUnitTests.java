package br.jus.stf.core.shared;

import org.junit.Assert;
import org.junit.Test;

import br.jus.stf.core.shared.eventos.PeticaoRegistrada;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 03.01.2016
 */
public class PeticaoRegistradaUnitTests {
	
	@Test
	public void devemSerConsideradosIguais() {
		PeticaoRegistrada evento1 = new PeticaoRegistrada(1L, "01/2016");
		PeticaoRegistrada evento2 = new PeticaoRegistrada(1L, "01/2016");
		
		Assert.assertTrue(evento1.sameEventAs(evento2));
	}
	

	@Test
	public void devemSerConsideradosDiferentes() {
		PeticaoRegistrada evento1 = new PeticaoRegistrada(1L, "01/2016");
		PeticaoRegistrada evento2 = new PeticaoRegistrada(2L, "02/2016");
		
		Assert.assertFalse(evento1.sameEventAs(evento2));
	}
	
}
