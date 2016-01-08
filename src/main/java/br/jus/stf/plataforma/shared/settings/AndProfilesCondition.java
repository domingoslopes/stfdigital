package br.jus.stf.plataforma.shared.settings;

import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Profile;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.MultiValueMap;

/**
 * Classe para permitir utilizar o operador AND ao restringir
 * os profiles aos quais uma configuração se aplica. (O Spring
 * não suporta essa funcionalidade diretamente na anotação @Profile ainda).
 * 
 * Classe reaproveitada de https://jira.spring.io/browse/SPR-12458
 * 
 * @author Tomas.Godoi
 *
 */
public class AndProfilesCondition implements Condition {

	public static final String VALUE = "value";
	public static final String DEFAULT_PROFILE = "default";

	@Override
	public boolean matches(final ConditionContext context, final AnnotatedTypeMetadata metadata) {
		if (context.getEnvironment() == null) {
			return true;
		}
		MultiValueMap<String, Object> attrs = metadata.getAllAnnotationAttributes(Profile.class.getName());
		if (attrs == null) {
			return true;
		}
		String[] activeProfiles = context.getEnvironment().getActiveProfiles();
		String[] definedProfiles = (String[]) attrs.getFirst(VALUE);
		Set<String> allowedProfiles = new HashSet<>(1);
		Set<String> restrictedProfiles = new HashSet<>(1);
		for (String nextDefinedProfile : definedProfiles) {
			if (!nextDefinedProfile.isEmpty() && nextDefinedProfile.charAt(0) == '!') {
				restrictedProfiles.add(nextDefinedProfile.substring(1, nextDefinedProfile.length()));
				continue;
			}
			allowedProfiles.add(nextDefinedProfile);
		}

		for (String nextActiveProfile : activeProfiles) {
			if (DEFAULT_PROFILE.equals(nextActiveProfile) && allowedProfiles.isEmpty()) {
				continue;
			}
			if (!allowedProfiles.contains(nextActiveProfile) || restrictedProfiles.contains(nextActiveProfile)
					|| allowedProfiles.size() != activeProfiles.length) {
				return false;
			}
		}
		return true;
	}

}