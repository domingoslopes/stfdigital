package br.jus.stf.plataforma.actions.dummy;

import java.util.ArrayList;
import java.util.List;

import br.jus.stf.plataforma.shared.actions.annotation.ActionController;
import br.jus.stf.plataforma.shared.actions.annotation.ActionMapping;
import br.jus.stf.plataforma.shared.actions.support.ResourcesMode;

/**
 * @author Lucas.Rodrigues
 *
 */
@ActionController(groups = "DummyObj")
public class DummyActionController {
	
	@ActionMapping(id = "do-nothing", name = "Do Nothing", resourcesMode = ResourcesMode.None)
	public void doNothing() {

	}
	
	@ActionMapping(id = "do-nothing-long", name = "Do Nothing Long", resourcesMode = ResourcesMode.One)
	public void doNothing(Long resource) {

	}
	
	@ActionMapping(id = "dummy-action", name = "Do Nothing", resourcesMode = ResourcesMode.Many)
	@DummyAnnotation
	public List<DummyObj> doNothingDummy(List<DummyObj> resources) {
		List<DummyObj> dummies = new ArrayList<DummyObj>();
		dummies.add(new DummyObj());
		return dummies;
	}

}
