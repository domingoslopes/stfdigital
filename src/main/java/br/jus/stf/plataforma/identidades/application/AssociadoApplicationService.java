/**
 * 
 */
package br.jus.stf.plataforma.identidades.application;

import org.springframework.beans.factory.annotation.Autowired;

import br.jus.stf.plataforma.identidades.domain.model.AssociadoRepository;
import br.jus.stf.plataforma.identidades.domain.model.Pessoa;
import br.jus.stf.plataforma.identidades.domain.model.PessoaRepository;

/**
 * Componente responsável pelas operações que envolvam associados de órgãos.
 * 
 * @author Anderson.Araujo
 * 
 * @since 30.11.2015
 *
 */
public class AssociadoApplicationService {
	
	@Autowired
	private AssociadoRepository associadoRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository; 
	
	/**
	 * Realiza o cadastro de um novo associado.
	 * 
	 * @param idOrgao Id do órgão de associação.
	 * @param cpf Nº do CPF do associado.
	 * @param nome Nome do associado.
	 * @param tipoAssociacao Tipo de associação.
	 * @param cargo Cargo do associado.
	 */
	public void cadastrar(Long idOrgao, String cpf, String nome, String tipoAssociacao, String cargo) {
		
		Pessoa pessoa = this.pessoaRepository.findByCpf(cpf);
	}
}
