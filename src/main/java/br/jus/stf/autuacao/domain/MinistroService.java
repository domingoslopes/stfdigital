package br.jus.stf.autuacao.domain;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @author Anderson.Araujo
 *
 */
@Component
public class MinistroService {
	
	@Autowired
	private MinistroRepository repository;
	
	/*
	public List<Ministro> listar() {
		
		return repository.listar();
	}
	*/
}
