package br.jus.stf.autuacao.interfaces;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.jus.stf.autuacao.application.PeticaoApplicationService;

import com.wordnik.swagger.annotations.ApiOperation;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 20.07.2015
 */
@RestController
public class ClasseProcessualRestService {

	@Autowired
	private PeticaoApplicationService peticaoApplicationService;

    @ApiOperation(value = "Retorna todas as classes processuais ativas")
	@RequestMapping(value = "/api/classesprocessuais", method = RequestMethod.GET)
	public List<String> listar() {
		
		return new LinkedList<String>();
	}

}
