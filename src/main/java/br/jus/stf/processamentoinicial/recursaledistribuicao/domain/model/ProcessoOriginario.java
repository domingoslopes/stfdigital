package br.jus.stf.processamentoinicial.recursaledistribuicao.domain.model;

import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import br.jus.stf.processamentoinicial.suporte.domain.model.TipoProcesso;
import br.jus.stf.shared.ClasseId;
import br.jus.stf.shared.PeticaoId;
import br.jus.stf.shared.PreferenciaId;
import br.jus.stf.shared.ProcessoId;

/**
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 13.01.2016
 */
@Entity
@DiscriminatorValue("ORIGINARIO")
public class ProcessoOriginario extends Processo {

	ProcessoOriginario() {

	}
	
	public ProcessoOriginario(final ProcessoId id, final ClasseId classe, final Long numero, final PeticaoId peticao, final Set<ParteProcesso> partes, final Set<PecaProcesso> pecas, final Set<PreferenciaId> preferencias) {
		super(id, classe, numero, peticao, partes, pecas, pecas.isEmpty() ? ProcessoSituacao.PECAS_ORGANIZADAS : ProcessoSituacao.DISTRIBUIDO, preferencias);
	}
	
	public TipoProcesso tipoProcesso() {
		return TipoProcesso.ORIGINARIO;
	}

}
