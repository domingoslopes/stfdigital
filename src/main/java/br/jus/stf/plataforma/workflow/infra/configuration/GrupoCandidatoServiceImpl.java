package br.jus.stf.plataforma.workflow.infra.configuration;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.jus.stf.plataforma.shared.security.AcessosRestAdapter;
import br.jus.stf.plataforma.shared.security.resource.ResourceType;
import br.jus.stf.plataforma.workflow.domain.model.GrupoCandidatoService;


/**
 * @author Lucas.Rodrigues
 *
 */
@Service("grupoCandidatoService")
public class GrupoCandidatoServiceImpl implements GrupoCandidatoService {

	@Autowired
	private AcessosRestAdapter acessosRestAdapter;
	
	@Override
	public Set<String> grupos(String nomeTarefa) {
		return acessosRestAdapter.carregarPapeisRecurso(nomeTarefa, ResourceType.ACAO.name());
	}
}
