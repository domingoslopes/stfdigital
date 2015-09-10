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
import br.jus.stf.generico.domain.model.Ministro;
import br.jus.stf.shared.domain.model.PeticaoId;
import br.jus.stf.shared.domain.model.ProcessoWorkflowId;

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
	public Long peticionar(PeticaoEletronica peticao) {
		String tipoRecebimento = "peticaoEletronica";
		String idProcesso = "";
		
		idProcesso = processoAdapter.iniciar(tipoRecebimento);
		peticao.associarProcessoWorkflow(new ProcessoWorkflowId(idProcesso));
		PeticaoId peticaoId = this.peticaoRepository.save(peticao);
	
		return peticaoId.toLong();
	}
	
	/**
	 * Registra o recebimento de uma petição física.
	 * 
	 * @param volumes Quantidade de volumes da petição física.
	 * 
	 * @return Id da petição física registrada.
	 */
	public Long registrar(PeticaoFisica peticao){
		String tipoRecebimento = "peticaoFisica";
		String idProcesso = "";
		
		idProcesso = processoAdapter.iniciar(tipoRecebimento);
		peticao.associarProcessoWorkflow(new ProcessoWorkflowId(idProcesso));
		PeticaoId peticaoId = this.peticaoRepository.save(peticao);
	
		return peticaoId.toLong();
	}

	/**
	 * Realiza a preautuação de uma petição física.
	 * @param peticaoFisica Dados da petição física.
	 */
	public void preautuar(PeticaoFisica peticaoFisica) {
		this.peticaoRepository.save(peticaoFisica);
		this.tarefaAdapter.completar(peticaoFisica.processosWorkflow().iterator().next().toString());
	}

	/**
	 * Realiza a atuação de uma petição.
	 * @param peticao Dados da petição.
	 * @param peticaoValida Indica se uma petição foi considerada válida.
	 */
	public void autuar(Peticao peticao, boolean peticaoValida) {
		
		String idPeticao = peticao.id().toString();
		
		this.peticaoRepository.save(peticao);
		
		if (peticaoValida) {
			this.tarefaAdapter.completar(idPeticao);
		} else {
			this.tarefaAdapter.sinalizar("Petição Inválida", idPeticao);
		}
	}

	/**
	 * Distribui um processo para um Ministro Relator.
	 * @param peticao Dados da petição.
	 * @param ministroRelator Dados do Ministro Relator do processo.
	 */
	public void distribuir(Peticao peticao, Ministro ministroRelator) {
		
		this.peticaoRepository.save(peticao);
		this.tarefaAdapter.completar(peticao.id().toString());
	}

	/**
	 * Devolve uma petição.
	 * @param peticao Dados da petição.
	 */
	public void devolver(Peticao peticao) {
		this.tarefaAdapter.completar(peticao.id().toString());
	}
	
	
	
	public String receberDocumentoPeticao(MultipartFile arquivo) throws IOException{
		return this.peticaoService.gravarArquivo(arquivo);
	}
}
