package br.jus.stf.plataforma.identidades.infra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.plataforma.identidades.domain.model.OrgaoAdapter;
import br.jus.stf.processamentoinicial.autuacao.domain.model.Orgao;
import br.jus.stf.processamentoinicial.autuacao.interfaces.OrgaoRestResource;
import br.jus.stf.processamentoinicial.autuacao.interfaces.dto.OrgaoDto;

/**
 * Implementação REST do adaptador destinado à consulta de órgãos.
 * 
 * @author Anderson.Araujo
 * 
 * @since 30.11.2015
 *
 */
@Component
public class OrgaoRestAdapter implements OrgaoAdapter {
	
	@Autowired
	private OrgaoRestResource orgaoRestResource; 
	
	/**
	 * Recupera os dados de um órgão de acordo com o id informado.
	 * 
	 * @param id Id do órgão.
	 * 
	 * @return Dados do órgão.
	 */
	@Override
	public Orgao consultar(Long id) {
		OrgaoDto dto = this.orgaoRestResource.consultar(id);
		return new Orgao(dto.getId(), dto.getNome());
	}

}
