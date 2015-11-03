package br.jus.stf.plataforma.shared.actions.handler;

import java.util.List;


/**
 * Abstração para tratar ações e determinar se uma ação pode ou não ser exibida
 * dado o contexto de execução.
 * 
 * Ex:
 * <pre>
 * {@code
 * Component
 * public class RequiresResourcesHandler implements ActionConditionHandler<RequiresResources> {
 * 
 * 	
 * 	public <T> boolean matches(RequiresResources annotation, Collection<T> resources) {
 * 		Mode mode = annotation.value();
 * 		if (mode.equals(Mode.One)) {
 * 			return resources.size() == 1;
 * 		}
 * 		if (mode.equals(Mode.Many)) {
 * 			return (resources.size() > 0);
 * 		}
 * 		return false;
 * 	}
 * 	
 * 	public Class<RequiresResources> annotation() {
 * 		return RequiresResources.class;
 * 	}
 * }
 * </pre>
 * 
 * @author Rodrigo.Barreiros
 * @since 24.05.2010
 * 
 * @param <A> o tipo da anotação vinculada ao handler
 * @param <T> o tipo dos recursos
 */
public interface ActionConditionHandler<A, T> {
    
	/**
	 * Verifica se uma dada ação pode ou não ser exibida ao usuário, dada a configuração 
	 * do handler (annotation), os recursos selecionados, o tipo de recurso e 
	 * outras opções que poderão ser utilizadas para decisão.
	 * 
	 * @param annotation a anotação utilizada para configurar e indicar que esse
	 * handler deve tratar uma determinada ação
	 * @param resources os recursos selecionada na tela
	 * 
	 * @return true, se a ação deve ser listada, false, caso contrário
	 */
	boolean matches(A annotation, List<T> resources);
    
    /**
     * Retorna a anotação utilizada para configurar e indicar que esse
	 * handler deve tratar uma determinada ação.
	 * 
     * @return a anotação associada ao handler
     */
    Class<A> annotation();

}