package br.jus.stf.plataforma.shared.certification.interfaces.validators;

import java.io.IOException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.web.multipart.MultipartFile;

public class DocumentSignatureValidatorMultipartFile extends GenericSignatureValidator
		implements ConstraintValidator<DocumentSigned, MultipartFile> {

	@Override
	public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
		try {
			return isValid(value.getInputStream(), context);
		} catch (IOException e) {
			throw new RuntimeException("Erro ao recuperar documento para validação.", e);
		}
	}

}
