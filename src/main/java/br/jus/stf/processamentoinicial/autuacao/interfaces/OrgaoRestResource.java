package br.jus.stf.processamentoinicial.autuacao.interfaces;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.jus.stf.processamentoinicial.autuacao.domain.model.PeticaoRepository;
import br.jus.stf.processamentoinicial.autuacao.interfaces.dto.OrgaoDto;
import br.jus.stf.processamentoinicial.autuacao.interfaces.dto.OrgaoDtoAssembler;

/**
 * @author Rafael Alencar
 */
@RestController
@RequestMapping("/api/orgaos")
public class OrgaoRestResource {

	@Autowired
	private OrgaoDtoAssembler orgaoDtoAssembler;

	@Autowired
	private PeticaoRepository peticaoRepository;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<OrgaoDto> listar() {
		return peticaoRepository.findAllOrgao().stream().map(orgao -> orgaoDtoAssembler.toDto(orgao)).collect(Collectors.toList());
	}

}
