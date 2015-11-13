package br.jus.stf.processamentoinicial.distribuicao.domain.model;

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
	private Set<MinistroId> ministrosCanditatos = new HashSet<MinistroId>(0);
	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "DISTRIBUICAO_MIN_IMPEDIDO", schema = "AUTUACAO", joinColumns = @JoinColumn(name = "SEQ_DISTRIBUICAO", nullable = false))
	private Set<MinistroId> ministrosImpedidos = new HashSet<MinistroId>(0);
	
	DistribuicaoComum() {
		
	}
	
	public DistribuicaoComum(ParametroDistribuicao parametroDistribuicao) {
		super(parametroDistribuicao);
		
		Validate.notEmpty(parametroDistribuicao.ministrosCanditatos(), "distribuicao.ministrosCanditatos.required");
		Validate.notEmpty(parametroDistribuicao.ministrosImpedidos(), "distribuicao.ministrosImpedidos.required");
		Validate.isTrue(this.validarListasMinistros(parametroDistribuicao.ministrosCanditatos(), parametroDistribuicao.ministrosImpedidos()), "distribuicao.listasMinistros.invalid");
		
		this.ministrosCanditatos = parametroDistribuicao.ministrosCanditatos();
		this.ministrosImpedidos = parametroDistribuicao.ministrosImpedidos();
	}
	
	public Set<MinistroId> ministrosCanditatos() {
		return Collections.unmodifiableSet(this.ministrosCanditatos);
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
		int indice = new Random().nextInt(ministrosCanditatos.size());
		
		return (MinistroId)ministrosCanditatos.toArray()[indice];
	}
	
	private boolean validarListasMinistros(Set<MinistroId> ministrosCandidatos, Set<MinistroId> ministrosImpedidos) {
		Set<MinistroId> intersecaoCandidatoImpedido = new HashSet<MinistroId>(ministrosCanditatos);
		Set<MinistroId> intersecaoImpedidoCandidato = new HashSet<MinistroId>(ministrosImpedidos);
		
		intersecaoCandidatoImpedido.retainAll(ministrosImpedidos);
		intersecaoImpedidoCandidato.retainAll(ministrosCandidatos);
		
		return intersecaoCandidatoImpedido.isEmpty() &&
				intersecaoImpedidoCandidato.isEmpty();
	}
	
}
