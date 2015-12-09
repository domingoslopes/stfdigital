package br.jus.stf.processamentoinicial.autuacao.infra.actions;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.plataforma.shared.actions.handler.ActionConditionHandler;
import br.jus.stf.processamentoinicial.autuacao.domain.model.PeticaoRepository;
import br.jus.stf.processamentoinicial.autuacao.domain.model.PeticaoStatus;
import br.jus.stf.processamentoinicial.autuacao.interfaces.actions.FiltrarPeticaoPorStatus;
import br.jus.stf.processamentoinicial.autuacao.interfaces.commands.AssinarDevolucaoPeticaoCommand;
import br.jus.stf.shared.PeticaoId;

@Component
public class FiltrarPeticaoPorStatusHandler implements ActionConditionHandler<FiltrarPeticaoPorStatus, AssinarDevolucaoPeticaoCommand> {

	@Autowired
	private PeticaoRepository peticaoRepository;
	
	@Override
	public boolean matches(FiltrarPeticaoPorStatus annotation, List<AssinarDevolucaoPeticaoCommand> resources) {
		List<PeticaoId> peticaoIds = resources.stream().map(c -> new PeticaoId(c.getPeticaoId())).collect(Collectors.toList());
		PeticaoStatus status = annotation.value();
		return peticaoRepository.estaoNoMesmoStatus(peticaoIds, status);
	}

	@Override
	public Class<FiltrarPeticaoPorStatus> annotation() {
		return FiltrarPeticaoPorStatus.class;
	}

}
