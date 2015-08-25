package br.jus.stf.autuacao.application;

import java.io.IOException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import br.jus.stf.autuacao.domain.PeticaoService;
import br.jus.stf.autuacao.domain.ProcessoAdapter;
import br.jus.stf.autuacao.domain.TarefaAdapter;
import br.jus.stf.autuacao.domain.entity.ClasseProcessual;
import br.jus.stf.autuacao.domain.entity.Documento;
import br.jus.stf.autuacao.domain.entity.Peticao;
import br.jus.stf.autuacao.domain.entity.Polo;
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
	private ProcessoAdapter processoAdapter;
	
	@Autowired
	private TarefaAdapter tarefaAdapter;
	
	private PeticaoDtoAssembler assemblerPeticao = new PeticaoDtoAssembler();

	/**
	 * Registra uma nova petilçao.
	 * 
	 * @param tipoRecebimento
	 * @param classe
	 * @param poloAtivo
	 * @param poloPassivo
	 * @param documentos
	 * @return
	 */
	public String registrar(String tipoRecebimento, String classeSugerida, Polo poloAtivo, Polo poloPassivo, List<Documento> documentos) {
		Peticao peticao = null;
		
		if (tipoRecebimento == null || tipoRecebimento.isEmpty())
			throw new RuntimeException("O tipo de recebimento não foi informado.");
		
		if (classeSugerida == null || classeSugerida.isEmpty())
			throw new RuntimeException("A classe processual não foi informada.");
		
		if (poloAtivo == null || poloAtivo.getPartes() == null || poloAtivo.getPartes().size() == 0)
			throw new RuntimeException("O polo ativo não foi informado.");
		
		if (poloPassivo == null || poloPassivo.getPartes() == null || poloPassivo.getPartes().size() == 0)
			throw new RuntimeException("O polo passivo não foi informado.");
		
		peticao = new Peticao();
		peticao.setClasseSugerida(new ClasseProcessual(classeSugerida));
		peticao.setPoloAtivo(poloAtivo);
		peticao.setPoloPassivo(poloPassivo);
		peticao.setDocumentos(documentos);
		
		return peticaoService.registrar(tipoRecebimento, peticao);
	}

	public void preautuar(String idPeticao) {
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
		return this.assemblerPeticao.toDto(this.peticaoService.consultar(id));
	}
	
	public String receberDocumentoPeticao(MultipartFile arquivo) throws IOException{
		return this.peticaoService.gravarArquivo(arquivo);
	}
}
