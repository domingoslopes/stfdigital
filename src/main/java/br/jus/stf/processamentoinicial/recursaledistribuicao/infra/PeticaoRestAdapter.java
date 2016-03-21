package br.jus.stf.processamentoinicial.recursaledistribuicao.infra;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.processamentoinicial.autuacao.domain.model.PartePeticao;
import br.jus.stf.processamentoinicial.autuacao.domain.model.PecaPeticao;
import br.jus.stf.processamentoinicial.autuacao.interfaces.PeticaoRestResource;
import br.jus.stf.processamentoinicial.autuacao.interfaces.dto.PecaDto;
import br.jus.stf.processamentoinicial.autuacao.interfaces.dto.PeticaoDto;
import br.jus.stf.processamentoinicial.recursaledistribuicao.domain.PeticaoAdapter;
import br.jus.stf.processamentoinicial.recursaledistribuicao.domain.model.Peticao;
import br.jus.stf.processamentoinicial.suporte.domain.model.MeioTramitacao;
import br.jus.stf.processamentoinicial.suporte.domain.model.Parte;
import br.jus.stf.processamentoinicial.suporte.domain.model.Peca;
import br.jus.stf.processamentoinicial.suporte.domain.model.Sigilo;
import br.jus.stf.processamentoinicial.suporte.domain.model.Situacao;
import br.jus.stf.processamentoinicial.suporte.domain.model.TipoPeca;
import br.jus.stf.processamentoinicial.suporte.domain.model.TipoPolo;
import br.jus.stf.processamentoinicial.suporte.domain.model.TipoProcesso;
import br.jus.stf.processamentoinicial.suporte.domain.model.Visibilidade;
import br.jus.stf.processamentoinicial.suporte.interfaces.dto.PreferenciaDto;
import br.jus.stf.shared.ClasseId;
import br.jus.stf.shared.DocumentoId;
import br.jus.stf.shared.PessoaId;
import br.jus.stf.shared.PeticaoId;
import br.jus.stf.shared.PreferenciaId;
import br.jus.stf.shared.ProcessoWorkflowId;
import br.jus.stf.shared.TipoDocumentoId;

/**
 * @author Rafael Alencar
 * 
 * @since 10.11.2015
 */
@Component
public class PeticaoRestAdapter implements PeticaoAdapter {

	@Autowired
	private PeticaoRestResource peticaoRestResource;

	@Override
	public Peticao consultar(Long id) {
		PeticaoDto peticaoDto = peticaoRestResource.consultar(id);
		Date dataAutuacao = Optional.ofNullable(peticaoDto.getDataAutuacao()).isPresent() ? new Date(peticaoDto.getDataAutuacao()) : null;
		
		return new Peticao(new PeticaoId(id), new ClasseId(peticaoDto.getClasse()), peticaoDto.getTipo(),
				carregarPartes(peticaoDto.getPartes()), carregarPecas(peticaoDto.getPecas()),
				new ProcessoWorkflowId(peticaoDto.getProcessoWorkflowId()), TipoProcesso.valueOf(peticaoDto.getTipoProcesso()),
				carregarPreferencias(peticaoDto.getPreferencias()), new Date(peticaoDto.getDataCadastramento()), dataAutuacao,
				MeioTramitacao.valueOf(peticaoDto.getMeioTramitacao()), Sigilo.valueOf(peticaoDto.getSigilo()));
	}
	
	private Set<Parte> carregarPartes(Map<String, List<Long>> partesDto) {
		Set<Parte> partes = partesDto.get("PoloAtivo").stream()
				.map(parteDto -> new PartePeticao(new PessoaId(parteDto), TipoPolo.POLO_ATIVO))
				.collect(Collectors.toSet());
		
		partes.addAll(partesDto.get("PoloPassivo").stream()
				.map(parteDto -> new PartePeticao(new PessoaId(parteDto), TipoPolo.POLO_PASSIVO))
				.collect(Collectors.toSet()));
		
		return partes;
	}
	
	private List<Peca> carregarPecas(List<PecaDto> pecasDto) {
		return pecasDto.stream()
				.map(pecaDto -> new PecaPeticao(new DocumentoId(pecaDto.getDocumentoId()), new TipoPeca(new TipoDocumentoId(pecaDto.getTipoId()),
						pecaDto.getTipoNome()), pecaDto.getDescricao(), Visibilidade.valueOf(pecaDto.getVisibilidade()),
						Situacao.valueOf(pecaDto.getSituacao())))
				.collect(Collectors.toList());
	}
	
	private Set<PreferenciaId> carregarPreferencias(List<PreferenciaDto> preferenciasDto) {
		return Optional.ofNullable(preferenciasDto)
			.map(p -> p.stream().map(preferenciaDto -> new PreferenciaId(preferenciaDto.getCodigo())).collect(Collectors.toSet()))
			.orElse(Collections.emptySet());
	}

}
