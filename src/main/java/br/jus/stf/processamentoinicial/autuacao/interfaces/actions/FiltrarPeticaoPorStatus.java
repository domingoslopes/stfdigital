package br.jus.stf.processamentoinicial.autuacao.interfaces.actions;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import br.jus.stf.processamentoinicial.autuacao.domain.model.PeticaoStatus;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface FiltrarPeticaoPorStatus {

	PeticaoStatus value();

}
