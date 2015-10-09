package br.jus.stf.plataforma.actions.dummy;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Component;

import br.jus.stf.plataforma.actions.handler.ActionConditionHandler;

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
