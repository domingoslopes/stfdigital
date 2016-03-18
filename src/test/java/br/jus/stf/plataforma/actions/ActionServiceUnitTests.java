package br.jus.stf.plataforma.actions;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.jus.stf.plataforma.actions.dummy.DummyActionController;
import br.jus.stf.plataforma.shared.actions.service.ActionMappingRegistry;
import br.jus.stf.plataforma.shared.actions.service.ActionService;
import br.jus.stf.plataforma.shared.actions.support.ActionMappingInfo;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class ActionServiceUnitTests {
	
	@Mock
	private ActionMappingRegistry actionMappingRegistry;
	
	@Mock
	private ApplicationContext applicationContext;
	
	@Mock
	private ObjectMapper objectMapper;
	
	@InjectMocks
	private ActionService actionService;
	
    @Before
    public void initMocks(){
        MockitoAnnotations.initMocks(this);
    }
	
	@Test
	public void listActionsTest() {
		Set<ActionMappingInfo> actions = mock(Set.class);
		when(actionMappingRegistry.getRegisteredActions()).thenReturn(actions);
		Assert.assertEquals(actionService.listActions(), actions);
	}
	
	@Test(expected = Exception.class)
	public void executeActionNotAllowed() throws Exception {
		initMockActionInfo();
		actionService.executeAction("actionid", null);
	}
	
	@Test(expected = Exception.class)
	public void executeActionAllowedException() throws Exception {
		actionService.executeAction("actionid", null);
	}
	
	@Test
	public void executeActionAllowed() throws Exception {
		ActionMappingInfo actionInfo = initMockActionInfo();
		when(actionInfo.getControllerClass()).thenReturn((Class) DummyActionController.class);
		when(actionInfo.getMethodName()).thenReturn("doNothing");
		when(applicationContext.getBean(DummyActionController.class)).thenReturn(mock(DummyActionController.class));
		Assert.assertNull(actionService.executeAction("actionid", null));
	}
	
	private ActionMappingInfo initMockActionInfo() {
		ActionMappingInfo actionInfo = mock(ActionMappingInfo.class);
		when(actionMappingRegistry.findRegisteredActionsById(anyString())).thenReturn(Optional.of(actionInfo));
		when(actionInfo.getResourcesClass()).thenReturn((Class) String.class);
		return actionInfo;
	}

}
