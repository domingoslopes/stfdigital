package br.jus.stf.plataforma.action.dummy;

import java.util.ArrayList;
import java.util.List;

import br.jus.stf.plataforma.component.action.annotation.ActionController;
import br.jus.stf.plataforma.component.action.annotation.ActionMapping;
import br.jus.stf.plataforma.component.action.annotation.ActionMapping.ResourcesMode;

/**
 * @author Lucas.Rodrigues
 *
 */
@ActionController
public class DummyActionController {
	
	@ActionMapping(id = "DO_NOTHING", name = "Do Nothing", resourcesMode = ResourcesMode.None)
	public void doNothing() {

	}
	
	@ActionMapping(id = "DO_NOTHING_LONG", name = "Do Nothing Long", resourcesMode = ResourcesMode.One,
			neededAuthorities = "RESTRICT_ACTION")
	public void doNothing(List<Long> resources) {

	}
	
	@ActionMapping(id = "DUMMY_ACTION", name = "Do Nothing", resourceClass = DummyObj.class)
	@DummyAnnotation
	public List<DummyObj> doNothingDummy(List<DummyObj> resources) {
		List<DummyObj> dummies = new ArrayList<DummyObj>();
		dummies.add(new DummyObj());
		return dummies;
	}

}
