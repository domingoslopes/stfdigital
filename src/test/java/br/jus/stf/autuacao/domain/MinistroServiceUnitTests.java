package br.jus.stf.autuacao.domain;

import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import br.jus.stf.autuacao.infra.MinistroRepositoryMemory;
import br.jus.stf.autuacao.interfaces.dto.MinistroDto;
import br.jus.stf.autuacao.interfaces.dto.MinistroDtoAssembler;

/**
 * Teste de operações relacionada a Ministros.
 * 
 * @author anderson.araujo
 * 
 * @since 1.0.0
 * @since 23.07.2015
 */
public class MinistroServiceUnitTests {
	
	@Mock(answer = Answers.CALLS_REAL_METHODS)
	private MinistroRepositoryMemory ministroRepository;
	
	@InjectMocks
	private MinistroService ministroService;
	
	private MinistroDtoAssembler assembler = new MinistroDtoAssembler();
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void listarMinistrosRepositoryTest(){
		
		List<MinistroDto> ministros = null;
		/*
    	List<Ministro> ministrosRet = this.ministroService.listar();
    	
    	if (ministrosRet != null){
    		ministros = new LinkedList<MinistroDto>();
    		
    		for(Ministro ministro : ministrosRet){
        		ministros.add(this.assembler.toDto(ministro));
        	}
    	}
		*/
		Assert.assertEquals(11, ministros.size());
	}
}
