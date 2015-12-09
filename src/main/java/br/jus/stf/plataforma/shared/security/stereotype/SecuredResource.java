package br.jus.stf.plataforma.shared.security.stereotype;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.security.access.prepost.PostFilter;

/**
 * @author Lucas.Rodrigues
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@PostFilter("@securityChecker.hasPermission(filterObject)")
public @interface SecuredResource {

}
