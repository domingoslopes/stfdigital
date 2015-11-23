package br.jus.stf.processamentoinicial.distribuicao.interfaces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.jus.stf.processamentoinicial.distribuicao.interfaces.commands.DistribuirPeticaoCommand;
import br.jus.stf.processamentoinicial.distribuicao.interfaces.dto.ProcessoDto;
import br.jus.stf.processamentoinicial.distribuicao.interfaces.facade.ProcessoServiceFacade;

import com.wordnik.swagger.annotations.ApiOperation;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 24.09.2015
 */
@RestController
@RequestMapping("/api")
public class ProcessoRestResource {
	
	@Autowired
	private ProcessoServiceFacade processoServiceFacade;

    @ApiOperation("Recupera as informações de um determinad processo")
	@RequestMapping(value = "processos/{id}", method = RequestMethod.GET)
	public ProcessoDto consultar(@PathVariable Long id) {
    	return processoServiceFacade.consultar(id);
	}

	@ApiOperation("Conclui a distribuição de uma determinada petição")
	@RequestMapping(value = "/peticoes/{id}/distribuir", method = RequestMethod.POST)
	public ProcessoDto distribuir(@PathVariable Long id, @RequestBody DistribuirPeticaoCommand command) {
		return processoServiceFacade.distribuir(command.getTipoDistribuicao(), id, command.getJustificativa(),
				command.getMinistrosCandidatos(), command.getMinistrosImpedidos(), command.getProcessosPreventos());
	}

}
