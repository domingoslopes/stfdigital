package br.jus.stf.core.framework.domaindrivendesign;

/**
 * Value Objects, conforme definição Domain-Driven Design.
 * 
 * @author Rafael Esdras
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 15.12.2015
 */
@FunctionalInterface
public interface ValueObject<T> {

    /**
     * Compara o valor dos atributos: Value Objetcs não possuem identidade.
     *
     * @param other o outro objeto de valor.
     * @return <code>true</code> se os valores são iguais, <code>false</code> caso contrário
     */
    boolean sameValueAs(T other);

}