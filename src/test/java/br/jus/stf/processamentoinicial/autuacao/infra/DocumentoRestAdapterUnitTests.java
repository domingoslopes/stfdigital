package br.jus.stf.processamentoinicial.autuacao.infra;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.Range;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import br.jus.stf.plataforma.documentos.interfaces.DocumentoRestResource;
import br.jus.stf.plataforma.documentos.interfaces.commands.DividirDocumentoCommand;
import br.jus.stf.plataforma.documentos.interfaces.commands.UnirDocumentosCommand;
import br.jus.stf.shared.DocumentoId;

/**
 * Testes unitários do adapter.
 * 
 * @author Tomas.Godoi
 *
 */
public class DocumentoRestAdapterUnitTests {
	
	@InjectMocks
	private DocumentoRestAdapter documentoRestAdapter;
	
	@Mock
	private DocumentoRestResource documentoRestResource;
	
	@Captor
	private ArgumentCaptor<List<DividirDocumentoCommand>> dividirCommandCaptor;
	
	@Captor
	private ArgumentCaptor<UnirDocumentosCommand> unirDocumentosCommandCaptor;
	
	@Before
	public void setUp() {
		documentoRestAdapter = new DocumentoRestAdapter();
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testDividirDocumento() {
		DocumentoId documentoId = new DocumentoId(7L);
		List<Range<Integer>> intervalos = Arrays.asList(Range.between(1, 5), Range.between(6, 10));
		
		Mockito.doReturn(Arrays.asList(8L, 9L)).when(documentoRestResource).dividirDocumento(Mockito.any(), Mockito.any());
		
		List<DocumentoId> documentos = documentoRestAdapter.dividirDocumento(documentoId, intervalos);
		
		Mockito.verify(documentoRestResource).dividirDocumento(dividirCommandCaptor.capture(), Matchers.any());
		
		Assert.assertEquals(2, dividirCommandCaptor.getValue().size());
		
		Assert.assertEquals(new Long(7L), dividirCommandCaptor.getValue().get(0).getDocumentoId());
		Assert.assertEquals(new Long(7L), dividirCommandCaptor.getValue().get(1).getDocumentoId());
		
		Assert.assertEquals(new Integer(1), dividirCommandCaptor.getValue().get(0).getPaginaInicial());
		Assert.assertEquals(new Integer(5), dividirCommandCaptor.getValue().get(0).getPaginaFinal());
		
		Assert.assertEquals(new Integer(6), dividirCommandCaptor.getValue().get(1).getPaginaInicial());
		Assert.assertEquals(new Integer(10), dividirCommandCaptor.getValue().get(1).getPaginaFinal());
		
		Assert.assertEquals(2, documentos.size());
		Assert.assertEquals(new DocumentoId(8L), documentos.get(0));
		Assert.assertEquals(new DocumentoId(9L), documentos.get(1));
	}
	
	@Test
	public void testUnirDocumentos() {
		DocumentoId documentoId1 = new DocumentoId(2L);
		DocumentoId documentoId2 = new DocumentoId(3L);
		
		Mockito.doReturn(4L).when(documentoRestResource).unirDocumentos(Mockito.any(), Mockito.any());
		
		DocumentoId documentoUnido = documentoRestAdapter.unirDocumentos(Arrays.asList(documentoId1, documentoId2));
		
		Mockito.verify(documentoRestResource).unirDocumentos(unirDocumentosCommandCaptor.capture(), Mockito.any());
		
		Assert.assertEquals(2, unirDocumentosCommandCaptor.getValue().getIdsDocumentos().size());
		
		Assert.assertEquals(new Long(2L), unirDocumentosCommandCaptor.getValue().getIdsDocumentos().get(0));
		Assert.assertEquals(new Long(3L), unirDocumentosCommandCaptor.getValue().getIdsDocumentos().get(1));
		
		Assert.assertEquals(new DocumentoId(4L), documentoUnido);
	}
	
}
