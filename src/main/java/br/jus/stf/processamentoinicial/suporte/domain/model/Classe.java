package br.jus.stf.processamentoinicial.suporte.domain.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import br.jus.stf.shared.ClasseId;
import br.jus.stf.shared.PreferenciaId;
import br.jus.stf.shared.stereotype.Entity;

/**
 * @author Rafael.Alencar
 * @version 1.0
 * @created 14-ago-2015 18:34:02
 */
@javax.persistence.Entity
@Table(name = "CLASSE", schema = "AUTUACAO")
public class Classe implements Entity<Classe, ClasseId> {

	@EmbeddedId
	private ClasseId sigla;
	
	@Column(name = "NOM_CLASSE", nullable = false)
	private String nome;
	
	@Column(name = "TIP_PROCESSO")
	@Enumerated(EnumType.STRING)
	private TipoProcesso tipo;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "CLASSE_PREFERENCIA", schema = "AUTUACAO",
		joinColumns = @JoinColumn(name = "SIG_CLASSE", nullable = false),
		inverseJoinColumns = @JoinColumn(name = "COD_PREFERENCIA", nullable = false))
	private Set<Preferencia> preferencias = new HashSet<Preferencia>(0);

	Classe() {

	}
	
	public Classe(final ClasseId sigla, final String nome, final TipoProcesso tipo){
		Validate.notNull(sigla, "classe.sigla.required");
		Validate.notBlank(nome, "classe.nome.required");
		Validate.notNull(tipo, "classe.tipo.required");
		
		this.sigla = sigla;
		this.nome = nome;
		this.tipo = tipo;
	}

	@Override
	public ClasseId id(){
		return this.sigla;
	}

	public String nome(){
		return this.nome;
	}
	
	public TipoProcesso tipo() {
		return tipo;
	}
	
	public Set<Preferencia> preferencias() {
		return Collections.unmodifiableSet(preferencias);
	}
	
	public void atribuirPreferencias(final Set<Preferencia> preferencias) {
		Validate.notEmpty(preferencias, "classe.preferencias.required");
		
		this.preferencias.addAll(preferencias);
	}
	
	public void removerPreferencias(final Set<PreferenciaId> preferencias) {
		Validate.notEmpty(preferencias, "classe.preferencias.required");
		
		Iterator<Preferencia> preferenciaIterator = this.preferencias.iterator();
		
		while(preferenciaIterator.hasNext()) {
			Preferencia preferencia = preferenciaIterator.next();
			
			if (preferencias.contains(preferencia.toLong())) {
				preferenciaIterator.remove();
			}
		}
	}
	
	@Override
	public int hashCode(){
		return new HashCodeBuilder().append(sigla).hashCode();
	}
	
	@Override
	public boolean equals(final Object o){
		
		if (this == o) {
			return true;
		}
		
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
	
		Classe other = (Classe) o;
		return sameIdentityAs(other);
	}
	
	@Override
	public boolean sameIdentityAs(final Classe other) {
		return other != null && this.sigla.sameValueAs(other.sigla);
	}
	
}