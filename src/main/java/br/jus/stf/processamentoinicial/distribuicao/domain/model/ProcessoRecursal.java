package br.jus.stf.processamentoinicial.distribuicao.domain.model;

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
@DiscriminatorValue("RECURSAL")
public class ProcessoRecursal extends Processo {

	ProcessoRecursal() {

	}
	
	public ProcessoRecursal(final ProcessoId id, final ClasseId classe, final Long numero, final PeticaoId peticao, final ProcessoSituacao situacao, final Set<PreferenciaId> preferencias) {
		super(id, classe, numero, peticao, situacao, preferencias);
	}
	
	public TipoProcesso tipoProcesso() {
		return TipoProcesso.RECURSAL;
	}

}
