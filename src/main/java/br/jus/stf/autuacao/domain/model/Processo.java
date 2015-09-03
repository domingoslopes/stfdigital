package br.jus.stf.autuacao.domain.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
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
public class Processo implements Entity<Processo> {

	@Embedded
	@AttributeOverride(name = "id",
		column = @Column(name = "SEQ_PPROCESSO", insertable = false, updatable = false))
	private ProcessoId processoId;
	
	@Embedded
	private ClasseId classe;
	
	@Column(name = "NUM_PROCESSO", nullable = false)
	private Long numero;
	
	@Embedded
	@AttributeOverride(name = "id",
			column = @Column(name = "SEQ_MINISTRO_RELATOR"))
	private MinistroId ministroRelator;
	
	@Embedded
	private PeticaoId peticaoId;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,
			targetEntity = ParteProcesso.class)
	@JoinColumn(name = "SEQ_PROCESSO")
	private Set<Parte> partes = new HashSet<Parte>(0);
	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "PROCESSO_DOCUMENTO", schema = "AUTUACAO",
			joinColumns = @JoinColumn(name = "SEQ_PROCESSO"))
	private Set<DocumentoId> pecas = new HashSet<DocumentoId>(0);

	/**
	 * 
	 * @param numero
	 * @param ministroRelator
	 * @param peticao
	 * @param partes
	 * @param pecas
	 */
	public Processo(final ClasseId classe, final Long numero, final MinistroId ministroRelator,
			final PeticaoId peticaoId, final Set<Parte> partes, final Set<DocumentoId> documentos) {
		Validate.notNull(classe, "processo.classe.required");
		Validate.notNull(numero, "processo.numero.required");
		Validate.notNull(ministroRelator, "processo.ministroRelator.required");
		Validate.notNull(peticaoId, "processo.peticao.required");
		Validate.notEmpty(partes, "processo.partes.notEmpty");
		Validate.notEmpty(documentos, "processo.pecas.notEmpty");
		
		this.ministroRelator = ministroRelator;
		this.peticaoId = peticaoId;
		this.partes.addAll(partes);
		this.pecas.addAll(documentos);
	}

	public ProcessoId id(){
		return this.processoId;
	}

	public MinistroId ministroRelator(){
		return this.ministroRelator;
	}

	public PeticaoId peticaoId(){
		return this.peticaoId;
	}

	public Set<Parte> partesPoloAtivo(){
		return Collections.unmodifiableSet(partes.stream()
		  .filter(p -> p.polo() == TipoPolo.POLO_ATIVO)
		  .collect(Collectors.toSet()));
	}

	public Set<Parte> partesPoloPassivo(){
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

	public Set<DocumentoId> documentos(){
		return Collections.unmodifiableSet(this.pecas);
	}

	/**
	 * 
	 * @param peca
	 */
	public boolean adicionarDocumento(final DocumentoId peca){
		Validate.notNull(peca, "peticao.peca.required");
	
		return this.pecas.add(peca);
	}
	
	/**
	 * 
	 * @param peca
	 */
	public boolean removerDocumento(final DocumentoId peca){
		Validate.notNull(peca, "peticao.peca.required");
	
		return this.pecas.remove(peca);
	}

	public Set<DocumentoId> pecas(){
		return Collections.unmodifiableSet(pecas);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((processoId == null) ? 0 : processoId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		
		Processo other = (Processo) obj;
		return sameIdentityAs(other);
	}

	/**
	 * 
	 * @param other
	 */
	public boolean sameIdentityAs(Processo other){
		return other != null && this.processoId.sameValueAs(other.processoId);
	}
	
	// Hibernate
	
	@Id
	@Column(name = "SEQ_PROCESSO")
	@SequenceGenerator(name = "PROCESSOID", sequenceName = "AUTUACAO.SEQ_PROCESSO", allocationSize = 1)
	@GeneratedValue(generator = "PROCESSOID", strategy=GenerationType.SEQUENCE)
	private Long id;
	
	Processo(){

	}

}