package br.jus.stf.plataforma.workflow.infra;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.jus.stf.autuacao.domain.entity.Peticao;
import br.jus.stf.plataforma.workflow.domain.ProcessoRepository;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 26.06.2015
 */
@Repository
public class ProcessoRepositoryActiviti implements ProcessoRepository {

	@Autowired
	private RuntimeService runtimeService;

	@Override
	public String criar(String id, Peticao peticao) {
		
		String idPeticao = "";
		String[] partesPoloAtivo = new String[peticao.getPoloAtivo().getPartes().size()]; 
		String[] partesPoloPassivo = new String[peticao.getPoloPassivo().getPartes().size()];		
		String[] documentos = new String[peticao.getDocumentos().size()];
		
		for(int i = 0; i < peticao.getPoloAtivo().getPartes().size(); i++){
			partesPoloAtivo[i] = peticao.getPoloAtivo().getPartes().get(i).getNome();
		}
		
		for(int i = 0; i < peticao.getPoloPassivo().getPartes().size(); i++){
			partesPoloPassivo[i] = peticao.getPoloPassivo().getPartes().get(i).getNome();
		}
		
		for(int i = 0; i < peticao.getDocumentos().size(); i++){
			documentos[i] = peticao.getDocumentos().get(i).getDescricao();
		}
		
		Map<String, Object> atributos = new HashMap<String, Object>();
		atributos.put("classeSugerida", peticao.getClasseSugerida().getSigla());
		atributos.put("teste", partesPoloAtivo);
		atributos.put("partesPoloAtivo", partesPoloAtivo);
		atributos.put("partesPoloPassivo", partesPoloPassivo);
		atributos.put("documentos", documentos);
		
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(id, atributos);
		
		idPeticao = processInstance.getId();
		
		System.out.println("Peticao: " + idPeticao);
		
		return idPeticao;
	}
	
	public void alterar(String id, String classe){
		this.runtimeService.setVariable(id, "classe", classe);
	}

}
