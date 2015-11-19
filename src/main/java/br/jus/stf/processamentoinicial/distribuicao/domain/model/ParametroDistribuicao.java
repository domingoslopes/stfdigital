package br.jus.stf.processamentoinicial.distribuicao.domain.model;

import java.util.Collections;
import java.util.Set;

import br.jus.stf.shared.MinistroId;
import br.jus.stf.shared.stereotype.ValueObject;

public class ParametroDistribuicao implements ValueObject<ParametroDistribuicao> {
	
	private static final long serialVersionUID = 1L;
	
	private Peticao peticao;
	private String justificativa;
	private String usuarioDistribuicao;
	
	private Set<MinistroId> ministrosCanditatos;
	private Set<MinistroId> ministrosImpedidos;
	
	private Set<Processo> processosPreventos;
	
	public ParametroDistribuicao(final Peticao peticao, final String justificativa, final String usuarioDistribuicao,
			final Set<MinistroId> ministrosCanditatos, final Set<MinistroId> ministrosImpedidos,
			final Set<Processo> processosPreventos) {
		this.peticao = peticao;
		this.justificativa = justificativa;
		this.usuarioDistribuicao = usuarioDistribuicao;
		
		this.ministrosCanditatos = ministrosCanditatos;
		this.ministrosImpedidos = ministrosImpedidos;
		
		this.processosPreventos = processosPreventos;
	}
	
	public Peticao peticao() {
		return this.peticao;
	}
	
	public String justificativa() {
		return this.justificativa;
	}
	
	public String usuarioDistribuicao() {
		return this.usuarioDistribuicao;
	}
	
	public Set<MinistroId> ministrosCanditatos() {
		return Collections.unmodifiableSet(this.ministrosCanditatos);
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
