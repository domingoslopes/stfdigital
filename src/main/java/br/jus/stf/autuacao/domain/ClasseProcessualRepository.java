package br.jus.stf.autuacao.domain;

import java.util.List;
import br.jus.stf.autuacao.domain.entity.ClasseProcessual;

/**
 * @author Anderson.Araujo
 *
 * @since 1.0.0
 * @since 21.07.2015
 */
public interface ClasseProcessualRepository {
	
	List<ClasseProcessual> listar();
	
}
