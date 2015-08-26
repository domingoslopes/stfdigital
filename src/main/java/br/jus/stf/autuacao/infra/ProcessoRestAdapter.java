package br.jus.stf.autuacao.infra;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.autuacao.domain.ProcessoAdapter;
import br.jus.stf.autuacao.domain.entity.ClasseProcessual;
import br.jus.stf.autuacao.domain.entity.Documento;
import br.jus.stf.autuacao.domain.entity.Parte;
import br.jus.stf.autuacao.domain.entity.Peticao;
import br.jus.stf.autuacao.domain.entity.Polo;
import br.jus.stf.plataforma.workflow.interfaces.ProcessoRestResource;
import br.jus.stf.plataforma.workflow.interfaces.commands.IniciarProcessoCommand;
import br.jus.stf.plataforma.workflow.interfaces.dto.ProcessoDto;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 26.06.2015
 */
@Component
public class ProcessoRestAdapter implements ProcessoAdapter {

	@Autowired
	private ProcessoRestResource processoRestService;

	@Override
	public String iniciar(String idProcesso, Peticao peticao) {
		
		IniciarProcessoCommand command = new IniciarProcessoCommand();
		String[] partesPoloAtivo = new String[peticao.getPoloAtivo().getPartes().size()];
		String[] partesPoloPassivo = new String[peticao.getPoloPassivo().getPartes().size()];
		String[] documentos = new String[peticao.getDocumentos().size()];
		
		for(int i = 0; i < peticao.getPoloAtivo().getPartes().size(); i++){
			partesPoloAtivo[i] = peticao.getPoloAtivo().getPartes().get(i).getNome();
		}
		
		for(int i = 0; i < peticao.getPoloPassivo().getPartes().size(); i++){
			partesPoloPassivo[i] = peticao.getPoloPassivo().getPartes().get(i).getNome();
		}
		
		for(int i = 0; i < peticao.getDocumentos().size(); i++){
			documentos[i] = peticao.getDocumentos().get(i).getDescricao();
		}
		
		command.setIdProcesso(idProcesso);
		command.setClasse(peticao.getClasseSugerida().getSigla());
		command.setPartesPoloAtivo(partesPoloAtivo);
		command.setPartesPoloPassivo(partesPoloPassivo);
		command.setDocumentos(documentos);
		
		return processoRestService.iniciar(command);
	}

	/**
	 * Altera a classe de uma petição.
	 */
	public void alterar(String id, String nome, String valor){
		this.processoRestService.alterar(id, nome, valor);
	}
	
	/**
	 * Consulta uma instância de um processo (Plataforma) de acordo com o id informado..
	 */
	public Peticao consultar(String id){
		
		List<Parte> partesPoloAtivo = new LinkedList<Parte>();
		List<Parte> partesPoloPassivo = new LinkedList<Parte>();
		List<Documento> documentos = new LinkedList<Documento>();
		ProcessoDto processo = this.processoRestService.consultar(id);
		Peticao peticao = new Peticao();
		
		//Partes do polo ativo.
		String[] p1 = (String[])processo.getVariaveis().get("partesPoloAtivo");
		for(int i = 0; i < p1.length; i++){
			partesPoloAtivo.add(new Parte(p1[i]));
		}
		
		//Partes do polo passivo.
		String[] p2 = (String[])processo.getVariaveis().get("partesPoloPassivo");
		for(int i = 0; i < p2.length; i++){
			partesPoloPassivo.add(new Parte(p2[i]));
		}
		
		//Documentos.
		String[] docs = (String[])processo.getVariaveis().get("documentos");
		for(int i = 0; i < docs.length; i++){
			documentos.add(new Documento(docs[i]));
		}
		
		//Monta a petição.
		peticao.setId(processo.getId());
		peticao.setClasseSugerida(new ClasseProcessual(processo.getVariaveis().get("classeSugerida").toString()));
		peticao.setPoloAtivo(new Polo(partesPoloAtivo));
		peticao.setPoloPassivo(new Polo(partesPoloPassivo));
		peticao.setDocumentos(documentos);
		peticao.setClasse(new ClasseProcessual(processo.getVariaveis().get("classeSugerida").toString()));		
		
		return peticao;
	}
}
