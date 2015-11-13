package br.jus.stf.plataforma.shared.certification.service;

import java.io.InputStream;

import org.springframework.stereotype.Service;

import br.jus.stf.plataforma.shared.certification.support.AssinadorPorPartes;
import br.jus.stf.plataforma.shared.certification.support.PreAssinaturaDto;
import br.jus.stf.plataforma.shared.certification.support.SHA256DetachedAssinadorPorPartes;

@Service
public class AssinaturaService {

	public PreAssinaturaDto preSign(InputStream documento) {
		AssinadorPorPartes app = new SHA256DetachedAssinadorPorPartes(false);
//		byte[] hash = app.preAssinar(cadeia, crls, pdf, reason, ca);
		byte[] hash = new byte[0];
		
		
		
//		byte[] assinatura = app.posAssinar(ca, assinatura);
		byte[] assinatura = new byte[0];
		
		PreAssinaturaDto dto = new PreAssinaturaDto();
		return dto;
	}

	public void postSign(String contextId, String signature) {
		
	}
	
}
