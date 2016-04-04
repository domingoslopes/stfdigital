package br.jus.stf.plataforma.identidades.interfaces.facade;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.plataforma.identidades.application.PessoaApplicationService;
import br.jus.stf.plataforma.identidades.domain.model.Pessoa;
import br.jus.stf.plataforma.identidades.domain.model.PessoaRepository;
import br.jus.stf.plataforma.identidades.interfaces.dto.PessoaDto;
import br.jus.stf.plataforma.identidades.interfaces.dto.PessoaDtoAssembler;
import br.jus.stf.shared.PessoaId;
import br.jus.stf.shared.ValidadorCPFCNPJ;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 25.09.2015
 */
@Component
public class PessoaServiceFacade {
	
	@Autowired
	private PessoaApplicationService pessoaApplicationService;	

	@Autowired
	private PessoaDtoAssembler pessoaDtoAssembler;
	
	@Autowired
	private PessoaRepository pessoaRepository;

	/**
	 * Cadastra uma lista de pessoas
	 * 
	 * @param pessoasNovas
	 * @return lista de identificadores de pessoas cadastradas
	 */
	public List<PessoaDto> cadastrarPessoas(List<String> pessoas) {
		return pessoaApplicationService.cadastrarPessoas(pessoas).stream().map(pessoa -> pessoaDtoAssembler.toDto(pessoa)).collect(Collectors.toList());
	}

	/**
	 * Pesquisa uma pessoa
	 * 
	 * @param pessoaId
	 * @return a pessoa
	 */
	public PessoaDto pesquisarPessoa(Long pessoaId) {
		PessoaId id = new PessoaId(pessoaId); 
		return pessoaDtoAssembler.toDto(pessoaRepository.findOne(id));
	}
	
	/**
	 * Retorna uma lista de todas as pessoas
	 * 
	 * @return a lista de pessoas
	 */
	public List<PessoaDto> listar() {
		return pessoaRepository.findAll().stream().map(pessoa -> pessoaDtoAssembler.toDto(pessoa)).collect(Collectors.toList());
	}
	
	public List<PessoaDto> consultarPessoasPorNumeroDocumento(String numeroDocumento){
		String numeroDoc = ValidadorCPFCNPJ.removerCaracteresEspeciais(StringUtils.deleteWhitespace(numeroDocumento));
		List<PessoaDto> pessoas = new ArrayList<PessoaDto>();
		PessoaDto pessoa = null;
		
		if (ValidadorCPFCNPJ.isCPFValido(numeroDoc)){
			pessoa = consultarPessoaPorCPF(numeroDoc); 
			
			if (pessoa != null){
				pessoas.add(pessoa);
			} else {
				pessoa = consultarPessoaWSRF(numeroDoc);
			}
		} else if (ValidadorCPFCNPJ.isCNPJValido(numeroDoc)){
			
		}
		
		return null;
	}
	
	private PessoaDto consultarPessoaPorCPF(String cpf){
		Pessoa pessoa = null;
		
		try{
			pessoa = pessoaRepository.findByCpf(cpf);
		} catch (Exception erro){
			return null;
		}
		
		return pessoaDtoAssembler.toDto(pessoa);
	}

	private PessoaDto consultarPessoaWSRF(String cpf){
		return null;
	}
}
