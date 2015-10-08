package br.jus.stf.plataforma.documentos.domain.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import br.jus.stf.shared.DocumentoId;

public class DocumentoTest {
	
	private DocumentoId idValido = new DocumentoId(1L);
	private String numeroConteudoValido = "0a1b2c3d4e5f6g7h8i9j";
	private DocumentoId idNulo = null;
	private String numeroConteudoVazio = "";
	private String numeroConteudoNulo = null;

	@Test
	public void criaDocumentoValido() {
		Documento documento = new Documento(idValido, numeroConteudoValido);
		
		assertNotNull(documento);
		assertEquals(documento.id(), idValido);
		assertEquals(documento.numeroConteudo(), numeroConteudoValido);
	}
	
	@Test(expected=NullPointerException.class)
	public void criaDocumentoComIdNulo() {
		new Documento(idNulo, numeroConteudoValido);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void criaDocumentoComNumeroConteudoVazio() {
		new Documento(idValido, numeroConteudoVazio);
	}
	
	@Test(expected=NullPointerException.class)
	public void criaDocumentoComNumeroConteudoNulo() {
		new Documento(idValido, numeroConteudoNulo);
	}
	
	@Test
	public void documentosIguais() {
		Documento documento1 = new Documento(idValido, numeroConteudoValido);
		Documento documento2 = new Documento(idValido, numeroConteudoValido);
		
		assertTrue(documento1.sameIdentityAs(documento2));
		assertTrue(documento1.equals(documento2));
	}
	
	@Test
	public void documentosDiferentes() {
		Documento documento1 = new Documento(idValido, numeroConteudoValido);
		Documento documento2 = new Documento(new DocumentoId(2L), "a0b1c2d3e4f5g6h7i8j9");
		
		assertFalse(documento1.sameIdentityAs(documento2));
		assertFalse(documento1.equals(documento2));
	}

}
