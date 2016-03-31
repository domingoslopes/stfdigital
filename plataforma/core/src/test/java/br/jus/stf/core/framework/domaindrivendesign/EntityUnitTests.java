package br.jus.stf.core.framework.domaindrivendesign;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import br.jus.stf.core.framework.domaindrivendesign.Entity;
import br.jus.stf.core.framework.domaindrivendesign.EntitySupport;

/**
 * Conjunto de testes para validação da lógica de comparação das classes {@link Entity} e 
 * {@link EntitySupport}. Testes focados no métodos {@link Entity#equals(Object)} e
 * {@link Entity#hashCode()}
 * 
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 18.12.2015
 */
public class EntityUnitTests {
    
    @Test
    public void umaEntidadeSeComparadaComValorNuloDeveSerDiferente() {
        RealEntity entity1 = new RealEntity("A");

        Assert.assertFalse(entity1.equals(null));
    }

    @Test
    public void umaEntidadeSeComparadaComOutraDeOutraClasseDeveSerDiferente() {
        RealEntity entity1 = new RealEntity("A");

        Assert.assertFalse(entity1.equals(new Object()));
    }

    @Test
    public void duasEntidadesComMesmoIdDevemSerIguais() {
        RealEntity entity1 = new RealEntity("A");
        RealEntity entity2 = new RealEntity("A");

        Assert.assertTrue(entity1.equals(entity2));
    }
    
    @Test
    public void duasEntidadesComMesmosAtributosDevemTerHashcodesIguais() {
        RealEntity entity1 = new RealEntity("A");
        
        Set<RealEntity> entities = new HashSet<EntityUnitTests.RealEntity>();
        entities.add(entity1);

        Assert.assertTrue(entities.contains(new RealEntity("A")));
    }
    

    @Test
    public void duasEntidadesComIdsDiferentesDevemSerDiferentes() {
        RealEntity entity1 = new RealEntity("A");
        RealEntity entity2 = new RealEntity("B");

        Assert.assertFalse(entity1.equals(entity2));
    }

    private class RealEntity extends EntitySupport<RealEntity, String> {
        
        private String id;

        RealEntity(String id) {
            this.id = id;
        }
        
        @Override
        public String identity() {
            return id;
        }
    }

}
