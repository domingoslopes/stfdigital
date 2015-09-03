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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
import br.jus.stf.shared.domain.model.ProcessInstanceId;
import br.jus.stf.shared.domain.stereotype.Entity;

/**
 * @author Rafael.Alencar
 * @version 1.0
 * @created 14-ago-2015 18:33:25
 */
@javax.persistence.Entity
@Table(name = "PETICAO", schema = "AUTUACAO",
	uniqueConstraints = @UniqueConstraint(columnNames = {"NUM_PETICAO", "NUM_ANO_PETICAO"}))
public class Peticao implements Entity<Peticao> {

	@Embedded
	@AttributeOverride(name = "id",
		column = @Column(name = "SEQ_PETICAO", insertable = false, updatable = false))
	private PeticaoId peticaoId;
	
	@Column(name = "NUM_PETICAO", nullable = false)
	private Long numero;
	
	@Column(name = "NUM_ANO_PETICAO", nullable = false)
	private Short ano;
	
	@Embedded
	@AttributeOverride(name = "sigla",
		column = @Column(name = "SIG_CLASSE_SUGERIDA"))
	private ClasseId classeSugerida;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,
			targetEntity = PartePeticao.class)
	@JoinColumn(name = "SEQ_PETICAO")
	private Set<Parte> partes = new HashSet<Parte>(0);
	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "PETICAO_DOCUMENTO", schema = "AUTUACAO",
			joinColumns = @JoinColumn(name = "SEQ_PETICAO"))
	private Set<DocumentoId> documentos = new TreeSet<DocumentoId>(
			(d1, d2) -> d1.toLong().compareTo(d2.toLong()));
	
	@Embedded
	private ClasseId classeProcessual;
	
	@Column(name = "DSC_MOTIVO_RECUSA")
	private String motivoRecusa;
	
	@Column(name = "TIP_STATUS_PETICAO")
	@Enumerated(EnumType.STRING)
	private PeticaoStatus status;
	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "PROCESS_INSTANCE_PETICAO",
			joinColumns = @JoinColumn(name = "SEQ_PETICAO"))
	private Set<ProcessInstanceId> processInstances = new TreeSet<ProcessInstanceId>(
			(p1, p2) -> p1.toLong().compareTo(p2.toLong()));

	/**
	 * 
	 * @param numeroPeticao
	 * @param classeSugerida
	 * @param partes
	 * @param documentos
	 */
	public Peticao(final Long numero, final Short ano, final ClasseId classeSugerida,
			final Set<Parte> partes, final Set<DocumentoId> documentos){
		Validate.notNull(numero, "peticao.numero.required");
		Validate.notNull(ano, "peticao.ano.required");
		Validate.notNull(classeSugerida, "peticao.classeSugerida.required");
		Validate.notEmpty(partes, "peticao.partes.notEmpty");
		Validate.notEmpty(documentos, "peticao.documentos.notEmpty");
	
		this.numero = numero;
		this.ano = ano;
		this.classeSugerida = classeSugerida;
		this.partes.addAll(partes);
		this.documentos.addAll(documentos);
	}

	public PeticaoId id() {
		return this.peticaoId;
	}

	public ClasseId classeSugerida() {
		return this.classeSugerida;
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
	public boolean adicionarParte(final Parte parte) {
		Validate.notNull(parte, "peticao.parte.required");
		
		return this.partes.add(parte);
	}
	
	/**
	 * 
	 * @param parte
	 */
	public boolean removerParte(final Parte parte) {
		Validate.notNull(parte, "peticao.parte.required");
		
		return this.partes.remove(parte);
	}

	public Set<DocumentoId> documentos(){
		return Collections.unmodifiableSet(this.documentos);
	}

	/**
	 * 
	 * @param documento
	 */
	public boolean adicionarDocumento(final DocumentoId documento) {
		Validate.notNull(documento, "peticao.documento.required");
	
		return this.documentos.add(documento);
	}
	
	/**
	 * 
	 * @param documento
	 */
	public boolean removerDocumento(final DocumentoId documento) {
		Validate.notNull(documento, "peticao.documento.required");
	
		return this.documentos.remove(documento);
	}

	public ClasseId classeProcessual() {
		return this.classeProcessual;
	}
	
	public String motivoRecusa() {
		return this.motivoRecusa;
	}

	public void registrar() {
		if (this.status != null) {
			throw new IllegalStateException("peticao.registrar.exception " + this.status);
		}
	
		this.status = PeticaoStatus.A_AUTUAR;
	}

	public void autuar(){
		if (this.status != PeticaoStatus.A_AUTUAR) {
			throw new IllegalStateException("peticao.autuar.exception " + this.status);
		}
	
		this.status = PeticaoStatus.EM_AUTUACAO;
	}

	/**
	 * 
	 * @param classeProcessual
	 */
	public void aceitar(final ClasseId classeProcessual) {
		Validate.notNull(classeProcessual, "peticao.classeProcessual.required");
	
		if (this.status != PeticaoStatus.EM_AUTUACAO) {
			throw new IllegalStateException("peticao.aceitar.exception " + this.status);
		}
	
		this.classeProcessual = classeProcessual;
		this.status = PeticaoStatus.ACEITA;
	}

	/**
	 * 
	 * @param motivoRecusa
	 */
	public void recusar(final String motivoRecusa) {
		Validate.notNull(motivoRecusa, "peticao.motivoRecusa.required");
	
		if (this.status != PeticaoStatus.EM_AUTUACAO) {
			throw new IllegalStateException("peticao.recusar.exception " + this.status);
		}
	
		this.motivoRecusa = motivoRecusa;
		this.status = PeticaoStatus.RECUSADA;
	}

	public Processo distribuir(final MinistroId ministroRelator, ProcessoRepository processoRepository){
		Validate.notNull(ministroRelator, "peticao.ministroRelator.required");

		if (this.status != PeticaoStatus.ACEITA) {
			throw new IllegalStateException("peticao.distribuir.exception " + this.status);
		}
			
		this.status = PeticaoStatus.DISTRIBUIDA;
		
		Long numero = processoRepository.nextNumero(classeProcessual);
		return new Processo(classeProcessual, numero, ministroRelator, peticaoId, partes, documentos);
	}

	public PeticaoStatus status(){
		return this.status;
	}

	public Set<ProcessInstanceId> processInstances(){
		return Collections.unmodifiableSet(processInstances);
	}

	/**
	 * 
	 * @param processInstances
	 */
	public void associarProcessInstance(final ProcessInstanceId processInstanceId) {
		Validate.notNull(processInstanceId, "peticao.processInstanceId.required");
	
		this.processInstances.add(processInstanceId);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((peticaoId == null) ? 0 : peticaoId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		
		Peticao other = (Peticao) obj;
		return sameIdentityAs(other);
	}

	@Override
	public boolean sameIdentityAs(Peticao other){
		return other != null && this.peticaoId.sameValueAs(other.peticaoId);
	}

	// Hibernate
	@Id
	@Column(name = "SEQ_PETICAO")
	@SequenceGenerator(name = "PETICAOID", sequenceName = "AUTUACAO.SEQ_PETICAO", allocationSize = 1)
	@GeneratedValue(generator = "PETICAOID", strategy=GenerationType.SEQUENCE)
	private Long id;
	
	Peticao(){

	}
	
}