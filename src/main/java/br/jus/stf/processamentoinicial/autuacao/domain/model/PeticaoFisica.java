package br.jus.stf.processamentoinicial.autuacao.domain.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.apache.commons.lang3.StringUtils;
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

	@Column(name = "QTD_VOLUME", nullable = false)
	private Integer volumes;
	
	@Column(name = "QTD_APENSO", nullable = false)
	private Integer apensos;
	
	@Column(name = "TIP_FORMA_RECEBIMENTO", nullable = false)
	@Enumerated(EnumType.STRING)
	private FormaRecebimento formaRecebimento;
	
	@Column(name = "NUM_SEDEX")
	private String numeroSedex;
	
	PeticaoFisica() {

	}
	
	public PeticaoFisica(final PeticaoId id, final Long numero, final String usuarioCadastramento, final Integer volumes, 
			final Integer apensos, final FormaRecebimento formaRecebimento, final String numeroSedex) {
		super(id, numero, usuarioCadastramento);
		
		Validate.isTrue(volumes != null && volumes > 0, "peticaoFisica.volumes.maiorQueZero");
		Validate.isTrue(apensos != null && apensos >= 0, "peticaoFisica.apensos.maiorIgualAZero");
		Validate.notNull(formaRecebimento, "peticaoFisica.formaRecebimento.required");
		Validate.isTrue((formaRecebimento != FormaRecebimento.SEDEX && StringUtils.isBlank(numeroSedex)) ||
						(formaRecebimento == FormaRecebimento.SEDEX && !StringUtils.isBlank(numeroSedex)),
						"peticaoFisica.formaRecebimento.invalid");
		
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
		
		super.sugerirClasse(classeSugerida);
	}
		
	@Override
	public boolean hasRepresentacao() {
		return false;
	}
	
}
