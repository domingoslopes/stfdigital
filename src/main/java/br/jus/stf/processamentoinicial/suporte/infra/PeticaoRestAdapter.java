package br.jus.stf.processamentoinicial.suporte.infra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.processamentoinicial.autuacao.interfaces.PeticaoRestResource;
import br.jus.stf.processamentoinicial.suporte.domain.PeticaoAdapter;
import br.jus.stf.shared.ModeloId;
import br.jus.stf.shared.PeticaoId;
import br.jus.stf.shared.TextoId;

@Component("suportePeticaoRestAdapter")
public class PeticaoRestAdapter implements PeticaoAdapter {

	@Autowired
	private PeticaoRestResource peticaoRestResource;
	
	@Override
	public void associarTextoDevolucao(PeticaoId peticaoId, TextoId textoId, ModeloId modeloId) {
		peticaoRestResource.associarTextoDevolucao(peticaoId.toLong(), textoId.toLong(), modeloId.toLong());
	}

}
