package br.jus.stf.processamentoinicial.distribuicao.domain.model;

import java.util.Date;

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
import javax.persistence.Transient;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import br.jus.stf.shared.MinistroId;
import br.jus.stf.shared.PeticaoId;
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
	@Column(nullable = false)
	private PeticaoId peticaoId;
	
	@Column(name = "DSC_JUSTIFICATIVA")
	private String justificativa;
	
	@Column(name = "DAT_DISTRIBUICAO", nullable = false)
	private Date dataDistribuicao;
	
	@Column(name = "SIG_USUARIO_DISTRIBUICAO", nullable = false)
	private String usuarioDistribuicao;
	
	@Transient
	private Peticao peticao;
	
	Distribuicao() {
		
	}
	
	protected Distribuicao(ParametroDistribuicao parametroDistribuicao) {
		Validate.notBlank(parametroDistribuicao.usuarioDistribuicao(), "distribuicao.usuarioDistribuicao.required");
		Validate.notNull(parametroDistribuicao.peticao(), "distribuicao.peticao.required");
		
		this.peticao = parametroDistribuicao.peticao();
		this.peticaoId = this.peticao.id();
		this.justificativa = parametroDistribuicao.justificativa();
		this.dataDistribuicao = new Date();
		this.usuarioDistribuicao = parametroDistribuicao.usuarioDistribuicao();
	}
	
	public abstract TipoDistribuicao tipo();
	
	protected abstract MinistroId sorteio();
	
	public Long toLong() {
		return this.sequencial;
	}
	
	public PeticaoId peticaoid() {
		return this.peticaoId;
	}
	
	public String justificativa() {
		return this.justificativa;
	}
	
	public Date dataDistribuicao() {
		return this.dataDistribuicao;
	}
	
	public String usuarioDistribuicao() {
		return this.usuarioDistribuicao;
	}
	
	public Processo executar() {
		return ProcessoFactory.criarProcesso(this.peticao.classeProcessual(), sorteio(), this.peticao.partes(), this.peticao.pecas(), this.peticaoId);
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
