package br.jus.stf.plataforma.actions;

import org.junit.Test;

import br.jus.stf.plataforma.shared.actions.service.ActionMappingRegistry;

public class ActionMappingRegistryUnitTests {

	@Test(expected = RuntimeException.class)
	public void findNotRegisteredAction() {
		ActionMappingRegistry registry = new ActionMappingRegistry();
		registry.findRegisteredActionsById("ACTIONID");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void findActionIdNull() {
		ActionMappingRegistry registry = new ActionMappingRegistry();
		registry.findRegisteredActionsById(null);
	}
	
}
