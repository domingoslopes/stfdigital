package br.jus.stf.workflow.interfaces;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.jus.stf.workflow.application.ProcessoApplicationService;
import br.jus.stf.workflow.interfaces.commands.IniciarProcessoCommand;
import br.jus.stf.workflow.interfaces.dto.ProcessoDto;
import br.jus.stf.workflow.interfaces.dto.ProcessoDtoAssembler;

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
	
	@Autowired
	private ProcessoDtoAssembler processoDtoAssembler;
	
	@Autowired
	private Validator validator;

	//TODO : Substituir validação pelo @Valid e injeção do BindingResult
	@RequestMapping(value = "/api/processo", method = RequestMethod.POST)
	public String iniciar(@RequestBody IniciarProcessoCommand command) {
		Set<ConstraintViolation<IniciarProcessoCommand>> result = validator.validate(command);
		if (!result.isEmpty()) {
			throw new IllegalArgumentException(result.toString());
		}
		return processoApplicationService.iniciar(command.getMensagem());
	}

	public void alterar(String id, String nome, String valor){
		this.processoApplicationService.alterar(id, nome, valor);
	}
	
	public ProcessoDto consultar(String id){ 
		return this.processoDtoAssembler.toDto(this.processoApplicationService.consultar(id));
	}
}
