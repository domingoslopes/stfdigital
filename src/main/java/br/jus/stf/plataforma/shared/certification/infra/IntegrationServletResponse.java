package br.jus.stf.plataforma.shared.certification.infra;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;

import org.apache.catalina.connector.Response;

public class IntegrationServletResponse extends Response {

	private ByteArrayOutputStream os = new ByteArrayOutputStream();

	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		return new ServletOutputStream() {

			@Override
			public void write(int b) throws IOException {
				os.write(b);
			}

			@Override
			public void setWriteListener(WriteListener listener) {

			}

			@Override
			public boolean isReady() {
				return true;
			}
		};
	}
	
	public byte[] getBytes() {
		return os.toByteArray();
	}

}
