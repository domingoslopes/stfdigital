package br.jus.stf.autuacao.application;

import java.io.IOException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import br.jus.stf.autuacao.domain.PeticaoService;
import br.jus.stf.autuacao.domain.ProcessoAdapter;
import br.jus.stf.autuacao.domain.TarefaAdapter;
import br.jus.stf.autuacao.domain.model.Peticao;
import br.jus.stf.autuacao.domain.model.PeticaoEletronica;
import br.jus.stf.autuacao.domain.model.PeticaoFisica;
import br.jus.stf.autuacao.domain.model.PeticaoRepository;
import br.jus.stf.autuacao.interfaces.dto.PeticaoDto;
import br.jus.stf.shared.domain.model.ClasseId;
import br.jus.stf.shared.domain.model.PeticaoId;
import br.jus.stf.shared.domain.model.ProcessoWorkflowId;
import br.jus.stf.workflow.interfaces.dto.TarefaDto;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 22.06.2015
 */
@Component
@Transactional
public class PeticaoApplicationService {

	@Autowired
	private PeticaoService peticaoService;
	
	@Autowired
	private PeticaoRepository peticaoRepository;
	
	@Autowired
	private ProcessoAdapter processoAdapter;
	
	@Autowired
	private TarefaAdapter tarefaAdapter;

	/**
	 * Registra uma nova petilçao.
	 * 
	 * @param peticaoEletronica Petição eletrônica recebida.
	 * 
	 * @return Id da petição eletrônica registrada.
	 */
	public String peticionar(PeticaoEletronica peticao) {
		String tipoRecebimento = "peticaoEletronica";
		String idProcesso = "";
		
		idProcesso = processoAdapter.iniciar(tipoRecebimento);
		peticao.associarProcessoWorkflow(new ProcessoWorkflowId(idProcesso));
		PeticaoId peticaoId = this.peticaoRepository.save(peticao);
	
		return peticaoId.toString();
	}
	
	/**
	 * Registra o recebimento de uma petição física.
	 * 
	 * @param volumes Quantidade de volumes da petição física.
	 * 
	 * @return Id da petição física registrada.
	 */
	public String registrar(PeticaoFisica peticao){
		String tipoRecebimento = "peticaoFisica";
		String idProcesso = "";
		
		idProcesso = processoAdapter.iniciar(tipoRecebimento);
		peticao.associarProcessoWorkflow(new ProcessoWorkflowId(idProcesso));
		PeticaoId peticaoId = this.peticaoRepository.save(peticao);
	
		return peticaoId.toString();
	}

	public void preautuar(Long idPeticao, String classeSugerida) {
		PeticaoFisica peticao = (PeticaoFisica) this.peticaoRepository.findOne(new PeticaoId(idPeticao));
		peticao.preautuar(new ClasseId(classeSugerida));
		
		this.tarefaAdapter.completar(peticao.processosWorkflow().iterator().next().toString());
	}

	public void autuar(String idPeticao, String classe, boolean peticaoValida, String motivo) {
		
		if (idPeticao == null || idPeticao.isEmpty())
			throw new RuntimeException("O identificador da petição não foi informado.");
		
		TarefaDto tarefa = this.tarefaAdapter.consultar(idPeticao);
		
		//Atualiza a classe da petição.
		processoAdapter.alterar(tarefa.getIdProcesso(), "classe", classe);

		//Realiza a atuação
		peticaoService.autuar(tarefa.getId(), peticaoValida, motivo);
		
	}

	public void distribuir(String idPeticao, String relator) {
		peticaoService.distribuir(idPeticao, relator);
	}

	public void devolver(String idPeticao) {
		peticaoService.devolver(idPeticao);
	}
	
	public PeticaoDto consultar(String id){
		return null;
		//return this.assemblerPeticao.toDto(this.peticaoService.consultar(id));
	}
	
	public String receberDocumentoPeticao(MultipartFile arquivo) throws IOException{
		return this.peticaoService.gravarArquivo(arquivo);
	}
}
