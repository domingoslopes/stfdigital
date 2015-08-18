package br.jus.stf.autuacao.domain;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import br.jus.stf.autuacao.domain.entity.ClasseProcessual;
import br.jus.stf.autuacao.domain.entity.Documento;
import br.jus.stf.autuacao.domain.entity.Parte;
import br.jus.stf.autuacao.domain.entity.Peticao;
import br.jus.stf.autuacao.domain.entity.Polo;
import br.jus.stf.autuacao.infra.ProcessoRestAdapter;
import br.jus.stf.plataforma.workflow.interfaces.TarefaRestResource;
import br.jus.stf.plataforma.workflow.interfaces.dto.TarefaDto;

/**
 * Apenas um exemplo de como usar os frameworks de teste.
 * 
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 16.07.2015
 */
public class PeticaoServiceUnitTests {
	
	private static final TarefaDto PREAUTUAR = new TarefaDto("", "", "");
	private static final String RECEBEDORES = "recebedores";
 
    @InjectMocks
    private PeticaoService peticaoService;
 
    @Spy
    private ProcessoRestAdapter processoAdapter;
 
    @Mock
    private TarefaRestResource tarefaService;
 
    @Before
    public void initMocks(){
        MockitoAnnotations.initMocks(this);
    }
 
    @Test
    public void testRegistrar() {
    	
        List<Documento> documentos = new LinkedList<Documento>();
        documentos.add(new Documento("Doc1"));
        documentos.add(new Documento("Doc2"));
        
        List<Parte> partesPoloAtivo = new LinkedList<Parte>();
        partesPoloAtivo.add(new Parte("Fulano Silva"));
        
        List<Parte> partesPoloPassivo = new LinkedList<Parte>();
        partesPoloPassivo.add(new Parte("Beltrano Silva"));
        
        Polo poloAtivo = new Polo();
        poloAtivo.setPartes(partesPoloAtivo);
        
        Polo poloPassivo = new Polo();
        poloPassivo.setPartes(partesPoloPassivo);
        
        Peticao peticao = new Peticao(new ClasseProcessual("HC"), null, poloAtivo, poloPassivo, documentos);
        
        Mockito.when(tarefaService.tarefas(RECEBEDORES)).thenReturn(Arrays.asList(PREAUTUAR));
        Mockito.doReturn("1").when(processoAdapter).iniciar("autuarOriginarios", peticao);
        
        String idPeticao = peticaoService.registrar("autuarOriginarios", peticao);
        
        Assert.assertEquals("1", idPeticao);
        
        List<TarefaDto> tarefas = tarefaService.tarefas(RECEBEDORES);
 
        Assert.assertEquals(1, tarefas.size());
    }
}