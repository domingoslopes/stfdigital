package br.jus.stf.plataforma.identidades.application;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.jus.stf.plataforma.identidades.domain.model.Associado;
import br.jus.stf.plataforma.identidades.domain.model.AssociadoRepository;
import br.jus.stf.plataforma.identidades.domain.model.OrgaoAdapter;
import br.jus.stf.plataforma.identidades.domain.model.Pessoa;
import br.jus.stf.plataforma.identidades.domain.model.PessoaRepository;
import br.jus.stf.plataforma.identidades.domain.model.TipoAssociado;
import br.jus.stf.processamentoinicial.autuacao.domain.model.Orgao;

/**
 * Componente responsável pelas operações que envolvam associados de órgãos.
 * 
 * @author Anderson.Araujo
 * 
 * @since 30.11.2015
 *
 */
@Service
@Transactional
public class AssociadoApplicationService {
	
	@Autowired
	private AssociadoRepository associadoRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private OrgaoAdapter orgaoAdapter;
		
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
		
		Pessoa pessoa = null;
		Orgao orgao = null;
		Pessoa pessoaInserida = null;
				
		orgao = this.orgaoAdapter.consultar(idOrgao);
		
		if (orgao == null) {
			throw new RuntimeException("O órgão informado não existe.");
		}
		
		pessoa = this.pessoaRepository.findByCpf(cpf);
			
		if (pessoa == null) {
			pessoa = new Pessoa(this.pessoaRepository.nextId(), nome, cpf);
			
		} else {
			throw new RuntimeException("Já existe um associado cadastrado com o CPF informado.");
		}
		
		pessoaInserida = this.pessoaRepository.save(pessoa);
		
		if (cargo.trim().isEmpty()){
			this.associadoRepository.save(new Associado(pessoaInserida, orgao, TipoAssociado.valueOf(tipoAssociacao.toUpperCase())));
		} else {
			this.associadoRepository.save(new Associado(pessoaInserida, orgao, TipoAssociado.valueOf(tipoAssociacao.toUpperCase()), cargo));
		}
		
	}
}
