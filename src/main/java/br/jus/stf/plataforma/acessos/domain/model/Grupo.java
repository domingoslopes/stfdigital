package br.jus.stf.plataforma.acessos.domain.model;

import java.util.Collections;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang3.Validate;

import br.jus.stf.shared.stereotype.ValueObject;

@Entity
@Table(name = "GRUPO", schema = "PLATAFORMA", uniqueConstraints = @UniqueConstraint(columnNames = {"NOM_GRUPO", "TIP_GRUPO"}))
public class Grupo implements ValueObject<Grupo>, Principal {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "SEQ_GRUPO")
	@SequenceGenerator(name = "GRUPOID", sequenceName = "PLATAFORMA.SEQ_GRUPO", allocationSize = 1)
	@GeneratedValue(generator = "GRUPOID", strategy = GenerationType.SEQUENCE)
	private Long sequencial;
	
	@Column(name = "NOM_GRUPO", nullable = false)
	private String nome;
	
	@Column(name = "TIP_GRUPO", nullable = false)
	@Enumerated(EnumType.STRING)
	private TipoGrupo tipo;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	@JoinTable(name = "PERMISSAO_GRUPO", schema = "PLATAFORMA",
		joinColumns = @JoinColumn(name = "SEQ_GRUPO", nullable = false),
		inverseJoinColumns = @JoinColumn(name = "SEQ_PERMISSAO", nullable = false))
	private Set<Permissao> permissoes;
	
	Grupo() {
		
	}
	
	public Grupo(final Long sequencial, final String nome, final TipoGrupo tipo) {
		Validate.notNull(sequencial, "grupo.sequencial.required");
		Validate.notBlank(nome, "grupo.nome.required");
		Validate.notNull(tipo, "grupo.tipo.required");
		
		this.sequencial = sequencial;
		this.nome = nome;
		this.tipo = tipo;
	}

	public Long toLong() {
		return sequencial;
	}
	
	public String nome() {
		return nome;
	}
	
	public TipoGrupo tipo() {
		return tipo;
	}
	
	@Override
	public Set<Permissao> permissoes() {
		return Collections.unmodifiableSet(permissoes);
	}

	@Override
	public void atribuirPermissoes(Set<Permissao> permissoes) {
		Validate.notEmpty(permissoes, "papel.permissoes.required");
		
		this.permissoes = permissoes;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		
		result = prime * result + ((sequencial == null) ? 0 : sequencial.hashCode());
		
		return result;
	}
	
	@Override
	public boolean equals(Object obj){
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
	
		Grupo other = (Grupo) obj;
		return sameValueAs(other);
	}

	@Override
	public boolean sameValueAs(Grupo other) {
		return other != null && sequencial.equals(other.sequencial);
	}

}
