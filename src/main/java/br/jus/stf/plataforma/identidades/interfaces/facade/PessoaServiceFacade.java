package br.jus.stf.plataforma.identidades.interfaces.facade;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.plataforma.identidades.application.PessoaApplicationService;
import br.jus.stf.plataforma.identidades.domain.model.PessoaRepository;
import br.jus.stf.plataforma.identidades.interfaces.dto.PessoaDto;
import br.jus.stf.plataforma.identidades.interfaces.dto.PessoaDtoAssembler;
import br.jus.stf.shared.PessoaId;

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

}
