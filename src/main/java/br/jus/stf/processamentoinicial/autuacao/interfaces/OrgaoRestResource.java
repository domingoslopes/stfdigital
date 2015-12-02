package br.jus.stf.processamentoinicial.autuacao.interfaces;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.jus.stf.plataforma.acessos.application.AcessosApplicationService;
import br.jus.stf.plataforma.shared.security.SecurityContextUtil;
import br.jus.stf.processamentoinicial.autuacao.domain.model.PeticaoRepository;
import br.jus.stf.processamentoinicial.autuacao.interfaces.dto.OrgaoDto;
import br.jus.stf.processamentoinicial.autuacao.interfaces.dto.OrgaoDtoAssembler;
import br.jus.stf.shared.PessoaId;

/**
 * @author Rafael Alencar
 */
@RestController
@RequestMapping("/api/orgaos")
public class OrgaoRestResource {

	@Autowired
	private AcessosApplicationService acessoApplicationService;
	
	@Autowired
	private OrgaoDtoAssembler orgaoDtoAssembler;

	@Autowired
	private PeticaoRepository peticaoRepository;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<OrgaoDto> listar() {
		PessoaId id = acessoApplicationService.recuperarInformacoesUsuario(SecurityContextUtil.getUsername()).pessoa().id();
		return peticaoRepository.findOrgaoByRepresentacao(id).stream().map(orgao -> orgaoDtoAssembler.toDto(orgao)).collect(Collectors.toList());
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public OrgaoDto consultar(@PathVariable Long id) {
		return this.orgaoDtoAssembler.toDto(peticaoRepository.findOneOrgao(id));
	}

}
