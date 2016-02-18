package br.jus.stf.processamentoinicial.recursaledistribuicao.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.jus.stf.processamentoinicial.suporte.domain.model.Peca;
import br.jus.stf.processamentoinicial.suporte.domain.model.Situacao;
import br.jus.stf.processamentoinicial.suporte.domain.model.TipoPeca;
import br.jus.stf.processamentoinicial.suporte.domain.model.Visibilidade;
import br.jus.stf.shared.DocumentoId;

@Entity
@Table(name = "PROCESSO_PECA", schema = "AUTUACAO")
public class PecaProcesso extends Peca {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "SEQ_PROCESSO_PECA")
	@SequenceGenerator(name = "PROCESSOPECAID", sequenceName = "AUTUACAO.SEQ_PROCESSO_PECA", allocationSize = 1)
	@GeneratedValue(generator = "PROCESSOPECAID", strategy = GenerationType.SEQUENCE)
	private Long sequencial;
	
	PecaProcesso() {
		
	}
	
	public PecaProcesso(final DocumentoId documento, final TipoPeca tipoPeca,
			final String descricao, final Visibilidade visibilidade, final Situacao situacao) {
		super(documento, tipoPeca, descricao, visibilidade, situacao);
	}
	
	public PecaProcesso(final DocumentoId documento, final TipoPeca tipoPeca,
			final String descricao, final Visibilidade visibilidade) {
		super(documento, tipoPeca, descricao, visibilidade);
	}
	
	public PecaProcesso(final DocumentoId documento, final TipoPeca tipoPeca,
			final String descricao, final Situacao situacao) {
		super(documento, tipoPeca, descricao, situacao);
	}
	
	public PecaProcesso(final DocumentoId documento, final TipoPeca tipoPeca,
			final String descricao) {
		super(documento, tipoPeca, descricao);
	}
	
	@Override
	public Long toLong() {
		return this.sequencial;
	}
	
}
