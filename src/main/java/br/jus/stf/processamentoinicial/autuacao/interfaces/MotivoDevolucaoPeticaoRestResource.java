package br.jus.stf.processamentoinicial.autuacao.interfaces;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.jus.stf.processamentoinicial.autuacao.domain.model.MotivoDevolucao;
import br.jus.stf.processamentoinicial.autuacao.domain.model.PeticaoRepository;
import br.jus.stf.processamentoinicial.suporte.interfaces.dto.ModeloDto;
import br.jus.stf.processamentoinicial.suporte.interfaces.dto.ModeloDtoAssembler;
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
	
	@Autowired
	private MotivoDevolucaoDtoAssembler motivoDevolucaoDtoAssembler;
	
	@Autowired
	private ModeloDtoAssembler modeloDtoAssembler;
	
	@ApiOperation("Recupera os motivos de devolução de petição cadastrados.")
	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<MotivoDevolucaoDto> listar() {
		return peticaoRepository.findAllMotivoDevolucao().stream().map(md -> motivoDevolucaoDtoAssembler.toDto(md)).collect(Collectors.toList());
	}
	
	@ApiOperation("Recupera os modelos de documentos de acordo com o motivo informado.")
	@RequestMapping(value = "/{id}/modelos", method = RequestMethod.GET)
	public List<ModeloDto> consultarModelosPorMotivo(@PathVariable Long id) {
		MotivoDevolucao motivoDevolucao = peticaoRepository.findOneMotivoDevolucao(id); 
		return peticaoRepository.findModeloByMotivoDevolucao(motivoDevolucao).stream().map(m -> modeloDtoAssembler.toDto(m)).collect(Collectors.toList());
	}
}
