package br.jus.stf.plataforma.infra.action.service;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import br.jus.stf.plataforma.infra.action.handlers.ActionConditionHandler;
import br.jus.stf.plataforma.infra.action.support.ActionConditionHandlerInfo;
import br.jus.stf.plataforma.infra.action.support.ActionMappingInfo;
import br.jus.stf.plataforma.infra.action.support.ActionMappingRegistry;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;


/**
 * Componente utilizado para definir uma lista de ações que serão apresentadas em função
 * dos diversos handlers (segurança e etc).
 * 
 * @author Rodrigo.Barreiros
 * @author Lucas.Rodrigues
 * @since 18.05.2010
 * 
 */
@Component
public class ActionModuleService {

	private Log logger = LogFactory.getLog(ActionModuleService.class);

	@Autowired
	private ApplicationContext applicationContext;
	
	@Autowired
	private ActionMappingRegistry mappingRegistry;

	/**
	 * Define a lista de ações em função dos recursos de entrada e de diversos handlers
	 * definidos para todos os tipos recursos.
	 * 
	 * @param resources os recursos de entrada
	 * @return a lista de ações
	 * @throws Exception 
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	public Collection<String> verifyActionsAllowed(Collection<String> ids, String resourcesType, Collection<?> resources) throws Exception {
		long start = System.currentTimeMillis();
		Set<String> actionsAllowed = new HashSet<String>();
		
		List<?> converted = null;
		
		for (ActionMappingInfo actionInfo : mappingRegistry.findRegisteredActions(ids).values()) {
			if (!actionInfo.getResourcesType().equals(resourcesType)) {
				break;
			}
			if (converted == null) {
				converted = convertResources(resources, actionInfo.getResourceClass());
			}
			if (isAllowed(actionInfo, converted)) {
				actionsAllowed.add(actionInfo.getId());
			}
		}
		
		long end = System.currentTimeMillis();
		logger.info(String.format("%s: [%s] milisegundos.", getClass().getSimpleName(), (end - start)));
		return actionsAllowed;
	}
	
	/**
	 * Verifica as restrições e executa uma ação.
	 * 
	 * @param actionId
	 * @param resources
	 * @throws Exception
	 */
	public String executeAction(String actionId, Collection<?> resources) throws Exception {
		
		ActionMappingInfo actionInfo = mappingRegistry.getRegisteredActions().get(actionId);
		if (actionInfo == null) {
			throw new RuntimeException("Ação com id: '" + actionId + "' não encontrada!");
		}
		
		List<?> converted = convertResources(resources, actionInfo.getResourceClass());
		Object ret = null;
		
		if (isAllowed(actionInfo, converted)) {
			Object controller = actionInfo.getController();
			Method method = BeanUtils.findDeclaredMethodWithMinimalParameters(
					controller.getClass(), actionInfo.getMethodName());
			
			ret = method.invoke(controller, converted);
		}
		return convertReturn(ret);
	}

	/**
	 * Verifica se uma ação deve ser exibida considerando os recursos de entrada.
	 * 
	 * <p>
	 * A ação e os recurso são submetidos aos handlers que definirão se a ação deve ser exibida ou não.
	 * 
	 * <p>
	 * Os handlers são definidos em função das anotações adicionadas à classe da ação. A cada anotação está relacionada um handler.
	 * 
	 * @param action
	 *            a ação avaliada
	 * @param resources
	 *            os recursos selecionados
	 * @return true, se a ação deve ser lista, false, caso contrário
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	private boolean isAllowed(ActionMappingInfo actionInfo, Collection<?> resources) {
			
		for (ActionConditionHandlerInfo handlerInfo : actionInfo.getActionHandlersInfo()) {
			if (!matches(handlerInfo, resources)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Converte a coleção de recursos para um tipo informado
	 * @param resources
	 * @param resourceClass
	 */
	private List<?> convertResources(Collection<?> resources, Class<?> resourceClass) {
	
		ObjectMapper mapper = new ObjectMapper();
		JavaType type = TypeFactory.defaultInstance()
				.constructParametricType(Collection.class, resourceClass); 

		try {
			return mapper.convertValue(resources, type);
		} catch (Exception e) {
			throw new RuntimeException("Erro ao converter recursos!", e);
		}
	}
	
	/**
	 * Converte o retorno em string
	 * @param ret
	 * @return o objeto em string
	 * @throws JsonProcessingException
	 */
	private String convertReturn(Object ret) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(ret);
	}

	/**
	 * Submete a ação a um dado handler, informando a anotação do handler e os recursos
	 * selecionados. O handler definirá se a ação deve, ou não, ser listada.
	 *
	 * @param handlerInfo as informações do handler
	 * @param resources os recursos selecionados
	 * @return true, se aprovado, false, caso contrário
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private boolean matches(ActionConditionHandlerInfo handlerInfo, Collection<?> resources) {
		ActionConditionHandler handler = handlerInfo.getHandler();
		Class<?> clazz = handlerInfo.getAnnotation().annotationType();
		if (handler != null) {
			return handler.matches(clazz.cast(handlerInfo.getAnnotation()), resources);
		}
		return true;
	}
	
}
