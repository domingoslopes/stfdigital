package br.jus.stf.processamentoinicial.distribuicao.domain.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang3.Validate;

import br.jus.stf.processamentoinicial.autuacao.domain.model.Parte;
import br.jus.stf.processamentoinicial.autuacao.domain.model.ParteProcesso;
import br.jus.stf.processamentoinicial.autuacao.domain.model.Peca;
import br.jus.stf.processamentoinicial.autuacao.domain.model.PecaProcesso;
import br.jus.stf.processamentoinicial.autuacao.domain.model.TipoPolo;
import br.jus.stf.shared.ClasseId;
import br.jus.stf.shared.MinistroId;
import br.jus.stf.shared.PeticaoId;
import br.jus.stf.shared.ProcessoId;
import br.jus.stf.shared.stereotype.Entity;

/**
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 14.08.2015
 */
@javax.persistence.Entity
@Table(name = "PROCESSO", schema = "AUTUACAO", uniqueConstraints = @UniqueConstraint(columnNames = {"SIG_CLASSE", "NUM_PROCESSO"}))
public class Processo implements Entity<Processo, ProcessoId> {

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
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, targetEntity = ParteProcesso.class)
	@JoinColumn(name = "SEQ_PROCESSO", nullable = false)
	private Set<Parte> partes = new HashSet<Parte>(0);
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, targetEntity = PecaProcesso.class)
	@JoinColumn(name = "SEQ_PROCESSO", nullable = false)
	private Set<Peca> pecas = new LinkedHashSet<Peca>(0); // Para utilizar TreeSet Peca deve implementar Comparable
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, targetEntity = Distribuicao.class)
	@JoinColumn(name = "SEQ_PROCESSO", referencedColumnName = "SEQ_PROCESSO", nullable = false)
	private Set<Distribuicao> distribuicoes = new HashSet<Distribuicao>(0);
	
	@Column(name = "TIP_SITUACAO")
	@Enumerated(EnumType.STRING)
	private ProcessoSituacao situacao;
	
	@Transient
	private String identificacao;
	
	Processo() {

	}

	/**
	 * @param classe
	 * @param numero
	 * @param relator
	 * @param peticao
	 * @param partes
	 * @param documentos
	 */
	public Processo(final ProcessoId id, final ClasseId classe, final Long numero, final MinistroId relator, final PeticaoId peticao, final Set<ParteProcesso> partes, final Set<PecaProcesso> pecas, final ProcessoSituacao situacao) {
		Validate.notNull(id, "processo.id.required");
		Validate.notNull(classe, "processo.classe.required");
		Validate.notNull(numero, "processo.numero.required");
		Validate.notNull(relator, "processo.relator.required");
		Validate.notNull(peticao, "processo.peticao.required");
		Validate.notNull(situacao, "processo.situacao.required");
		
		this.id = id;
		this.classe = classe;
		this.numero = numero;
		this.relator = relator;
		this.peticao = peticao;
		this.partes.addAll(partes);
		this.pecas.addAll(pecas);
		this.identificacao = montarIdentificacao();
		this.situacao = situacao;
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
	
	public Set<Distribuicao> distribuicoes() {
		return Collections.unmodifiableSet(this.distribuicoes);
	}
	
	public ProcessoSituacao situacao() {
		return this.situacao;
	}
	
	/**
	 * 
	 * @param distribuicao
	 */
	public boolean associarDistribuicao(final Distribuicao distribuicao){
		Validate.notNull(distribuicao, "processo.distribuicao.required");
		
		return this.distribuicoes.add(distribuicao);
	}
	
	/**
	 * 
	 * @param parte
	 */
	public boolean adicionarParte(final Parte parte){
		Validate.notNull(parte, "processo.parte.required");
		
		return this.partes.add(parte);
	}
	
	/**
	 * 
	 * @param parte
	 */
	public boolean removerParte(final Parte parte){
		Validate.notNull(parte, "processo.parte.required");
		
		return this.partes.remove(parte);
	}

	/**
	 * 
	 * @param peca
	 */
	public boolean adicionarPeca(final Peca peca){
		Validate.notNull(peca, "processo.peca.required");
	
		return this.pecas.add(peca);
	}
	
	/**
	 * 
	 * @param peca
	 */
	public boolean removerPeca(final Peca peca){
		Validate.notNull(peca, "processo.peca.required");
	
		return this.pecas.remove(peca);
	}

	public Set<Peca> pecas() {
		return Collections.unmodifiableSet(pecas);
	}
	
	public String identificacao() {
		return Optional.ofNullable(identificacao)
				.orElse(identificacao = montarIdentificacao());
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
			.append(classe.toString()).append(" ").append(numero).toString();
	}

}
