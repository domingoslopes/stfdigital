package br.jus.stf.processamentoinicial.recursaledistribuicao.interfaces.facade;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.plataforma.shared.security.SecurityContextUtil;
import br.jus.stf.processamentoinicial.recursaledistribuicao.application.ProcessoApplicationService;
import br.jus.stf.processamentoinicial.recursaledistribuicao.domain.model.ParametroDistribuicao;
import br.jus.stf.processamentoinicial.recursaledistribuicao.domain.model.PecaProcesso;
import br.jus.stf.processamentoinicial.recursaledistribuicao.domain.model.Processo;
import br.jus.stf.processamentoinicial.recursaledistribuicao.domain.model.ProcessoRepository;
import br.jus.stf.processamentoinicial.recursaledistribuicao.domain.model.ProcessoSituacao;
import br.jus.stf.processamentoinicial.recursaledistribuicao.domain.model.TipoDistribuicao;
import br.jus.stf.processamentoinicial.recursaledistribuicao.infra.PeticaoRestAdapter;
import br.jus.stf.processamentoinicial.recursaledistribuicao.interfaces.commands.PecaProcessual;
import br.jus.stf.processamentoinicial.recursaledistribuicao.interfaces.dto.PecaProcessoDto;
import br.jus.stf.processamentoinicial.recursaledistribuicao.interfaces.dto.PecaProcessoDtoAssembler;
import br.jus.stf.processamentoinicial.recursaledistribuicao.interfaces.dto.ProcessoDto;
import br.jus.stf.processamentoinicial.recursaledistribuicao.interfaces.dto.ProcessoDtoAssembler;
import br.jus.stf.processamentoinicial.recursaledistribuicao.interfaces.dto.ProcessoStatusDto;
import br.jus.stf.processamentoinicial.suporte.domain.model.Peca;
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
	
	@Autowired
	private PeticaoRestAdapter peticaoRestAdapter;
	
	@Autowired
	private PecaProcessoDtoAssembler pecaProcessoDtoAssembler; 

	/**
	 * Consulta um processo judicial, dado o seu identificador primário
	 * 
	 * @param id o código de identificação do processo
	 * @return dto o DTO com as informações de retorno do processo
	 */
	public ProcessoDto consultar(Long id){
		Processo processo = carregarProcesso(id);
		return processoDtoAssembler.toDto(processo);
	}

	/**
	 * Retorna as peças de um processo.
	 * 
	 * @param id Id do processo.
	 * @return Lista de peças;
	 */
	public List<PecaProcessoDto> consultarPecas(Long id){
		ProcessoId processoId = new ProcessoId(id);
		Processo processo = Optional.ofNullable(processoRepository.findOne(processoId)).orElseThrow(IllegalArgumentException::new);
		List<PecaProcessoDto> pecas = new LinkedList<PecaProcessoDto>(); 
		processo.pecas().forEach(p -> pecas.add(pecaProcessoDtoAssembler.toDto(p)));
		
		return pecas;
	}
	
	/**
	 * Distribui um processo para um ministro relator.
	 * 
	 * @param tipoDistribuicao Tipo de distribuição.
	 * @param peticao Id da petição.
	 * @param classeProcessual Classe do processo.
	 * @param justificativa Justificativa da distribuição.
	 * @param ministrosCandidatos Lista de possíveis relatores.
	 * @param ministrosImpedidos Lista de ministros impedidos.
	 * @param processosPreventos Lista de processos preventos.
	 */
	public ProcessoDto distribuir(String tipoDistribuicao, Long peticao, String justificativa,
			Set<Long> ministrosCandidatos, Set<Long> ministrosImpedidos, Set<Long> processosPreventos) {
		PeticaoId peticaoId = new PeticaoId(peticao);
		String usuarioCadastramento = SecurityContextUtil.getUser().getUsername();
		TipoDistribuicao tipo = TipoDistribuicao.valueOf(tipoDistribuicao);
		
		ParametroDistribuicao parametroDistribuicao = new ParametroDistribuicao(tipo, peticaoId, justificativa, usuarioCadastramento,
				this.carregarMinistros(ministrosCandidatos), this.carregarMinistros(ministrosImpedidos), this.carregarProcessos(processosPreventos));
		Processo processo = processoApplicationService.distribuir(parametroDistribuicao);
		
		return processoDtoAssembler.toDto(processo);
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
	
	/**
	 * Cadastra um processo recursal
	 * 
	 * @return processoId
	 */
	public Long cadastrarRecursal(Long peticaoId) {
		PeticaoId peticao = new PeticaoId(peticaoId);
		Processo processo = processoApplicationService.cadastrarRecursal(peticao);
		return processo.id().toLong();
	}

	/**
	 * Consulta um processo associado a uma petição.
	 * 
	 * @param id
	 * @return
	 */
	public ProcessoDto consultarPelaPeticao(Long id) {
		return processoDtoAssembler.toDto(processoRepository.findByPeticao(new PeticaoId(id)));
	}
	
	/**
	 * Insere peças processuais.
	 * 
	 * @param processoId ID do processo.
	 * @param pecas Conjunto de peças processuais a serem inseridas.
	 */
	public void inserirPecas(Long processoId, List<PecaProcessual> pecas){
		Processo processo = processoRepository.findOne(new ProcessoId(processoId));
		processoApplicationService.inserirPecas(processo, pecas);
	}
	
	/**
	 * Exclui peças processuais.
	 * @param processoId Id do processo.
	 * @param pecas Lista de peças.
	 */
	public void excluirPecas(Long processoId, List<Long> pecas){
		Processo processo = processoRepository.findOne(new ProcessoId(processoId));
		List<Peca> pecasProcesso = new LinkedList<Peca>(); 
		pecas.forEach(pecaId -> pecasProcesso.add(processoRepository.findOnePeca(pecaId)));
		
		processoApplicationService.excluirPecas(processo, pecasProcesso);
	}
	
	/**
	 * Atribui a lista de peças com nova organização para um processo.
	 * 
	 * @param processoId
	 * @param pecasOrganizadas
	 * @param concluirTarefa
	 * @return
	 */
	public void organizarPecas(Long processoId, List<Long> pecasOrganizadas, boolean concluirTarefa) {
		Processo processo = carregarProcesso(processoId);
		
		processoApplicationService.organizarPecas(processo, pecasOrganizadas, concluirTarefa);
	}
	
	/**
	 * Verifica se um processo possui peças.
	 * 
	 * @param processoId
	 * @return
	 */
	public boolean temPecas(String processoId) {
		Processo processo = processoRepository.findByPeticao(new PeticaoId(Long.valueOf(processoId)));
		
		return !processo.pecas().isEmpty();
	}
	
	private Set<MinistroId> carregarMinistros(Set<Long> listaMinistros) {
		return Optional.ofNullable(listaMinistros)
				.map(lista -> lista.stream()
					.map(id -> new MinistroId(id))
					.collect(Collectors.toSet()))
				.orElse(Collections.emptySet());
	}
	
	private Set<Processo> carregarProcessos(Set<Long> listaProcessos) {
		return Optional.ofNullable(listaProcessos)
				.map(lista -> lista.stream()
						.map(id -> processoRepository.findOne(new ProcessoId(id)))
						.collect(Collectors.toSet()))
				.orElse(Collections.emptySet());
	}
	
	private Processo carregarProcesso(Long id) {
	    ProcessoId processoId = new ProcessoId(id);
		return Optional.ofNullable(processoRepository.findOne(processoId)).orElseThrow(IllegalArgumentException::new);
    }
	
	/**
	 * Divide uma peça processual.
	 * @param processoId Id do processo.
	 * @param pecas Lista de peças.
	 */
	public void dividirPeca(Long processoId, Long pecaOriginalId, List<PecaProcessual> novasPecas){
		PecaProcesso pecaOriginal = (PecaProcesso)processoRepository.findOnePeca(pecaOriginalId);
		Processo processo = processoRepository.findOne(new ProcessoId(processoId));
		List<Range<Integer>> intervalos = new LinkedList<Range<Integer>>();
		novasPecas.forEach(peca -> intervalos.add(Range.between(peca.getPaginaInicial(), peca.getPaginaFinal())));
				
		processoApplicationService.dividirPeca(processo, pecaOriginal, intervalos, novasPecas);
	}
	
	/**
	 * Uni peças processuais.
	 * @param processoId Id do processo.
	 * @param pecas Lista de peças.
	 */
	public void unirPecas(Long processoId, List<Long> pecasParaUniao){
		List<PecaProcesso> pecas = new LinkedList<PecaProcesso>();
		pecasParaUniao.forEach(p -> pecas.add((PecaProcesso)processoRepository.findOnePeca(p)));
		Processo processo = processoRepository.findOne(new ProcessoId(processoId));
			
		processoApplicationService.unirPecas(processo, pecas);
	}
	
	/**
	 * Permite a edição de uma peça.
	 * @param pecaId Id da peça.
	 * @param tipoPecaId Id do tipo da peça.
	 * @param descricao Descrição da peça.
	 * @param numeroOrdem Nº de ordem da peça.
	 * @param visibilidade Visibilidade da peça.
	 */
	public void editarPeca(Long pecaId, Long tipoPecaId, String descricao, Long numeroOrdem, String visibilidade){
		Peca peca = processoRepository.findOnePeca(pecaId);
		processoApplicationService.editarPeca(peca);
	}
}
