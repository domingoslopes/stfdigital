package br.jus.stf.plataforma.workflow.interfaces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import br.jus.stf.plataforma.workflow.application.ProcessoApplicationService;
import br.jus.stf.plataforma.workflow.interfaces.commands.IniciarProcessoCommand;
import br.jus.stf.plataforma.workflow.interfaces.dto.ProcessoDto;
import br.jus.stf.plataforma.workflow.interfaces.dto.ProcessoDtoAssembler;

/**
 * @author Rodrigo Barreiros
 * @author anderson.araujo
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
		return processoApplicationService.iniciar(command.getTipoRecebimento());
	}

	public void alterar(String id, String nome, String valor){
		this.processoApplicationService.alterar(id, nome, valor);
	}
	
	public ProcessoDto consultar(String id){ 
		return this.assembler.toDto(this.processoApplicationService.consultar(id));
	}
}
