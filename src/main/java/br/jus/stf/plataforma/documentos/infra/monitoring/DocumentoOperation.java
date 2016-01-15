package br.jus.stf.plataforma.documentos.infra.monitoring;

/**
 * Representa uma operação sobre um documento.
 * 
 * @author Tomas.Godoi
 *
 */
public class DocumentoOperation {

	private String id;
	private String operation;
	private String persisterClass;
	private long size;
	private long durationInMillis;
	private boolean errorOcurred;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getPersisterClass() {
		return persisterClass;
	}

	public void setPersisterClass(String persisterClass) {
		this.persisterClass = persisterClass;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public long getDurationInMillis() {
		return durationInMillis;
	}

	public void setDurationInMillis(long durationInMillis) {
		this.durationInMillis = durationInMillis;
	}

	public boolean isErrorOcurred() {
		return errorOcurred;
	}

	public void setErrorOcurred(boolean errorOcurred) {
		this.errorOcurred = errorOcurred;
	}
}
