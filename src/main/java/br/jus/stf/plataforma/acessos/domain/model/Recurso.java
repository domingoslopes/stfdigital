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

import org.apache.commons.lang3.Validate;

import br.jus.stf.shared.stereotype.ValueObject;

@Entity
@Table(name = "RECURSO", schema = "PLATAFORMA")
public class Recurso implements ValueObject<Recurso> {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "SEQ_RECURSO")
	@SequenceGenerator(name = "RECURSOID", sequenceName = "PLATAFORMA.SEQ_RECURSO", allocationSize = 1)
	@GeneratedValue(generator = "RECURSOID", strategy=GenerationType.SEQUENCE)
	private Long sequencia;
	
	@Column(name = "NOM_RECURSO", nullable = false)
	private String nome;
	
	@Column(name = "TIP_RECURSO", nullable = false)
	@Enumerated(EnumType.STRING)
	private TipoRecurso tipoRecurso;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	@JoinTable(name = "PERMISSAO_RECURSO", schema = "PLATAFORMA",
		joinColumns = @JoinColumn(name = "SEQ_RECURSO", nullable = false),
		inverseJoinColumns = @JoinColumn(name = "SEQ_PERMISSAO", nullable = false))
	private Set<Permissao> permissoesExigidas;
	
	public Recurso(String nome, TipoRecurso tipoRecurso, Set<Permissao> permissoesExigidas) {
		Validate.notBlank(nome, "recurso.nome.required");
		Validate.notNull(tipoRecurso, "recurso.tipoRecurso.required");
		Validate.notEmpty(permissoesExigidas, "recurso.permissoesExigidas.required");
		
		this.nome = nome;
		this.tipoRecurso = tipoRecurso;
		this.permissoesExigidas = permissoesExigidas;
	}
	
	public String nome() {
		return nome;
	}
	
	public TipoRecurso tipoRecurso() {
		return tipoRecurso;
	}
	
	public Set<Permissao> permissoesExigidas() {
		return Collections.unmodifiableSet(permissoesExigidas);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((permissoesExigidas == null) ? 0 : permissoesExigidas.hashCode());
		
		return result;
	}
	
	@Override
	public boolean equals(Object obj){
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
	
		Recurso other = (Recurso) obj;
		return sameValueAs(other);
	}
	
	@Override
	public boolean sameValueAs(Recurso other) {
		return other != null && nome.equals(other.nome)
				&& permissoesExigidas.equals(other.permissoesExigidas);
	}

}
