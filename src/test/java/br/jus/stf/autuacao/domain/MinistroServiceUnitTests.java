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
import br.jus.stf.autuacao.domain.MinistroService;
import br.jus.stf.autuacao.domain.entity.Ministro;
import br.jus.stf.autuacao.interfaces.dto.MinistroDto;
import br.jus.stf.autuacao.interfaces.dto.MinistroDtoAssembler;
import br.jus.stf.plataforma.ApplicationContextInitializer;

/**
 * Teste de operações relacionada a Ministros.
 * 
 * @author anderson.araujo
 * 
 * @since 1.0.0
 * @since 23.07.2015
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ApplicationContextInitializer.class)
@WebAppConfiguration
public class MinistroServiceUnitTests {
	
	@Autowired
	private MinistroService ministroService;
	private MinistroDtoAssembler assembler = new MinistroDtoAssembler();
	
	@Test
	public void listarMinistrosRepositoryTest(){
		
		List<MinistroDto> ministros = null;
    	List<Ministro> ministrosRet = this.ministroService.listar();
    	
    	if (ministrosRet != null){
    		ministros = new LinkedList<MinistroDto>();
    		
    		for(Ministro ministro : ministrosRet){
        		ministros.add(this.assembler.toDto(ministro));
        	}
    	}
		
		Assert.assertEquals(11, ministros.size());
	}
}
