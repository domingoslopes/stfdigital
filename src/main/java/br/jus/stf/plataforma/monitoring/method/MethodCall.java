package br.jus.stf.plataforma.monitoring.method;

import static java.time.ZonedDateTime.now;
import static java.time.format.DateTimeFormatter.ofPattern;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Representa uma chamada de método monitorada.
 * 
 * @author Tomas.Godoi
 *
 */
public class MethodCall {

	private String monitorGroup;
	@JsonProperty("@timestamp")
	private final String timestamp;
	private String clazz;
	private String method;
	private String resourceId;
	private long resourceSize;
	private long durationInMillis;
	private boolean errorOcurred;

	public MethodCall() {
		this.timestamp = now().format(ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ"));
	}

	public String getMonitorGroup() {
		return monitorGroup;
	}

	public void setMonitorGroup(String monitorGroup) {
		this.monitorGroup = monitorGroup;
	}

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public long getResourceSize() {
		return resourceSize;
	}

	public void setResourceSize(long resourceSize) {
		this.resourceSize = resourceSize;
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

	public String getTimestamp() {
		return timestamp;
	}
}
