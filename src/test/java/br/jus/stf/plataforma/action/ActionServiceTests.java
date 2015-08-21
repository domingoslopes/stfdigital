package br.jus.stf.plataforma.action;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collection;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.springframework.context.ApplicationContext;

import br.jus.stf.plataforma.action.dummy.DummyActionController;
import br.jus.stf.plataforma.component.action.service.ActionMappingRegistry;
import br.jus.stf.plataforma.component.action.service.ActionService;
import br.jus.stf.plataforma.component.action.support.ActionMappingInfo;

import com.fasterxml.jackson.databind.node.ArrayNode;

@PrepareForTest(ActionService.class)
public class ActionServiceTests {
	
	@Mock
	private ActionMappingRegistry actionMappingRegistry;
	
	@Mock
	private ApplicationContext applicationContext;
	
	@InjectMocks
	private ActionService actionService;
	
    @Before
    public void initMocks(){
        MockitoAnnotations.initMocks(this);
    }
	
	@Test
	public void listActionsTest() {
		Collection<ActionMappingInfo> actions = mock(Collection.class);
		when(actionMappingRegistry.getRegisteredActions()).thenReturn(actions);
		Assert.assertEquals(actionService.listActions(), actions);
	}
	
	@Test
	public void isAllowedNotValidResourcesAndNotGrantedAuthorities() {
		initMockActionInfo();
		Assert.assertFalse(actionService.isAllowed("ACTIONID", new ArrayNode(null)));
	}
	
	@Test
	public void isAllowedValidResourcesAndGrantedAuthorities() {
		ActionMappingInfo actionInfo = initMockActionInfo();
		validResourcesAndAuthorities(actionInfo, false);
		Assert.assertFalse(actionService.isAllowed("ACTIONID", new ArrayNode(null)));
	}
	
	@Test(expected = RuntimeException.class)
	public void executeActionNotAllowed() {
		initMockActionInfo();
		actionService.executeAction("ACTIONID", null);
	}
	
	@Test(expected = RuntimeException.class)
	public void executeActionAllowedException() throws Exception {
		ActionMappingInfo actionInfo = initMockActionInfo();
		validResourcesAndAuthorities(actionInfo, true);
		actionService.executeAction("ACTIONID", null);
	}
	
	@Test
	public void executeActionAllowed() throws Exception {
		ActionMappingInfo actionInfo = initMockActionInfo();
		validResourcesAndAuthorities(actionInfo, true);
		when(actionInfo.getControllerClass()).thenReturn((Class) DummyActionController.class);
		when(actionInfo.getMethodName()).thenReturn("doNothing");
		when(applicationContext.getBean(DummyActionController.class)).thenReturn(mock(DummyActionController.class));
		Assert.assertNull(actionService.executeAction("ACTIONID", null));
	}
	
	private ActionMappingInfo initMockActionInfo() {
		ActionMappingInfo actionInfo = mock(ActionMappingInfo.class);
		when(actionMappingRegistry.findRegisteredActionsById(anyString())).thenReturn(actionInfo);
		when(actionInfo.getResourcesClass()).thenReturn((Class) String.class);
		return actionInfo;
	}
	
	private void validResourcesAndAuthorities(ActionMappingInfo actionInfo, boolean bool) {
		when(actionInfo.isValidResourceMode(any())).thenReturn(bool);
		when(actionInfo.hasNeededAuthorities()).thenReturn(bool);
	}

}
