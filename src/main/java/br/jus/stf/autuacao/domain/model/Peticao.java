package br.jus.stf.autuacao.domain.model;

import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang3.Validate;

import br.jus.stf.autuacao.infra.persistence.GerarNumeroPeticao;
import br.jus.stf.shared.domain.model.ClasseId;
import br.jus.stf.shared.domain.model.DocumentoId;
import br.jus.stf.shared.domain.model.MinistroId;
import br.jus.stf.shared.domain.model.PeticaoId;
import br.jus.stf.shared.domain.model.ProcessoWorkflowId;
import br.jus.stf.shared.domain.stereotype.Entity;

/**
 * @author Rafael.Alencar
 * @version 1.0
 * @created 14-ago-2015 18:33:25
 */
@javax.persistence.Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TIP_MEIO_PETICAO")
@Table(name = "PETICAO", schema = "AUTUACAO",
	uniqueConstraints = @UniqueConstraint(columnNames = {"NUM_PETICAO", "NUM_ANO_PETICAO"}))
public abstract class Peticao implements Entity<Peticao> {

	@Embedded
	@AttributeOverride(name = "id",
		column = @Column(name = "SEQ_PETICAO", insertable = false, updatable = false))
	private PeticaoId peticaoId;
	
	@Embedded
	private ClasseId classeProcessual;
	
	@Column(name = "DSC_MOTIVO_RECUSA")
	private String motivoRecusa;
	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "PROCESSO_WORKFLOW_PETICAO", schema = "AUTUACAO",
			joinColumns = @JoinColumn(name = "SEQ_PETICAO"))
	private Set<ProcessoWorkflowId> processosWorkflow = new TreeSet<ProcessoWorkflowId>(
			(p1, p2) -> p1.toLong().compareTo(p2.toLong()));
	
	@GerarNumeroPeticao
	@Column(name = "NUM_PETICAO", nullable = false)
	protected Long numero;
	
	@Column(name = "NUM_ANO_PETICAO", nullable = false)
	protected Integer ano = Calendar.getInstance().get(Calendar.YEAR);
	
	@Embedded
	@AttributeOverride(name = "sigla",
		column = @Column(name = "SIG_CLASSE_SUGERIDA"))
	protected ClasseId classeSugerida;
	
	@Column(name = "TIP_STATUS_PETICAO")
	@Enumerated(EnumType.STRING)
	protected PeticaoStatus status;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,
			targetEntity = PartePeticao.class)
	@JoinColumn(name = "SEQ_PETICAO")
	protected Set<Parte> partes = new HashSet<Parte>(0);
	

	public PeticaoId id() {
		return this.peticaoId;
	}
	
	public Long numero() {
		return numero;
	}
	
	public Integer ano() {
		return ano;
	}
	
	public String identificacao() {
		return new StringBuilder()
				.append(numero)
				.append("/")
				.append(ano)
				.toString();
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

	public ClasseId classeProcessual() {
		return this.classeProcessual;
	}
	
	public String motivoRecusa() {
		return this.motivoRecusa;
	}

	public void autuar(){
		if (PeticaoStatus.A_AUTUAR.equals(status)) {
			throw new IllegalStateException("peticao.autuar.exception");
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
			throw new IllegalStateException("peticao.aceitar.exception");
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
			throw new IllegalStateException("peticao.recusar.exception");
		}
	
		this.motivoRecusa = motivoRecusa;
		this.status = PeticaoStatus.RECUSADA;
	}

	/**
	 * @param ministroRelator
	 * @param processoRepository
	 * @return o Processo
	 */
	public Processo distribuir(final MinistroId relator) {
		Validate.notNull(relator, "peticao.ministroRelator.required");

		if (this.status != PeticaoStatus.ACEITA) {
			throw new IllegalStateException("peticao.distribuir.exception");
		}
		this.status = PeticaoStatus.DISTRIBUIDA;
		Set<DocumentoId> documentos = Collections.emptySet();
		
		if (getClass().equals(PeticaoEletronica.class)) {
			documentos = ((PeticaoEletronica) this).documentos();
		}
		Set<ParteProcesso> partesProcesso = new HashSet<ParteProcesso>();
		partes.stream().forEach(parte -> partesProcesso.add(new ParteProcesso(parte.pessoaId(), parte.polo())));
		
		return ProcessoFactory.criarProcesso(classeProcessual, relator, peticaoId, partesProcesso, documentos);
	}

	public PeticaoStatus status() {
		return this.status;
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
		if (obj == null || !(obj instanceof Peticao)) return false;
		
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
	
	Peticao() {
		
	}
	
}