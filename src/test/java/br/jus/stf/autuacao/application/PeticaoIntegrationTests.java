package br.jus.stf.autuacao.application;

import java.util.LinkedList;
import java.util.List;

import org.activiti.engine.task.Task;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import br.jus.stf.AbstractIntegrationTests;
import br.jus.stf.autuacao.domain.entity.ClasseProcessual;
import br.jus.stf.autuacao.domain.entity.Documento;
import br.jus.stf.autuacao.domain.entity.Parte;
import br.jus.stf.autuacao.domain.entity.Peticao;
import br.jus.stf.autuacao.domain.entity.Polo;
import br.jus.stf.plataforma.workflow.application.TarefaApplicationService;

/**
 * Teste de integração do processo de peticionamento.
 * 
 * @author anderson.araujo
 * 
 * @since 1.0.0
 * @since 19.08.2015
 */
public class PeticaoIntegrationTests extends AbstractIntegrationTests {
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
		this.peticao.setQuantidadeVolumes(1);
		this.peticao.setQuantidadeApensos(0);
		this.peticao.setFormaRecebimento("Sedex");
		this.peticao.setNumeroSedex("SE000111222BR");
	}
	
	@Test
	public void iniciarRegistroPeticaoEletronica(){
		String idPeticao = "";
		String idTarefa = "";
		String tipoRecebimento = "remessaEletronica";
		
		//Registra uma nova petição.
		idPeticao = this.peticaoApplicationService.peticionar(tipoRecebimento, this.peticao.getClasseSugerida().getSigla(), this.peticao.getPoloAtivo(), this.peticao.getPoloPassivo(), this.peticao.getDocumentos());
		
        Assert.assertTrue(!StringUtils.isEmpty(idPeticao));
        
        //Retorna a lista de tarefas do autuador.
        List<Task> tarefasAutuador = this.tarefaApplicationService.tarefas("autuador");
        Assert.assertTrue(tarefasAutuador.size() > 0);
        
        /*
        idTarefa = tarefasAutuador.get(0).getId().toString();
        
        String classe = "HC";
        boolean peticaoValida = true;
        
        //Autuação.
        this.peticaoApplicationService.autuar(idTarefa, classe, peticaoValida, "");
        
        //Retorna a lista de tarefas do autuador.
        List<Task> tarefasDistribuidor = this.tarefaApplicationService.tarefas("distribuidor");
        Assert.assertEquals(1, tarefasDistribuidor.size());
        
        idTarefa = tarefasDistribuidor.get(0).getId().toString();
        
        //Distribui o processo.
        this.peticaoApplicationService.distribuir(idTarefa, "Min. Cármen Lúcia");
        
      //Retorna a lista de tarefas do distribuidor para saber se ela está vazia.
        List<Task> tarefasDistribuidorNova = this.tarefaApplicationService.tarefas("distribuidor");
        Assert.assertEquals(0, tarefasDistribuidorNova.size());
        */
	}
	
	@Test
	public void iniciarRegistroPeticaoFisica(){
		String idPeticao = "";
		String idTarefa = "";
		String tipoRecebimento = "remessaFisica";
		
		//Registra uma nova petição física.
		idPeticao = this.peticaoApplicationService.registrar(tipoRecebimento, peticao.getQuantidadeVolumes(), peticao.getQuantidadeApensos(), peticao.getFormaRecebimento(), peticao.getNumeroSedex());
		
        Assert.assertTrue(!StringUtils.isEmpty(idPeticao));
        
        
        //Retorna a lista de tarefas do autuador.
        List<Task> tarefasAutuador = this.tarefaApplicationService.tarefas("pre-autuador");
        Assert.assertTrue(tarefasAutuador.size() > 0);
        
        /*
        idTarefa = tarefasAutuador.get(0).getId().toString();
        
        String classe = "HC";
        boolean peticaoValida = true;
        
        //Autuação.
        this.peticaoApplicationService.autuar(idTarefa, classe, peticaoValida, "");
        
        //Retorna a lista de tarefas do autuador.
        List<Task> tarefasDistribuidor = this.tarefaApplicationService.tarefas("distribuidor");
        Assert.assertEquals(1, tarefasDistribuidor.size());
        
        idTarefa = tarefasDistribuidor.get(0).getId().toString();
        
        //Distribui o processo.
        this.peticaoApplicationService.distribuir(idTarefa, "Min. Cármen Lúcia");
        
      //Retorna a lista de tarefas do distribuidor para saber se ela está vazia.
        List<Task> tarefasDistribuidorNova = this.tarefaApplicationService.tarefas("distribuidor");
        Assert.assertEquals(0, tarefasDistribuidorNova.size());
        */
	}
}
