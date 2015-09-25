package br.jus.stf.processamentoinicial.autuacao.domain.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.apache.commons.lang3.Validate;

import br.jus.stf.shared.ClasseId;
import br.jus.stf.shared.PeticaoId;

/**
 * @author Lucas.Rodrigues
 *
 */
@Entity
@DiscriminatorValue("FISICO")
public class PeticaoFisica extends Peticao {

	@Column(name = "QTD_VOLUME")
	private Integer volumes;
	
	@Column(name = "QTD_APENSO")
	private Integer apensos;
	
	@Column(name = "TIP_FORMA_RECEBIMENTO")
	@Enumerated(EnumType.STRING)
	private FormaRecebimento formaRecebimento;
	
	@Column(name = "NUM_SEDEX")
	private String numeroSedex;
	
	public PeticaoFisica(final PeticaoId id, final Long numero, final Integer volumes, 
			final Integer apensos, final FormaRecebimento formaRecebimento, final String numeroSedex) {
		super(id, numero);
		
		Validate.isTrue(volumes != null && volumes > 0);
		Validate.notNull(apensos);
		Validate.notNull(formaRecebimento);
		Validate.isTrue(formaRecebimento != FormaRecebimento.SEDEX ||
				!Validate.notBlank(numeroSedex).isEmpty());
		
		this.volumes = volumes;
		this.apensos = apensos;
		this.formaRecebimento = formaRecebimento;
		this.numeroSedex = numeroSedex;
	}
	
	public Integer volumes() {
		return volumes;
	}
	
	public Integer apensos() {
		return apensos;
	}
	
	public FormaRecebimento formaRecebimento() {
		return formaRecebimento;
	}
	
	public String numeroSedex() {
		return numeroSedex;
	}
	
	public void preautuar(final ClasseId classeSugerida) {
		Validate.notNull(classeSugerida, "peticao.classeSugerida.required");
		
		/*if (!statusAtual.equals(PeticaoStatus.A_PREAUTUAR)) {
			throw new IllegalStateException("peticao.status.invalid");
		}*/
		super.sugerirClasse(classeSugerida);
	}
	
	//Hibernate
	
	PeticaoFisica() {
		
	}
	
}
