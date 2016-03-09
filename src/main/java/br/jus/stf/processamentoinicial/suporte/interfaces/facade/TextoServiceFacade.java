package br.jus.stf.processamentoinicial.suporte.interfaces.facade;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.processamentoinicial.suporte.application.TextoApplicationService;
import br.jus.stf.processamentoinicial.suporte.domain.model.Texto;
import br.jus.stf.processamentoinicial.suporte.interfaces.commands.SubstituicaoTagTexto;
import br.jus.stf.processamentoinicial.suporte.interfaces.dto.TextoDto;
import br.jus.stf.shared.ModeloId;
import br.jus.stf.shared.PeticaoId;

/**
 * Façade para serviços de Texto.
 * 
 * @author Tomas.Godoi
 *
 */
@Component
public class TextoServiceFacade {

	@Autowired
	private TextoApplicationService textoApplicationService;

	public TextoDto gerarTextoDePeticao(Long peticaoId, Long modeloId, List<SubstituicaoTagTexto> substituicoes) {
		Texto texto = textoApplicationService.gerarTextoDePeticao(new PeticaoId(peticaoId), new ModeloId(modeloId),
		        substituicoes);
		return new TextoDto(texto.id().toLong(), texto.documento().toLong());
	}

}
