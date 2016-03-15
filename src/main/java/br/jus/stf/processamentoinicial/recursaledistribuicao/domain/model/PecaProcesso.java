package br.jus.stf.processamentoinicial.recursaledistribuicao.domain.model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang3.Validate;

import br.jus.stf.processamentoinicial.suporte.domain.model.Peca;
import br.jus.stf.processamentoinicial.suporte.domain.model.Situacao;
import br.jus.stf.processamentoinicial.suporte.domain.model.TipoPeca;
import br.jus.stf.processamentoinicial.suporte.domain.model.Visibilidade;
import br.jus.stf.shared.DocumentoId;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Rafael.Alencar
 *
 */
@Entity
@Table(name = "PROCESSO_PECA", schema = "AUTUACAO")
public class PecaProcesso extends Peca {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "SEQ_PROCESSO_PECA")
	@SequenceGenerator(name = "PROCESSOPECAID", sequenceName = "AUTUACAO.SEQ_PROCESSO_PECA", allocationSize = 1)
	@GeneratedValue(generator = "PROCESSOPECAID", strategy = GenerationType.SEQUENCE)
	private Long sequencial;
	
	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "VINCULO_PECA_ORIGINAL", schema = "AUTUACAO",
	joinColumns = @JoinColumn(name = "SEQ_PROCESSO_PECA", nullable = false),
	inverseJoinColumns = @JoinColumn(name = "SEQ_PROCESSO_PECA_ORIGINAL", nullable = false))
	@JsonIgnore
	private List<PecaProcessoOriginal> pecasOriginaisVinculadas = new LinkedList<PecaProcessoOriginal>();
	
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
	
	public void atribuirSequencial(final Long sequencial) {
		Validate.notNull(sequencial, "pecaProcesso.sequencial.required");
		
		this.sequencial = sequencial;
	}
	
	@Override
	public Long toLong() {
		return this.sequencial;
	}
	
	/**
	 * Estabelece o vínculo entre a peça e a peça original.
	 * 
	 * @param pecaOriginal
	 */
	public void vincularPecaOriginal(final PecaProcessoOriginal pecaOriginal){
		Validate.notNull(pecaOriginal, "pecaProcesso.pecaOriginal.required");
		
		pecasOriginaisVinculadas.add(pecaOriginal);
	}
	
	public List<PecaProcessoOriginal> pecasOriginaisVinculadas() {
		return Collections.unmodifiableList(pecasOriginaisVinculadas);
	}
	
}
