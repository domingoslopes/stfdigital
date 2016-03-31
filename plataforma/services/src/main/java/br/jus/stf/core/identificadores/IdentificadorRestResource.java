package br.jus.stf.core.identificadores;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 25.03.2016
 */
@RestController
@RequestMapping("/api/identificadores")
public class IdentificadorRestResource {

	@Autowired
    private IdentificadorRepository identificadorRepository;
	
	@RequestMapping(method = GET)
	public Object numero(@RequestParam(required = false) String categoria) {
		if (StringUtils.isNotBlank(categoria)) {
			Identificador identficador = identificadorRepository.novoIdentficador(categoria);
			
			return new IdentificacaoDto(identficador.identity(), identficador.numero());
		}
		
		return identificadorRepository.novoIdentificadorId();
	}
	
}
