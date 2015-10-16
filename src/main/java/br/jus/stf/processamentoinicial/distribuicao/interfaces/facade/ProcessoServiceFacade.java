package br.jus.stf.processamentoinicial.distribuicao.interfaces.facade;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.processamentoinicial.autuacao.domain.model.Peticao;
import br.jus.stf.processamentoinicial.autuacao.domain.model.PeticaoRepository;
import br.jus.stf.processamentoinicial.distribuicao.application.ProcessoApplicationService;
import br.jus.stf.processamentoinicial.distribuicao.domain.model.Processo;
import br.jus.stf.processamentoinicial.distribuicao.domain.model.ProcessoRepository;
import br.jus.stf.processamentoinicial.distribuicao.interfaces.dto.ProcessoDto;
import br.jus.stf.processamentoinicial.distribuicao.interfaces.dto.ProcessoDtoAssembler;
import br.jus.stf.shared.MinistroId;
import br.jus.stf.shared.PeticaoId;
import br.jus.stf.shared.ProcessoId;

@Component
public class ProcessoServiceFacade {
	
	@Autowired
	private ProcessoApplicationService processoApplicationService;
	
	@Autowired
	private ProcessoDtoAssembler processoDtoAssembler;
	
	@Autowired
	private ProcessoRepository processoRepository;

	// TODO: Remover essa dependência com o módulo de petições
	@Autowired
	private PeticaoRepository peticaoRepository;

	/**
	 * Consulta um processo judicial, dado o seu identificador primário
	 * 
	 * @param id o código de identificação do processo
	 * @return dto o DTO com as informações de retorno do processo
	 */
	public ProcessoDto consultar(Long id){
		ProcessoId processoId = new ProcessoId(id);
		Processo processo = Optional.ofNullable(processoRepository.findOne(processoId)).orElseThrow(IllegalArgumentException::new);
		return processoDtoAssembler.toDto(processo);
	}

	/**
	 * Distribui um processo para um ministro relator.
	 * 
	 * @param peticaoId Id da petição.
	 * @param ministroId Id do Ministro Relator.
	 */
	public ProcessoDto distribuir(Long peticaoId, Long ministroId) {
		MinistroId ministro = new MinistroId(ministroId);
		PeticaoId id = new PeticaoId(peticaoId);
		Peticao peticao = Optional.ofNullable((Peticao) peticaoRepository.findOne(id))
								  .orElseThrow(IllegalArgumentException::new);
		Processo processo = processoApplicationService.distribuir(peticao, ministro);
		return processoDtoAssembler.toDto(processo);
	}

}
