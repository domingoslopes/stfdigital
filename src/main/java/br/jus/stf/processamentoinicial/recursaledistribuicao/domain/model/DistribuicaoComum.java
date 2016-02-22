package br.jus.stf.processamentoinicial.recursaledistribuicao.domain.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;

import org.apache.commons.lang3.Validate;

import br.jus.stf.shared.MinistroId;

@Entity
@DiscriminatorValue("COMUM")
public class DistribuicaoComum extends Distribuicao {

	private static final long serialVersionUID = 1L;
	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "DISTRIBUICAO_MIN_CANDIDATO", schema = "AUTUACAO", joinColumns = @JoinColumn(name = "SEQ_DISTRIBUICAO", nullable = false))
	private Set<MinistroId> ministrosCandidatos = new HashSet<MinistroId>(0);
	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "DISTRIBUICAO_MIN_IMPEDIDO", schema = "AUTUACAO", joinColumns = @JoinColumn(name = "SEQ_DISTRIBUICAO", nullable = false))
	private Set<MinistroId> ministrosImpedidos = new HashSet<MinistroId>(0);
	
	DistribuicaoComum() {
		
	}
	
	protected DistribuicaoComum(ParametroDistribuicao parametroDistribuicao) {
		super(parametroDistribuicao);
		
		Validate.notEmpty(parametroDistribuicao.ministrosCandidatos(), "distribuicao.ministrosCandidatos.required");
		Validate.notNull(parametroDistribuicao.ministrosImpedidos(), "distribuicao.ministrosImpedidos.required");
		Validate.isTrue(this.validarListasMinistros(parametroDistribuicao.ministrosCandidatos(), parametroDistribuicao.ministrosImpedidos()), "distribuicao.listasMinistros.invalid");
		
		this.ministrosCandidatos = parametroDistribuicao.ministrosCandidatos();
		this.ministrosImpedidos = parametroDistribuicao.ministrosImpedidos();
	}
	
	public Set<MinistroId> ministrosCandidatos() {
		return Collections.unmodifiableSet(this.ministrosCandidatos);
	}
	
	public Set<MinistroId> ministrosImpedidos() {
		return Collections.unmodifiableSet(this.ministrosImpedidos);
	}
	
	@Override
	public TipoDistribuicao tipo() {
		return TipoDistribuicao.COMUM;
	}
	
	@Override
	protected MinistroId sorteio() {
		int indice = new Random().nextInt(ministrosCandidatos.size());
		
		return (MinistroId)ministrosCandidatos.toArray()[indice];
	}
	
	private boolean validarListasMinistros(Set<MinistroId> candidatos, Set<MinistroId> impedidos) {
		Set<MinistroId> intersecaoCandidatoImpedido = new HashSet<MinistroId>(candidatos);
		Set<MinistroId> intersecaoImpedidoCandidato = new HashSet<MinistroId>(impedidos);
		
		intersecaoCandidatoImpedido.retainAll(impedidos);
		intersecaoImpedidoCandidato.retainAll(candidatos);
		
		return intersecaoCandidatoImpedido.isEmpty() &&
				intersecaoImpedidoCandidato.isEmpty();
	}
	
}
