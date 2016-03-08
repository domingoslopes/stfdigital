package br.jus.stf.plataforma.actions;

import org.junit.Test;

import br.jus.stf.plataforma.shared.actions.support.ActionMappingInfo;

/**
 * @author Lucas.Rodrigues
 *
 */
public class ActionMappingInfoUnitTests {
    
	@Test(expected = NullPointerException.class)
	public void construtorNull() {
		new ActionMappingInfo(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void construtorEmpty() {
		new ActionMappingInfo("");
	}
	
	@Test
	public void construtor() {
		new ActionMappingInfo("teste");
	}

}
