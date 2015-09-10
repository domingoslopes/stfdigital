package br.jus.stf.autuacao.infra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.autuacao.domain.ProcessoAdapter;
import br.jus.stf.autuacao.domain.model.Peticao;
import br.jus.stf.workflow.interfaces.ProcessoRestResource;
import br.jus.stf.workflow.interfaces.commands.IniciarProcessoCommand;

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
	public String iniciar(String tipoRecebimento) {
		
		IniciarProcessoCommand command = new IniciarProcessoCommand();
		command.setMensagem(tipoRecebimento);
		
		return processoRestService.iniciar(command);
	}

	/**
	 * Altera a classe de uma petição.
	 */
	/*public void alterar(String id, String nome, String valor){
		this.processoRestService.alterar(id, nome, valor);
	}*/
	
	/**
	 * Consulta uma instância de um processo (Plataforma) de acordo com o id informado..
	 */
	public Peticao consultar(String id){
		/*
		/*List<Parte> partesPoloAtivo = new LinkedList<Parte>();
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
		
		return peticao;*/
		return null;
	}
}
