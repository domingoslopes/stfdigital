package br.jus.stf.processamentoinicial.suporte.interfaces;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.jus.stf.processamentoinicial.autuacao.domain.model.PeticaoRepository;
import br.jus.stf.processamentoinicial.suporte.interfaces.dto.MotivoDevolucaoDto;
import br.jus.stf.processamentoinicial.suporte.interfaces.dto.MotivoDevolucaoDtoAssembler;

import com.wordnik.swagger.annotations.ApiOperation;

/**
 * API REST usada para recuperar os motivos de devolução de uma petição.
 * 
 * @author Anderson.Araujo
 * @since 15.03.2016
 *
 */
@RestController
@RequestMapping("/api/motivos-devolucao")
public class MotivoDevolucaoPeticaoRestResource {
	@Autowired
	private PeticaoRepository peticaoRepository;
	
	private MotivoDevolucaoDtoAssembler motivoDevolucaoDtoAssembler;
	
	@ApiOperation("Recupera os motivos de devolução de petição cadastrados.")
	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<MotivoDevolucaoDto> listar() {
		return peticaoRepository.findAllMotivoDevolucao().stream().map(md -> motivoDevolucaoDtoAssembler.toDto(md)).collect(Collectors.toList());
	}
}
