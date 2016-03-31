package br.jus.stf.core.shared;

import org.junit.Assert;
import org.junit.Test;

import br.jus.stf.core.shared.eventos.EnvolvidoRegistrado;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 29.02.2016
 */
public class EnvolvidoRegistradoUnitTests {
	
	@Test
	public void devemSerConsideradosIguais() {
		EnvolvidoRegistrado evento1 = new EnvolvidoRegistrado(1L, "01/2016", "João da Silva");
		EnvolvidoRegistrado evento2 = new EnvolvidoRegistrado(1L, "01/2016", "João da Silva");
		
		Assert.assertTrue(evento1.sameEventAs(evento2));
	}
	

	@Test
	public void devemSerConsideradosDiferentes() {
		EnvolvidoRegistrado evento1 = new EnvolvidoRegistrado(1L, "01/2016", "João da Silva");
		EnvolvidoRegistrado evento2 = new EnvolvidoRegistrado(2L, "02/2016", "Maria da Silva");
		
		Assert.assertFalse(evento1.sameEventAs(evento2));
	}
	
}
