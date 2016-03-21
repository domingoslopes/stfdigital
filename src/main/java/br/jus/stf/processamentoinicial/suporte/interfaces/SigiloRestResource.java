package br.jus.stf.processamentoinicial.suporte.interfaces;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.jus.stf.processamentoinicial.suporte.domain.model.Sigilo;
import br.jus.stf.processamentoinicial.suporte.interfaces.dto.SigiloDto;

import com.wordnik.swagger.annotations.ApiOperation;

/**
 * Serviço REST para recuperar informações sobre os sigilos.
 * 
 * @author Rafael Alencar
 *
 */
@RestController
@RequestMapping("/api/sigilos")
public class SigiloRestResource {
	
	@ApiOperation(value = "Retorna a lista de sigilos.")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<SigiloDto> listar() {
		List<SigiloDto> sigilos = new ArrayList<SigiloDto>();
		
		for (Sigilo s : Sigilo.values()) {
			sigilos.add(new SigiloDto(s.name(), s.descricao()));
		}
		
		return sigilos.stream().sorted((s1, s2) -> s1.getNome().compareTo(s2.getNome())).collect(Collectors.toList());
    }
}
