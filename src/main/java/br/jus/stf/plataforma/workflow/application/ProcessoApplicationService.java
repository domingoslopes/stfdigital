package br.jus.stf.plataforma.workflow.application;

import java.util.List;

import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.autuacao.domain.entity.ClasseProcessual;
import br.jus.stf.autuacao.domain.entity.Documento;
import br.jus.stf.autuacao.domain.entity.Peticao;
import br.jus.stf.autuacao.domain.entity.Polo;
import br.jus.stf.plataforma.workflow.domain.ProcessoRepository;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 26.06.2015
 */
@Component
public class ProcessoApplicationService {

	@Autowired
	private ProcessoRepository processoRepository;

	public String iniciar(String tipoRecebimento, ClasseProcessual classeSugerida, Polo poloAtivo, Polo poloPassivo, List<Documento> documentos) {
		
		Peticao peticao = new Peticao(classeSugerida, null, poloAtivo, poloPassivo, documentos);
		
		return processoRepository.criar(tipoRecebimento, peticao);
	}

	public void alterar(String id, String nome, String valor){
		this.processoRepository.alterar(id, nome, valor);
	}
	
	public ProcessInstance consultar(String id){
		return this.processoRepository.consultar(id);
	}
}
