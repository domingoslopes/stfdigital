package br.jus.stf.processamentoinicial.recursaledistribuicao.interfaces.actions;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import br.jus.stf.jurisprudencia.controletese.domain.model.AssuntoRepository;
import br.jus.stf.plataforma.shared.actions.annotation.ActionController;
import br.jus.stf.plataforma.shared.actions.annotation.ActionMapping;
import br.jus.stf.plataforma.shared.actions.support.ResourcesMode;
import br.jus.stf.processamentoinicial.autuacao.domain.PessoaAdapter;
import br.jus.stf.processamentoinicial.autuacao.interfaces.facade.PeticaoServiceFacade;
import br.jus.stf.processamentoinicial.recursaledistribuicao.application.ProcessoApplicationService;
import br.jus.stf.processamentoinicial.recursaledistribuicao.interfaces.commands.AnalisarPressupostosFormaisCommand;
import br.jus.stf.processamentoinicial.recursaledistribuicao.interfaces.commands.AnalisarRepercussaoGeralCommand;
import br.jus.stf.processamentoinicial.recursaledistribuicao.interfaces.commands.AutuarProcessoCriminalEleitoralCommand;
import br.jus.stf.processamentoinicial.recursaledistribuicao.interfaces.commands.AutuarProcessoRecursalCommand;
import br.jus.stf.processamentoinicial.recursaledistribuicao.interfaces.commands.DistribuirPeticaoCommand;
import br.jus.stf.processamentoinicial.recursaledistribuicao.interfaces.commands.GerarAcronimosPartesCommand;
import br.jus.stf.processamentoinicial.recursaledistribuicao.interfaces.dto.AcronimoParteDto;
import br.jus.stf.processamentoinicial.recursaledistribuicao.interfaces.dto.AcronimoParteDtoAssembler;
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
	
	@Autowired
	private AcronimoParteDtoAssembler acronimoParteDtoAssembler;
	
	@ActionMapping(id = "autuar-recursal-criminal-eleitoral", name = "Autuar Petição Física Recursal Criminal/Eleitoral", resourcesMode = ResourcesMode.One)
	public void autuarRecursalCriminalEleitoral(AutuarProcessoCriminalEleitoralCommand command) {
		processoApplicationService.autuar(command.getProcessoId(), command.getAssuntos(), command.getPartesPoloAtivo(), command.getPartesPoloPassivo());
	}
	
	@ActionMapping(id = "analisar-pressupostos-formais", name = "Analisar Pressupostos Formais", resourcesMode = ResourcesMode.One)
	public void analisarPressupostosFormais(AnalisarPressupostosFormaisCommand command) {
		processoApplicationService.analisarPressupostosFormais(command.getProcessoId(), command.getClassificacao().toUpperCase(), 
				command.getMotivos(), command.getObservacao(), false);
	}
	
	@ActionMapping(id = "revisar-processo-inapto", name = "Revisar Processo Inapto", resourcesMode = ResourcesMode.One)
	public void revisarPressupostosFormais(AnalisarPressupostosFormaisCommand command) {
		processoApplicationService.analisarPressupostosFormais(command.getProcessoId(), command.getClassificacao().toUpperCase(), 
				command.getMotivos(), command.getObservacao(), true);
	}
	
	@ActionMapping(id = "analisar-repercussao-geral", name = "Analisar Repercussão Geral", resourcesMode = ResourcesMode.One)
	public void analisarRepercussaoGeral(AnalisarRepercussaoGeralCommand command) {
		processoApplicationService.analisarRepercussaoGeral(command.getProcessoId(), command.getAssuntos(), command.getTeses(), command.getObservacao(), false);
	}
	
	@ActionMapping(id = "revisar-repercussao-geral", name = "Revisar Repercussão Geral", resourcesMode = ResourcesMode.One)
	public void revisarRepercussaoGeral(AnalisarRepercussaoGeralCommand command) {
		processoApplicationService.analisarRepercussaoGeral(command.getProcessoId(), command.getAssuntos(), command.getTeses(), command.getObservacao(), true);
	}
	
	@ActionMapping(id = "autuar-recursal", name = "Autuar Processo Recursal", resourcesMode = ResourcesMode.One)
	public void autuar(AutuarProcessoRecursalCommand command) {
		processoApplicationService.autuar(command.getProcessoId(), command.getAssuntos(), command.getPartesPoloAtivo(), command.getPartesPoloPassivo());
	}
	
	@ActionMapping(id = "distribuir-processo", name = "Distribuir Processo", resourcesMode = ResourcesMode.One)
	public ProcessoDto distribuir(DistribuirPeticaoCommand command) {
		return processoServiceFacade.distribuir(command.getTipoDistribuicao(), command.getPeticaoId(), command.getJustificativa(),
				command.getMinistrosCandidatos(), command.getMinistrosImpedidos(), command.getProcessosPreventos());
	}
	
	@ActionMapping(id = "gerar-acronimos", name = "Gerar Acrônimos", resourcesMode = ResourcesMode.Many)
	public Map<String, List<AcronimoParteDto>> gerarAcronimoPartes(GerarAcronimosPartesCommand command){
		
		Map<String, List<AcronimoParteDto>> acronimos = new LinkedHashMap<String, List<AcronimoParteDto>>();
		List<AcronimoParteDto> acronimosPoloAtivo = new LinkedList<AcronimoParteDto>();
		List<AcronimoParteDto> acronimosPoloPassivo = new LinkedList<AcronimoParteDto>();
		List<AcronimoParteDto> acronimosPoloInteressados = new LinkedList<AcronimoParteDto>();
		
		if (command.getPartesPoloAtivo() != null && command.getPartesPoloAtivo().size() > 0){
			command.getPartesPoloAtivo().forEach(ppa -> acronimosPoloAtivo.add(acronimoParteDtoAssembler.toDto(ppa)));
		}
		
		if (command.getPartesPoloPassivo() != null && command.getPartesPoloPassivo().size() > 0){
			command.getPartesPoloPassivo().forEach(ppp -> acronimosPoloPassivo.add(acronimoParteDtoAssembler.toDto(ppp)));
		}
		
		if (command.getPartesPoloInteressados() != null && command.getPartesPoloInteressados().size() > 0){
			command.getPartesPoloInteressados().forEach(ppi -> acronimosPoloInteressados.add(acronimoParteDtoAssembler.toDto(ppi)));
		}
		
		acronimos.put("PoloAtivo", acronimosPoloAtivo);
		acronimos.put("PoloPassivo", acronimosPoloPassivo);
		acronimos.put("PoloPassivo", acronimosPoloPassivo);
		
		return acronimos;
	}
}
