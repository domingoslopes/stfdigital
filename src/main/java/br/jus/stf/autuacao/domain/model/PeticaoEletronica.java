package br.jus.stf.autuacao.domain.model;

import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.apache.commons.lang3.Validate;

import br.jus.stf.shared.domain.model.ClasseId;
import br.jus.stf.shared.domain.model.DocumentoId;
import br.jus.stf.shared.domain.model.PeticaoId;

/**
 * @author Lucas.Rodrigues
 *
 */
@Entity
@DiscriminatorValue("ELETRONICO")
public class PeticaoEletronica extends Peticao {
	
	public PeticaoEletronica(final PeticaoId id, final Long numero, final ClasseId classeSugerida,
			final Set<PartePeticao> partes, final Set<DocumentoId> documentos) {
		super(id, numero);
		
		Validate.notNull(classeSugerida, "peticao.classeSugerida.required");
		Validate.notEmpty(partes, "peticao.partes.notEmpty");
		Validate.notEmpty(documentos, "peticao.documentos.notEmpty");
	
		this.classeSugerida = classeSugerida;
		this.partes.addAll(partes);
		this.documentos.addAll(documentos);
	}
}
