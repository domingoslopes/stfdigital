package br.jus.stf.shared;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DocumentoIdTest {
	
	@Test
	public void criaDocumentoIdValido() {
		DocumentoId id = new DocumentoId(1L);
		
		assertNotNull(id);
		assertTrue(id.toLong() == 1L);
	}
	
	@Test(expected=NullPointerException.class)
	public void criaDocumentoIdComSequencialNula() {
		new DocumentoId(null);
	}
	
	@Test
	public void comparaDocumentoIdIguais() {
		DocumentoId id1 = new DocumentoId(1L);
		DocumentoId id2 = new DocumentoId(1L);
		
		assertTrue(id1.equals(id2));
	}
	
	@Test
	public void comparaDocumentoIdComValoresIguais() {
		DocumentoId id1 = new DocumentoId(1L);
		DocumentoId id2 = new DocumentoId(1L);
		
		assertTrue(id1.sameValueAs(id2));
	}
	
	@Test
	public void comparaDocumentoIdComHashesIguais() {
		DocumentoId id1 = new DocumentoId(1L);
		DocumentoId id2 = new DocumentoId(1L);
		
		assertTrue(id1.hashCode() == id2.hashCode());
	}
	
	@Test
	public void comparaDocumentoIdDiferentes() {
		DocumentoId id1 = new DocumentoId(1L);
		DocumentoId id2 = new DocumentoId(2L);
		
		assertFalse(id1.equals(id2));
	}
	
	@Test
	public void comparaDocumentoIdComValoresDiferentes() {
		DocumentoId id1 = new DocumentoId(1L);
		DocumentoId id2 = new DocumentoId(2L);
		
		assertFalse(id1.sameValueAs(id2));
	}
	
	@Test
	public void comparaDocumentoIdComHashesDiferentes() {
		DocumentoId id1 = new DocumentoId(1L);
		DocumentoId id2 = new DocumentoId(2L);
		
		assertFalse(id1.hashCode() == id2.hashCode());
	}
	
	@Test
	public void testaToString() {
		DocumentoId id1 = new DocumentoId(1L);
		
		assertEquals(id1.toString(), "1");
	}

}
