package br.jus.stf.plataforma.workflow.domain.model;

import java.util.Set;


/**
 * Serviço que verifica os grupos candidatos para uma tarefa do workflow
 * 
 * @author Lucas.Rodrigues
 *
 */
public interface GrupoCandidatoService {
	
	Set<String> grupos(String nomeTarefa);
	
}
