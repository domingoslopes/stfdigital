package br.jus.stf.core.framework.domaindrivendesign;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import br.jus.stf.core.framework.domaindrivendesign.ValueObject;
import br.jus.stf.core.framework.domaindrivendesign.ValueObjectSupport;

/**
 * Conjunto de testes para validação da lógica de comparação das classes {@link ValueObject} e 
 * {@link ValueObjectSupport}. Testes focados no métodos {@link ValueObjectSupport#equals(Object)} e
 * {@link ValueObjectSupport#hashCode()}
 * 
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 18.12.2015
 */
public class ValueObjectUnitTests {

    @Test
    public void umValueObjectSeComparadoComValorNuloDeveSerDiferente() {
        RealValueObject vo1 = new RealValueObject("A", 1L);

        Assert.assertFalse(vo1.equals(null));
    }

    @Test
    public void umValueObjectSeComparadoComOutraDeOutraClasseDeveSerDiferente() {
        RealValueObject vo1 = new RealValueObject("A", 1L);

        Assert.assertFalse(vo1.equals(new Object()));
    }

    @Test
    public void doisValueObjectsComMesmoIdDevemSerIguais() {
        RealValueObject vo1 = new RealValueObject("A", 1L);
        RealValueObject vo2 = new RealValueObject("A", 1L);

        Assert.assertTrue(vo1.equals(vo2));
    }

    @Test
    public void doisValueObjectsComIdsDiferentesDevemSerDiferentes() {
        RealValueObject vo1 = new RealValueObject("A", 1L);
        RealValueObject vo2 = new RealValueObject("B", 2L);

        Assert.assertFalse(vo1.equals(vo2));
    }

    @Test
    public void doisValueObjectsComMesmosAtributosDevemTerHashcodesIguais() {
        RealValueObject vo1 = new RealValueObject("A", 1L);
        
        Set<RealValueObject> vos = new HashSet<ValueObjectUnitTests.RealValueObject>();
        vos.add(vo1);

        Assert.assertTrue(vos.contains(new RealValueObject("A", 1L)));
    }

    class RealValueObject extends ValueObjectSupport<RealValueObject> {
        
        String field1;
        Long field2;

        public RealValueObject(String field1, Long field2) {
            this.field1 = field1;
            this.field2 = field2;
        }
        
    }

}
