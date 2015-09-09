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

import br.jus.stf.autuacao.infra.ClasseProcessualRepositoryMemory;
import br.jus.stf.autuacao.interfaces.dto.ClasseDto;
import br.jus.stf.autuacao.interfaces.dto.ClasseProcessualDtoAssembler;

/**
 * Teste de operações relacionadas à classe processual.
 * 
 * @author anderson.araujo
 * 
 * @since 1.0.0
 * @since 21.07.2015
 */
public class ClasseProcessualServiceUnitTests {
	
	@Mock(answer = Answers.CALLS_REAL_METHODS)
	private ClasseProcessualRepositoryMemory classeProcessualRepository;
	
	@InjectMocks
	private ClasseProcessualService classeProcessualService;
	private ClasseProcessualDtoAssembler assembler = new ClasseProcessualDtoAssembler();
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void listarClassesProcessuaisRepositoryTest(){
		
		List<ClasseDto> classes = null;
    	/*
		List<ClasseProcessual> classesRep = this.classeProcessualService.listar();
    	
    	if (classesRep != null){
    		classes = new LinkedList<ClasseDto>();
    		
    		for(ClasseProcessual classe : classesRep){
        		classes.add(this.assembler.toDto(classe));
        	}
    	}
		*/
		Assert.assertEquals(42, classes.size());
	}
}
