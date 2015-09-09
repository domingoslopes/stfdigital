package br.jus.stf.autuacao.domain.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.apache.commons.lang3.Validate;

import br.jus.stf.shared.domain.model.ClasseId;

/**
 * @author Lucas.Rodrigues
 *
 */
@Entity
@DiscriminatorValue("F")
public class PeticaoFisica extends Peticao {

	@Column(name = "QTD_VOLUMES")
	private Integer volumes;
	
	@Column(name = "QTD_APENSOS")
	private Integer apensos;
	
	@Column(name = "TIP_FORMA_RECEBI")
	@Enumerated(EnumType.STRING)
	private FormaRecebimento formaRecebimento;
	
	@Column(name = "NUM_SEDEX")
	private String numeroSedex;
	
	PeticaoFisica(final Integer volumes, final Integer apensos, 
			final FormaRecebimento formaRecebimento, final String numeroSedex) {
		
		Validate.isTrue(volumes != null && volumes > 0);
		Validate.notNull(apensos);
		Validate.notNull(formaRecebimento);
		Validate.isTrue(formaRecebimento != FormaRecebimento.SEDEX ||
				!Validate.notBlank(numeroSedex).isEmpty());
		
		this.volumes = volumes;
		this.apensos = apensos;
		this.formaRecebimento = formaRecebimento;
		this.numeroSedex = numeroSedex;
		this.status = PeticaoStatus.A_PREAUTUAR;
	}
	
	public void preautuar() {
		if (!PeticaoStatus.A_PREAUTUAR.equals(status)) {
			throw new IllegalStateException("peticao.preautuar.exception");
		}
		this.status = PeticaoStatus.EM_PREAUTUACAO;
	}
	
	public void sugerirClassificacao(final ClasseId classeSugerida) {
		if (!PeticaoStatus.EM_PREAUTUACAO.equals(status)) {
			throw new IllegalStateException("peticao.preautuacao.exception");
		}
		Validate.notNull(classeSugerida, "peticao.classeSugerida.required");
		
		this.status = PeticaoStatus.A_AUTUAR;
		this.classeSugerida = classeSugerida;
	}
	
}
