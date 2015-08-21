package br.jus.stf.plataforma.action;

import org.junit.Test;

import br.jus.stf.plataforma.component.action.service.ActionMappingRegistry;

public class ActionMappingRegistryTests {

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
