package br.jus.stf.plataforma.action.dummy;

import java.util.Collection;

import org.springframework.stereotype.Component;

import br.jus.stf.plataforma.component.action.handler.ActionConditionHandler;

/**
 * @author Lucas.Rodrigues
 *
 */
@Component
public class DummyConditionHandler implements ActionConditionHandler<DummyAnnotation, DummyObj> {

	@Override
	public boolean matches(DummyAnnotation annotation, Collection<DummyObj> resources) {
		return true;
	}

	@Override
	public Class<DummyAnnotation> annotation() {
		return DummyAnnotation.class;
	}

}
