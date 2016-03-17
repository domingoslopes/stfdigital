package br.jus.stf.processamentoinicial.recursaledistribuicao.domain.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import br.jus.stf.processamentoinicial.suporte.domain.ControladorOrdenacaoPecas;
import br.jus.stf.processamentoinicial.suporte.domain.model.Parte;
import br.jus.stf.processamentoinicial.suporte.domain.model.Peca;
import br.jus.stf.processamentoinicial.suporte.domain.model.Situacao;
import br.jus.stf.processamentoinicial.suporte.domain.model.TipoPeca;
import br.jus.stf.processamentoinicial.suporte.domain.model.TipoPolo;
import br.jus.stf.processamentoinicial.suporte.domain.model.TipoProcesso;
import br.jus.stf.processamentoinicial.suporte.domain.model.Visibilidade;
import br.jus.stf.shared.ClasseId;
import br.jus.stf.shared.MinistroId;
import br.jus.stf.shared.PeticaoId;
import br.jus.stf.shared.PreferenciaId;
import br.jus.stf.shared.ProcessoId;
import br.jus.stf.shared.stereotype.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 14.08.2015
 */
@javax.persistence.Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TIP_PROCESSO")
@Table(name = "PROCESSO", schema = "AUTUACAO", uniqueConstraints = @UniqueConstraint(columnNames = {"SIG_CLASSE", "NUM_PROCESSO"}))
public abstract class Processo implements Entity<Processo, ProcessoId> {

	@EmbeddedId
	private ProcessoId id;
	
	@Embedded
	@Column(nullable = false)
	private ClasseId classe;
	
	@Column(name = "NUM_PROCESSO", nullable = false)
	private Long numero;
	
	@Embedded
	@AttributeOverride(name = "codigo",
		column = @Column(name = "COD_MINISTRO_RELATOR"))
	private MinistroId relator;

	@Embedded
	@Column(nullable = false)
	private PeticaoId peticao;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "SEQ_PROCESSO", nullable = false)
	private Set<ParteProcesso> partes = new HashSet<ParteProcesso>(0);
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, targetEntity = PecaProcesso.class)
	@JoinColumn(name = "SEQ_PROCESSO", nullable = false)
	@OrderBy("numeroOrdem ASC")
	private List<Peca> pecas = new LinkedList<Peca>();
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, targetEntity = Distribuicao.class)
	@JoinColumn(name = "SEQ_PROCESSO", referencedColumnName = "SEQ_PROCESSO", nullable = false)
	private Set<Distribuicao> distribuicoes = new HashSet<Distribuicao>(0);
	
	@Column(name = "TIP_SITUACAO")
	@Enumerated(EnumType.STRING)
	private ProcessoSituacao situacao;
	
	@Transient
	private String identificacao;
	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "PROCESSO_PREFERENCIA", schema = "AUTUACAO", joinColumns = @JoinColumn(name = "SEQ_PROCESSO", nullable = false))
	private Set<PreferenciaId> preferencias = new HashSet<PreferenciaId>(0);
	
	@Transient
	private ControladorOrdenacaoPecas controladorOrdenacaoPecas;
	
	@OneToMany(cascade = CascadeType.ALL, targetEntity = PecaProcessoOriginal.class)
	@JoinColumn(name = "SEQ_PROCESSO", nullable = false)
	@JsonIgnore
	private List<PecaProcessoOriginal> pecasOriginaisVinculadas = new LinkedList<PecaProcessoOriginal>();
	
	Processo() {

	}
	
	/**
	 * @param classe
	 * @param numero
	 * @param peticao
	 * @param situacao
	 */
	protected Processo(final ProcessoId id, final ClasseId classe, final Long numero, final PeticaoId peticao, final ProcessoSituacao situacao, final Set<PreferenciaId> preferencias) {
		Validate.notNull(id, "processo.id.required");
		Validate.notNull(classe, "processo.classe.required");
		Validate.notNull(numero, "processo.numero.required");
		Validate.notNull(peticao, "processo.peticao.required");
		Validate.notNull(situacao, "processo.situacao.required");
		
		this.id = id;
		this.classe = classe;
		this.numero = numero;
		this.peticao = peticao;
		this.situacao = situacao;

		Optional.ofNullable(preferencias).ifPresent(prefs -> {
			if (!prefs.isEmpty()) {
				atribuirPreferencias(prefs);
			}
		});
		
		this.identificacao = montarIdentificacao();
		
		this.controladorOrdenacaoPecas = new ControladorOrdenacaoPecas(this.pecas);
	}

	/**
	 * @param classe
	 * @param numero
	 * @param relator
	 * @param peticao
	 * @param partes
	 * @param documentos
	 */
	protected Processo(final ProcessoId id, final ClasseId classe, final Long numero, final PeticaoId peticao, final Set<ParteProcesso> partes, final Set<PecaProcesso> pecas, final ProcessoSituacao situacao, final Set<PreferenciaId> preferencias) {
		this(id, classe, numero, peticao, situacao, preferencias);
		this.partes.addAll(partes);
		this.identificacao = montarIdentificacao();
		
		this.controladorOrdenacaoPecas = new ControladorOrdenacaoPecas(this.pecas);
		pecas.forEach(p -> adicionarPeca(p));
	}
	
	public abstract TipoProcesso tipoProcesso();
	
	@PostLoad
	@PostPersist
	@PostUpdate
	private void init() {
		this.identificacao = montarIdentificacao();
		this.controladorOrdenacaoPecas = new ControladorOrdenacaoPecas(this.pecas);
	}
	
	@Override
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
	
	public Set<Distribuicao> distribuicoes() {
		return Collections.unmodifiableSet(this.distribuicoes);
	}
	
	public ProcessoSituacao situacao() {
		return situacao;
	}
	
	/**
	 * Associa o ministro relator ao processo
	 * 
	 * @param relator
	 */
	public void associarRelator(MinistroId relator) {
		Validate.notNull(relator, "processo.relator.required");
		
		this.relator = relator;
	}
	
	/**
	 * Adiciona uma parte ao processo
	 * 
	 * @param parte
	 */
	public boolean adicionarParte(final ParteProcesso parte){
		Validate.notNull(parte, "processo.parte.required");
		
		return partes.add(parte);
	}
	
	/**
	 * 
	 * @param parte
	 */
	public boolean removerParte(final ParteProcesso parte){
		Validate.notNull(parte, "processo.parte.required");
		
		return partes.remove(parte);
	}

	/**
	 * 
	 * @param peca
	 */
	public boolean adicionarPeca(final Peca peca){
		Validate.notNull(peca, "processo.peca.required");
	
		controladorOrdenacaoPecas.numerarPeca(peca);
		
		return pecas.add(peca);
	}
	
	/**
	 * Altera a situação da peça no processo para JUNTADA.
	 * 
	 * @param peca
	 */
	public void juntarPeca(final Peca peca) {
		Validate.notNull(peca, "processo.peca.required");
		Validate.isTrue(peca.situacao() == Situacao.PENDENTE_JUNTADA, "processo.peca.situacao.invalid");
		
		peca.juntar();
	}

	/**
	 * Realiza a substituição de uma peça por outra,
	 * o número de ordem da peça substituta será o mesmo
	 * da peça original.
	 * 
	 * @param pecaOriginal
	 * @param pecaSubstituta
	 */
	public void substituirPeca(Peca pecaOriginal, Peca pecaSubstituta) {
		Validate.notNull(pecaOriginal, "processo.pecaOriginal.required");
		Validate.notNull(pecaSubstituta, "processo.pecaSubstituta.required");
		
		controladorOrdenacaoPecas.substituirPeca(pecaOriginal, pecaSubstituta);
	}
	
	/**
	 * Edita as informações de uma peça do processo.
	 * 
	 * @param pecaOriginal
	 * @param tipoPeca
	 * @param descricao
	 * @param numeroOrdem
	 * @param visibilidade
	 */
	public void editarPeca(Peca pecaOriginal, TipoPeca tipoPeca, String descricao, Long numeroOrdem, Visibilidade visibilidade) {
		Validate.notNull(pecaOriginal, "processo.pecaModificada.required");
		Validate.notNull(tipoPeca, "processo.tipoPeca.required");
		Validate.notBlank(descricao, "processo.descricao.required");
		Validate.notNull(numeroOrdem, "processo.numeroOrdem.required");
		Validate.notNull(visibilidade, "processo.visibilidade.required");
		Validate.isTrue(numeroOrdem <= controladorOrdenacaoPecas.ultimoNumeroOrdemPeca() &&
				numeroOrdem >= controladorOrdenacaoPecas.primeiroNumeroOrdemPeca(), "processo.peca.numeroOrdem.invalid");
		
		if (!pecaOriginal.numeroOrdem().equals(numeroOrdem)) {
			controladorOrdenacaoPecas.reordenarPeca(pecaOriginal, numeroOrdem);
		}
		
		pecaOriginal.editar(tipoPeca, descricao, visibilidade);
	}
	
	/**
	 * Divide uma peça, substituindo-a pelas peças especificadas.
	 * 
	 * @param pecaDividida
	 * @param pecasDivisao
	 */
	public void dividirPeca(PecaProcesso pecaDividida, List<Peca> pecasDivisao) {
		Validate.notNull(pecaDividida, "processo.pecaDividida.required");
		Validate.notEmpty(pecasDivisao, "processo.pecasDivisao.required");

		Long numeroOrdem = pecaDividida.numeroOrdem();
		PecaProcessoOriginal pecaOriginal = PecaProcessoOriginal.criar(pecaDividida, TipoVinculo.DIVISAO);
		
		pecasDivisao.forEach(p -> ((PecaProcesso)p).vincularPecaOriginal(pecaOriginal));
		pecasOriginaisVinculadas.add(pecaOriginal);
		pecas.remove(pecaDividida);
		pecasDivisao.forEach(p -> adicionarPeca(p));
		controladorOrdenacaoPecas.reordenarPecas(pecasDivisao, numeroOrdem);
	}
	
	/**
	 * Realiza a união da lista de peças, sendo substutuídas pela
	 * peça especificada.
	 * 
	 * @param pecasUniao
	 * @param pecaUnida
	 */
	public void unirPecas(List<PecaProcesso> pecasUniao, PecaProcesso pecaUnida) {
		Validate.notEmpty(pecasUniao, "processo.pecasUniao.required");
		Validate.notNull(pecaUnida, "processo.pecaUnida.required");
		
		Long menorNumeroOrdem = pecasUniao.stream().min((p1, p2) -> p1.numeroOrdem().compareTo(p2.numeroOrdem())).get().numeroOrdem();
		
		pecasUniao.forEach(p -> {
			PecaProcessoOriginal pecaOriginal = PecaProcessoOriginal.criar(p, TipoVinculo.UNIAO);
			
			pecaUnida.vincularPecaOriginal(pecaOriginal);
			pecasOriginaisVinculadas.add(pecaOriginal);
			pecas.remove(p);
		});
		
		adicionarPeca(pecaUnida);
		controladorOrdenacaoPecas.reordenarPeca(pecaUnida, menorNumeroOrdem);
	}
	
	/**
	 * Marca uma peça como removida.
	 * 
	 * @param peca
	 */
	public void removerPeca(final Peca peca){
		Validate.notNull(peca, "processo.peca.required");
		Validate.isTrue(peca.situacao() != Situacao.EXCLUIDA, "processo.peca.situacao.invalid");
	
		peca.excluir();
	}
	
	/**
	 * @param pecasOrganizadas
	 */
	public void organizarPecas(final List<Long> pecasOrganizadas) {
		Validate.notNull(pecasOrganizadas, "processo.pecasOrganizadas.required");
		Validate.isTrue(pecas.size() == pecasOrganizadas.size(), "processo.pecasOrganizadas.invalid");
		
		for(Peca p : pecas) {
			p.numerarOrdem(Long.valueOf(pecasOrganizadas.indexOf(p.toLong()) + 1));
		}
		
		controladorOrdenacaoPecas.ordenarPecas();
	}

	public List<Peca> pecas() {
		return Collections.unmodifiableList(pecas);
	}
	
	public List<PecaProcessoOriginal> pecasOriginaisVinculadas() {
		return Collections.unmodifiableList(pecasOriginaisVinculadas);
	}
	
	public Set<PreferenciaId> preferencias(){
		return Collections.unmodifiableSet(preferencias);
	}
	
	public void atribuirPreferencias(final Set<PreferenciaId> preferencias) {
		Validate.notNull(preferencias, "processo.preferencias.required");
		
		this.preferencias.addAll(preferencias);
		this.preferencias.retainAll(preferencias);
	}
	
	public void atribuirPartes(final Set<ParteProcesso> partes, final TipoPolo polo) {
		Validate.notNull(partes, "processo.partes.required");
		Validate.notNull(polo, "processo.partesPoloAtivo.required");
		
		this.partes.removeIf(parte -> polo.equals(parte.polo()) && !partes.contains(parte));
		this.partes.addAll(partes);
	}
	
	public String identificacao() {
		return Optional.ofNullable(identificacao)
				.orElse(identificacao = montarIdentificacao());
	}
	
	/**
	 * Verifica se o processo é criminal e/ou eleitoral
	 * 
	 * @return
	 */
	public boolean isCriminalEleitoral() {
		// TODO: Melhorar forma de implementação dessa verificação.
		return preferencias.contains(new PreferenciaId(2L)) ||
				preferencias.contains(new PreferenciaId(3L));
	}
	
	/**
	 * Associa uma distribuição ao processo
	 * 
	 * @param distribuicao
	 */
	public void associarDistribuicao(final Distribuicao distribuicao){
		Validate.notNull(distribuicao, "processo.distribuicao.required");
		
		distribuicoes.add(distribuicao);
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(id).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		
		Processo other = (Processo) obj;
		return sameIdentityAs(other);
	}

	@Override
	public boolean sameIdentityAs(Processo other) {
		return other != null && this.id.sameValueAs(other.id);
	}
	
	/**
	 * Define a situacao do processo como A_AUTUAR
	 */
	protected void aAutuar() {
		situacao = ProcessoSituacao.A_AUTUAR;
	}
	
	private String montarIdentificacao() {
		return new StringBuilder()
			.append(classe.toString()).append(" ").append(numero).toString();
	}
	
}
