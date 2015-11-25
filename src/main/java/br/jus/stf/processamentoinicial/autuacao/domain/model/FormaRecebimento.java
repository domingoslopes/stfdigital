package br.jus.stf.processamentoinicial.autuacao.domain.model;


/**
 * @author Lucas.Rodrigues
 *
 */
public enum FormaRecebimento {

	BALCAO, SEDEX, MALOTE, FAX, EMAIL;
	
	@Override
	public String toString() {
		String forma = null;
		switch(this) {
			case BALCAO: 
				forma = "Balcão";
				break;
			case EMAIL:
				forma = "E-mail";
				break;
			case FAX: 
				forma = "Fax"; 
				break;
			case MALOTE:
				forma = "Malote"; 
				break;
			case SEDEX: 
				forma = "Sedex"; 
				break;
			default: 
				break;
		}
		return forma;
	}
	
}
