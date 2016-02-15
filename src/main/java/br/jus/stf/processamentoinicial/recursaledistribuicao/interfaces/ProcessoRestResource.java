package br.jus.stf.processamentoinicial.recursaledistribuicao.interfaces;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.jus.stf.processamentoinicial.recursaledistribuicao.interfaces.dto.ProcessoDto;
import br.jus.stf.processamentoinicial.recursaledistribuicao.interfaces.dto.ProcessoStatusDto;
import br.jus.stf.processamentoinicial.recursaledistribuicao.interfaces.facade.ProcessoServiceFacade;

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

    @ApiOperation("Recupera as informações de um determinado processo")
	@RequestMapping(value = "processos/{id}", method = RequestMethod.GET)
	public ProcessoDto consultar(@PathVariable Long id) {
    	return processoServiceFacade.consultar(id);
	}

	@ApiOperation(value = "Retorna a lista de status que podem ser atribuídos a um processo.")
    @RequestMapping(value = "processos/status", method = RequestMethod.GET)
    public List<ProcessoStatusDto> consultarStatus() {
    	return this.processoServiceFacade.consultarStatus();
    }
	
	@ApiOperation("Retorna o processo de uma petição.")
	@RequestMapping(value = "/peticoes/{id}/processo", method = RequestMethod.GET)
	public ProcessoDto consultarPelaPeticao(@PathVariable Long id) {
		return processoServiceFacade.consultarPelaPeticao(id);
	}
	
}
