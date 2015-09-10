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
	
	@Column(name = "DSC_MOTIVO_REJEICAO")
	private String motivoRejeicao;
	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "PETICAO_PROCESSO_WORKFLOW", schema = "AUTUACAO",
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
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,
			targetEntity = PartePeticao.class)
	@JoinColumn(name = "SEQ_PETICAO")
	protected Set<Parte> partes = new HashSet<Parte>(0);
	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "PETICAO_DOCUMENTO", schema = "AUTUACAO",
			joinColumns = @JoinColumn(name = "SEQ_PETICAO"))
	protected Set<DocumentoId> documentos = new TreeSet<DocumentoId>(
			(d1, d2) -> d1.toLong().compareTo(d2.toLong()));
	

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

		Set<DocumentoId> documentos = Collections.emptySet();
		
		if (getClass().equals(PeticaoEletronica.class)) {
			documentos = ((PeticaoEletronica) this).documentos();
		}
		Set<ParteProcesso> partesProcesso = new HashSet<ParteProcesso>();
		partes.stream().forEach(parte -> partesProcesso.add(new ParteProcesso(parte.pessoaId(), parte.polo())));
		
		return ProcessoFactory.criarProcesso(classeProcessual, relator, peticaoId, partesProcesso, documentos);
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
