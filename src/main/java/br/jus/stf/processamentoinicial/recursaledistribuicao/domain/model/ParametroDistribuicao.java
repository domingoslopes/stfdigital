package br.jus.stf.processamentoinicial.recursaledistribuicao.domain.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.Validate;

import br.jus.stf.processamentoinicial.suporte.domain.model.MeioTramitacao;
import br.jus.stf.processamentoinicial.suporte.domain.model.Sigilo;
import br.jus.stf.shared.ClasseId;
import br.jus.stf.shared.MinistroId;
import br.jus.stf.shared.PeticaoId;
import br.jus.stf.shared.PreferenciaId;
import br.jus.stf.shared.stereotype.ValueObject;

public class ParametroDistribuicao implements ValueObject<ParametroDistribuicao> {

	private static final long serialVersionUID = 1L;

	private TipoDistribuicao tipoDistribuicao;
	private String justificativa;
	private String usuarioDistribuicao;

	private Set<MinistroId> ministrosCandidatos;
	private Set<MinistroId> ministrosImpedidos;
	private Set<Processo> processosPreventos;

	private PeticaoId peticaoId;

	private ClasseId classeProcessual;
	private Set<PreferenciaId> preferencias = new HashSet<PreferenciaId>(0);
	private MeioTramitacao meioTramitacao;
	private Sigilo sigilo;
	private Long quantidadeRecursos;

	public ParametroDistribuicao(final TipoDistribuicao tipoDistribuicao, final PeticaoId peticaoId,
			final String justificativa, final String usuarioDistribuicao, final Set<MinistroId> ministrosCandidatos,
			final Set<MinistroId> ministrosImpedidos, final Set<Processo> processosPreventos) {

		Validate.notNull(tipoDistribuicao, "parametroDistribuicao.tipoDistribuicao.required");

		this.tipoDistribuicao = tipoDistribuicao;
		this.usuarioDistribuicao = usuarioDistribuicao;
		this.justificativa = justificativa;

		this.ministrosCandidatos = ministrosCandidatos;
		this.ministrosImpedidos = ministrosImpedidos;

		this.processosPreventos = processosPreventos;

		this.peticaoId = peticaoId;
	}

	public ParametroDistribuicao(final TipoDistribuicao tipoDistribuicao, final String justificativa,
			final String usuarioDistribuicao, final Set<MinistroId> ministrosCandidatos,
			final Set<MinistroId> ministrosImpedidos, final Set<Processo> processosPreventos,
			final ClasseId classeProcessual, final Set<PreferenciaId> preferencias, final MeioTramitacao meioTramitacao,
			final Sigilo sigilo, final Long quantidadeRecursos) {
		this(tipoDistribuicao, null, justificativa, usuarioDistribuicao, ministrosCandidatos, ministrosImpedidos,
				processosPreventos);

		Validate.notNull(classeProcessual, "parametroDistribuicao.classeProcessual.required");
		Validate.notNull(meioTramitacao, "parametroDistribuicao.meioTramitacao.required");
		Validate.notNull(sigilo, "parametroDistribuicao.sigilo.required");
		Validate.isTrue(quantidadeRecursos >= 0, "parametroDistribuicao.quantidadeRecursos.invalid");

		this.classeProcessual = classeProcessual;
		
		if (preferencias != null) {
			this.preferencias = preferencias;
		}
		
		this.meioTramitacao = meioTramitacao;
		this.sigilo = sigilo;
		this.quantidadeRecursos = quantidadeRecursos;
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
		return Collections.unmodifiableSet(ministrosCandidatos);
	}

	public Set<MinistroId> ministrosImpedidos() {
		return Collections.unmodifiableSet(ministrosImpedidos);
	}

	public Set<Processo> processosPreventos() {
		return Collections.unmodifiableSet(processosPreventos);
	}

	public ClasseId classeProcessual() {
		return classeProcessual;
	}

	public Set<PreferenciaId> preferencias() {
		return Collections.unmodifiableSet(preferencias);
	}

	public MeioTramitacao meioTramitacao() {
		return meioTramitacao;
	}

	public Sigilo sigilo() {
		return sigilo;
	}

	public Long quantidadeRecursos() {
		return quantidadeRecursos;
	}

	@Override
	public boolean sameValueAs(final ParametroDistribuicao other) {
		return this.equals(other);
	}

}
