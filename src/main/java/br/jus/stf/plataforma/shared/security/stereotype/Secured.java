package br.jus.stf.plataforma.shared.security.stereotype;


/**
 * Interface para recursos da plataforma como ações, dashboards, dashlets, tarefas, etc. 
 * 
 * @author Lucas.Rodrigues
 *
 */
public interface Secured {
	
	/**
	 * Identificador do recurso
	 * 
	 * @return uma string com o tipo do recurso
	 */
	String id();

	/**
	 * Tipo do recurso
	 * 
	 * @return uma string com o tipo do recurso
	 */
	String type();
	
}
