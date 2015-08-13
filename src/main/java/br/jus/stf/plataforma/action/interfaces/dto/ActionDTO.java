/**
 * 
 */
package br.jus.stf.plataforma.action.interfaces.dto;

/**
 * @author Lucas.Rodrigues
 *
 */
public class ActionDTO {

	private String id;
	private String description;
	
	public ActionDTO(String id, String description) {
		this.id = id;
		this.description = description;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
}
