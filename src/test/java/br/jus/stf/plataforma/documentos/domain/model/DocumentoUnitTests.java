package br.jus.stf.plataforma.documentos.domain.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import br.jus.stf.shared.DocumentoId;

public class DocumentoUnitTests {
	
	@Test
	public void criaDocumentoValido() {
		Documento documento = new Documento(new DocumentoId(1L), "0a1b2c3d4e5f6g7h8i9j");
		
		assertNotNull(documento);
		assertEquals(documento.id(), new DocumentoId(1L));
		assertEquals(documento.numeroConteudo(), "0a1b2c3d4e5f6g7h8i9j");
	}
	
	@Test(expected=NullPointerException.class)
	public void criaDocumentoComIdNulo() {
		new Documento(null, "0a1b2c3d4e5f6g7h8i9j");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void criaDocumentoComNumeroConteudoVazio() {
		new Documento(new DocumentoId(1L), "");
	}
	
	@Test(expected=NullPointerException.class)
	public void criaDocumentoComNumeroConteudoNulo() {
		new Documento(new DocumentoId(1L), null);
	}
	
	@Test
	public void comparaDocumentosIguais() {
		Documento documento1 = new Documento(new DocumentoId(1L), "0a1b2c3d4e5f6g7h8i9j");
		Documento documento2 = new Documento(new DocumentoId(1L), "0a1b2c3d4e5f6g7h8i9j");
		
		assertTrue(documento1.equals(documento2));
	}
	
	@Test
	public void comparaDocumentosDiferentes() {
		Documento documento1 = new Documento(new DocumentoId(1L), "0a1b2c3d4e5f6g7h8i9j");
		Documento documento2 = new Documento(new DocumentoId(2L), "a0b1c2d3e4f5g6h7i8j9");
		
		assertFalse(documento1.equals(documento2));
	}
	
	@Test
	public void comparaDocumentosComIdentidadesIguais() {
		Documento documento1 = new Documento(new DocumentoId(1L), "0a1b2c3d4e5f6g7h8i9j");
		Documento documento2 = new Documento(new DocumentoId(1L), "0a1b2c3d4e5f6g7h8i9j");
		
		assertTrue(documento1.sameIdentityAs(documento2));
	}
	
	@Test
	public void comparaDocumentosComIdentidadesDiferentes() {
		Documento documento1 = new Documento(new DocumentoId(1L), "0a1b2c3d4e5f6g7h8i9j");
		Documento documento2 = new Documento(new DocumentoId(2L), "a0b1c2d3e4f5g6h7i8j9");
		
		assertFalse(documento1.sameIdentityAs(documento2));
	}
	
	@Test
	public void comparaDocumentosComHashesIguais() {
		Documento documento1 = new Documento(new DocumentoId(1L), "0a1b2c3d4e5f6g7h8i9j");
		Documento documento2 = new Documento(new DocumentoId(1L), "0a1b2c3d4e5f6g7h8i9j");
		
		assertTrue(documento1.hashCode() == documento2.hashCode());
	}
	
	@Test
	public void comparaDocumentosComHashesDiferentes() {
		Documento documento1 = new Documento(new DocumentoId(1L), "0a1b2c3d4e5f6g7h8i9j");
		Documento documento2 = new Documento(new DocumentoId(2L), "a0b1c2d3e4f5g6h7i8j9");
		
		assertFalse(documento1.hashCode() == documento2.hashCode());
	}

}
