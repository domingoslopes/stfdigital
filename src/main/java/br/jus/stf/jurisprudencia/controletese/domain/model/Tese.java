package br.jus.stf.jurisprudencia.controletese.domain.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import br.jus.stf.shared.AssuntoId;
import br.jus.stf.shared.TeseId;

@Entity
@Table(name = "TESE", schema = "JURISPRUDENCIA",
		uniqueConstraints = {@UniqueConstraint(columnNames = {"TIP_TESE", "DSC_TESE"}),
							 @UniqueConstraint(columnNames = {"TIP_TESE", "NUM_TESE"})})
public class Tese implements br.jus.stf.shared.stereotype.Entity<Tese, TeseId> {
	
	@EmbeddedId
	private TeseId codigo;
	
	@Column(name = "DSC_TESE", nullable = false)
	private String descricao;
	
	@Column(name = "NUM_TESE", nullable = false)
	private Long numero;
	
	@Column(name = "TIP_TESE", nullable = false)
	@Enumerated(EnumType.STRING)
	private TipoTese tipo;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "TESE_ASSUNTO", schema = "JURISPRUDENCIA",
		joinColumns = @JoinColumn(name = "SEQ_TESE", nullable = false),
		inverseJoinColumns = @JoinColumn(name = "COD_ASSUNTO", nullable = false))
	private Set<Assunto> assuntos = new HashSet<Assunto>(0);
	
	Tese() {
		
	}
	
	public Tese(final TeseId codigo, final String descricao, final TipoTese tipo, final Long numero) {
		Validate.notNull(codigo, "tese.codigo.required");
		Validate.notBlank(descricao, "tese.descricao.required");
		Validate.notNull(tipo, "tese.tipo.required");
		Validate.notNull(numero, "tese.numero.required");
		
		this.codigo = codigo;
		this.descricao = descricao;
		this.tipo = tipo;
		this.numero = numero;
	}
	
	public String descricao() {
		return descricao;
	}
	
	public TipoTese tipo() {
		return tipo;
	}
	
	public Long numero() {
		return numero;
	}
	
	public Set<Assunto> assuntos() {
		return Collections.unmodifiableSet(assuntos);
	}
	
	public void atribuirAssuntos(final Set<Assunto> assuntos) {
		Validate.notEmpty(assuntos, "tese.assuntos.required");
		
		this.assuntos.addAll(assuntos);
	}
	
	public void removerAssuntos(final Set<AssuntoId> assuntos) {
		Validate.notEmpty(assuntos, "tese.assuntos.required");
		
		Iterator<Assunto> assuntoIterator = this.assuntos.iterator();
		
		while(assuntoIterator.hasNext()) {
			Assunto assunto = assuntoIterator.next();
			
			if (assuntos.contains(assunto.id())) {
				assuntoIterator.remove();
			}
		}
	}
	
	@Override
	public TeseId id() {
		return this.codigo;
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(codigo).hashCode();
	}
	
	@Override
	public boolean equals(Object obj){
		
		if (this == obj) {
			return true;
		}
		
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
	
		Tese other = (Tese) obj;
		
		return codigo.equals(other.codigo);
	}

	@Override
	public boolean sameIdentityAs(final Tese other) {
		return other != null
				&& codigo.equals(other.codigo);
	}

}
