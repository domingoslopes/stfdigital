package br.jus.stf.processamentoinicial.autuacao.application;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import br.jus.stf.processamentoinicial.autuacao.domain.ProcessoAdapter;
import br.jus.stf.processamentoinicial.autuacao.domain.TarefaAdapter;
import br.jus.stf.processamentoinicial.autuacao.domain.WorkflowAdapter;
import br.jus.stf.processamentoinicial.autuacao.domain.model.Peticao;
import br.jus.stf.processamentoinicial.autuacao.domain.model.PeticaoFisica;
import br.jus.stf.processamentoinicial.autuacao.domain.model.PeticaoRepository;
import br.jus.stf.processamentoinicial.distribuicao.domain.model.ProcessoFactory;
import br.jus.stf.processamentoinicial.distribuicao.domain.model.ProcessoRecursal;
import br.jus.stf.shared.ClasseId;

/**
 * @author Rafael Alencar
 * 
 * @since 13.01.2016
 */
@Component
@Transactional
public class RecursalApplicationService extends AutuacaoApplicationService {
	
	@Autowired
	private PeticaoRepository peticaoRepository;
	
	@Autowired
	private WorkflowAdapter processoAdapter;
	
	@Autowired
	@Qualifier("peticaoTarefaRestAdapter")
	private TarefaAdapter tarefaAdapter;
	
	@Autowired
	private PeticaoApplicationEvent peticaoApplicationEvent;
	
	@Autowired
	private ProcessoAdapter processoRest;

	/**
	 * Realiza a preautuação de uma petição física.
	 * 
	 * @param peticao Dados da petição física.
	 * @param classeSugerida Classe processual sugerida.
	 * @param motivoDevolucao Descrição do motivo da devolução da petição.
	 * @param peticaoValida Indica se a petição é valida ou inválida.
	 */
	public void preautuar(PeticaoFisica peticao, ClasseId classeSugerida, boolean peticaoValida, String motivoDevolucao) {
		super.preautuar(peticao, classeSugerida, peticaoValida, motivoDevolucao);
		
		processoRest.cadastrarRecursal((ProcessoRecursal)ProcessoFactory.criarProcesso(peticao.classeProcessual(), null, null, null, peticao.id(), peticao.tipoProcesso(), peticao.preferencias()));
		
		System.out.println("Para testar!");
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
		if (peticaoValida) {
			peticao.aceitar(classe);
			peticaoRepository.save(peticao);
			tarefaAdapter.completarAutuacao(peticao);
			this.peticaoApplicationEvent.peticaoAutuada(peticao);
		} else {
			peticao.rejeitar(motivoRejeicao);
			peticaoRepository.save(peticao);
			processoAdapter.rejeitarAutuacao(peticao);
			this.peticaoApplicationEvent.peticaoRejeitada(peticao);
		}
	}
	
}
