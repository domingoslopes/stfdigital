package br.jus.stf.autuacao.application;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.autuacao.domain.PeticaoService;
import br.jus.stf.autuacao.domain.ProcessoAdapter;
import br.jus.stf.autuacao.domain.TarefaAdapter;
import br.jus.stf.autuacao.domain.model.FormaRecebimento;
import br.jus.stf.autuacao.domain.model.Peticao;
import br.jus.stf.autuacao.domain.model.PeticaoEletronica;
import br.jus.stf.autuacao.domain.model.PeticaoFactory;
import br.jus.stf.autuacao.domain.model.PeticaoFisica;
import br.jus.stf.autuacao.domain.model.PeticaoRepository;
import br.jus.stf.autuacao.domain.model.Processo;
import br.jus.stf.shared.domain.model.ClasseId;
import br.jus.stf.shared.domain.model.MinistroId;
import br.jus.stf.shared.domain.model.ProcessoWorkflowId;
import br.jus.stf.workflow.interfaces.dto.TarefaDto;

/**
 * @author Rodrigo Barreiros
 * @author Anderson.Araujo
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
	
	@Autowired
	private PeticaoFactory peticaoFactory;

	/**
	 * Registra uma nova petição.
	 * 
	 * @param peticaoEletronica Petição eletrônica recebida.
	 * 
	 * @return Id da petição eletrônica registrada.
	 */
	public Peticao peticionar(ClasseId classeSugerida, List<String> poloAtivo, List<String> poloPassivo, List<String> documentos) {
		String tipoRecebimento = "remessaEletronica";
		String idProcesso = "";
		
		PeticaoEletronica peticao = peticaoFactory.criarPeticaoEletronica(classeSugerida, poloAtivo, poloPassivo, documentos);
		
		idProcesso = processoAdapter.iniciar(tipoRecebimento);
		peticao.associarProcessoWorkflow(new ProcessoWorkflowId(idProcesso));
		
		return peticaoRepository.save(peticao);
	}
	
	/**
	 * Registra o recebimento de uma petição física.
	 * 
	 * @param volumes Quantidade de volumes da petição física.
	 * 
	 * @return Id da petição física registrada.
	 */
	public Peticao registrar(Integer volumes, Integer apensos, FormaRecebimento formaRecebimento, String numeroSedex){
		String tipoRecebimento = "remessaFisica";
		String idProcesso = "";

		PeticaoFisica peticao = peticaoFactory.criarPeticaoFisica(volumes, apensos, formaRecebimento, numeroSedex);
		
		idProcesso = processoAdapter.iniciar(tipoRecebimento);
		peticao.associarProcessoWorkflow(new ProcessoWorkflowId(idProcesso));
		return peticaoRepository.save(peticao);
	}

	/**
	 * Realiza a preautuação de uma petição física.
	 * 
	 * @param peticaoFisica Dados da petição física.
	 */
	public void preautuar(PeticaoFisica peticaoFisica) {
		this.peticaoRepository.save(peticaoFisica);
		
		String idProcesso = peticaoFisica.processosWorkflow().iterator().next().toString();
		
		TarefaDto tarefa = this.tarefaAdapter.consultarPorProcesso(idProcesso);
		
		this.tarefaAdapter.completar(tarefa.getId());
	}

	/**
	 * Realiza a atuação de uma petição.
	 * 
	 * @param peticao Dados da petição.
	 * @param classe Classe processual informada pelo autuador.
	 * @param peticaoValida Indica se uma petição foi considerada válida.
	 * @param motivoRejeicao Motivo da rejeição da petição.
	 */
	public void autuar(Peticao peticao, String classe, boolean peticaoValida, String motivoRejeicao) {
		
		String idProcesso = "";
		TarefaDto tarefa = null;
		
		idProcesso = peticao.processosWorkflow().iterator().next().toString();
		tarefa = this.tarefaAdapter.consultarPorProcesso(idProcesso);
		
		if (peticaoValida){
			peticao.aceitar(new ClasseId(classe));
			this.peticaoRepository.save(peticao);
			this.tarefaAdapter.completar(tarefa.getId());
		} else {
			peticao.rejeitar(motivoRejeicao);
			this.tarefaAdapter.sinalizar("peticaoInvalida", tarefa.getId());
		}
	}

	/**
	 * Distribui um processo para um Ministro Relator.
	 * @param peticao Dados da petição.
	 * @param ministroRelator Dados do Ministro Relator do processo.
	 */
	public Processo distribuir(Peticao peticao, MinistroId ministroRelator) {
		
		Processo processo = null;
		TarefaDto tarefa = null;
		String idProcesso = "";
		
		//Salva os dados da tarefa.
		peticaoRepository.save(peticao);
		
		//Consulta a terefa e ser concluída no workflow.
		idProcesso = peticao.processosWorkflow().iterator().next().toString();
		tarefa = this.tarefaAdapter.consultarPorProcesso(idProcesso);
		
		//Conclui a tarefa.
		tarefaAdapter.completar(tarefa.getId());
		
		//Gera o processo e o distribui a um Ministro Relator.
		processo = peticao.distribuir(ministroRelator);
		
		return processo;
	}
}
