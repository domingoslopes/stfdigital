package br.jus.stf.processamentoinicial.recursaledistribuicao.domain.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

import org.apache.commons.lang3.Validate;

import br.jus.stf.shared.MinistroId;

@Entity
@DiscriminatorValue("PREVENCAO")
public class DistribuicaoPrevencao extends Distribuicao {

	private static final long serialVersionUID = 1L;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	@JoinTable(name = "DISTRIBUICAO_PROC_PREVENTO", schema = "AUTUACAO",
		joinColumns = @JoinColumn(name = "SEQ_DISTRIBUICAO", nullable = false),
		inverseJoinColumns = @JoinColumn(name = "SEQ_PROCESSO", nullable = false))
	private Set<Processo> processosPreventos = new HashSet<Processo>(0);
	
	DistribuicaoPrevencao() {
		
	}
	
	protected DistribuicaoPrevencao(ParametroDistribuicao parametroDistribuicao) {
		super(parametroDistribuicao);
		
		Validate.notBlank(parametroDistribuicao.justificativa(), "distribuicao.justificativa.required");
		Validate.notEmpty(parametroDistribuicao.processosPreventos(), "distribuicao.processosPreventos.required");
		Validate.isTrue(this.validarProcessosPreventos(parametroDistribuicao.processosPreventos()), "distribuicao.processosPreventos.invalid");
		
		this.processosPreventos = parametroDistribuicao.processosPreventos();
	}
	
	public Set<Processo> processosPreventos() {
		return Collections.unmodifiableSet(this.processosPreventos);
	}
	
	@Override
	public TipoDistribuicao tipo() {
		return TipoDistribuicao.PREVENCAO;
	}
	
	@Override
	protected MinistroId sorteio() {
		return this.processosPreventos.iterator().next().relator();
	}
	
	private boolean validarProcessosPreventos(Set<Processo> processosPreventos) {
		MinistroId relator = processosPreventos.iterator().next().relator();
		
		return processosPreventos.stream()
			.filter(processo -> relator.sameValueAs(processo.relator()))
			.count() == processosPreventos.size();
	}
		
}
