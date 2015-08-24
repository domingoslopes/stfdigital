package br.jus.stf.autuacao.domain.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.AttributeOverride;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang3.Validate;

import br.jus.stf.shared.domain.model.ClasseId;
import br.jus.stf.shared.domain.model.DocumentoId;
import br.jus.stf.shared.domain.model.MinistroId;
import br.jus.stf.shared.domain.model.ProcessInstanceId;
import br.jus.stf.shared.domain.stereotype.Entity;

/**
 * @author Rafael.Alencar
 * @version 1.0
 * @created 14-ago-2015 18:33:25
 */
@javax.persistence.Entity
@Table(name = "PETICAO")
@SequenceGenerator(name = "PETICAOID", sequenceName = "SEQ_PETICAO", allocationSize = '1')
public class Peticao implements Entity<Peticao> {

	@Embedded
	private PeticaoId numeroPeticao;
	
	@Embedded
	@AttributeOverride(column = @Column(name = "SIG_CLASSE_SUGERIDA"),
						name = "sigla")
	private ClasseId classeSugerida;
	
	@ElementCollection
	@CollectionTable(name = "PARTE_PETICAO",
						joinColumns = @JoinColumn(name = "SEQ_PETICAO"))
	private Set<Parte> partes = new HashSet<Parte>(0);
	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "DOCUMENTO_PETICAO",
			joinColumns = @JoinColumn(name = "SEQ_PETICAO"))
	private Set<DocumentoId> documentos = new HashSet<DocumentoId>(0);
	
	@Embedded
	private ClasseId classeProcessual;
	
	@Column(name = "TXT_MOTIVO_RECUSA")
	private String motivoRecusa;
	
	@Column(name = "TIP_STATUS_PETICAO")
	@Enumerated(EnumType.STRING)
	private PeticaoStatus status;
	
	@Embedded
	private ProcessInstanceId processInstanceId;

	/**
	 * 
	 * @param numeroPeticao
	 * @param classeSugerida
	 * @param partes
	 * @param documentos
	 */
	public Peticao(PeticaoId numeroPeticao, ClasseId classeSugerida, Set<Parte> partes, Set<DocumentoId> documentos){
		Validate.notNull(numeroPeticao, "peticao.numeroPeticao.required");
		Validate.notNull(classeSugerida, "peticao.classeSugerida.required");
		Validate.notEmpty(partes, "peticao.partes.notEmpty");
		Validate.notEmpty(documentos, "peticao.documentos.notEmpty");
	
		this.numeroPeticao = numeroPeticao;
		this.classeSugerida = classeSugerida;
		this.partes = partes;
		this.documentos = documentos;
	}

	public PeticaoId id(){
		return this.numeroPeticao;
	}

	public ClasseId classeSugerida(){
		return this.classeSugerida;
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
		Validate.notNull(parte, "peticao.parte.notNull");
		
		return this.partes.add(parte);
	}
	
	/**
	 * 
	 * @param parte
	 */
	public boolean removerParte(final Parte parte){
		Validate.notNull(parte, "peticao.parte.notNull");
		
		return this.partes.remove(parte);
	}

	public Set<DocumentoId> documentos(){
		return Collections.unmodifiableSet(this.documentos);
	}

	/**
	 * 
	 * @param documento
	 */
	public boolean adicionarDocumento(final DocumentoId documento){
		Validate.notNull(documento, "peticao.documento.notNull");
	
		return this.documentos.add(documento);
	}
	
	/**
	 * 
	 * @param documento
	 */
	public boolean removerDocumento(final DocumentoId documento){
		Validate.notNull(documento, "peticao.documento.notNull");
	
		return this.documentos.remove(documento);
	}

	public ClasseId classeProcessual(){
		return this.classeProcessual;
	}
	
	public String motivoRecusa(){
		return this.motivoRecusa;
	}

	public void registrar(){
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
	public void aceitar(final ClasseId classeProcessual){
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
	public void recusar(final String motivoRecusa){
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
		
		ProcessoId processoId = processoRepository.nextProcessoId(this.classeProcessual);
		return new Processo(processoId, ministroRelator, this);
	}

	public PeticaoStatus status(){
		return this.status;
	}

	public ProcessInstanceId processInstanceId(){
		return processInstanceId;
	}

	/**
	 * 
	 * @param processInstanceId
	 */
	public void setProcessInstanceId(final ProcessInstanceId processInstanceId){
		Validate.notNull(processInstanceId, "peticao.processInstanceId.required");
	
		this.processInstanceId = processInstanceId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((numeroPeticao == null) ? 0 : numeroPeticao.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		
		Peticao other = (Peticao) obj;
		return sameIdentityAs(other);
	}

	/**
	 * 
	 * @param other
	 */
	public boolean sameIdentityAs(Peticao other){
		return other != null && this.numeroPeticao.sameValueAs(other.numeroPeticao);
	}

	// Hibernate
	@Id
	@GeneratedValue(generator = "PETICAOID", strategy=GenerationType.SEQUENCE)
	@Column(name = "SEQ_PETICAO")
	private Long id;
	
	Peticao(){

	}
	
}