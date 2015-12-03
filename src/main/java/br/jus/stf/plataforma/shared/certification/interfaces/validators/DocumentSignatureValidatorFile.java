package br.jus.stf.plataforma.shared.certification.interfaces.validators;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

@Component
public class DocumentSignatureValidatorFile extends GenericSignatureValidator
		implements ConstraintValidator<DocumentSigned, File> {

	@Override
	public boolean isValid(File value, ConstraintValidatorContext context) {
		InputStream is = null;
		try {
			is = new FileInputStream(value);
			return isValid(is, context);
		} catch (FileNotFoundException e) {
			throw new RuntimeException("Erro ao recuperar documento para validação.", e);
		} finally {
			IOUtils.closeQuietly(is);
		}
	}

}
