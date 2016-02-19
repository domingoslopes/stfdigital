package br.jus.stf.processamentoinicial.recursaledistribuicao.interfaces.actions;

import org.springframework.beans.factory.annotation.Autowired;

import br.jus.stf.jurisprudencia.controletese.domain.model.AssuntoRepository;
import br.jus.stf.plataforma.shared.actions.annotation.ActionController;
import br.jus.stf.plataforma.shared.actions.annotation.ActionMapping;
import br.jus.stf.processamentoinicial.autuacao.domain.PessoaAdapter;
import br.jus.stf.processamentoinicial.autuacao.interfaces.facade.PeticaoServiceFacade;
import br.jus.stf.processamentoinicial.recursaledistribuicao.application.ProcessoApplicationService;
import br.jus.stf.processamentoinicial.recursaledistribuicao.interfaces.commands.AnalisarPressupostosFormaisCommand;
import br.jus.stf.processamentoinicial.recursaledistribuicao.interfaces.commands.AnalisarRepercussaoGeralCommand;
import br.jus.stf.processamentoinicial.recursaledistribuicao.interfaces.commands.AutuarProcessoCriminalEleitoralCommand;
import br.jus.stf.processamentoinicial.recursaledistribuicao.interfaces.commands.AutuarProcessoRecursalCommand;
import br.jus.stf.processamentoinicial.recursaledistribuicao.interfaces.commands.DistribuirPeticaoCommand;
import br.jus.stf.processamentoinicial.recursaledistribuicao.interfaces.commands.ExcluirPecasCommand;
import br.jus.stf.processamentoinicial.recursaledistribuicao.interfaces.commands.OrganizarPecasCommand;
import br.jus.stf.processamentoinicial.recursaledistribuicao.interfaces.commands.SalvarPecasCommand;
import br.jus.stf.processamentoinicial.recursaledistribuicao.interfaces.dto.ProcessoDto;
import br.jus.stf.processamentoinicial.recursaledistribuicao.interfaces.facade.ProcessoServiceFacade;

@ActionController(groups = "processo")
public class ProcessoActionsResource {

	@Autowired
	private ProcessoServiceFacade processoServiceFacade;
	
	@Autowired
	private ProcessoApplicationService processoApplicationService;
	
	@Autowired
	private AssuntoRepository assuntoRepository;
	
	@Autowired
	private PessoaAdapter pessoaAdapter;
	
	@Autowired
	private PeticaoServiceFacade peticaoServiceFacade;
	
	@ActionMapping(id = "autuar-recursal-criminal-eleitoral", name = "Autuar Petição Física Recursal Criminal Eleitoral")
	public void autuarRecursalCriminalEleitoral(AutuarProcessoCriminalEleitoralCommand command) {
		processoApplicationService.autuar(command.getProcessoId(), command.getAssuntos(), command.getPartesPoloAtivo(), command.getPartesPoloPassivo());
	}
	
	@ActionMapping(id = "analisar-pressupostos-formais", name = "Analisar Pressupostos Formais")
	public void analisarPressupostosFormais(AnalisarPressupostosFormaisCommand command) {
		processoApplicationService.analisarPressupostosFormais(command.getProcessoId(), command.getClassificacao().toUpperCase(), 
				command.getMotivos(), command.getObservacao(), false);
	}
	
	@ActionMapping(id = "revisar-processo-inapto", name = "Revisar Processo Inapto")
	public void revisarPressupostosFormais(AnalisarPressupostosFormaisCommand command) {
		processoApplicationService.analisarPressupostosFormais(command.getProcessoId(), command.getClassificacao().toUpperCase(), 
				command.getMotivos(), command.getObservacao(), true);
	}
	
	@ActionMapping(id = "analisar-repercussao-geral", name = "Analisar Repercussão Geral")
	public void analisarRepercussaoGeral(AnalisarRepercussaoGeralCommand command) {
		processoApplicationService.analisarRepercussaoGeral(command.getProcessoId(), command.getAssuntos(), command.getTeses(), command.getObservacao(), false);
	}
	
	@ActionMapping(id = "revisar-repercussao-geral", name = "Revisar Repercussão Geral")
	public void revisarRepercussaoGeral(AnalisarRepercussaoGeralCommand command) {
		processoApplicationService.analisarRepercussaoGeral(command.getProcessoId(), command.getAssuntos(), command.getTeses(), command.getObservacao(), true);
	}
	
	@ActionMapping(id = "autuar-recursal", name = "Autuar Processo Recursal")
	public void autuar(AutuarProcessoRecursalCommand command) {
		processoApplicationService.autuar(command.getProcessoId(), command.getAssuntos(), command.getPartesPoloAtivo(), command.getPartesPoloPassivo());
	}
	
	@ActionMapping(id = "distribuir-processo", name = "Distribuir Processo")
	public ProcessoDto distribuir(DistribuirPeticaoCommand command) {
		return processoServiceFacade.distribuir(command.getTipoDistribuicao(), command.getPeticaoId(), command.getJustificativa(),
				command.getMinistrosCandidatos(), command.getMinistrosImpedidos(), command.getProcessosPreventos());
	}
	
	@ActionMapping(id = "inserir-pecas", name = "Inserir Peçad Processual")
	public void inserirPecas(SalvarPecasCommand command) {
		processoServiceFacade.inserirPecas(command.getProcessoId(), command.getPecas());
	}
	
	@ActionMapping(id = "excluir-pecas", name = "Excluir Peças Processuais")
	public void excluirPecas(ExcluirPecasCommand command) {
		processoServiceFacade.excluirPecas(command.getProcessoId(), command.getPecas());
	}
	
	@ActionMapping(id = "organizar-pecas", name = "Organizar Peças")
	public void organizarPecas(OrganizarPecasCommand command) {
		processoServiceFacade.organizarPecas(command.getProcessoId(), command.getPecasOrganizadas(), command.isConcluirTarefa());
	}
	
}
