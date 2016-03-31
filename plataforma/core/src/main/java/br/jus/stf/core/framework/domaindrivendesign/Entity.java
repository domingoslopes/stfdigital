package br.jus.stf.core.framework.domaindrivendesign;

/**
 * Entities, conforme definição Domain-Driven Design.
 * 
 * @author Rafael Esdras
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 15.12.2015
 */
public interface Entity<T, I> {

    /**
     * Entidades são comparadas pela identidade, não pelo atributos.
     *
     * @param other uma outra entidade.
     * @return <code>true</code> se as identidades são iguais, <code>true</code>, caso contrário.
     */
    boolean sameIdentityAs(T other);

    /**
     * Entidade sempre possuem uma identidade.
     *
     * @return a identidade desta entidade.
     */
    I identity();

}