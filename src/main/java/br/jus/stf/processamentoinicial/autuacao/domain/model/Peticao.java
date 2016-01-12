package br.jus.stf.processamentoinicial.autuacao.domain.model;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import br.jus.stf.processamentoinicial.distribuicao.domain.model.Processo;
import br.jus.stf.processamentoinicial.distribuicao.domain.model.ProcessoFactory;
import br.jus.stf.processamentoinicial.suporte.domain.model.Preferencia;
import br.jus.stf.processamentoinicial.suporte.domain.model.TipoProcesso;
import br.jus.stf.shared.ClasseId;
import br.jus.stf.shared.MinistroId;
import br.jus.stf.shared.PeticaoId;
import br.jus.stf.shared.PreferenciaId;
import br.jus.stf.shared.ProcessoWorkflow;
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
	
	@Column(name = "DSC_MOTIVO_DEVOLUCAO")
	private String motivoDevolucao;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, targetEntity = PartePeticao.class)
	@JoinColumn(name = "SEQ_PETICAO", nullable = false)
	private Set<Parte> partes = new HashSet<Parte>(0);
		
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, targetEntity = PecaPeticao.class)
	@JoinColumn(name = "SEQ_PETICAO", nullable = false)
	private Set<Peca> pecas = new LinkedHashSet<Peca>(0);
	
	@OneToMany(cascade = CascadeType.REFRESH, orphanRemoval = true, fetch = FetchType.EAGER)
	@JoinTable(name = "PETICAO_PROCESSO_WORKFLOW", schema = "AUTUACAO", joinColumns = @JoinColumn(name = "SEQ_PETICAO", nullable = false),
		inverseJoinColumns = @JoinColumn(name = "NUM_PROCESS_INSTANCE", nullable = false))
	private Set<ProcessoWorkflow> processosWorkflow = new TreeSet<ProcessoWorkflow>((p1, p2) -> p1.id().toLong().compareTo(p2.id().toLong()));
	
	@Column(name = "DAT_CADASTRAMENTO")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataCadastramento;
	
	@Column(name = "SIG_USUARIO_CADASTRAMENTO")
	private String usuarioCadastramento;
	
	@Column(name = "TIP_PROCESSO")
	@Enumerated(EnumType.STRING)
	private TipoProcesso tipoProcesso = TipoProcesso.RECURSAL;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "PETICAO_PREFERENCIA", schema = "AUTUACAO",
		joinColumns = @JoinColumn(name = "SEQ_PETICAO", nullable = false),
		inverseJoinColumns = @JoinColumn(name = "SEQ_PREFERENCIA", nullable = false))
	private Set<Preferencia> preferencias = new HashSet<Preferencia>(0);
		
	@Transient
	private String identificacao;

	Peticao() {

	}
	
	public Peticao(final PeticaoId id, final Long numero, final String usuarioCadastramento) {
		Validate.notNull(id, "peticao.id.required");
		Validate.notNull(numero, "peticao.numero.required");
		Validate.notBlank(usuarioCadastramento, "peticao.usuarioCadastramento.required");
		
		this.id = id;
		this.numero = numero;
		this.ano = Calendar.getInstance().get(Calendar.YEAR);
		this.identificacao = montarIdentificacao();
		this.dataCadastramento = new Date();
		this.usuarioCadastramento = usuarioCadastramento;
	}
	
	public Peticao(final PeticaoId id, final Long numero, final String usuarioCadastramento, final TipoProcesso tipoProcesso) {
		this(id, numero, usuarioCadastramento);
		
		Validate.notNull(tipoProcesso, "peticao.tipoProcesso.required");
		
		this.tipoProcesso = tipoProcesso;
	}

	@PostLoad
	private void init() {
		this.identificacao = montarIdentificacao();
	}
	
	@Override
	public PeticaoId id() {
		return this.id;
	}
	
	public String usuarioCadastramento() {
		return usuarioCadastramento;
	}

	public Date dataCadastramento() {
		return dataCadastramento;
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
		
		return partes.add(parte);
	}
	
	/**
	 * 
	 * @param parte
	 */
	public boolean removerParte(final Parte parte) {
		Validate.notNull(parte, "peticao.parte.required");
		
		return partes.remove(parte);
	}
	
	public Set<Peca> pecas(){
		return Collections.unmodifiableSet(pecas);
	}

	/**
	 * 
	 * @param peca
	 */
	public boolean juntar(final Peca peca) {
		Validate.notNull(peca, "peticao.peca.required");
	
		return pecas.add(peca);
	}
	
	public void substituirPeca(Peca pecaOriginal, Peca pecaSubstituta) {
		Validate.notNull(pecaOriginal, "peticao.pecaOriginal.required");
		Validate.notNull(pecaSubstituta, "peticao.pecaSubstituta.required");
		removerPeca(pecaOriginal);
		juntar(pecaSubstituta);
	}
	
	/**
	 * 
	 * @param peca
	 */
	public boolean removerPeca(final Peca peca) {
		Validate.notNull(peca, "peticao.peca.required");
	
		return pecas.remove(peca);
	}

	public ClasseId classeProcessual() {
		return classeProcessual;
	}
	
	public String motivoRejeicao() {
		return motivoRejeicao;
	}
	
	public String motivoDevolucao() {
		return motivoDevolucao;
	}
	
	public TipoProcesso tipoProcesso() {
		return tipoProcesso;
	}
	
	public Set<Preferencia> preferencias(){
		return Collections.unmodifiableSet(preferencias);
	}

	public void atribuirPreferencias(final Set<Preferencia> preferencias) {
		Validate.notEmpty(preferencias, "peticao.preferencias.required");
		
		this.preferencias.addAll(preferencias);
	}
	
	public void removerPreferencias(final Set<PreferenciaId> preferencias) {
		Validate.notEmpty(preferencias, "peticao.preferencias.required");
		
		Iterator<Preferencia> preferenciaIterator = this.preferencias.iterator();
		
		while(preferenciaIterator.hasNext()) {
			Preferencia preferencia = preferenciaIterator.next();
			
			if (preferencias.contains(preferencia.toLong())) {
				preferenciaIterator.remove();
			}
		}
	}

	/**
	 * 
	 * @param classeProcessual
	 */
	public void aceitar(final ClasseId classeProcessual) {
		Validate.notNull(classeProcessual, "peticao.classeProcessual.required");
		Validate.notNull(classeSugerida, "peticao.aceitar.classeSugerida.invalid");
		
		this.classeProcessual = classeProcessual;
	}

	/**
	 * 
	 * @param motivoRejeicao
	 */
	public void rejeitar(final String motivoRejeicao) {
		Validate.notBlank(motivoRejeicao, "peticao.motivoRejeicao.required");
		Validate.notNull(classeSugerida, "peticao.rejeitar.classeSugerida.invalid");
		Validate.isTrue(classeProcessual == null, "peticao.rejeitar.classeProcessual.invalid");
	
		this.motivoRejeicao = motivoRejeicao;
	}
	
	/**
	 * Registra o motivo da devolução de uma petição
	 * 
	 * @param motivoDevolucao Descrição do motivo da devolução da petição.
	 */
	public void devolver(final String motivoDevolucao) {
		Validate.notBlank(motivoDevolucao, "peticao.motivoDevolucao.required");
			
		this.motivoDevolucao = motivoDevolucao;
	}

	/**
	 * @param ministroRelator
	 * @param processoRepository
	 * @return o Processo
	 */
	public Processo distribuir(final MinistroId relator) {
		Validate.notNull(relator, "peticao.ministroRelator.required");
		Validate.notNull(classeProcessual, "peticao.distribuir.classeProcessual.invalid");
		
		return ProcessoFactory.criarProcesso(classeProcessual, relator, partes, pecas, id, tipoProcesso);
	}

	public Set<ProcessoWorkflow> processosWorkflow() {
		return Collections.unmodifiableSet(processosWorkflow);
	}

	/**
	 * 
	 * @param processosWorkflow
	 */
	public void associarProcessoWorkflow(final ProcessoWorkflow processoWorkflow) {
		Validate.notNull(processoWorkflow, "peticao.processoWorkflow.required");
	
		this.processosWorkflow.add(processoWorkflow);
	}
	
	public boolean isEletronica() {
		return this.getClass().equals(PeticaoEletronica.class);
	}
	
	public abstract boolean hasRepresentacao();
	
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
		return new HashCodeBuilder().append(id).hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		
		if (obj == null || !(obj instanceof Peticao)) {
			return false;
		}
		
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
	
}
