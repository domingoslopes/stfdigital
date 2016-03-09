package br.jus.stf.processamentoinicial.autuacao.interfaces;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.jus.stf.processamentoinicial.autuacao.interfaces.dto.PeticaoDto;
import br.jus.stf.processamentoinicial.autuacao.interfaces.dto.PeticaoStatusDto;
import br.jus.stf.processamentoinicial.autuacao.interfaces.facade.PeticaoServiceFacade;

import com.wordnik.swagger.annotations.ApiOperation;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 22.06.2015
 */
@RestController
@RequestMapping("/api/peticoes")
public class PeticaoRestResource {

	private PeticaoServiceFacade peticaoServiceFacade;
	
	@Autowired
	public PeticaoRestResource(PeticaoServiceFacade peticaoServiceFacade) {
		this.peticaoServiceFacade = peticaoServiceFacade;
	}

    @ApiOperation("Recupera as informações de uma determinada petição")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public PeticaoDto consultar(@PathVariable Long id) {
		return peticaoServiceFacade.consultar(id);
	}
    
    @ApiOperation("Recupera as informações de várias petições")
	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<PeticaoDto> consultar(@RequestParam("ids") List<Long> ids) {
		return peticaoServiceFacade.consultar(ids);
	}

    @ApiOperation(value = "Retorna as partes de uma petição", hidden = true)
	@RequestMapping(value = "/{id}/partes", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public Map<String, List<Long>> consultarPartes(@PathVariable Long id) {
		PeticaoDto peticao = peticaoServiceFacade.consultar(id);
		return peticao.getPartes();
	}
    
    @ApiOperation(value = "Retorna a lista de status que podem ser atribuídos a uma petição.")
    @RequestMapping(value = "/status", method = RequestMethod.GET)
    public List<PeticaoStatusDto> consultarStatus() {
    	return peticaoServiceFacade.consultarStatusPeticao();
    }
    
    @ApiOperation(value = "Retorna o processo de workflow associado a uma petição.")
    @RequestMapping(value = "/{id}/processo-workflow", method = RequestMethod.GET)
    public Long consultarProcessoWorkflow(@PathVariable Long id) {
    	return peticaoServiceFacade.consultarProcessoWorkflow(id);
    }
    
    @ApiOperation(value = "Retorna o template de documento de devolução.")
    @RequestMapping(value = "/template-devolucao", method = RequestMethod.GET)
    public ResponseEntity<InputStreamResource> consultarTemplateDevolucao(@RequestParam("tipo") String tipoDevolucao, @RequestParam(value = "ext", defaultValue = "html") String extensao)
    		throws Exception {
    	InputStream is = peticaoServiceFacade.consultarTemplateDevolucao(tipoDevolucao, extensao);
    	InputStreamResource isr = new InputStreamResource(is);
    	HttpHeaders headers = createResponseHeaders(extensao);
    	return new ResponseEntity<InputStreamResource>(isr, headers, HttpStatus.OK);
    }

	/**
	 * Cria os headers da resposta da requisição do template de devolução
	 * 
     * @param extensao
     * @return
     */
    private HttpHeaders createResponseHeaders(String extensao) {
	    HttpHeaders headers = new HttpHeaders();
    	MediaType mediaType = null;
    	if (extensao.equalsIgnoreCase("odt")) {
    		mediaType = MediaType.parseMediaType("application/vnd.oasis.opendocument.text");
    	} else if (extensao.equalsIgnoreCase("html")) {
    		mediaType = MediaType.TEXT_HTML;
    	}
    	headers.setContentType(mediaType);
	    return headers;
    }
    
    @ApiOperation("Associa um texto de devolução a uma petição.")
    @RequestMapping(value = "/{peticaoId}/associar-texto-devolucao/{textoId}", method = RequestMethod.POST)
    public void associarTextoDevolucao(@PathVariable("peticaoId") Long peticaoId, @PathVariable("textoId") Long textoId) {
    	peticaoServiceFacade.associarTextoDevolucao(peticaoId, textoId);
    }
    
}
