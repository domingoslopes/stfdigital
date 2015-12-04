package br.jus.stf.plataforma.identidades.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import br.jus.stf.processamentoinicial.autuacao.domain.model.Orgao;

/**
 * @author Rafael.Alencar
 *
 */
@Entity
@Table(name = "ASSOCIADO", schema = "CORPORATIVO", uniqueConstraints = @UniqueConstraint(columnNames = {"SEQ_PESSOA", "SEQ_ORGAO"}))
public class Associado implements br.jus.stf.shared.stereotype.Entity<Associado, Long> {

	@Id
	@Column(name = "SEQ_ASSOCIADO")
	private Long sequencial;

	@ManyToOne
	@JoinColumn(name = "SEQ_PESSOA", referencedColumnName = "SEQ_PESSOA", nullable = false)
	private Pessoa pessoa;

	@ManyToOne
	@JoinColumn(name = "SEQ_ORGAO", referencedColumnName = "SEQ_ORGAO", nullable = false)
	private Orgao orgao;

	@Column(name = "TIP_ASSOCIADO", nullable = false)
	@Enumerated(EnumType.STRING)
	private TipoAssociado tipo;
	
	@Column(name = "DSC_CARGO_FUNCAO")
	private String cargoFuncao;
	
	Associado() {
		
	}

	public Associado(final Long sequencial, final Pessoa pessoa, final Orgao orgao, final TipoAssociado tipo) {
		Validate.notNull(sequencial, "associado.sequencial.required");
		Validate.notNull(pessoa, "associado.pessoa.required");
		Validate.notNull(orgao, "associado.orgao.required");
		Validate.notNull(tipo, "associado.tipo.required");

		this.sequencial = sequencial;
		this.pessoa = pessoa;
		this.orgao = orgao;
		this.tipo = tipo;
	}
	
	public Associado(final Long sequencial, final Pessoa pessoa, final Orgao orgao, final TipoAssociado tipo, final String cargoFuncao) {
		this(sequencial, pessoa, orgao, tipo);
		
		Validate.notBlank(cargoFuncao, "associado.cargoFuncao.required");

		this.cargoFuncao = cargoFuncao;
	}

	/**
	 * @return
	 */
	public Pessoa pessoa() {
		return pessoa;
	}

	/**
	 * @return
	 */
	public Orgao orgao() {
		return orgao;
	}

	/**
	 * @return
	 */
	public TipoAssociado tipo() {
		return tipo;
	}
	
	/**
	 * @return
	 */
	public String cargoFuncao() {
		return cargoFuncao;
	}
	
	@Override
	public Long id() {
		return sequencial;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(sequencial)
				.hashCode();
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}

		Associado other = (Associado) obj;

		return sameIdentityAs(other);
	}

	@Override
	public boolean sameIdentityAs(Associado other) {
		return other != null && sequencial.equals(other.sequencial);
	}

}
