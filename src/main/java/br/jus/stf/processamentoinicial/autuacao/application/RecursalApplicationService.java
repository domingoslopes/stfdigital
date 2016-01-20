package br.jus.stf.processamentoinicial.autuacao.application;

import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import br.jus.stf.processamentoinicial.autuacao.domain.DocumentoAdapter;
import br.jus.stf.processamentoinicial.autuacao.domain.PecaDevolucaoBuilder;
import br.jus.stf.processamentoinicial.autuacao.domain.ProcessoAdapter;
import br.jus.stf.processamentoinicial.autuacao.domain.TarefaAdapter;
import br.jus.stf.processamentoinicial.autuacao.domain.model.FormaRecebimento;
import br.jus.stf.processamentoinicial.autuacao.domain.model.PecaPeticao;
import br.jus.stf.processamentoinicial.autuacao.domain.model.Peticao;
import br.jus.stf.processamentoinicial.autuacao.domain.model.PeticaoFactory;
import br.jus.stf.processamentoinicial.autuacao.domain.model.PeticaoFisica;
import br.jus.stf.processamentoinicial.autuacao.domain.model.PeticaoRepository;
import br.jus.stf.processamentoinicial.autuacao.domain.model.TipoDevolucao;
import br.jus.stf.processamentoinicial.distribuicao.domain.model.MotivoInaptidaoProcesso;
import br.jus.stf.processamentoinicial.distribuicao.domain.model.ParteProcesso;
import br.jus.stf.processamentoinicial.distribuicao.domain.model.ProcessoFactory;
import br.jus.stf.processamentoinicial.distribuicao.domain.model.ProcessoRecursal;
import br.jus.stf.processamentoinicial.suporte.domain.model.Classificacao;
import br.jus.stf.processamentoinicial.suporte.domain.model.Peca;
import br.jus.stf.processamentoinicial.suporte.domain.model.TipoPeca;
import br.jus.stf.processamentoinicial.suporte.domain.model.TipoProcesso;
import br.jus.stf.shared.AssuntoId;
import br.jus.stf.shared.ClasseId;
import br.jus.stf.shared.DocumentoId;
import br.jus.stf.shared.DocumentoTemporarioId;
import br.jus.stf.shared.PeticaoId;

/**
 * @author Rafael Alencar
 * 
 * @since 13.01.2016
 */
@Component
@Transactional
public class RecursalApplicationService {
	
	@Autowired
	private ProcessoAdapter processoRest;
	
	@Autowired
	private PeticaoFactory peticaoFactory;
	
	@Autowired
	private PeticaoRepository peticaoRepository;
	
	@Autowired
	private DocumentoAdapter documentoAdapter;
	
	@Autowired
	private PecaDevolucaoBuilder pecaDevolucaoBuilder;
	
	@Autowired
	@Qualifier("peticaoTarefaRestAdapter")
	private TarefaAdapter tarefaAdapter;
	
	@Autowired
	private PeticaoApplicationEvent peticaoApplicationEvent;
	
	/**
	 * Registra o recebimento de um processo recursal físico.
	 * 
	 * @param volumes Quantidade de volumes do processo.
	 * 
	 * @return Id da petição física registrada.
	 */
	public PeticaoFisica registrar(Integer volumes, Integer apensos, FormaRecebimento formaRecebimento, String numeroSedex, String tipoProcesso){
		PeticaoFisica peticao = peticaoFactory.criarPeticaoFisica(volumes, apensos, formaRecebimento, numeroSedex, TipoProcesso.RECURSAL);
		peticaoRepository.save(peticao);
		return peticao;
	}
	
	/**
	 * Realiza a preautuação de recursais.
	 * 
	 * @param id Id da petição física.
	 * @param classeId Id da classe sugerida.
	 */
	public void preautuar(Long id, String classeId) {
		ClasseId classeSugerida = new ClasseId(classeId);
		PeticaoId peticaoId = new PeticaoId(id);
		PeticaoFisica peticao = peticaoRepository.findOne(peticaoId);
		
		peticao.preautuar(classeSugerida, peticao.preferencias());
		peticaoRepository.save(peticao);
		processoRest.cadastrarRecursal((ProcessoRecursal)ProcessoFactory.criarProcesso(peticao.classeSugerida(), null, null, null, peticao.id(), peticao.tipoProcesso(), peticao.preferencias()));
	}

	/**
	 * Realiza a autuação de processo recursal.
	 * 
	 * @param processo Processo recursal a ser autuado
	 * @param assuntos Lista de assuntos do processo
	 * @param poloAtivo Lista de partes do polo ativo
	 * @param poloPassivo Lista de partes do polo passivo
	 */
	public void autuar(ProcessoRecursal processo, Set<AssuntoId> assuntos, Set<ParteProcesso> poloAtivo, Set<ParteProcesso> poloPassivo) {
		
		processo.autuar(assuntos, poloAtivo, poloPassivo);
	}
	
	/**
	 * Realiza análise de pressupostos formais para processo recursal.
	 * 
	 * @param processo Processo recursal a ser autuado
	 * @param classificacao Classificação (APTO/INAPTO)
	 * @param motivosInaptidao Lista de motivos de inaptidão do processo
	 * @param observacao Observação da análise
	 */
	public void analisarPressupostosFormais(ProcessoRecursal processo, Classificacao classificacao, Set<MotivoInaptidaoProcesso> motivosInaptidao, String observacao) {
		// TODO: Verificar possíveis chamadas a eventos e ao workflow.
		switch (classificacao) {
			case APTO:
				processo.analisar(observacao);
				break;
			case INAPTO:
				processo.analisarInapto(motivosInaptidao, observacao);
				break;
			default:
				throw new IllegalArgumentException("Classificacao inexistente: " + classificacao);
		}
	}
	
	/**
	 * Realiza a devolução de um processo recursal.
	 * 
	 * @param motivo Motivo da devolução do processo recursal.
	 */
	public void devolver(Long peticaoId, String motivo) {
		PeticaoFisica peticao = peticaoRepository.findOne(new PeticaoId(peticaoId)); 
		peticao.devolver(motivo);
		peticaoRepository.save(peticao);
		
		//processoAdapter.devolver(peticao);
		//peticaoApplicationEvent.remessaInvalida(peticao);
	}
	
	/**
	 * Confirma a devolução de um processo recursal.
	 * 
	 * @param id Id da petição.
	 * @param tipo Tipo de devolução.
	 * @param numero Nº do ofócio.
	 */
	public void confirmarDevolucao(Long id, String tipo, Long numero) {
    	
		Peticao peticao = peticaoRepository.findOne(new PeticaoId(id));
		TipoDevolucao tipoDevolucao = TipoDevolucao.valueOf(tipo);
		
		// Passo 01: Gerando o Ofício de Devolução e fazendo o upload do documento (arquivo temporário)...
		byte[] oficio = pecaDevolucaoBuilder.build(peticao.identificacao(), tipoDevolucao, numero);
		DocumentoTemporarioId documentoTemporarioId = documentoAdapter.upload(tipoDevolucao.nome(), oficio);
		
		// Passo 02: Salvando o Documento Temporário no Sistema de Armazenamento Definitivo...
		DocumentoId documentoId = documentoAdapter.salvar(documentoTemporarioId);
		
		// Passo 03: Juntando a Peça de Devolução (Ofício) à Petição...
		TipoPeca tipoPeca = peticaoRepository.findOneTipoPeca(Long.valueOf(8)); // TODO: Alterar o Tipo de Peça.
		peticao.juntar(new PecaPeticao(documentoId, tipoPeca, String.format("Ofício nº %s", numero)));
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
