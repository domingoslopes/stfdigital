package br.jus.stf.processamentoinicial.autuacao.application;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import br.jus.stf.processamentoinicial.autuacao.domain.TarefaAdapter;
import br.jus.stf.processamentoinicial.autuacao.domain.WorkflowAdapter;
import br.jus.stf.processamentoinicial.autuacao.domain.model.FormaRecebimento;
import br.jus.stf.processamentoinicial.autuacao.domain.model.PecaTemporaria;
import br.jus.stf.processamentoinicial.autuacao.domain.model.Peticao;
import br.jus.stf.processamentoinicial.autuacao.domain.model.PeticaoEletronica;
import br.jus.stf.processamentoinicial.autuacao.domain.model.PeticaoFactory;
import br.jus.stf.processamentoinicial.autuacao.domain.model.PeticaoFisica;
import br.jus.stf.processamentoinicial.autuacao.domain.model.PeticaoRepository;
import br.jus.stf.shared.ClasseId;

/**
 * @author Rafael Alencar
 * 
 * @since 13.01.2016
 */
@Component
@Transactional
public abstract class AutuacaoApplicationService {
	
	@Autowired
	private PeticaoRepository peticaoRepository;
	
	@Autowired
	private WorkflowAdapter processoAdapter;
	
	@Autowired
	@Qualifier("peticaoTarefaRestAdapter")
	private TarefaAdapter tarefaAdapter;
	
	@Autowired
	private PeticaoFactory peticaoFactory;
	
	@Autowired
	private PeticaoApplicationEvent peticaoApplicationEvent;
	
	public abstract void autuar(Peticao peticao, ClasseId classe, boolean peticaoValida, String motivoRejeicao);

	/**
	 * Registra uma nova petilçao.
	 * 
	 * @param peticaoEletronica Petição eletrônica recebida.
	 * @param orgaoId o órgão do representante
	 * @return Id da petição eletrônica registrada.
	 */
	public PeticaoEletronica peticionar(ClasseId classeSugerida, List<String> poloAtivo, List<String> poloPassivo, List<PecaTemporaria> pecas, Optional<Long> orgaoId) {
		PeticaoEletronica peticao = peticaoFactory.criarPeticaoEletronica(classeSugerida, poloAtivo, poloPassivo, pecas, orgaoId, null);
		processoAdapter.iniciarWorkflow(peticao);
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
		PeticaoFisica peticao = peticaoFactory.criarPeticaoFisica(volumes, apensos, formaRecebimento, numeroSedex, null);
		processoAdapter.iniciarWorkflow(peticao);
		peticaoRepository.save(peticao);
		peticaoApplicationEvent.peticaoRecebida(peticao);
		return peticao;
	}

	/**
	 * Realiza a preautuação de uma petição física.
	 * 
	 * @param peticao Dados da petição física.
	 * @param classeSugerida Classe processual sugerida.
	 * @param motivoDevolucao Descrição do motivo da devolução da petição.
	 * @param peticaoValida Indica se a petição é valida ou inválida.
	 */
	public void preautuar(PeticaoFisica peticao, ClasseId classeSugerida, boolean peticaoValida, String motivoDevolucao) {
		if (peticaoValida) {
			tarefaAdapter.completarPreautuacao(peticao);
			peticao.preautuar(classeSugerida, null);
			peticaoRepository.save(peticao);
			peticaoApplicationEvent.peticaoPreautuada(peticao);
		} else {
			peticao.devolver(motivoDevolucao);
			peticao.preautuar(classeSugerida, null);
			peticaoRepository.save(peticao);
			processoAdapter.devolver(peticao);
			peticaoApplicationEvent.remessaInvalida(peticao);
		}
		
	}

}
