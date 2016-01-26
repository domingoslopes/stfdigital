package br.jus.stf.processamentoinicial.autuacao.interfaces.actions;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import br.jus.stf.jurisprudencia.controletese.domain.model.AssuntoRepository;
import br.jus.stf.plataforma.shared.actions.annotation.ActionController;
import br.jus.stf.plataforma.shared.actions.annotation.ActionMapping;
import br.jus.stf.processamentoinicial.autuacao.domain.PessoaAdapter;
import br.jus.stf.processamentoinicial.autuacao.interfaces.commands.PreautuarPeticaoFisicaCommand;
import br.jus.stf.processamentoinicial.autuacao.interfaces.facade.PeticaoServiceFacade;
import br.jus.stf.processamentoinicial.recursaledistribuicao.application.ProcessoApplicationService;
import br.jus.stf.processamentoinicial.recursaledistribuicao.domain.model.ParteProcesso;
import br.jus.stf.processamentoinicial.recursaledistribuicao.interfaces.commands.AutuarProcessoCriminalEleitoralCommand;
import br.jus.stf.processamentoinicial.suporte.domain.model.TipoPolo;
import br.jus.stf.shared.AssuntoId;
import br.jus.stf.shared.PessoaId;
import br.jus.stf.shared.ProcessoId;

/**
 * @author Anderson.Araujo
 * 
 * @since 18.01.2016
 *
 */
@ActionController(groups="recursal")
public class PeticaoRecursalActionsResource {

	@Autowired
	private PeticaoServiceFacade peticaoServiceFacade;
	
	@Autowired
	private ProcessoApplicationService processoApplicationService;
	
	@Autowired
	private AssuntoRepository assuntoRepository;
	
	@Autowired
	private PessoaAdapter pessoaAdapter;
	
	@ActionMapping(id = "preautuar-recursal", name = "Preautuar Petição Física Recursal")
	public void preautuar(PreautuarPeticaoFisicaCommand command) {
		peticaoServiceFacade.preautuar(command.getPeticaoId(), command.getClasseId(), command.isValida(), command.getMotivo(), command.getPreferencias());
	}
	
	@ActionMapping(id = "autuar-recursal-criminal-eleitoral", name = "Autuar Petição Física Recursal Criminal Eleitoral")
	public void autuarRecursalCriminalEleitoral(AutuarProcessoCriminalEleitoralCommand command) {
		ProcessoId processoId = new ProcessoId(command.getProcessoId());
		Set<AssuntoId> assuntos = command.getAssuntos().stream().map(id -> new AssuntoId(id)).collect(Collectors.toSet());
		Set<ParteProcesso> partesPoloAtivo = new HashSet<ParteProcesso>();
		Set<ParteProcesso> partesPoloPassivo = new HashSet<ParteProcesso>();
		
		adicionarPartes(partesPoloAtivo, command.getPartesPoloAtivo(), TipoPolo.POLO_ATIVO);
		adicionarPartes(partesPoloPassivo, command.getPartesPoloPassivo(), TipoPolo.POLO_PASSIVO);		
		
		processoApplicationService.autuar(processoId, assuntos, partesPoloAtivo, partesPoloPassivo);
	}
	
	/**
	 * Cria as partes do processo recursal.
	 * 
	 * @param partes Conjunto de partes.
	 * @param polo Lista de partes.
	 * @param tipo Tipo de polo.
	 * 
	 */
	private void adicionarPartes(Set<ParteProcesso> partes, List<String> polo, TipoPolo tipo) {
		Set<PessoaId> pessoas = pessoaAdapter.cadastrarPessoas(polo);
		pessoas.forEach(pessoa -> partes.add(new ParteProcesso(pessoa, tipo)));
	}
	
}
