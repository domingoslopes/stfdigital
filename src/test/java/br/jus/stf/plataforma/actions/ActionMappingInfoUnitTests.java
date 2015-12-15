package br.jus.stf.plataforma.actions;

import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.jus.stf.plataforma.shared.actions.support.ActionMappingInfo;
import br.jus.stf.plataforma.shared.actions.support.ResourcesMode;

/**
 * @author Lucas.Rodrigues
 *
 */
public class ActionMappingInfoUnitTests {

	@Mock(answer = Answers.CALLS_REAL_METHODS)
	private ActionMappingInfo actionMappingInfo;
	@Mock
	private List<String> resources;
	
    @Before
    public void initMocks(){
        MockitoAnnotations.initMocks(this);
    }
    
	@Test
	public void resourceNullModeNone() {
		actionMappingInfo.setResourcesMode(ResourcesMode.None);
		Assert.assertTrue(actionMappingInfo.isValidResourceMode(null));
	}
	
	@Test
	public void resourceNullModeOne() {
		actionMappingInfo.setResourcesMode(ResourcesMode.One);
		Assert.assertFalse(actionMappingInfo.isValidResourceMode(null));
	}
	
	@Test
	public void resource0ModeNone() {
		when(resources.size()).thenReturn(0);
		actionMappingInfo.setResourcesMode(ResourcesMode.None);
		Assert.assertTrue(actionMappingInfo.isValidResourceMode(resources));
	}
	
	@Test
	public void resource0ModeOne() {
		when(resources.size()).thenReturn(0);
		actionMappingInfo.setResourcesMode(ResourcesMode.One);
		Assert.assertFalse(actionMappingInfo.isValidResourceMode(resources));
	}
	
	@Test
	public void resource1ModeOne() {
		when(resources.size()).thenReturn(1);
		actionMappingInfo.setResourcesMode(ResourcesMode.One);
		Assert.assertTrue(actionMappingInfo.isValidResourceMode(resources));
	}
	
	@Test
	public void resource1ModeMany() {
		when(resources.size()).thenReturn(1);
		actionMappingInfo.setResourcesMode(ResourcesMode.Many);
		Assert.assertTrue(actionMappingInfo.isValidResourceMode(resources));
	}
	
	@Test
	public void resource1ModeNone() {
		when(resources.size()).thenReturn(1);
		actionMappingInfo.setResourcesMode(ResourcesMode.None);
		Assert.assertFalse(actionMappingInfo.isValidResourceMode(resources));
	}
	
	@Test
	public void resource2ModeMany() {
		when(resources.size()).thenReturn(2);
		actionMappingInfo.setResourcesMode(ResourcesMode.Many);
		Assert.assertTrue(actionMappingInfo.isValidResourceMode(resources));
	}
	
	@Test
	public void resource2ModeOne() {
		when(resources.size()).thenReturn(2);
		actionMappingInfo.setResourcesMode(ResourcesMode.One);
		Assert.assertFalse(actionMappingInfo.isValidResourceMode(resources));
	}

}
