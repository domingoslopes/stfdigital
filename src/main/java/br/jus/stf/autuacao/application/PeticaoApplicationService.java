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
import br.jus.stf.autuacao.interfaces.dto.PeticaoDtoAssembler;
import br.jus.stf.plataforma.workflow.interfaces.dto.TarefaDto;

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
	
	private PeticaoDtoAssembler assemblerPeticao = new PeticaoDtoAssembler();

	/**
	 * Registra uma nova petilçao.
	 * 
	 * @param peticaoEletronica Petição eletrônica recebida.
	 * 
	 * @return Id da petição eletrônica registrada.
	 */
	public String peticionar(PeticaoEletronica peticaoEletronica) {
		peticaoRepository.store(peticaoEletronica);
		
//		return peticaoService.registrar(tipoRecebimento, peticao);
		return "";
	}
	
	/**
	 * Registra o recebimento de uma petição física.
	 * 
	 * @param volumes Quantidade de volumes da petição física.
	 * 
	 * @return Id da petição física registrada.
	 */
	public String registrar(PeticaoFisica peticaoFisica){
		
		return "";
	}

	public void preautuar(String idPeticao, String classeSugerida) {
		peticaoService.preautuar(idPeticao);
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
		//return this.assemblerPeticao.toDto(this.peticaoService.consultar(id));
		return null;
	}
	
	public String receberDocumentoPeticao(MultipartFile arquivo) throws IOException{
		return this.peticaoService.gravarArquivo(arquivo);
	}
}
