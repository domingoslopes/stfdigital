package br.jus.stf.processamentoinicial.autuacao.domain.model;

import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.apache.commons.lang3.Validate;

import br.jus.stf.shared.ClasseId;
import br.jus.stf.shared.PeticaoId;

/**
 * @author Lucas Rodrigues
 */
@Entity
@DiscriminatorValue("ELETRONICO")
public class PeticaoEletronica extends Peticao {
	
	public PeticaoEletronica(final PeticaoId id, final Long numero, final ClasseId classeSugerida, final Set<PartePeticao> partes, final Set<PecaPeticao> pecas) {
		super(id, numero);
		
		Validate.notNull(classeSugerida, "peticao.classeSugerida.required");
		Validate.notEmpty(partes, "peticao.partes.notEmpty");
		Validate.notEmpty(pecas, "peticao.pecas.notEmpty");
	
		super.sugerirClasse(classeSugerida);
		partes.forEach(parte -> super.adicionarParte(parte));
		pecas.forEach(peca -> super.adicionarPeca(peca));
	}
	
	PeticaoEletronica() {
	}
	
}
