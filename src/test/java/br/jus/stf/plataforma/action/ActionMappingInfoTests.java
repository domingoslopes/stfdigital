package br.jus.stf.plataforma.action;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import br.jus.stf.plataforma.component.action.annotation.ActionMapping.ResourcesMode;
import br.jus.stf.plataforma.component.action.support.ActionMappingInfo;

/**
 * @author Lucas.Rodrigues
 *
 */
public class ActionMappingInfoTests {

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
	public void resource0ModeNone() {
		when(resources.size()).thenReturn(0);
		actionMappingInfo.setResourcesMode(ResourcesMode.None);
		Assert.assertTrue(actionMappingInfo.isValidResourceMode(resources));
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
	public void resourceModeMany() {
		when(resources.size()).thenReturn(2);
		actionMappingInfo.setResourcesMode(ResourcesMode.Many);
		Assert.assertTrue(actionMappingInfo.isValidResourceMode(resources));
	}
	
	@Test
	public void hasNeededAuthorities() {
		ReflectionTestUtils.setField(actionMappingInfo, "neededAuthorities", new ArrayList<String>());
		actionMappingInfo.getNeededAuthorities().clear();
		Assert.assertTrue(actionMappingInfo.hasNeededAuthorities());
	}
	
	@Test
	public void noNeededAuthorities() {
		ReflectionTestUtils.setField(actionMappingInfo, "neededAuthorities", new ArrayList<String>());
		actionMappingInfo.getNeededAuthorities().add("RESTRICT_ACTION");
		Assert.assertFalse(actionMappingInfo.hasNeededAuthorities());
	}

}
