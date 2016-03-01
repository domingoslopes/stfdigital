package br.jus.stf.plataforma.acessos.domain.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import br.jus.stf.plataforma.identidades.domain.model.Pessoa;
import br.jus.stf.shared.GrupoId;
import br.jus.stf.shared.PapelId;
import br.jus.stf.shared.UsuarioId;
import br.jus.stf.shared.stereotype.Entity;

@javax.persistence.Entity
@Table(name = "USUARIO", schema = "PLATAFORMA", uniqueConstraints = @UniqueConstraint(columnNames = {"SIG_USUARIO"}))
public class Usuario implements Entity<Usuario, UsuarioId>, Principal {
	
	@EmbeddedId
	private UsuarioId id;
	
	@ManyToOne
	@JoinColumn(name = "SEQ_PESSOA", nullable = false)
	private Pessoa pessoa;
	
	@Column(name = "SIG_USUARIO", nullable = false)
	private String login;
	
	@ManyToOne
	@JoinColumn(name = "COD_SETOR_LOTACAO", referencedColumnName = "COD_SETOR")
	private Setor lotacao;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "USUARIO_RECURSO", schema = "PLATAFORMA",
		joinColumns = @JoinColumn(name = "SEQ_USUARIO", nullable = false),
		inverseJoinColumns = @JoinColumn(name = "SEQ_RECURSO", nullable = false))
	private Set<Recurso> recursos = new HashSet<Recurso>(0);
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "PAPEL_USUARIO", schema = "PLATAFORMA",
		joinColumns = @JoinColumn(name = "SEQ_USUARIO", nullable = false),
		inverseJoinColumns = @JoinColumn(name = "SEQ_PAPEL", nullable = false))
	private Set<Papel> papeis = new HashSet<Papel>(0);
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "GRUPO_USUARIO", schema = "PLATAFORMA",
		joinColumns = @JoinColumn(name = "SEQ_USUARIO", nullable = false),
		inverseJoinColumns = @JoinColumn(name = "SEQ_GRUPO", nullable = false))
	private Set<Grupo> grupos = new HashSet<Grupo>(0);
	
	Usuario() {
		
	}
	
	public Usuario(final UsuarioId id, final Pessoa pessoa, final String login) {
		Validate.notNull(id, "usuario.id.required");
		Validate.notNull(pessoa, "usuario.pessoa.required");
		Validate.notBlank(login, "usuario.login.required");
		
		this.id = id;
		this.pessoa = pessoa;
		this.login = login;
	}
	
	public Usuario(final UsuarioId id, final Pessoa pessoa, final String login, final Setor lotacao) {
		this(id, pessoa, login);
		
		Validate.notNull(lotacao, "usuario.lotacao.required");
		
		this.lotacao = lotacao;
	}
	
	public Pessoa pessoa() {
		return pessoa;
	}
	
	public String login() {
		return login;
	}
	
	public Set<Papel> papeis() {
		return Collections.unmodifiableSet(papeis);
	}
	
	public void atribuirPapeis(final Set<Papel> papeis) {
		Validate.notEmpty(papeis, "usuario.papeis.required");
		
		this.papeis.addAll(papeis);
	}
	
	public void removerPapeis(final Set<PapelId> papeis) {
		Validate.notEmpty(papeis, "usuario.papeis.required");
		
		Iterator<Papel> papelIterator = this.papeis.iterator();
		
		while(papelIterator.hasNext()) {
			Papel papel = papelIterator.next();
			
			if (papeis.contains(papel.id())) {
				papelIterator.remove();
			}
		}
	}
	
	public Set<Grupo> grupos() {
		return Collections.unmodifiableSet(grupos);
	}
	
	public void atribuirGrupos(final Set<Grupo> grupos) {
		Validate.notEmpty(grupos, "usuario.grupos.required");
		
		this.grupos.addAll(grupos);
	}
	
	public void removerGrupos(final Set<GrupoId> grupos) {
		Validate.notEmpty(grupos, "usuario.grupos.required");
		
		Iterator<Grupo> grupoIterator = this.grupos.iterator();
		
		while(grupoIterator.hasNext()) {
			Grupo grupo = grupoIterator.next();
			
			if (grupos.contains(grupo.id())) {
				grupoIterator.remove();
			}
		}
	}
	
	@Override
	public Set<Recurso> recursos() {
		Set<Recurso> recursosCompletos = new HashSet<Recurso>();
		
		Optional.ofNullable(papeis).ifPresent(p -> p.forEach(papel -> recursosCompletos.addAll(papel.recursos())));
		Optional.ofNullable(grupos).ifPresent(g -> g.forEach(grupo -> recursosCompletos.addAll(grupo.recursos())));
		Optional.ofNullable(recursos).ifPresent(p -> recursosCompletos.addAll(p));
		
		return Collections.unmodifiableSet(recursosCompletos);
	}
	
	@Override
	public void atribuirRecursos(final Set<Recurso> recursos) {
		Validate.notEmpty(recursos, "usuario.recursos.required");
		
		this.recursos.addAll(recursos);
	}
	
	@Override
	public void removerRecursos(Set<Recurso> recursos) {
		Validate.notEmpty(recursos, "usuario.recursos.required");
		
		this.recursos.removeAll(recursos);
	}
	
	public Setor lotacao(){
		return lotacao;
	}
	
	public Setor setor(){
		return lotacao;
	}
	
	@Override
	public UsuarioId id() {
		return id;
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(id).hashCode();
	}
	
	@Override
	public boolean equals(Object obj){
		
		if (this == obj) {
			return true;
		}
		
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
	
		Usuario other = (Usuario) obj;
		
		return sameIdentityAs(other);
	}

	@Override
	public boolean sameIdentityAs(Usuario other) {
		return other != null
				&& id.equals(other.id);
	}
	
}
