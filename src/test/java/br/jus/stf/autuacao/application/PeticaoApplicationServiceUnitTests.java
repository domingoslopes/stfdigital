package br.jus.stf.autuacao.application;

import java.util.LinkedList;
import java.util.List;

import org.activiti.engine.task.Task;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import br.jus.stf.autuacao.domain.entity.ClasseProcessual;
import br.jus.stf.autuacao.domain.entity.Documento;
import br.jus.stf.autuacao.domain.entity.Parte;
import br.jus.stf.autuacao.domain.entity.Peticao;
import br.jus.stf.autuacao.domain.entity.Polo;
import br.jus.stf.plataforma.ApplicationContextInitializer;
import br.jus.stf.plataforma.workflow.application.TarefaApplicationService;

/**
 * Teste do processo de peticionamento.
 * 
 * @author anderson.araujo
 * 
 * @since 1.0.0
 * @since 23.07.2015
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ApplicationContextInitializer.class)
@WebAppConfiguration
public class PeticaoApplicationServiceUnitTests {
	
	@Autowired
	private PeticaoApplicationService peticaoApplicationService;
	
	@Autowired
	private TarefaApplicationService tarefaApllicationService;
	
	private Peticao peticao;
	
	@Before
	public void iniciarPeticao(){
		
        List<Parte> partesPoloAtivo = new LinkedList<Parte>();
        partesPoloAtivo.add(new Parte("Fulano Silva"));
        Polo poloAtivo = new Polo();
        poloAtivo.setPartes(partesPoloAtivo);
        
        List<Parte> partesPoloPassivo = new LinkedList<Parte>();
        partesPoloPassivo.add(new Parte("Beltrano Silva"));
        Polo poloPassivo = new Polo();
        poloPassivo.setPartes(partesPoloPassivo);
                
        List<Documento> documentos = new LinkedList<Documento>();
        documentos.add(new Documento("Doc1"));
        documentos.add(new Documento("Doc2"));
    
        this.peticao = new Peticao();
		this.peticao.setClasseSugerida(new ClasseProcessual("HC", "HC"));
		this.peticao.setClasse(new ClasseProcessual("HC", "HC"));
		this.peticao.setPoloAtivo(poloAtivo);
		this.peticao.setPoloPassivo(poloPassivo);
		this.peticao.setDocumentos(documentos);
	}
	
	@Test(expected=RuntimeException.class)
	public void capturarExcecaoSeTipoRecebimentoNaoInformado(){
		
		String idPeticao = "";
		String tipoRecebimento = "";
		String classeProcessual = "HC";
		        		
        List<Parte> partesPoloAtivo = new LinkedList<Parte>();
        partesPoloAtivo.add(new Parte("Fulano Silva"));
        
        List<Parte> partesPoloPassivo = new LinkedList<Parte>();
        partesPoloPassivo.add(new Parte("Beltrano Silva"));
        
        Polo poloAtivo = new Polo();
        poloAtivo.setPartes(partesPoloAtivo);
        
        Polo poloPassivo = new Polo();
        poloPassivo.setPartes(partesPoloPassivo);
        
        List<Documento> documentos = new LinkedList<Documento>();
        documentos.add(new Documento("Doc1"));
        documentos.add(new Documento("Doc2"));
        
        idPeticao = this.peticaoApplicationService.registrar(tipoRecebimento, classeProcessual, poloAtivo, poloPassivo, documentos);
       
        Assert.assertEquals("4", idPeticao);
	}
	
	@Test(expected=RuntimeException.class)
	public void capturarExcecaoSeClasseProcessualNaoInformada(){
		
		String idPeticao = "";
		String tipoRecebimento = "autuarOriginarios";
		        		
        List<Parte> partesPoloAtivo = new LinkedList<Parte>();
        partesPoloAtivo.add(new Parte("Fulano Silva"));
        
        List<Parte> partesPoloPassivo = new LinkedList<Parte>();
        partesPoloPassivo.add(new Parte("Beltrano Silva"));
        
        Polo poloAtivo = new Polo();
        poloAtivo.setPartes(partesPoloAtivo);
        
        Polo poloPassivo = new Polo();
        poloPassivo.setPartes(partesPoloPassivo);
        
        List<Documento> documentos = new LinkedList<Documento>();
        documentos.add(new Documento("Doc1"));
        documentos.add(new Documento("Doc2"));
        
        idPeticao = this.peticaoApplicationService.registrar(tipoRecebimento, null, poloAtivo, poloPassivo, documentos);
       
        Assert.assertEquals("4", idPeticao);
	}
	
	@Test(expected=RuntimeException.class)
	public void capturarExcecaoSePoloAtivoNaoInformado(){
		
		String idPeticao = "";
		String tipoRecebimento = "autuarOriginarios";
		String classeProcessual = "HC";
		
		List<Parte> partesPoloAtivo = new LinkedList<Parte>();
        partesPoloAtivo.add(new Parte("Fulano Silva"));
        Polo poloAtivo = new Polo();

        List<Parte> partesPoloPassivo = new LinkedList<Parte>();
        partesPoloPassivo.add(new Parte("Beltrano Silva"));
        Polo poloPassivo = new Polo();
        poloPassivo.setPartes(partesPoloPassivo);
        
        List<Documento> documentos = new LinkedList<Documento>();
        documentos.add(new Documento("Doc1"));
        documentos.add(new Documento("Doc2"));
        
        idPeticao = this.peticaoApplicationService.registrar(tipoRecebimento, classeProcessual, poloAtivo, poloPassivo, documentos);
	}
	
	@Test(expected=RuntimeException.class)
	public void capturarExcecaoSePoloPassivoNaoInformado(){
		
		String idPeticao = "";
		String tipoRecebimento = "autuarOriginarios";
		String classeProcessual = "HC";
		
        List<Parte> partesPoloAtivo = new LinkedList<Parte>();
        partesPoloAtivo.add(new Parte("Fulano Silva"));
        Polo poloAtivo = new Polo();
        poloAtivo.setPartes(partesPoloAtivo);
        
        Polo poloPassivo = null;
                
        List<Documento> documentos = new LinkedList<Documento>();
        documentos.add(new Documento("Doc1"));
        documentos.add(new Documento("Doc2"));
                
        idPeticao = this.peticaoApplicationService.registrar(tipoRecebimento, classeProcessual, poloAtivo, poloPassivo, documentos);
	}
	
	@Test
	public void registrarPeticao(){
		
		String idPeticao = "";
		String tipoRecebimento = "autuarOriginarios";
				
		idPeticao = this.peticaoApplicationService.registrar(tipoRecebimento, this.peticao.getClasseSugerida().getSigla(), this.peticao.getPoloAtivo(), this.peticao.getPoloPassivo(), this.peticao.getDocumentos());
        
        Assert.assertEquals("22", idPeticao);
	}
	
	@Test
	public void listarTarefasAutuador(){
	
		String tipoRecebimento = "autuarOriginarios";
	
		String idPeticao =  this.peticaoApplicationService.registrar(tipoRecebimento, this.peticao.getClasse().getSigla(), this.peticao.getPoloAtivo(), this.peticao.getPoloPassivo(), this.peticao.getDocumentos());
		
		List<Task> tarefas = this.tarefaApllicationService.tarefas("recebedor");
		
		Assert.assertEquals(1, tarefas.size());
	}
	
	@Test(expected=RuntimeException.class)
	public void capturarExcecaoAutuarPeticaoSeIdPeticaoNaoInformado(){
		String idPeticao = "";
		String classe = "";
		boolean peticaoValida = true;
		
		this.peticaoApplicationService.autuar(idPeticao, classe, peticaoValida);
	}
	
	@Test
	public void autuarPeticaoValida(){
		String idPeticao = "4";
		boolean peticaoValida = true;
		
		this.peticaoApplicationService.autuar(idPeticao, this.peticao.getClasse().getSigla(), peticaoValida);
		
		List<Task> tarefas = this.tarefaApllicationService.tarefas("distribuidor");
		
		Assert.assertEquals(1, tarefas.size());
	}
}
