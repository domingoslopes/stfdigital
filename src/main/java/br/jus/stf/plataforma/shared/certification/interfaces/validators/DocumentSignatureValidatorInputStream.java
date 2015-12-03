package br.jus.stf.plataforma.shared.certification.interfaces.validators;

import java.io.InputStream;

import javax.validation.ConstraintValidator;

import org.springframework.stereotype.Component;

@Component
public class DocumentSignatureValidatorInputStream extends GenericSignatureValidator
		implements ConstraintValidator<DocumentSigned, InputStream> {

}
