package br.jus.stf.plataforma.actions;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;

import br.jus.stf.plataforma.shared.actions.service.ActionMappingRegistry;
import br.jus.stf.plataforma.shared.actions.support.ActionMappingInfo;

public class ActionMappingRegistryUnitTests {

	@Test
	public void findNotRegisteredAction() {
		ActionMappingRegistry registry = new ActionMappingRegistry();
		Optional<ActionMappingInfo> op = registry.findRegisteredActionsById("ACTIONID");
		Assert.assertFalse(op.isPresent());
	}
	
	@Test(expected = NullPointerException.class)
	public void findActionIdNull() {
		ActionMappingRegistry registry = new ActionMappingRegistry();
		registry.findRegisteredActionsById(null);
	}
	
}
