package br.jus.stf.autuacao.infra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.autuacao.domain.ProcessoAdapter;
import br.jus.stf.autuacao.domain.entity.Peticao;
import br.jus.stf.plataforma.workflow.interfaces.ProcessoRestResource;
import br.jus.stf.plataforma.workflow.interfaces.commands.IniciarProcessoCommand;

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

	public void alterar(String idPeticao, String classe){
		this.processoRestService.alterar(idPeticao, classe);
	}
}
