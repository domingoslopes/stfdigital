package br.jus.stf.autuacao.domain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @author Anderson.Araujo
 *
 */
@Component
public class ClasseProcessualService {
	
	@Autowired
	private ClasseProcessualRepository repository;
	
	/*
	public List<ClasseProcessual> listar(){
		return this.repository.listar();
	}
	*/
}
