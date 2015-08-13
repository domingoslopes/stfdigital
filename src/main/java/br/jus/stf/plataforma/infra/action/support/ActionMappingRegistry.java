package br.jus.stf.plataforma.infra.action.support;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import br.jus.stf.plataforma.infra.action.annotation.ActionController;
import br.jus.stf.plataforma.infra.action.annotation.ActionMapping;
import br.jus.stf.plataforma.infra.action.handlers.ActionConditionHandler;

/**
 * Registro que fornece as ações definidas. O registro é realizado na inicialização do bean
 * e procura por métodos anotados com {@link ActionMapping} dentro das classes controladoras
 * anotadas com {@link ActionController}
 * 
 * @author Lucas.Rodrigues
 *
 */
@Component
public class ActionMappingRegistry implements InitializingBean {

	/**
	 * Armazena uma lista ordenada de todas ações definidas para a entidade (resourceClass) em questão.
	 */
	
	private Map<String, ActionMappingInfo> actions = new HashMap<String, ActionMappingInfo>();

	@Autowired
	private ApplicationContext applicationContext;
	
	@Autowired
	private ActionServiceAdapter actionServiceAdapter;

	/**
	 * Recupera a lista de ações registradas pelo mecanismo local
	 * 
	 * @return a lista de ações registradas
	 */
	public Map<String, ActionMappingInfo> getRegisteredActions() {
		return Collections.unmodifiableMap(actions);
	}
	
	/**
	 * Recupera a lista de ações registradas pelo mecanismo local
	 * de acordo com os ids informados
	 * 
	 * @return a lista de ações registradas
	 */
	public Map<String, ActionMappingInfo> findRegisteredActions(Collection<String> ids) {
		Map<String, ActionMappingInfo> findedActions = new HashMap<String, ActionMappingInfo>();
		
		actions.keySet().stream()
			.filter(key -> ids.contains(key))
			.forEach(key -> findedActions.put(key, actions.get(key)));
			
		return Collections.unmodifiableMap(findedActions);
	}
	
	/**
	 * Procura, registra e invoca o registro externo dos mapeamentos das ações
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void afterPropertiesSet() {
		
		Map<Class, ActionConditionHandler> handlers = new HashMap<Class, ActionConditionHandler>(0);
		
		applicationContext.getBeansOfType(ActionConditionHandler.class).values()
			.forEach(handler -> handlers.put(handler.getAnnotation(), handler));
		
		applicationContext.getBeansWithAnnotation(ActionController.class).values()
			.forEach(controller -> processActionController(controller, handlers));
		
		actionServiceAdapter.register(actions);
	}
	
	/**
	 * Processa um controlador de ações
	 * 
	 * @param handlers
	 * @param controller
	 */
	@SuppressWarnings("rawtypes")
	private void processActionController(Object controller, Map<Class, ActionConditionHandler> handlers) {
		
		ActionController annotation = controller.getClass().getAnnotation(ActionController.class);
		ActionControllerInfo info = new ActionControllerInfo(controller, annotation.context());
		
		Arrays.asList(controller.getClass().getMethods()).stream()
			.filter(method -> method.isAnnotationPresent(ActionMapping.class))
			.forEach(method -> processActionMethod(info, method, handlers));
	}

	/**
	 * Processa os metódos dos controladores de ação
	 * 
	 * @param handlers
	 * @param actionController
	 * @param method
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private void processActionMethod(ActionControllerInfo controllerInfo, Method method,
			Map<Class, ActionConditionHandler> handlers) {
		
		ActionMappingInfo info = new ActionMappingInfo();
		
		for (Annotation annotation : method.getAnnotations()) {
			if (annotation.annotationType().equals(ActionMapping.class)) {
				ActionMapping actionMapping = (ActionMapping) annotation;
				info.setId(actionMapping.id());
				info.setDescription(actionMapping.name());
				info.setControllerInfo(controllerInfo);
				info.setMethodName(method.getName());
				info.setResourceClass(actionMapping.resourceClass());
				info.setResourcesMode(actionMapping.resourcesMode());
				info.getGrantedAuthorities().addAll(
						Arrays.asList(actionMapping.grantedAuthorities()));
				
			} else if (handlers.containsKey(annotation.annotationType())) {
				info.getActionHandlersInfo().add(
						new ActionConditionHandlerInfo(
								annotation, handlers.get(annotation.annotationType())));
			}
		}
		actions.put(info.getId(), info);
	}
	
}
