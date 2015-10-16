package br.jus.stf.shared;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DocumentoTemporarioIdTest {
	
	@Test
	public void criaDocumentoTemporarioIdValido() {
		DocumentoTemporarioId id = new DocumentoTemporarioId("_DocTemp_id1.pdf");
		
		assertNotNull(id);
		assertEquals(id.toString(), "_DocTemp_id1.pdf");
	}
	
	@Test(expected=NullPointerException.class)
	public void criaDocumentoTemporarioIdComIdNulo() {
		new DocumentoTemporarioId(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void criaDocumentoTemporarioIdComIdVazio() {
		new DocumentoTemporarioId("");
	}
	
	@Test
	public void comparaDocumentoTemporarioIdIguais() {
		DocumentoTemporarioId id1 = new DocumentoTemporarioId("_DocTemp_id1.pdf");
		DocumentoTemporarioId id2 = new DocumentoTemporarioId("_DocTemp_id1.pdf");
		
		assertTrue(id1.equals(id2));
	}
	
	@Test
	public void comparaDocumentoTemporarioIdComValoresIguais() {
		DocumentoTemporarioId id1 = new DocumentoTemporarioId("_DocTemp_id1.pdf");
		DocumentoTemporarioId id2 = new DocumentoTemporarioId("_DocTemp_id1.pdf");
		
		assertTrue(id1.sameValueAs(id2));
	}
	
	@Test
	public void comparaDocumentoTemporarioIdComHashesIguais() {
		DocumentoTemporarioId id1 = new DocumentoTemporarioId("_DocTemp_id1.pdf");
		DocumentoTemporarioId id2 = new DocumentoTemporarioId("_DocTemp_id1.pdf");
		
		assertTrue(id1.hashCode() == id2.hashCode());
	}
	
	@Test
	public void comparaDocumentoTemporarioIdDiferentes() {
		DocumentoTemporarioId id1 = new DocumentoTemporarioId("_DocTemp_id1.pdf");
		DocumentoTemporarioId id2 = new DocumentoTemporarioId("_DocTemp_id2.pdf");
		
		assertFalse(id1.equals(id2));
	}
	
	@Test
	public void comparaDocumentoTemporarioIdComValoresDiferentes() {
		DocumentoTemporarioId id1 = new DocumentoTemporarioId("_DocTemp_id1.pdf");
		DocumentoTemporarioId id2 = new DocumentoTemporarioId("_DocTemp_id2.pdf");
		
		assertFalse(id1.sameValueAs(id2));
	}
	
	@Test
	public void comparaDocumentoTemporarioIdComHashesDiferentes() {
		DocumentoTemporarioId id1 = new DocumentoTemporarioId("_DocTemp_id1.pdf");
		DocumentoTemporarioId id2 = new DocumentoTemporarioId("_DocTemp_id2.pdf");
		
		assertFalse(id1.hashCode() == id2.hashCode());
	}

}
