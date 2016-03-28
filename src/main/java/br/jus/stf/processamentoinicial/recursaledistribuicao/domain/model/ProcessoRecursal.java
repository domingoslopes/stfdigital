package br.jus.stf.processamentoinicial.recursaledistribuicao.domain.model;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.apache.commons.lang3.Validate;

import br.jus.stf.processamentoinicial.suporte.domain.model.Classificacao;
import br.jus.stf.processamentoinicial.suporte.domain.model.MeioTramitacao;
import br.jus.stf.processamentoinicial.suporte.domain.model.Sigilo;
import br.jus.stf.processamentoinicial.suporte.domain.model.TipoPolo;
import br.jus.stf.processamentoinicial.suporte.domain.model.TipoProcesso;
import br.jus.stf.shared.AssuntoId;
import br.jus.stf.shared.ClasseId;
import br.jus.stf.shared.PeticaoId;
import br.jus.stf.shared.PreferenciaId;
import br.jus.stf.shared.ProcessoId;
import br.jus.stf.shared.TeseId;

/**
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 13.01.2016
 */
@Entity
@DiscriminatorValue("RECURSAL")
public class ProcessoRecursal extends Processo {
	
	@ElementCollection
	@CollectionTable(name = "PROCESSO_ASSUNTO", schema = "AUTUACAO", joinColumns = @JoinColumn(name = "SEQ_PROCESSO", nullable = false))
	private Set<AssuntoId> assuntos = new HashSet<AssuntoId>(0);
	
	@ElementCollection
	@CollectionTable(name = "PROCESSO_TESE", schema = "AUTUACAO", joinColumns = @JoinColumn(name = "SEQ_PROCESSO", nullable = false))
	private Set<TeseId> teses = new HashSet<TeseId>(0);
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, targetEntity = MotivoInaptidaoProcesso.class)
	@JoinColumn(name = "SEQ_PROCESSO", nullable = false)
	private Set<MotivoInaptidaoProcesso> motivosInaptidao = new HashSet<MotivoInaptidaoProcesso>(0);
	
	@Column(name = "DSC_ANALISE")
	private String observacaoAnalise;
	
	@Column(name = "TIP_CLASSIFICACAO")
	@Enumerated(EnumType.STRING)
	private Classificacao classificacao;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, targetEntity = Origem.class)
	@JoinColumn(name = "SEQ_PROCESSO", nullable = false)
	private Set<Origem> origens = new HashSet<Origem>(0);
	
	@Column(name = "QTD_RECURSO")
	private Long quantidadeRecursos;

	ProcessoRecursal() {

	}
	
	public ProcessoRecursal(final ProcessoId id, final ClasseId classe, final Long numero, final PeticaoId peticao,
			final Set<PreferenciaId> preferencias, final Date dataRecebimento, final MeioTramitacao meioTramitacao,
			final Sigilo sigilo, final Long quantidadeRecursos) {
		super(id, classe, numero, peticao, ProcessoSituacao.A_ANALISAR, preferencias, dataRecebimento,
				meioTramitacao, sigilo);
		
		Validate.isTrue(quantidadeRecursos >= 0, "processoRecursal.quantidadeRecursos.invalid");
		
		this.quantidadeRecursos = quantidadeRecursos;
		
		if(isCriminalEleitoral()) {
			super.aAutuar();
		}
	}

	@Override
	public TipoProcesso tipoProcesso() {
		return TipoProcesso.RECURSAL;
	}
	
	public Set<AssuntoId> assuntos(){
		return Collections.unmodifiableSet(assuntos);
	}

	public void atribuirAssuntos(final Set<AssuntoId> assuntos) {
		Validate.notEmpty(assuntos, "processoRecursal.assuntos.required");
		
		this.assuntos.retainAll(assuntos);
		this.assuntos.addAll(assuntos);
	}
	
	public Set<TeseId> teses(){
		return Collections.unmodifiableSet(teses);
	}

	public void atribuirTeses(final Set<TeseId> teses) {
		Validate.notEmpty(teses, "processoRecursal.teses.required");
		
		this.teses.retainAll(teses);
		this.teses.addAll(teses);
	}
	
	public Set<MotivoInaptidaoProcesso> motivosInaptidao(){
		return Collections.unmodifiableSet(motivosInaptidao);
	}
	
	public Classificacao classificacao() {
		return classificacao;
	}
	
	public String observacaoAnalise() {
		return observacaoAnalise;
	}
	
	public Set<Origem> origens(){
		return Collections.unmodifiableSet(origens);
	}
	
	public void atribuirOrigens(final Set<Origem> origens) {
		Validate.notEmpty(origens, "processoRecursal.origens.required");
		Validate.isTrue(origens.stream()
				.filter(origem -> origem.origemPrincipal())
				.count() == 1, "processoRecursal.origens.invalid");
		
		this.origens.retainAll(origens);
		this.origens.addAll(origens);
	}
	
	public Long quantidadeRecursos() {
		return quantidadeRecursos;
	}
	
	public void autuar(Set<AssuntoId> assuntos, Set<ParteProcesso> poloAtivo, Set<ParteProcesso> poloPassivo) {
		super.atribuirDataAutuacao(new Date());
		atribuirAssuntos(assuntos);
		atribuirPartes(poloAtivo, TipoPolo.POLO_ATIVO);
		atribuirPartes(poloPassivo, TipoPolo.POLO_PASSIVO);
	}
	
	public void analisarPressupostosFormais(Classificacao classificacao, String observacaoAnalise, Set<MotivoInaptidaoProcesso> motivosInaptidao) {
		Validate.notNull(classificacao, "processoRecursal.classificacao.required");
		
		if (Classificacao.INAPTO.equals(classificacao)) {
			Validate.notEmpty(motivosInaptidao, "processoRecursal.motivosInaptidao.required");
			
			this.motivosInaptidao.addAll(motivosInaptidao);
		}
		this.classificacao = classificacao;
		this.observacaoAnalise = observacaoAnalise;
	}
	
	public void analisarRepercussaoGeral(Set<AssuntoId> assuntos, Set<TeseId> teses, String observacaoAnalise) {
		atribuirAssuntos(assuntos);
		atribuirTeses(teses);
		this.observacaoAnalise = observacaoAnalise;
	}

}
