package br.jus.stf.plataforma.shared.certification.service;

import java.io.InputStream;

import org.springframework.stereotype.Service;

import br.jus.stf.plataforma.shared.certification.interfaces.dto.PreSignatureDto;
import br.jus.stf.plataforma.shared.certification.support.AssinadorPorPartes;
import br.jus.stf.plataforma.shared.certification.support.DocumentToSign;
import br.jus.stf.plataforma.shared.certification.support.SHA256DetachedAssinadorPorPartes;

@Service
public class SignatureService {
	
	public String storeToSign(InputStream stream) {
		DocumentToSign document = new DocumentToSign(stream);
		return document.tempId();
	}
	
	public PreSignatureDto preSign(InputStream documento) {
		AssinadorPorPartes app = new SHA256DetachedAssinadorPorPartes(false);
//		byte[] hash = app.preAssinar(cadeia, crls, pdf, reason, ca);
		byte[] hash = new byte[0];
		
		
		
//		byte[] assinatura = app.posAssinar(ca, assinatura);
		byte[] assinatura = new byte[0];
		
		PreSignatureDto dto = new PreSignatureDto();
		return dto;
	}

	public void postSign(String contextId, String signature) {
		
	}
	
}
