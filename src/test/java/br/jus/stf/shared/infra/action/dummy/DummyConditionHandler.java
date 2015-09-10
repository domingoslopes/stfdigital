package br.jus.stf.shared.infra.action.dummy;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Component;

import br.jus.stf.shared.infra.action.handler.ActionConditionHandler;

/**
 * @author Lucas.Rodrigues
 *
 */
@Component
public class DummyConditionHandler implements ActionConditionHandler<DummyAnnotation, DummyObj> {

	@Override
	public boolean matches(DummyAnnotation annotation, List<DummyObj> resources) {
		return ((Collection<DummyObj>) resources).size() > 0;
	}

	@Override
	public Class<DummyAnnotation> annotation() {
		return DummyAnnotation.class;
	}

}
