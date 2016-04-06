package br.jus.stf.processamentoinicial.recursaledistribuicao.domain.model;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import br.jus.stf.processamentoinicial.suporte.domain.model.MeioTramitacao;
import br.jus.stf.processamentoinicial.suporte.domain.model.Sigilo;
import br.jus.stf.shared.ClasseId;
import br.jus.stf.shared.MinistroId;
import br.jus.stf.shared.PeticaoId;
import br.jus.stf.shared.PreferenciaId;
import br.jus.stf.shared.stereotype.ValueObject;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TIP_DISTRIBUICAO")
@Table(name = "DISTRIBUICAO", schema = "AUTUACAO")
public abstract class Distribuicao implements ValueObject<Distribuicao> {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "SEQ_DISTRIBUICAO")
	@SequenceGenerator(name = "DISTRIBUICAOID", sequenceName = "AUTUACAO.SEQ_DISTRIBUICAO", allocationSize = 1)
	@GeneratedValue(generator = "DISTRIBUICAOID", strategy=GenerationType.SEQUENCE)
	private Long sequencial;
	
	@Embedded
	private PeticaoId peticaoId;
	
	@Column(name = "DSC_JUSTIFICATIVA")
	private String justificativa;
	
	@Column(name = "DAT_DISTRIBUICAO", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataDistribuicao;
	
	@Column(name = "SIG_USUARIO_DISTRIBUICAO", nullable = false)
	private String usuarioDistribuicao;
	
	@Transient
	private ClasseId classeProcessual;
	
	@Transient
	private Set<PreferenciaId> preferencias;
	
	@Transient
	private MeioTramitacao meioTramitacao;
	
	@Transient
	private Sigilo sigilo;
	
	@Transient
	private Long quantidadeRecursos;
	
	Distribuicao() {
		
	}
	
	protected Distribuicao(ParametroDistribuicao parametroDistribuicao) {
		Validate.notBlank(parametroDistribuicao.usuarioDistribuicao(), "distribuicao.usuarioDistribuicao.required");
		
		this.usuarioDistribuicao = parametroDistribuicao.usuarioDistribuicao();
		this.justificativa = parametroDistribuicao.justificativa();
		this.dataDistribuicao = new Date();
		
		this.peticaoId = parametroDistribuicao.peticaoId();
		
		this.classeProcessual = parametroDistribuicao.classeProcessual();
		this.preferencias = parametroDistribuicao.preferencias();
		this.meioTramitacao = parametroDistribuicao.meioTramitacao();
		this.sigilo = parametroDistribuicao.sigilo();
		this.quantidadeRecursos = parametroDistribuicao.quantidadeRecursos();
	}
	
	public abstract TipoDistribuicao tipo();
	
	protected abstract MinistroId sorteio();
	
	public Long toLong() {
		return sequencial;
	}
	
	public String justificativa() {
		return justificativa;
	}
	
	public Date dataDistribuicao() {
		return dataDistribuicao;
	}
	
	public String usuarioDistribuicao() {
		return usuarioDistribuicao;
	}
	
	public static Distribuicao criar(ParametroDistribuicao parametroDistribuicao) {
		Distribuicao distribuicao = null;
		TipoDistribuicao tipoDistribuicao = parametroDistribuicao.tipoDistribuicao();
		
		switch(tipoDistribuicao) {
			case COMUM:
				distribuicao = new DistribuicaoComum(parametroDistribuicao);
				break;
			case PREVENCAO:
				distribuicao = new DistribuicaoPrevencao(parametroDistribuicao);
				break;
			default:
				throw new IllegalArgumentException("Tipo de distribuição inexistente: " + tipoDistribuicao);
		}
		return distribuicao;
	}
	
	public Processo executar() {
		Processo processo = Optional.ofNullable(peticaoId).isPresent() ? ProcessoFactory.criar(peticaoId) : null;
		
		if(processo == null) {
			processo = ProcessoFactory.criarProcessoRecursal(classeProcessual, preferencias, meioTramitacao, sigilo, quantidadeRecursos);
		}
		
		processo.associarRelator(sorteio());
		processo.associarDistribuicao(this);
		return processo;
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(sequencial).toHashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
	    if (this == obj) {
	    	return true;
	    }
	    
	    if (obj == null || !(obj instanceof Distribuicao)) {
	    	return false;
	    }
	    
	    final Distribuicao other = (Distribuicao) obj;
	    return sameValueAs(other);
	}
	
	@Override
	public boolean sameValueAs(final Distribuicao other){
		return other != null && this.sequencial.equals(other.sequencial);
	}
	
}
