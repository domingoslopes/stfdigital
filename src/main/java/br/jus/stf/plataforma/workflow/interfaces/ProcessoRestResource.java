package br.jus.stf.plataforma.workflow.interfaces;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.jus.stf.autuacao.domain.entity.ClasseProcessual;
import br.jus.stf.autuacao.domain.entity.Documento;
import br.jus.stf.autuacao.domain.entity.Parte;
import br.jus.stf.autuacao.domain.entity.Polo;
import br.jus.stf.plataforma.workflow.application.ProcessoApplicationService;
import br.jus.stf.plataforma.workflow.interfaces.commands.IniciarProcessoCommand;
import br.jus.stf.plataforma.workflow.interfaces.dto.ProcessoDto;
import br.jus.stf.plataforma.workflow.interfaces.dto.ProcessoDtoAssembler;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 23.06.2015
 */
@Component
public class ProcessoRestResource {

	@Autowired
	private ProcessoApplicationService processoApplicationService;
	
	private ProcessoDtoAssembler assembler = new ProcessoDtoAssembler(); 

	@RequestMapping(value = "/api/processo", method = RequestMethod.POST)
	public String iniciar(@RequestBody IniciarProcessoCommand command) {
		
		ClasseProcessual classeSugerida = new ClasseProcessual(command.getClasse());
		Polo poloAtivo = new Polo();
		Polo poloPassivo = new Polo();
		List<Documento> documentos = new LinkedList<Documento>();
		List<Parte> partesPoloAtivo = new LinkedList<Parte>();
		List<Parte> partesPoloPassivo = new LinkedList<Parte>();
		
		for(int i = 0; i < command.getPartesPoloAtivo().length; i++){
			partesPoloAtivo.add(new Parte(command.getPartesPoloAtivo()[i]));
		}
		
		for(int i = 0; i < command.getPartesPoloPassivo().length; i++){
			partesPoloPassivo.add(new Parte(command.getPartesPoloPassivo()[i]));
		}
		
		for(int i = 0; i < command.getDocumentos().length; i++){
			documentos.add(new Documento(command.getDocumentos()[i]));
		}
		
		poloAtivo.setPartes(partesPoloAtivo);
		poloPassivo.setPartes(partesPoloPassivo);
		
		return processoApplicationService.iniciar(command.getIdProcesso(), classeSugerida, poloAtivo, poloPassivo, documentos);
	}

	public void alterar(String id, String nome, String valor){
		this.processoApplicationService.alterar(id, nome, valor);
	}
	
	public ProcessoDto consultar(String id){ 
		return this.assembler.toDto(this.processoApplicationService.consultar(id));
	}
}
