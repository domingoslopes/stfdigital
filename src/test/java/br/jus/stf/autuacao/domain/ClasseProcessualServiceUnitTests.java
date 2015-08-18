package br.jus.stf.autuacao.domain;

import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import br.jus.stf.autuacao.domain.ClasseProcessualService;
import br.jus.stf.autuacao.domain.entity.ClasseProcessual;
import br.jus.stf.autuacao.interfaces.dto.ClasseDto;
import br.jus.stf.autuacao.interfaces.dto.ClasseProcessualDtoAssembler;
import br.jus.stf.plataforma.ApplicationContextInitializer;

/**
 * Teste de operações relacionadas à classe processual.
 * 
 * @author anderson.araujo
 * 
 * @since 1.0.0
 * @since 21.07.2015
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ApplicationContextInitializer.class)
@WebAppConfiguration
public class ClasseProcessualServiceUnitTests {
	
	@Autowired
	private ClasseProcessualService classeProcessualService;
	private ClasseProcessualDtoAssembler assembler = new ClasseProcessualDtoAssembler();
	
	@Test
	public void listarClassesProcessuaisRepositoryTest(){
		
		List<ClasseDto> classes = null;
    	List<ClasseProcessual> classesRep = this.classeProcessualService.listar();
    	
    	if (classesRep != null){
    		classes = new LinkedList<ClasseDto>();
    		
    		for(ClasseProcessual classe : classesRep){
        		classes.add(this.assembler.toDto(classe));
        	}
    	}
		
		Assert.assertEquals(149, classes.size());
	}
}
