package br.jus.stf.processamentoinicial.suporte.interfaces;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.jus.stf.processamentoinicial.suporte.domain.model.MeioTramitacao;
import br.jus.stf.processamentoinicial.suporte.interfaces.dto.MeioTramitacaoDto;

import com.wordnik.swagger.annotations.ApiOperation;

/**
 * Serviço REST para recuperar informações sobre os meios de tramitação.
 * 
 * @author Rafael Alencar
 *
 */
@RestController
@RequestMapping("/api/meiosTramitacao")
public class MeioTramitacaoRestResource {
	
	@ApiOperation(value = "Retorna a lista de meios de tramitação.")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<MeioTramitacaoDto> listar() {
		List<MeioTramitacaoDto> meiosTramitacao = new ArrayList<MeioTramitacaoDto>();
		
		for (MeioTramitacao m : MeioTramitacao.values()) {
			meiosTramitacao.add(new MeioTramitacaoDto(m.name(), m.descricao()));
		}
		
		return meiosTramitacao.stream().sorted((s1, s2) -> s1.getNome().compareTo(s2.getNome())).collect(Collectors.toList());
    }
}
