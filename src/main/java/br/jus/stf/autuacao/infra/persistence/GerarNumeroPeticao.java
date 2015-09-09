package br.jus.stf.autuacao.infra.persistence;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.hibernate.annotations.ValueGenerationType;

/**
 * Anotação necessária para marcar a propriedade que irá gerar o número da petição
 * 
 * @author Lucas.Rodrigues
 *
 */
@ValueGenerationType(generatedBy = GeradorNumeroPeticao.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface GerarNumeroPeticao {

}
