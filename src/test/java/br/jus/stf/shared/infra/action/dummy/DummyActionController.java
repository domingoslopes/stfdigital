package br.jus.stf.shared.infra.action.dummy;

import java.util.ArrayList;
import java.util.List;

import br.jus.stf.plataforma.actions.annotation.ActionController;
import br.jus.stf.plataforma.actions.annotation.ActionMapping;

/**
 * @author Lucas.Rodrigues
 *
 */
@ActionController
public class DummyActionController {
	
	@ActionMapping(id = "DO_NOTHING", name = "Do Nothing")
	public void doNothing() {

	}
	
	@ActionMapping(id = "DO_NOTHING_LONG", name = "Do Nothing Long",
			neededAuthorities = "RESTRICT_ACTION")
	public void doNothing(Long resource) {

	}
	
	@ActionMapping(id = "DUMMY_ACTION", name = "Do Nothing")
	@DummyAnnotation
	public List<DummyObj> doNothingDummy(List<DummyObj> resources) {
		List<DummyObj> dummies = new ArrayList<DummyObj>();
		dummies.add(new DummyObj());
		return dummies;
	}

}
