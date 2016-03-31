package br.jus.stf.core.framework.domaindrivendesign;

/**
 * Base-class para entidades, conforme definição Domain-Driven Design.
 * 
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 15.12.2015
 * 
 * @param <T> o própria entitidade
 * @param <I> o identificador da entidade
 */
public abstract class EntitySupport<T extends Entity<T, I>, I> implements Entity<T, I> {

    /**
     * @see br.jus.stf.core.framework.domaindrivendesign.Entity#sameIdentityAs(java.lang.Object)
     */
	@Override
    public final boolean sameIdentityAs(final T other) {
        return other != null && this.identity().equals(other.identity());
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @SuppressWarnings("unchecked")
	@Override
    public final boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        return sameIdentityAs((T) o);
    }
    
    /**
     * @see java.lang.Object#hashCode()
     */
	@Override
    public final int hashCode() {
        return identity().hashCode();
    }

}
