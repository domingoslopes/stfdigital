package br.jus.stf.processamentoinicial.recursaledistribuicao.domain.model;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.Validate;

import br.jus.stf.processamentoinicial.suporte.domain.model.MeioTramitacao;
import br.jus.stf.processamentoinicial.suporte.domain.model.Parte;
import br.jus.stf.processamentoinicial.suporte.domain.model.Peca;
import br.jus.stf.processamentoinicial.suporte.domain.model.Sigilo;
import br.jus.stf.processamentoinicial.suporte.domain.model.TipoProcesso;
import br.jus.stf.shared.ClasseId;
import br.jus.stf.shared.PeticaoId;
import br.jus.stf.shared.PreferenciaId;
import br.jus.stf.shared.ProcessoWorkflowId;
import br.jus.stf.shared.stereotype.ValueObject;

public class Peticao implements ValueObject<Peticao> {
	
	private static final long serialVersionUID = 1L;
	
	private PeticaoId id;
	private ClasseId classeProcessual;
	private String tipo;
	private Set<Parte> partes;
	private List<Peca> pecas;
	private ProcessoWorkflowId processoWorkflowId;
	private TipoProcesso tipoProcesso;
	private Set<PreferenciaId> preferencias;
	private Date dataCadastramento;
	private Date dataAutuacao;
	private MeioTramitacao meioTramitacao;
	private Sigilo sigilo;
	
	public Peticao(final PeticaoId id, final ClasseId classeProcessual, final String tipo, final Set<Parte> partes,
			final List<Peca> pecas, final ProcessoWorkflowId processoWorkflowId, final TipoProcesso tipoProcesso,
			final Set<PreferenciaId> preferencias, final Date dataCadastramento, final Date dataAutuacao,
			final MeioTramitacao meioTramitacao, final Sigilo sigilo) {
		Validate.notNull(id, "peticao.id.required");
		Validate.notNull(classeProcessual, "peticao.classeProcessual.required");
		Validate.notBlank(tipo, "peticao.tipo.required");
		Validate.notNull(processoWorkflowId, "peticao.processoWorkflowId.required");
		Validate.notNull(tipoProcesso, "peticao.tipoProcesso.required");
		Validate.notNull(dataCadastramento, "peticao.dataCadastramento.required");
		Validate.notNull(meioTramitacao, "peticao.meioTramitacao.required");
		Validate.notNull(sigilo, "peticao.sigilo.required");
		
		this.id = id;
		this.classeProcessual = classeProcessual;
		this.tipo = tipo;
		this.partes = partes;
		this.pecas = pecas;
		this.processoWorkflowId = processoWorkflowId;
		this.tipoProcesso = tipoProcesso;
		this.preferencias = preferencias;
		this.dataCadastramento = dataCadastramento;
		this.dataAutuacao = dataAutuacao;
		this.meioTramitacao = meioTramitacao;
		this.sigilo = sigilo;
	}
	
	public PeticaoId id() {
		return this.id;
	}
	
	public ClasseId classeProcessual() {
		return this.classeProcessual;
	}
	
	public String tipo() {
		return tipo;
	}

	public Set<Parte> partes() {
		return Collections.unmodifiableSet(partes);
	}
	
	public List<Peca> pecas() {
		return Collections.unmodifiableList(pecas);
	}
	
	public ProcessoWorkflowId processoWorkflowId() {
		return this.processoWorkflowId;
	}
	
	public TipoProcesso tipoProcesso() {
		return tipoProcesso;
	}
	
	public Set<PreferenciaId> preferencias() {
		return Collections.unmodifiableSet(preferencias);
	}
	
	public Date dataCadastramento() {
		return dataCadastramento;
	}
	
	public Date dataAutuacao() {
		return dataAutuacao;
	}
	
	public MeioTramitacao meioTramitacao() {
		return meioTramitacao;
	}
	
	public Sigilo sigilo() {
		return sigilo;
	}
	
	@Override
	public boolean sameValueAs(Peticao other) {
		return id.sameValueAs(other.id);
	}

}
