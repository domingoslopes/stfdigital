package br.jus.stf.processamentoinicial.distribuicao.interfaces.facade;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.processamentoinicial.autuacao.domain.model.Peticao;
import br.jus.stf.processamentoinicial.autuacao.domain.model.PeticaoRepository;
import br.jus.stf.processamentoinicial.distribuicao.application.ProcessoApplicationService;
import br.jus.stf.processamentoinicial.distribuicao.domain.model.Processo;
import br.jus.stf.processamentoinicial.distribuicao.interfaces.dto.ProcessoDto;
import br.jus.stf.processamentoinicial.distribuicao.interfaces.dto.ProcessoDtoAssembler;
import br.jus.stf.shared.MinistroId;
import br.jus.stf.shared.PeticaoId;

@Component
public class ProcessoServiceFacade {
	
	@Autowired
	private ProcessoApplicationService processoApplicationService;
	
	@Autowired
	private ProcessoDtoAssembler processoDtoAssembler;

	// TODO: Remover essa dependência com o módulo de petições
	@Autowired
	private PeticaoRepository peticaoRepository;

	/**
	 * Distribui um processo para um ministro relator.
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
