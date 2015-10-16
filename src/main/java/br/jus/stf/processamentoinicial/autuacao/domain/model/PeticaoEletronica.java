package br.jus.stf.processamentoinicial.autuacao.domain.model;

import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.apache.commons.lang3.Validate;

import br.jus.stf.shared.ClasseId;
import br.jus.stf.shared.PeticaoId;

/**
 * @author Lucas Rodrigues
 */
@Entity
@DiscriminatorValue("ELETRONICO")
public class PeticaoEletronica extends Peticao {

	@ManyToOne
	@JoinColumn(name = "SEQ_ORGAO_REPRESENTADO", referencedColumnName = "SEQ_ORGAO")
	private Orgao orgaoRepresentado;

	PeticaoEletronica() {

	}

	public PeticaoEletronica(final PeticaoId id, final Long numero,
			final ClasseId classeSugerida, final Set<PartePeticao> partes,
			final Set<PecaPeticao> pecas) {
		super(id, numero);

		Validate.notNull(classeSugerida, "peticao.classeSugerida.required");
		Validate.notEmpty(partes, "peticao.partes.notEmpty");
		Validate.notEmpty(pecas, "peticao.pecas.notEmpty");

		super.sugerirClasse(classeSugerida);
		partes.forEach(parte -> super.adicionarParte(parte));
		pecas.forEach(peca -> super.adicionarPeca(peca));
	}

	public PeticaoEletronica(final PeticaoId id, final Long numero,
			final ClasseId classeSugerida, final Set<PartePeticao> partes,
			final Set<PecaPeticao> pecas, final Orgao orgaoRepresentado) {
		this(id, numero, classeSugerida, partes, pecas);
		
		Validate.notNull(orgaoRepresentado,
				"peticao.orgaoRepresentado.required");

		this.orgaoRepresentado = orgaoRepresentado;
	}

	public Orgao orgaoRepresentado() {
		return this.orgaoRepresentado;
	}

	@Override
	public boolean hasRepresentacao() {
		return (this.orgaoRepresentado != null);
	}

}
