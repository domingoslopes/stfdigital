package br.jus.stf.processamentoinicial.autuacao.domain.model;

import java.util.List;
import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.apache.commons.lang3.Validate;

import br.jus.stf.processamentoinicial.suporte.domain.model.MeioTramitacao;
import br.jus.stf.processamentoinicial.suporte.domain.model.TipoProcesso;
import br.jus.stf.shared.ClasseId;
import br.jus.stf.shared.PeticaoId;

/**
 * @author Lucas Rodrigues
 */
@Entity
@DiscriminatorValue("ELETRONICO")
public class PeticaoEletronica extends Peticao {
	
	@Transient
	private final MeioTramitacao meioTramitacao = MeioTramitacao.ELETRONICO;

	@ManyToOne
	@JoinColumn(name = "SEQ_ORGAO_REPRESENTADO", referencedColumnName = "SEQ_ORGAO")
	private Orgao orgaoRepresentado;

	PeticaoEletronica() {

	}

	public PeticaoEletronica(final PeticaoId id, final Long numero, final String usuarioCadastramento,
			final ClasseId classeSugerida, final Set<PartePeticao> partes,
			final List<PecaPeticao> pecas, final TipoProcesso tipoProcesso) {
		super(id, numero, usuarioCadastramento, tipoProcesso);

		Validate.notNull(classeSugerida, "peticao.classeSugerida.required");
		Validate.notEmpty(partes, "peticao.partes.notEmpty");
		Validate.notEmpty(pecas, "peticao.pecas.notEmpty");

		super.sugerirClasse(classeSugerida);
		partes.forEach(parte -> super.adicionarParte(parte));
		pecas.forEach(peca -> super.adicionarPeca(peca));
	}

	public PeticaoEletronica(final PeticaoId id, final Long numero, final String usuarioCadastramento,
			final ClasseId classeSugerida, final Set<PartePeticao> partes,
			final List<PecaPeticao> pecas, final Orgao orgaoRepresentado, final TipoProcesso tipoProcesso) {
		super(id, numero, usuarioCadastramento, tipoProcesso);

		Validate.notNull(classeSugerida, "peticao.classeSugerida.required");
		Validate.notEmpty(partes, "peticao.partes.notEmpty");
		Validate.notEmpty(pecas, "peticao.pecas.notEmpty");
		Validate.notNull(orgaoRepresentado,
				"peticao.orgaoRepresentado.required");

		super.sugerirClasse(classeSugerida);
		partes.forEach(parte -> super.adicionarParte(parte));
		pecas.forEach(peca -> super.adicionarPeca(peca));
		this.orgaoRepresentado = orgaoRepresentado;
	}

	public Orgao orgaoRepresentado() {
		return this.orgaoRepresentado;
	}
	
	@Override
	public MeioTramitacao meioTramitacao() {
		return meioTramitacao;
	}
	
	@Override
	public boolean hasRepresentacao() {
		return this.orgaoRepresentado != null;
	}

}
