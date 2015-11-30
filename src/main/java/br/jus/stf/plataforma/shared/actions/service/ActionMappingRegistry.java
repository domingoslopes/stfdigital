package br.jus.stf.plataforma.shared.actions.service;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import br.jus.stf.plataforma.shared.actions.annotation.ActionController;
import br.jus.stf.plataforma.shared.actions.annotation.ActionMapping;
import br.jus.stf.plataforma.shared.actions.handler.ActionConditionHandler;
import br.jus.stf.plataforma.shared.actions.support.ActionConditionHandlerInfo;
import br.jus.stf.plataforma.shared.actions.support.ActionMappingInfo;
import br.jus.stf.plataforma.shared.actions.support.ResourcesMode;
import br.jus.stf.plataforma.shared.security.AcessosRestAdapter;

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
	
	@Autowired
	private AcessosRestAdapter acessosRestAdapter;

	/**
	 * Armazena uma lista ordenada de todas ações definidas para a entidade (resourceClass) em questão.
	 */
	
	private Map<String, ActionMappingInfo> actions = new HashMap<String, ActionMappingInfo>();

	@Autowired
	private ApplicationContext applicationContext;

	/**
	 * Recupera a lista de ações registradas pelo mecanismo local
	 * 
	 * @return a lista de ações registradas
	 */
	public Collection<ActionMappingInfo> getRegisteredActions() {
		return Collections.unmodifiableCollection(actions.values());
	}
	
	/**
	 * Recupera uma ação registrada de acordo com seu id
	 * 
	 * @return a lista de ações registradas
	 */
	public ActionMappingInfo findRegisteredActionsById(String actionId) {
		String id = Optional.ofNullable(actionId).orElseThrow(IllegalArgumentException::new);
		Optional<ActionMappingInfo> actionInfo = Optional.ofNullable(actions.get(id.toLowerCase()));
		return actionInfo.orElseThrow(
				() -> new RuntimeException("Ação não registrada: " + actionId));
	}
	
	/**
	 * Procura, registra e invoca o registro externo dos mapeamentos das ações
	 */
	@Override
	public void afterPropertiesSet() {
		
		Map<Class<?>, Class<?>> handlers = new HashMap<Class<?>, Class<?>>(0);
		
		applicationContext.getBeansOfType(ActionConditionHandler.class).values()
			.forEach(handler -> handlers.put(handler.annotation(), handler.getClass()));
		
		applicationContext.getBeansWithAnnotation(ActionController.class).values()
			.forEach(controller -> processActionController(controller.getClass(), handlers));
	}
	
	/**
	 * Processa um controlador de ações
	 * 
	 * @param controllerClass
	 * @param handlersClass
	 */
	private void processActionController(Class<?> controllerClass, Map<Class<?>, Class<?>> handlers) {
		
		Arrays.asList(controllerClass.getMethods()).stream()
			.filter(method -> method.isAnnotationPresent(ActionMapping.class))
			.forEach(method -> processActionMethod(controllerClass, method, handlers));
	}

	/**
	 * Processa os metódos dos controladores de ação
	 * 
	 * @param handlers
	 * @param actionController
	 * @param method
	 * @return
	 */
	private void processActionMethod(Class<?> controllerClass, Method method, Map<Class<?>, Class<?>> handlers) {
		
		ActionMappingInfo info = new ActionMappingInfo();
		
		for (Annotation annotation : method.getAnnotations()) {
			if (annotation.annotationType().equals(ActionMapping.class)) {
				ActionMapping actionMapping = (ActionMapping) annotation;
				info.setId(actionMapping.id().toLowerCase());
				info.setDescription(actionMapping.name());
				info.setControllerClass(controllerClass);
				info.setMethodName(method.getName());
				info.setResourcesClass(getResourcesClass(method));
				info.setResourcesMode(getResourcesMode(method)); 
				info.getNeededAuthorities().addAll(
						acessosRestAdapter.carregarPermissoesRecurso(actionMapping.id(), "ACAO"));
				
			} else if (handlers.containsKey(annotation.annotationType())) {
				info.getActionHandlersInfo().add(
						new ActionConditionHandlerInfo(
								annotation, handlers.get(annotation.annotationType())));
			}
		}
		actions.put(info.getId(), info);
	}
	
	/**
	 * Verifica qual o modo de recursos
	 * 
	 * @param method
	 * @return
	 */
	private ResourcesMode getResourcesMode(Method method) {
		if (method.getParameterCount() == 0) {
			return ResourcesMode.None;
		} else if (method.getParameterCount() == 1) {
			Class<?> clazz = method.getParameterTypes()[0];
			return Collection.class.isAssignableFrom(clazz) ? 
					ResourcesMode.Many : ResourcesMode.One;
		}
		throw new IllegalArgumentException("O mecanismo permite no máximo 1 parâmetro");
	}
	
	/**
	 * Retorna o tipo dos recursos a partir do método
	 * 
	 * @param method
	 * @return a classe dos recursos
	 */
	private Class<?> getResourcesClass(Method method) {
		if (method.getParameterCount() == 0) {
			return null;
		}
		Class<?> clazz = method.getParameterTypes()[0];
		if (Collection.class.isAssignableFrom(clazz)) {
			ParameterizedType type = (ParameterizedType) method.getGenericParameterTypes()[0];
			return (Class<?>) type.getActualTypeArguments()[0];
		} else {
			return clazz;
		}
	}
	
}
