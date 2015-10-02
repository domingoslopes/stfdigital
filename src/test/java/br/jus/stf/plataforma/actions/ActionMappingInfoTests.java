package br.jus.stf.plataforma.actions;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.util.ReflectionTestUtils;

import br.jus.stf.plataforma.actions.support.ActionMappingInfo;
import br.jus.stf.plataforma.actions.support.ResourcesMode;

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
	
	@Test
	public void noNeededAuthorities() {
		ReflectionTestUtils.setField(actionMappingInfo, "neededAuthorities", new ArrayList<String>());
		Assert.assertTrue(actionMappingInfo.hasNeededAuthorities());
	}
	
	@Test
	public void notHasNeededAuthorities() {
		setAuthenticationAuthorities(new String[] {});
		List<String> authorities = Collections.singletonList("RESTRICT_ACTION");
		ReflectionTestUtils.setField(actionMappingInfo, "neededAuthorities", authorities);
		Assert.assertFalse(actionMappingInfo.hasNeededAuthorities());
	}
	
	@Test
	public void hasNeededAuthorities() {
		List<String> authorities = Collections.singletonList("RESTRICT_ACTION");
		setAuthenticationAuthorities("RESTRICT_ACTION");
		ReflectionTestUtils.setField(actionMappingInfo, "neededAuthorities", authorities);
		Assert.assertTrue(actionMappingInfo.hasNeededAuthorities());
		setAuthenticationAuthorities(new String[] {});
	}
	
	private void setAuthenticationAuthorities(String... authorities) {
		Authentication authentication = new TestingAuthenticationToken("", "", authorities);
		authentication.setAuthenticated(true);
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

}
