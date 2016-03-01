package br.jus.stf.plataforma.shared.security.stereotype;


/**
 * Interface para recursos da plataforma como ações, dashboards, dashlets, tarefas, etc. 
 * 
 * @author Lucas.Rodrigues
 *
 */
public interface Resource {
	
	/**
	 * Identificador do recurso
	 * 
	 * @return uma string com o id do recurso
	 */
	String resourceId();

	/**
	 * Tipo do recurso
	 * 
	 * @return uma string com o tipo do recurso
	 */
	String type();
	
}
