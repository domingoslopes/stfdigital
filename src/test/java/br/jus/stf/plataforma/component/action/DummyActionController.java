package br.jus.stf.plataforma.component.action;

import java.util.List;

import org.junit.Assert;

import br.jus.stf.plataforma.infra.action.annotation.ActionController;
import br.jus.stf.plataforma.infra.action.annotation.ActionMapping;
import br.jus.stf.plataforma.infra.action.annotation.ActionMapping.ResourcesMode;

/**
 * @author Lucas.Rodrigues
 *
 */
@ActionController(context = "Dummy")
public class DummyActionController {
	
	
	@ActionMapping(id = "DO_NOTHING", name = "Do Nothing", resourcesMode = ResourcesMode.None)
	
	public void doNothing(List<String> resources) {
		Assert.assertTrue(resources.size() > 0);
	}
	
	@ActionMapping(id = "DO_NOTHING_REQUIRES", name = "Do Nothing Requires",
			resourceClass = DummyObj.class, resourcesMode = ResourcesMode.One)
	public void doNothingRequiresResources(List<String> resources) {
		Assert.assertTrue(resources.size() == 1);
	}
	
	@ActionMapping(id = "DO_NOTHING_RESTRICT", name = "Do Nothing Restrict",
			grantedAuthorities = {"RESTRICT_ACTION"})
	public void doNothingRestrict(List<String> resources) {
		Assert.assertTrue(resources.size() > 0);
	}
	
	@ActionMapping(id = "DO_NOTHING_INTEGER", name = "Do Nothing Integer", 
			resourceClass = Integer.class)
	public void doNothingInteger(List<Integer> resources) {
		Assert.assertTrue(resources.size() > 0);
	}

}
