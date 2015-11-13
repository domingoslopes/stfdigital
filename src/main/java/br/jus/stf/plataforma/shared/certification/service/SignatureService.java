package br.jus.stf.plataforma.shared.certification.service;

import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;
import org.springframework.stereotype.Service;

import br.jus.stf.plataforma.shared.certification.interfaces.dto.PreSignatureDto;
import br.jus.stf.plataforma.shared.certification.support.DocumentToSign;

@Service
public class SignatureService {
	
	public String storeToSign(InputStream stream) {
		DocumentToSign document = new DocumentToSign(stream);
		return document.tempId();
	}

	public PreSignatureDto preSign(String tempDocId, String certificateAsHex) {
		byte[] hash = aplicarHashSHA256(new String(tempDocId + certificateAsHex).getBytes());
		PreSignatureDto dto = new PreSignatureDto();
		dto.setHash(Hex.encodeHexString(hash));
		return dto;
	}

	private byte[] aplicarHashSHA256(byte[] arr) {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
			messageDigest.update(arr);
			byte hash[] = messageDigest.digest();
			return hash;
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Algoritmo de hash SHA-256 não encontrado.", e);
		}
	}
	
	public void postSign(String contextId, String signature) {
		
	}

	
}
