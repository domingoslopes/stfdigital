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
 * Teste de integração do processo de peticionamento.
 * 
 * @author anderson.araujo
 * 
 * @since 1.0.0
 * @since 19.08.2015
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ApplicationContextInitializer.class)
@WebAppConfiguration
public class PeticaoIntegrationTests {
	@Autowired
	private PeticaoApplicationService peticaoApplicationService;
	@Autowired
	private TarefaApplicationService tarefaApplicationService;
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
	
	@Test
	public void iniciarRegistroPeticaoValida(){
		String idPeticao = "";
		String idTarefa = "";
		String tipoRecebimento = "autuarOriginarios";
		
		//Registra uma nova petição.
		idPeticao = this.peticaoApplicationService.registrar(tipoRecebimento, this.peticao.getClasseSugerida().getSigla(), this.peticao.getPoloAtivo(), this.peticao.getPoloPassivo(), this.peticao.getDocumentos());
        
        Assert.assertEquals("31", idPeticao);
        
        //Retorna a lista de tarefas do recebedor.
        List<Task> tarefasRecebedor = this.tarefaApplicationService.tarefas("recebedor");
        Assert.assertEquals(1, tarefasRecebedor.size());
        
        idTarefa = tarefasRecebedor.get(0).getId().toString();
        
        //Pré atutuação.
        this.peticaoApplicationService.preautuar(idTarefa);
        
        //Retorna a lista de tarefas do autuador.
        List<Task> tarefasAutuador = this.tarefaApplicationService.tarefas("autuador");
        Assert.assertEquals(1, tarefasAutuador.size());
        
        idTarefa = tarefasAutuador.get(0).getId().toString();
        
        String classe = "HC";
        boolean peticaoValida = true;
        
        //Autuação.
        this.peticaoApplicationService.autuar(idTarefa, classe, peticaoValida);
        
        //Retorna a lista de tarefas do autuador.
        List<Task> tarefasDistribuidor = this.tarefaApplicationService.tarefas("distribuidor");
        Assert.assertEquals(1, tarefasDistribuidor.size());
        
        idTarefa = tarefasDistribuidor.get(0).getId().toString();
        
        //Distribui o processo.
        this.peticaoApplicationService.distribuir(idTarefa);
        
      //Retorna a lista de tarefas do distribuidor para saber se ela está vazia.
        List<Task> tarefasDistribuidorNova = this.tarefaApplicationService.tarefas("distribuidor");
        Assert.assertEquals(0, tarefasDistribuidorNova.size());
	}
	
	@Test
	public void iniciarRegistroPeticaoInvalida(){
		String idPeticao = "";
		String idTarefa = "";
		String tipoRecebimento = "autuarOriginarios";
		
		//Registra uma nova petição.
		idPeticao = this.peticaoApplicationService.registrar(tipoRecebimento, this.peticao.getClasseSugerida().getSigla(), this.peticao.getPoloAtivo(), this.peticao.getPoloPassivo(), this.peticao.getDocumentos());
        
        Assert.assertEquals("4", idPeticao);
        
        //Retorna a lista de tarefas do recebedor.
        List<Task> tarefasRecebedor = this.tarefaApplicationService.tarefas("recebedor");
        Assert.assertEquals(1, tarefasRecebedor.size());
        
        idTarefa = tarefasRecebedor.get(0).getId().toString();
        
        //Pré atutuação.
        this.peticaoApplicationService.preautuar(idTarefa);
        
        //Retorna a lista de tarefas do autuador.
        List<Task> tarefasAutuador = this.tarefaApplicationService.tarefas("autuador");
        Assert.assertEquals(1, tarefasAutuador.size());
        
        idTarefa = tarefasAutuador.get(0).getId().toString();
        
        String classe = "HC";
        boolean peticaoValida = false;
        
        //Autuação.
        this.peticaoApplicationService.autuar(idTarefa, classe, peticaoValida);
        
        //Retorna a lista de tarefas do autuador.
        List<Task> tarefasDevolvedor = this.tarefaApplicationService.tarefas("devolvedor");
        Assert.assertEquals(1, tarefasDevolvedor.size());
        
        idTarefa = tarefasDevolvedor.get(0).getId().toString();
        
        //Distribui o processo.
        this.peticaoApplicationService.devolver(idTarefa);
        
        //Retorna a lista de tarefas do distribuidor para saber se ela está vazia.
        List<Task> tarefasDevolvedorNova = this.tarefaApplicationService.tarefas("devolvedor");
        Assert.assertEquals(0, tarefasDevolvedorNova.size());
	}
}
