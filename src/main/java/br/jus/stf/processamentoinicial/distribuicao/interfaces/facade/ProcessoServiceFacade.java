package br.jus.stf.processamentoinicial.distribuicao.interfaces.facade;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.plataforma.shared.security.SecurityContextUtil;
import br.jus.stf.processamentoinicial.distribuicao.application.ProcessoApplicationService;
import br.jus.stf.processamentoinicial.distribuicao.domain.model.ParametroDistribuicao;
import br.jus.stf.processamentoinicial.distribuicao.domain.model.Peticao;
import br.jus.stf.processamentoinicial.distribuicao.domain.model.Processo;
import br.jus.stf.processamentoinicial.distribuicao.domain.model.ProcessoRepository;
import br.jus.stf.processamentoinicial.distribuicao.domain.model.ProcessoSituacao;
import br.jus.stf.processamentoinicial.distribuicao.domain.model.TipoDistribuicao;
import br.jus.stf.processamentoinicial.distribuicao.infra.PeticaoRestAdapter;
import br.jus.stf.processamentoinicial.distribuicao.interfaces.dto.ProcessoDto;
import br.jus.stf.processamentoinicial.distribuicao.interfaces.dto.ProcessoDtoAssembler;
import br.jus.stf.processamentoinicial.distribuicao.interfaces.dto.ProcessoStatusDto;
import br.jus.stf.shared.MinistroId;
import br.jus.stf.shared.ProcessoId;

@Component
public class ProcessoServiceFacade {
	
	@Autowired
	private ProcessoApplicationService processoApplicationService;
	
	@Autowired
	private ProcessoDtoAssembler processoDtoAssembler;
	
	@Autowired
	private ProcessoRepository processoRepository;
	
	@Autowired
	private PeticaoRestAdapter peticaoRestAdapter;

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
	 * @param tipoDistribuicao Tipo de distribuição.
	 * @param peticaoId Id da petição.
	 * @param classeProcessual Classe do processo.
	 * @param justificativa Justificativa da distribuição.
	 * @param ministrosCandidatos Lista de possíveis relatores.
	 * @param ministrosImpedidos Lista de ministros impedidos.
	 * @param processosPreventos Lista de processos preventos.
	 */
	public ProcessoDto distribuir(String tipoDistribuicao, Long peticaoId, String justificativa,
			Set<Long> ministrosCandidatos, Set<Long> ministrosImpedidos, Set<Long> processosPreventos) {
		Peticao peticao = peticaoRestAdapter.consultar(peticaoId);
		String usuarioCadastramento = SecurityContextUtil.getUsername();
		TipoDistribuicao tipo = TipoDistribuicao.valueOf(tipoDistribuicao); 
		ParametroDistribuicao parametroDistribuicao = new ParametroDistribuicao(peticao, justificativa, usuarioCadastramento,
				this.carregarMinistros(ministrosCandidatos), this.carregarMinistros(ministrosImpedidos), this.carregarProcessos(processosPreventos));
		Processo processo = processoApplicationService.distribuir(tipo, parametroDistribuicao);
		
		return processoDtoAssembler.toDto(processo);
	}
	
	private Set<MinistroId> carregarMinistros(Set<Long> listaMinistros) {
		if (!Optional.ofNullable(listaMinistros).isPresent()) {
			return null;
		}
		
		return listaMinistros.stream()
				.map(id -> new MinistroId(id))
				.collect(Collectors.toSet());
	}
	
	private Set<Processo> carregarProcessos(Set<Long> listaProcessos) {
		if (!Optional.ofNullable(listaProcessos).isPresent()) {
			return null;
		}

		return listaProcessos.stream()
				.map(id -> processoRepository.findOne(new ProcessoId(id)))
				.collect(Collectors.toSet());
	}

	/**
	 * Retorna a lista de status atribuídos a um processo.
	 * 
	 * @return Lista de status de processos.
	 */
	public List<ProcessoStatusDto> consultarStatus() {
		
		List<ProcessoStatusDto> statusProcesso = new ArrayList<ProcessoStatusDto>();
		
		for (ProcessoSituacao p : ProcessoSituacao.values()) {
			statusProcesso.add(new ProcessoStatusDto(p.name(), p.descricao()));
		}
		
		return statusProcesso.stream().sorted((s1, s2) -> s1.getNome().compareTo(s2.getNome())).collect(Collectors.toList());
    }
}
