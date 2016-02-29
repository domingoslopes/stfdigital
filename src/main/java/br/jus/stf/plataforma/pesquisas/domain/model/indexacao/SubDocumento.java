package br.jus.stf.plataforma.pesquisas.domain.model.indexacao;

/**
 * Um subdocumento é um item de uma coleção de um documento.
 * 
 * @author Tomas.Godoi
 *
 */
public class SubDocumento extends Documento {

	private String campoColecao;
	private String expressaoId;
	private Object idItem;

	public SubDocumento(String id, String tipo, Indice indice, String campoColecao, String expressaoId, Object idItem,
			String objeto) {
		super(id, tipo, indice, objeto);
		this.campoColecao = campoColecao;
		this.expressaoId = expressaoId;
		this.idItem = idItem;
	}

	public String campoColecao() {
		return campoColecao;
	}

	public String expressaoId() {
		return expressaoId;
	}

	public Object idItem() {
		return idItem;
	}

}
