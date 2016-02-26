package br.jus.stf.processamentoinicial.recursaledistribuicao.domain.model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang3.Validate;

import br.jus.stf.processamentoinicial.suporte.domain.model.Peca;
import br.jus.stf.processamentoinicial.suporte.domain.model.Situacao;
import br.jus.stf.processamentoinicial.suporte.domain.model.TipoPeca;
import br.jus.stf.processamentoinicial.suporte.domain.model.Visibilidade;
import br.jus.stf.shared.DocumentoId;

/**
 * @author Rafael.Alencar
 *
 */
@Entity
@Table(name = "PROCESSO_PECA_ORIGINAL", schema = "AUTUACAO")
public class PecaProcessoOriginal extends Peca {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "SEQ_PROCESSO_PECA_ORIGINAL")
	@SequenceGenerator(name = "PROCESSOPECAORIGINALID", sequenceName = "AUTUACAO.SEQ_PROCESSO_PECA_ORIGINAL", allocationSize = 1)
	@GeneratedValue(generator = "PROCESSOPECAORIGINALID", strategy = GenerationType.SEQUENCE)
	private Long sequencial;
	
	@Column(name = "SEQ_PROCESSO_PECA", nullable = false)
	private Long sequencialPecaProcesso;
	
	@Column(name = "TIP_VINCULO", nullable = false)
	@Enumerated(EnumType.STRING)
	private TipoVinculo tipoVinculo;
	
	@ManyToMany(mappedBy = "pecasOriginaisVinculadas")
	private List<PecaProcesso> pecasVinculadas = new LinkedList<PecaProcesso>();
	
	PecaProcessoOriginal() {
		
	}
	
	private PecaProcessoOriginal(final Long sequencialPecaProcesso, final DocumentoId documento,
			final TipoPeca tipoPeca, final String descricao, final Long numeroOrdem, final Visibilidade visibilidade,
			final Situacao situacao, final TipoVinculo tipoVinculo) {
		super(documento, tipoPeca, descricao, visibilidade, situacao);
		
		Validate.notNull(sequencialPecaProcesso, "pecaProcessoOriginal.sequencialPecaProcesso.required");
		Validate.notNull(tipoVinculo, "pecaProcessoOriginal.tipoVinculo.required");
		Validate.notNull(numeroOrdem, "pecaProcessoOriginal.numeroOrdem.required");
		
		this.sequencialPecaProcesso = sequencialPecaProcesso;
		this.tipoVinculo = tipoVinculo;
		super.numerarOrdem(numeroOrdem);
	}
	
	public static PecaProcessoOriginal criar(final PecaProcesso peca, final TipoVinculo tipoVinculo) {
		return new PecaProcessoOriginal(peca.toLong(), peca.documento(), peca.tipo(), peca.descricao(),
		        peca.numeroOrdem(), peca.visibilidade(), peca.situacao(), tipoVinculo);
	}
	
	@Override
	public Long toLong() {
		return this.sequencial;
	}
	
	public Long sequencialPecaProcesso() {
		return sequencialPecaProcesso;
	}
	
	public TipoVinculo tipoVinculo() {
		return tipoVinculo;
	}
	
	public List<PecaProcesso> pecasVinculadas() {
		return Collections.unmodifiableList(pecasVinculadas);
	}
	
}
