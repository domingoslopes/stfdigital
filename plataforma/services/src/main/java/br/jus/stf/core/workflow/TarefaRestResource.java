package br.jus.stf.core.workflow;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.net.URI;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.wordnik.swagger.annotations.ApiOperation;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 23.06.2015
 */
@RestController
@RequestMapping("/api/tarefas")
public class TarefaRestResource {
    
    @Autowired
    private DiscoveryClient discoveryClient;
	
    @ApiOperation(value = "Lista todas as tarefas associadas ao usuário corrente")
	@RequestMapping(method = GET)
	public List<TarefaDto> tarefas() {
    	List<TarefaDto> tarefas = new LinkedList<>();
    	
    	List<String> servicos = Arrays.asList("recebimento", "autuacao", "distribuicao");
    	
    	servicos.forEach(servico -> tarefas.addAll(tarefas(servico)));
    	
		return tarefas;
	}

    @SuppressWarnings("unchecked")
	private List<TarefaDto> tarefas(String servico) {
		URI servicesUri = discoveryClient.getInstances(servico).get(0).getUri();
		
		URI uri = UriComponentsBuilder.fromUri(servicesUri).path("/api/tarefas").build().toUri();
		
		return new RestTemplate().getForObject(uri, List.class);
	}
    
}
