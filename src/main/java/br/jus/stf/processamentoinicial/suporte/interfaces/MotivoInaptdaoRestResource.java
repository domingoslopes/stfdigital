/**
 * 
 */
package br.jus.stf.processamentoinicial.suporte.interfaces;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.jus.stf.processamentoinicial.recursaledistribuicao.domain.model.ProcessoRepository;
import br.jus.stf.processamentoinicial.suporte.interfaces.dto.MotivoInaptidaoDto;
import br.jus.stf.processamentoinicial.suporte.interfaces.dto.MotivoInaptidaoDtoAssembler;

/**
 * @author Anderson.Araujo
 *
 */
@RestController
@RequestMapping("/api/motivos")
public class MotivoInaptdaoRestResource {
	
	@Autowired
	private ProcessoRepository processoRepository;
	
	@Autowired
	private MotivoInaptidaoDtoAssembler motivoInaptidaoDtoAssembler;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<MotivoInaptidaoDto> listar() {
		return processoRepository.findAllMotivoInaptidao().stream().map(motivo -> motivoInaptidaoDtoAssembler.toDto(motivo)).collect(Collectors.toList());
	}
}
