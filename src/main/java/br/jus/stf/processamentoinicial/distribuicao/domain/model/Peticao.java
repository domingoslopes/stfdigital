package br.jus.stf.processamentoinicial.distribuicao.domain.model;

import java.util.Collections;
import java.util.Set;

import org.apache.commons.lang3.Validate;

import br.jus.stf.processamentoinicial.autuacao.domain.model.Parte;
import br.jus.stf.processamentoinicial.autuacao.domain.model.Peca;
import br.jus.stf.processamentoinicial.suporte.domain.model.TipoProcesso;
import br.jus.stf.shared.ClasseId;
import br.jus.stf.shared.PeticaoId;
import br.jus.stf.shared.ProcessoWorkflowId;
import br.jus.stf.shared.stereotype.ValueObject;

public class Peticao implements ValueObject<Peticao> {
	
	private static final long serialVersionUID = 1L;
	
	private PeticaoId id;
	private ClasseId classeProcessual;
	private String tipo;
	private Set<Parte> partes;
	private Set<Peca> pecas;
	private ProcessoWorkflowId processoWorkflowId;
	private TipoProcesso tipoProcesso;
	
	public Peticao(final PeticaoId id, final ClasseId classeProcessual, final String tipo, final Set<Parte> partes, final Set<Peca> pecas, final ProcessoWorkflowId processoWorkflowId, final TipoProcesso tipoProcesso) {
		Validate.notNull(id, "peticao.id.required");
		Validate.notNull(classeProcessual, "peticao.classeProcessual.required");
		Validate.notBlank(tipo, "peticao.tipo.required");
		Validate.notNull(processoWorkflowId, "peticao.processoWorkflowId.required");
		Validate.notNull(tipoProcesso, "peticao.tipoProcesso.required");
		
		this.id = id;
		this.classeProcessual = classeProcessual;
		this.tipo = tipo;
		this.partes = partes;
		this.pecas = pecas;
		this.processoWorkflowId = processoWorkflowId;
		this.tipoProcesso = tipoProcesso;
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
		return Collections.unmodifiableSet(this.partes);
	}
	
	public Set<Peca> pecas() {
		return Collections.unmodifiableSet(this.pecas);
	}
	
	public ProcessoWorkflowId processoWorkflowId() {
		return this.processoWorkflowId;
	}
	
	public TipoProcesso tipoProcesso() {
		return tipoProcesso;
	}
	
	@Override
	public boolean sameValueAs(Peticao other) {
		return this.id.sameValueAs(other.id);
	}

}
