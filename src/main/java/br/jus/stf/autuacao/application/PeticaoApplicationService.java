package br.jus.stf.autuacao.application;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.autuacao.domain.ProcessoWorkflowAdapter;
import br.jus.stf.autuacao.domain.TarefaAdapter;
import br.jus.stf.autuacao.domain.model.FormaRecebimento;
import br.jus.stf.autuacao.domain.model.Peticao;
import br.jus.stf.autuacao.domain.model.PeticaoEletronica;
import br.jus.stf.autuacao.domain.model.PeticaoFactory;
import br.jus.stf.autuacao.domain.model.PeticaoFisica;
import br.jus.stf.autuacao.domain.model.PeticaoRepository;
import br.jus.stf.autuacao.domain.model.Processo;
import br.jus.stf.autuacao.domain.model.ProcessoRepository;
import br.jus.stf.shared.domain.model.ClasseId;
import br.jus.stf.shared.domain.model.MinistroId;

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
	private PeticaoRepository peticaoRepository;
	
	@Autowired
	private ProcessoWorkflowAdapter processoAdapter;
	
	@Autowired
	private TarefaAdapter tarefaAdapter;
	
	@Autowired
	private PeticaoFactory peticaoFactory;
	
	@Autowired
	private PeticaoApplicationEvent peticaoApplicationEvent;
	
	@Autowired
	private ProcessoRepository processoRepository;

	/**
	 * Registra uma nova petilçao.
	 * 
	 * @param peticaoEletronica Petição eletrônica recebida.
	 * @return Id da petição eletrônica registrada.
	 */
	public PeticaoEletronica peticionar(ClasseId classeSugerida, List<String> poloAtivo, List<String> poloPassivo, List<String> documentos) {
		PeticaoEletronica peticao = peticaoFactory.criarPeticaoEletronica(classeSugerida, poloAtivo, poloPassivo, documentos);
		processoAdapter.iniciarProcessoWorkflow(peticao);
		peticaoRepository.save(peticao);
		peticaoApplicationEvent.peticaoRecebida(peticao);
		return peticao;
	}
	
	/**
	 * Registra o recebimento de uma petição física.
	 * 
	 * @param volumes Quantidade de volumes da petição física.
	 * 
	 * @return Id da petição física registrada.
	 */
	public PeticaoFisica registrar(Integer volumes, Integer apensos, FormaRecebimento formaRecebimento, String numeroSedex){
		PeticaoFisica peticao = peticaoFactory.criarPeticaoFisica(volumes, apensos, formaRecebimento, numeroSedex);
		processoAdapter.iniciarProcessoWorkflow(peticao);
		peticaoRepository.save(peticao);
		peticaoApplicationEvent.peticaoRecebida(peticao);
		return peticao;
	}

	/**
	 * Realiza a preautuação de uma petição física.
	 * 
	 * @param peticao Dados da petição física.
	 * @param classeSugerida
	 */

	public void preautuar(PeticaoFisica peticao, ClasseId classeSugerida) {
		peticao.preautuar(classeSugerida);
		tarefaAdapter.completarPreautuacao(peticao);
		peticaoRepository.save(peticao);
	}

	/**
	 * Realiza a atuação de uma petição.
	 * 
	 * @param peticao Dados da petição.
	 * @param classe Classe processual informada pelo autuador.
	 * @param peticaoValida Indica se uma petição foi considerada válida.
	 * @param motivoRejeicao Motivo da rejeição da petição.
	 */	
	public void autuar(Peticao peticao, ClasseId classe, boolean peticaoValida, String motivoRejeicao) {
		if (peticaoValida){
			peticao.aceitar(classe);
			tarefaAdapter.completarAutuacao(peticao);
		} else {
			peticao.rejeitar(motivoRejeicao);
			processoAdapter.rejeitarAutuacao(peticao);
		}
		peticaoRepository.save(peticao);
	}

	/**
	 * Distribui um processo para um Ministro Relator.
	 * @param peticao Dados da petição.
	 * @param ministroRelator Dados do Ministro Relator do processo.
	 * @return processo
	 */
	public Processo distribuir(Peticao peticao, MinistroId ministroRelator) {
		tarefaAdapter.completarDistribuicao(peticao);
		Processo processo = peticao.distribuir(ministroRelator);
		processoRepository.save(processo);
		peticaoApplicationEvent.processoDistribuido(processo);
		return processo;
	}


	/**
	 * Devolve uma petição.
	 * @param peticao Dados da petição.
	 */
	public void devolver(Peticao peticao) {
		tarefaAdapter.completarDevolucao(peticao);
	}
	
}
