package br.jus.stf.processamentoinicial.recursaledistribuicao.domain.model;

import java.util.Collections;
import java.util.Set;

import org.apache.commons.lang3.Validate;

import br.jus.stf.shared.MinistroId;
import br.jus.stf.shared.PeticaoId;
import br.jus.stf.shared.stereotype.ValueObject;

public class ParametroDistribuicao implements ValueObject<ParametroDistribuicao> {
	
	private static final long serialVersionUID = 1L;
	
	private TipoDistribuicao tipoDistribuicao;
	private PeticaoId peticaoId;
	private String justificativa;
	private String usuarioDistribuicao;
	private Set<MinistroId> ministrosCandidatos;
	private Set<MinistroId> ministrosImpedidos;
	private Set<Processo> processosPreventos;
	
	public ParametroDistribuicao(final TipoDistribuicao tipoDistribuicao, final PeticaoId peticaoId, 
			final String justificativa, final String usuarioDistribuicao,
			final Set<MinistroId> ministrosCandidatos, final Set<MinistroId> ministrosImpedidos,
			final Set<Processo> processosPreventos) {
		
		Validate.notNull(tipoDistribuicao, "parametroDistribuicao.tipoDistribuicao.required");
		Validate.notNull(peticaoId, "parametroDistribuicao.peticaoId.required");
		
		this.tipoDistribuicao = tipoDistribuicao;
		this.peticaoId = peticaoId;
		this.justificativa = justificativa;
		this.usuarioDistribuicao = usuarioDistribuicao;
		
		this.ministrosCandidatos = ministrosCandidatos;
		this.ministrosImpedidos = ministrosImpedidos;
		
		this.processosPreventos = processosPreventos;
	}
	
	public TipoDistribuicao tipoDistribuicao() {
		return tipoDistribuicao;
	}
	
	public PeticaoId peticaoId() {
		return this.peticaoId;
	}
	
	public String justificativa() {
		return this.justificativa;
	}
	
	public String usuarioDistribuicao() {
		return this.usuarioDistribuicao;
	}
	
	public Set<MinistroId> ministrosCandidatos() {
		return Collections.unmodifiableSet(this.ministrosCandidatos);
	}
	
	public Set<MinistroId> ministrosImpedidos() {
		return Collections.unmodifiableSet(this.ministrosImpedidos);
	}
	
	public Set<Processo> processosPreventos() {
		return Collections.unmodifiableSet(this.processosPreventos);
	}
	
	@Override
	public boolean sameValueAs(final ParametroDistribuicao other){
		return this.equals(other);
	}

}
