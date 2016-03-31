package br.jus.stf.core.framework.domaindrivendesign;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Base-class para Value Objects, conforme definição Domain-Driven Design.
 * 
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 18.12.2015
 * 
 * @param <T> o próprio value object
 */
public abstract class ValueObjectSupport<T extends ValueObject<T>> implements ValueObject<T> {

    private int cachedHashCode = 0;

    /**
     * @see br.jus.stf.core.framework.domaindrivendesign.ValueObject#sameValueAs(java.lang.Object)
     */
    @Override
    public final boolean sameValueAs(final T other) {
        return other != null && EqualsBuilder.reflectionEquals(this, other, false);
    }

    /**
     * O hash dos Value Objects é calculado a partir dos campos não-transientes.
     * 
     * @return o hash code do objeto
     */
    @Override
    public final int hashCode() {
        if (cachedHashCode == 0) {
            cachedHashCode = HashCodeBuilder.reflectionHashCode(this, false);
        }

        return cachedHashCode;
    }

    /**
     * @see br.jus.stf.core.framework.domaindrivendesign.ValueObject#sameValueAs(java.lang.Object)
     */
    @SuppressWarnings("unchecked")
    @Override
    public final boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        return sameValueAs((T) o);
    }

}