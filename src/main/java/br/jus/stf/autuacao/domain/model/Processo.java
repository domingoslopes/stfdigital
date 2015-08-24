package br.jus.stf.autuacao.domain.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang3.Validate;

import br.jus.stf.shared.domain.model.DocumentoId;
import br.jus.stf.shared.domain.model.MinistroId;
import br.jus.stf.shared.domain.stereotype.Entity;

/**
 * @author Rafael.Alencar
 * @version 1.0
 * @created 14-ago-2015 18:33:25
 */
@javax.persistence.Entity
@Table(name = "PROCESSO")
@SequenceGenerator(name = "PROCESSOID", sequenceName = "SEQ_PROCESSO", allocationSize = 1)
public class Processo implements Entity<Processo> {

	@Embedded
	private ProcessoId processoId;
	
	@Embedded
	private MinistroId ministroRelator;
	
	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	private Peticao peticao;
	
	@ElementCollection
	@CollectionTable(name = "PARTE_PROCESSO",
						joinColumns = @JoinColumn(name = "SEQ_PROCESSO"))
	private Set<Parte> partes = new HashSet<Parte>(0);
	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "DOCUMENTO_PROCESSO",
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
	public Processo(final ProcessoId processoId, final MinistroId ministroRelator, final Peticao peticao){
		Validate.notNull(processoId, "processo.numeroProcesso.required");
		Validate.notNull(ministroRelator, "processo.ministroRelator.required");
		Validate.notNull(peticao, "processo.peticao.required");
		Validate.notEmpty(peticao.partesPoloAtivo(), "processo.partes.notEmpty");
		Validate.notEmpty(peticao.partesPoloPassivo(), "processo.partes.notEmpty");
		Validate.notEmpty(peticao.documentos(), "processo.pecas.notEmpty");
		
		this.processoId = processoId;
		this.ministroRelator = ministroRelator;
		this.peticao = peticao;
		this.partes.addAll(peticao.partesPoloAtivo());
		this.partes.addAll(peticao.partesPoloPassivo());
		this.pecas.addAll(peticao.documentos());
	}

	public ProcessoId id(){
		return this.processoId;
	}

	public MinistroId ministroRelator(){
		return this.ministroRelator;
	}

	public Peticao peticao(){
		return this.peticao;
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
	@GeneratedValue(generator = "PROCESSOID", strategy=GenerationType.SEQUENCE)
	@Column(name = "SEQ_PROCESSO")
	private Long id;
	
	Processo(){

	}

}