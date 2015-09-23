package br.jus.stf.autuacao.domain.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang3.Validate;

import br.jus.stf.shared.domain.model.ClasseId;
import br.jus.stf.shared.domain.model.DocumentoId;
import br.jus.stf.shared.domain.model.MinistroId;
import br.jus.stf.shared.domain.model.PeticaoId;
import br.jus.stf.shared.domain.model.ProcessoId;
import br.jus.stf.shared.domain.stereotype.Entity;

/**
 * @author Rafael.Alencar
 * @version 1.0
 * @created 14-ago-2015 18:33:25
 */
@javax.persistence.Entity
@Table(name = "PROCESSO", schema = "AUTUACAO",
	uniqueConstraints = @UniqueConstraint(columnNames = {"SIG_CLASSE", "NUM_PROCESSO"}))
public class Processo implements Entity<Processo, ProcessoId> {

	@EmbeddedId
	private ProcessoId id;
	
	@Embedded
	private ClasseId classe;
	
	@Column(name = "NUM_PROCESSO", nullable = false)
	private Long numero;
	
	@Embedded
	@AttributeOverride(name = "codigo",
		column = @Column(name = "COD_MINISTRO_RELATOR"))
	private MinistroId relator;

	@Embedded
	private PeticaoId peticao;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, 
			targetEntity = ParteProcesso.class)
	@JoinColumn(name = "SEQ_PROCESSO")
	private Set<Parte> partes = new HashSet<Parte>(0);
	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "PROCESSO_DOCUMENTO", schema = "AUTUACAO",
			joinColumns = @JoinColumn(name = "SEQ_PROCESSO"))
	private Set<DocumentoId> pecas = new TreeSet<DocumentoId>(
			(p1, p2) -> p1.toLong().compareTo(p2.toLong()));
	
	@Transient
	private String identificacao;

	/**
	 * @param classe
	 * @param numero
	 * @param relator
	 * @param peticao
	 * @param partes
	 * @param documentos
	 */
	public Processo(final ProcessoId id, final ClasseId classe, final Long numero, final MinistroId relator,
			final PeticaoId peticao, final Set<ParteProcesso> partes, final Set<DocumentoId> pecas) {
		Validate.notNull(id, "processo.id.required");
		Validate.notNull(classe, "processo.classe.required");
		Validate.notNull(numero, "processo.numero.required");
		Validate.notNull(relator, "processo.relator.required");
		Validate.notNull(peticao, "processo.peticao.required");
		//Validate.notEmpty(partes, "processo.partes.notEmpty");
		//Validate.notNull(pecas, "processo.pecas.required");
		
		this.id = id;
		this.classe = classe;
		this.numero = numero;
		this.relator = relator;
		this.peticao = peticao;
		this.partes.addAll(partes);
		this.pecas.addAll(pecas);
		this.identificacao = montarIdentificacao();
	}

	public ProcessoId id() {
		return this.id;
	}
	
	public ClasseId classe() {
		return classe;
	}
	
	public Long numero() {
		return numero;
	}

	public MinistroId relator() {
		return this.relator;
	}

	public PeticaoId peticao() {
		return this.peticao;
	}

	public Set<Parte> partesPoloAtivo() {
		return Collections.unmodifiableSet(partes.stream()
		  .filter(p -> p.polo() == TipoPolo.POLO_ATIVO)
		  .collect(Collectors.toSet()));
	}

	public Set<Parte> partesPoloPassivo() {
		return Collections.unmodifiableSet(partes.stream()
		  .filter(p -> p.polo() == TipoPolo.POLO_PASSIVO)
		  .collect(Collectors.toSet()));
	}
	
	/**
	 * 
	 * @param parte
	 */
	public boolean adicionarParte(final Parte parte){
		Validate.notNull(parte, "peticao.parte.required");
		
		return this.partes.add(parte);
	}
	
	/**
	 * 
	 * @param parte
	 */
	public boolean removerParte(final Parte parte){
		Validate.notNull(parte, "peticao.parte.required");
		
		return this.partes.remove(parte);
	}

	/**
	 * 
	 * @param peca
	 */
	public boolean adicionarPeca(final DocumentoId peca){
		Validate.notNull(peca, "peticao.peca.required");
	
		return this.pecas.add(peca);
	}
	
	/**
	 * 
	 * @param peca
	 */
	public boolean removerPeca(final DocumentoId peca){
		Validate.notNull(peca, "peticao.peca.required");
	
		return this.pecas.remove(peca);
	}

	public Set<DocumentoId> pecas() {
		return Collections.unmodifiableSet(pecas);
	}
	
	public String identificacao() {
		return identificacao;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		
		Processo other = (Processo) obj;
		return sameIdentityAs(other);
	}

	@Override
	public boolean sameIdentityAs(Processo other) {
		return other != null && this.id.sameValueAs(other.id);
	}
	
	private String montarIdentificacao() {
		return new StringBuilder()
			.append(classe.toString()).append(numero).toString();
	}
	
	// Hibernate
	
	Processo() {

	}

}
