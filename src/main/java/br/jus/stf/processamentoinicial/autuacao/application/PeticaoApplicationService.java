package br.jus.stf.processamentoinicial.autuacao.application;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import br.jus.stf.processamentoinicial.autuacao.domain.DocumentoAdapter;
import br.jus.stf.processamentoinicial.autuacao.domain.PessoaAdapter;
import br.jus.stf.processamentoinicial.autuacao.domain.TarefaAdapter;
import br.jus.stf.processamentoinicial.autuacao.domain.WorkflowAdapter;
import br.jus.stf.processamentoinicial.autuacao.domain.model.FormaRecebimento;
import br.jus.stf.processamentoinicial.autuacao.domain.model.PartePeticao;
import br.jus.stf.processamentoinicial.autuacao.domain.model.PecaPeticao;
import br.jus.stf.processamentoinicial.autuacao.domain.model.PecaTemporaria;
import br.jus.stf.processamentoinicial.autuacao.domain.model.Peticao;
import br.jus.stf.processamentoinicial.autuacao.domain.model.PeticaoEletronica;
import br.jus.stf.processamentoinicial.autuacao.domain.model.PeticaoFactory;
import br.jus.stf.processamentoinicial.autuacao.domain.model.PeticaoFisica;
import br.jus.stf.processamentoinicial.autuacao.domain.model.PeticaoRepository;
import br.jus.stf.processamentoinicial.autuacao.domain.model.TipoDevolucao;
import br.jus.stf.processamentoinicial.autuacao.infra.PecaDevolucaoITextFromHtmlBuilder;
import br.jus.stf.processamentoinicial.suporte.domain.model.Peca;
import br.jus.stf.processamentoinicial.suporte.domain.model.Situacao;
import br.jus.stf.processamentoinicial.suporte.domain.model.Texto;
import br.jus.stf.processamentoinicial.suporte.domain.model.TextoRepository;
import br.jus.stf.processamentoinicial.suporte.domain.model.TipoPeca;
import br.jus.stf.processamentoinicial.suporte.domain.model.TipoPolo;
import br.jus.stf.processamentoinicial.suporte.domain.model.TipoProcesso;
import br.jus.stf.processamentoinicial.suporte.domain.model.Visibilidade;
import br.jus.stf.shared.ClasseId;
import br.jus.stf.shared.DocumentoId;
import br.jus.stf.shared.DocumentoTemporarioId;
import br.jus.stf.shared.PessoaId;
import br.jus.stf.shared.PeticaoId;
import br.jus.stf.shared.PreferenciaId;
import br.jus.stf.shared.ProcessoWorkflow;
import br.jus.stf.shared.TextoId;

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
	private WorkflowAdapter processoAdapter;
	
	@Autowired
	private PeticaoFactory peticaoFactory;
	
	@Autowired
	@Qualifier("peticaoTarefaRestAdapter")
	private TarefaAdapter tarefaAdapter;

	@Autowired
	private PeticaoApplicationEvent peticaoApplicationEvent;
	
	@Autowired
	private DocumentoAdapter documentoAdapter;
	
	@Autowired
	private PessoaAdapter pessoaAdapter;
	
	@Autowired
	private TextoRepository textoRepository;
	
	/**
	 * Registra uma nova petição.
	 * 
	 * @param peticaoEletronica Petição eletrônica recebida.
	 * @param orgaoId o órgão do representante
	 * @return Id da petição eletrônica registrada.
	 */
	public PeticaoEletronica peticionar(ClasseId classeSugerida, List<String> poloAtivo, List<String> poloPassivo, List<PecaTemporaria> pecas, Optional<Long> orgaoId) {
		PeticaoEletronica peticao = peticaoFactory.criarPeticaoEletronica(classeSugerida, poloAtivo, poloPassivo, pecas, orgaoId, TipoProcesso.ORIGINARIO);
		ProcessoWorkflow processoWorkflow = processoAdapter.iniciarWorkflow(peticao);
		peticao.associarProcessoWorkflow(processoWorkflow);
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
	public PeticaoFisica registrar(Integer volumes, Integer apensos, FormaRecebimento formaRecebimento,
			String numeroSedex, TipoProcesso tipoProcesso){
		PeticaoFisica peticao = peticaoFactory.criarPeticaoFisica(volumes, apensos, formaRecebimento, numeroSedex, tipoProcesso);
		ProcessoWorkflow processoWorkflow = processoAdapter.iniciarWorkflow(peticao);
		peticao.associarProcessoWorkflow(processoWorkflow);
		peticaoRepository.save(peticao);
		peticaoApplicationEvent.peticaoRecebida(peticao);
		return peticao;
	}

	/**
	 * Realiza a preautuação de uma petição física.
	 * 
	 * @param peticao Dados da petição física.
	 * @param classeSugerida Classe processual sugerida.
	 * @param peticaoValida Indica se a petição é válida ou inválida.
	 * @param motivoDevolucao Descrição do motivo da devolução da petição.
	 * @param preferencias Preferências processuais.
	 */
	public void preautuar(PeticaoFisica peticao, ClasseId classeSugerida, boolean peticaoValida, String motivoDevolucao, Set<PreferenciaId> preferencias) {
		if (peticaoValida) {
			peticao.preautuar(classeSugerida, preferencias);
			peticaoRepository.save(peticao);
			tarefaAdapter.completarPreautuacao(peticao);
			peticaoApplicationEvent.peticaoPreautuada(peticao);
		} else {
			peticao.devolver(motivoDevolucao);
			peticaoRepository.save(peticao);
			tarefaAdapter.completarPreautuacaoIndevida(peticao);
			peticaoApplicationEvent.remessaInvalida(peticao);
		}
	}

	/**
	 * Realiza a atuação de uma petição.
	 * 
	 * @param peticao Dados da petição.
	 * @param classe Classe processual informada pelo autuador.
	 * @param peticaoValida Indica se uma petição foi considerada válida.
	 * @param motivoRejeicao Motivo da rejeição da petição.
	 * @param partesPoloAtivo Partes do polo ativo
	 * @param partesPoloPassivo Partes do polo passivo
	 */	
	public void autuar(Peticao peticao, ClasseId classe, boolean peticaoValida, String motivoRejeicao, List<String> partesPoloAtivo, List<String> partesPoloPassivo) {
					
		if (peticaoValida) {
			carregarPartes(peticao, partesPoloAtivo, TipoPolo.POLO_ATIVO);
			carregarPartes(peticao, partesPoloPassivo, TipoPolo.POLO_PASSIVO);
			peticao.aceitar(classe);
			peticaoRepository.save(peticao);
			tarefaAdapter.completarAutuacao(peticao);
			peticaoApplicationEvent.peticaoAutuada(peticao);
		} else {
			peticao.rejeitar(motivoRejeicao);
			peticaoRepository.save(peticao);
			tarefaAdapter.completarAutuacaoRejeitada(peticao);
			peticaoApplicationEvent.peticaoRejeitada(peticao);
		}
	}

	/**
	 * Devolve uma petição.
	 * 
	 * @param peticao Dados da petição.
	 * @param tipoDevolucao
	 * @param numero
	 */
	public void prepararDevolucao(Peticao peticao, TipoDevolucao tipoDevolucao, Long numero) {
		// Passo 01: Recuperando o texto de devolução
		TextoId textoId = peticao.textoDevolucao();
		Texto texto = textoRepository.findOne(textoId);
		
		// Passo 02: Gerando o documento final e associando ao texto
		DocumentoId documentoFinalId = documentoAdapter.gerarDocumentoFinal(texto.documento());
		
		texto.associarDocumentoFinal(documentoFinalId);
		texto = textoRepository.save(texto);
		
		// Passo 03: Juntando a Peça de Devolução (Ofício) à Petição...
		TipoPeca tipo = peticaoRepository.findOneTipoPeca(Long.valueOf(8)); // TODO: Alterar o Tipo de Peça.
		peticao.adicionarPeca(new PecaPeticao(documentoFinalId, tipo, String.format("Ofício nº %s", numero), Visibilidade.PUBLICO, Situacao.PENDENTE_JUNTADA));
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
		Peca pecaAssinada = new PecaPeticao(documentoId, tipo, pecaOriginal.descricao(), pecaOriginal.visibilidade(), Situacao.JUNTADA);
		
		peticao.substituirPeca(pecaOriginal, pecaAssinada);
		peticaoRepository.save(peticao);
		tarefaAdapter.completarDevolucao(peticao);
		peticaoApplicationEvent.peticaoDevolucaoAssinada(peticao);
	}
	
	/**
	 * Cria as partes da petição.
	 * 
	 * @param peticao Petição.
	 * @param polo Lista de partes.
	 * @param tipo Tipo de polo.
	 * 
	 */
	private void carregarPartes(Peticao peticao, List<String> polo, TipoPolo tipo) {
		Set<PessoaId> pessoas = pessoaAdapter.cadastrarPessoas(polo);
		Set<PartePeticao> partes = pessoas.stream().map(pessoa -> new PartePeticao(pessoa, tipo)).collect(Collectors.toSet());
		peticao.atribuirPartes(partes, tipo);
	}

	public void associarTextoDevolucao(PeticaoId peticaoId, TextoId textoId) {
		Peticao peticao = peticaoRepository.findOne(peticaoId);
		peticao.associarTextoDevolucao(textoId);
		peticaoRepository.save(peticao);
	}
	
}
