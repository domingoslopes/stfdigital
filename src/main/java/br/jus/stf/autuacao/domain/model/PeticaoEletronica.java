package br.jus.stf.autuacao.domain.model;

import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.apache.commons.lang3.Validate;

import br.jus.stf.shared.domain.model.ClasseId;
import br.jus.stf.shared.domain.model.DocumentoId;

/**
 * @author Lucas.Rodrigues
 *
 */
@Entity
@DiscriminatorValue("ELETRONICA")
public class PeticaoEletronica extends Peticao {
	
	PeticaoEletronica(final ClasseId classeSugerida, final Set<PartePeticao> partes, final Set<DocumentoId> documentos) {
		
		Validate.notNull(classeSugerida, "peticao.classeSugerida.required");
		Validate.notEmpty(partes, "peticao.partes.notEmpty");
		Validate.notEmpty(documentos, "peticao.documentos.notEmpty");
	
		this.classeSugerida = classeSugerida;
		this.partes.addAll(partes);
		this.documentos.addAll(documentos);
	}
	
}
