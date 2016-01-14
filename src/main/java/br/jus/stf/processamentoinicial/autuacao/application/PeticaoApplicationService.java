package br.jus.stf.processamentoinicial.autuacao.application;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import br.jus.stf.processamentoinicial.autuacao.domain.DocumentoAdapter;
import br.jus.stf.processamentoinicial.autuacao.domain.PecaDevolucaoBuilder;
import br.jus.stf.processamentoinicial.autuacao.domain.TarefaAdapter;
import br.jus.stf.processamentoinicial.autuacao.domain.WorkflowAdapter;
import br.jus.stf.processamentoinicial.autuacao.domain.model.Peca;
import br.jus.stf.processamentoinicial.autuacao.domain.model.PecaPeticao;
import br.jus.stf.processamentoinicial.autuacao.domain.model.Peticao;
import br.jus.stf.processamentoinicial.autuacao.domain.model.PeticaoRepository;
import br.jus.stf.processamentoinicial.autuacao.domain.model.TipoDevolucao;
import br.jus.stf.processamentoinicial.autuacao.domain.model.TipoPeca;
import br.jus.stf.shared.ClasseId;
import br.jus.stf.shared.DocumentoId;
import br.jus.stf.shared.DocumentoTemporarioId;

/**
 * @author Rodrigo Barreiros
 * @author Anderson.Araujo
 * 
 * @since 1.0.0
 * @since 22.06.2015
 */
@Component
@Transactional
public class PeticaoApplicationService extends AutuacaoApplicationService {
	
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
	private DocumentoAdapter documentoAdapter;
	
	@Autowired
	private PecaDevolucaoBuilder pecaDevolucaoBuilder;

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

	/**
	 * Devolve uma petição.
	 * 
	 * @param peticao Dados da petição.
	 */
	public void prepararDevolucao(Peticao peticao, TipoDevolucao tipoDevolucao, Long numero) {
    	// Passo 01: Gerando o Ofício de Devolução e fazendo o upload do documento (arquivo temporário)...
		byte[] oficio = pecaDevolucaoBuilder.build(peticao.identificacao(), tipoDevolucao, numero);
		DocumentoTemporarioId documentoTemporarioId = documentoAdapter.upload(tipoDevolucao.nome(), oficio);
		
		// Passo 02: Salvando o Documento Temporário no Sistema de Armazenamento Definitivo...
		DocumentoId documentoId = documentoAdapter.salvar(documentoTemporarioId);
		
		// Passo 03: Juntando a Peça de Devolução (Ofício) à Petição...
		TipoPeca tipo = peticaoRepository.findOneTipoPeca(Long.valueOf(8)); // TODO: Alterar o Tipo de Peça.
		peticao.juntar(new PecaPeticao(documentoId, tipo, String.format("Ofício nº %s", numero)));
		peticaoRepository.save(peticao);
		
		// Passo 04: Completando a tarefa no BPM...
		tarefaAdapter.completarPreparacaoParaDevolucao(peticao);
		
		//Passo 05: Gera o evento de peticao inválida.
		peticaoApplicationEvent.peticaoPreparadaParaDevolucao(peticao);
	}
	
	/**
	 * Assina o documento de devolução de uma Petição.
	 * 
	 * @param peticao
	 * @param documentoTemporarioId
	 */
	public void assinarDevolucao(Peticao peticao, DocumentoTemporarioId documentoTemporarioId) {
		// Salva definitivamente o documento assinado.
		DocumentoId documentoId = documentoAdapter.salvar(documentoTemporarioId);
		
		TipoPeca tipo = peticaoRepository.findOneTipoPeca(Long.valueOf(8));
		Peca pecaOriginal = peticao.pecas().stream().filter(p -> p.tipo().equals(tipo)).findFirst().get();
		Peca pecaAssinada = new PecaPeticao(documentoId, tipo, pecaOriginal.descricao());
		
		peticao.substituirPeca(pecaOriginal, pecaAssinada);
		
		peticaoRepository.save(peticao);

		tarefaAdapter.completarDevolucao(peticao);
		
		peticaoApplicationEvent.peticaoDevolucaoAssinada(peticao);
	}
	
}
