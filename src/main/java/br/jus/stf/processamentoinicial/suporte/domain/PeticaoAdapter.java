package br.jus.stf.processamentoinicial.suporte.domain;

import br.jus.stf.shared.PeticaoId;
import br.jus.stf.shared.TextoId;

public interface PeticaoAdapter {

	void associarTextoDevolucao(PeticaoId peticaoId, TextoId id);

}
