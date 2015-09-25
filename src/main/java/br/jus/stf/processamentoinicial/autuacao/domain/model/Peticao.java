package br.jus.stf.processamentoinicial.autuacao.domain.model;

import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang3.Validate;

import br.jus.stf.processamentoinicial.distribuicao.domain.model.Processo;
import br.jus.stf.processamentoinicial.distribuicao.domain.model.ProcessoFactory;
import br.jus.stf.shared.ClasseId;
import br.jus.stf.shared.DocumentoId;
import br.jus.stf.shared.MinistroId;
import br.jus.stf.shared.PeticaoId;
import br.jus.stf.shared.ProcessoWorkflowId;
import br.jus.stf.shared.stereotype.Entity;

/**
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 14.08.2015
 */
@javax.persistence.Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TIP_MEIO_PETICAO")
@Table(name = "PETICAO", schema = "AUTUACAO", uniqueConstraints = @UniqueConstraint(columnNames = {"NUM_PETICAO", "NUM_ANO_PETICAO"}))
public abstract class Peticao implements Entity<Peticao, PeticaoId> {

	@EmbeddedId
	private PeticaoId id;
	
	@Column(name = "NUM_PETICAO", nullable = false, updatable = false)
	private Long numero;
	
	@Column(name = "NUM_ANO_PETICAO", nullable = false, updatable = false)
	private Integer ano;
	
	@Embedded
	@AttributeOverride(name = "sigla", column = @Column(name = "SIG_CLASSE_SUGERIDA"))
	private ClasseId classeSugerida;
	
	@Embedded
	private ClasseId classeProcessual;
	
	@Column(name = "DSC_MOTIVO_REJEICAO")
	private String motivoRejeicao;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, targetEntity = PartePeticao.class)
	@JoinColumn(name = "SEQ_PETICAO")
	private Set<Parte> partes = new HashSet<Parte>(0);
		
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "PETICAO_DOCUMENTO", schema = "AUTUACAO", joinColumns = @JoinColumn(name = "SEQ_PETICAO"))
	private Set<DocumentoId> documentos = new TreeSet<DocumentoId>((d1, d2) -> d1.toLong().compareTo(d2.toLong()));
	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "PETICAO_PROCESSO_WORKFLOW", schema = "AUTUACAO", joinColumns = @JoinColumn(name = "SEQ_PETICAO"))
	private Set<ProcessoWorkflowId> processosWorkflow = new TreeSet<ProcessoWorkflowId>((p1, p2) -> p1.toLong().compareTo(p2.toLong()));
	
	@Transient
	private String identificacao;

	public Peticao(final PeticaoId id, final Long numero) {
		Validate.notNull(id, "peticao.id.required");
		Validate.notNull(numero, "peticao.numero.required");
		
		this.id = id;
		this.numero = numero;
		this.ano = Calendar.getInstance().get(Calendar.YEAR);
		this.identificacao = montarIdentificacao();
	}

	public PeticaoId id() {
		return this.id;
	}
	
	public Long numero() {
		return numero;
	}
	
	public Short ano() {
		return ano.shortValue();
	}
	
	public String identificacao() {
		return Optional.ofNullable(identificacao)
				.orElse(identificacao = montarIdentificacao());
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
	 * Adiciona um parte à petição. Caso nulo irá lançar uma exceção
	 * 
	 * @param parte
	 * @exception 
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
	
	public String motivoRejeicao() {
		return this.motivoRejeicao;
	}

	/**
	 * 
	 * @param classeProcessual
	 */
	public void aceitar(final ClasseId classeProcessual) {
		Validate.notNull(classeProcessual, "peticao.classeProcessual.required");

		this.classeProcessual = classeProcessual;
	}

	/**
	 * 
	 * @param motivoRejeicao
	 */
	public void rejeitar(final String motivoRejeicao) {
		Validate.notNull(motivoRejeicao, "peticao.motivoRejeicao.required");
	
		this.motivoRejeicao = motivoRejeicao;
	}

	/**
	 * @param ministroRelator
	 * @param processoRepository
	 * @return o Processo
	 */
	public Processo distribuir(final MinistroId relator) {
		Validate.notNull(relator, "peticao.ministroRelator.required");
		
		return ProcessoFactory.criarProcesso(classeProcessual, relator, this);
	}

	public Set<ProcessoWorkflowId> processosWorkflow() {
		return Collections.unmodifiableSet(processosWorkflow);
	}

	/**
	 * 
	 * @param processosWorkflow
	 */
	public void associarProcessoWorkflow(final ProcessoWorkflowId processoWorkflowId) {
		Validate.notNull(processoWorkflowId, "peticao.processoWorkflowId.required");
	
		this.processosWorkflow.add(processoWorkflowId);
	}
	
	public boolean isEletronica() {
		return this.getClass().equals(PeticaoEletronica.class);
	}
	
	/**
	 * Sugestão de classe
	 * 
	 * @param classeSugerida
	 */
	protected void sugerirClasse(final ClasseId classeSugerida) {
		Validate.notNull(classeSugerida, "peticao.classeSugerida.required");
		
		this.classeSugerida = classeSugerida;
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
		if (obj == null || !(obj instanceof Peticao)) return false;
		
		Peticao other = (Peticao) obj;
		return sameIdentityAs(other);
	}

	@Override
	public boolean sameIdentityAs(Peticao other){
		return other != null && this.id.sameValueAs(other.id);
	}
	
	private String montarIdentificacao() {
		return new StringBuilder()
			.append(numero).append("/").append(ano).toString();
	}

	// Hibernate
	Peticao() {
		
	}
	
}
