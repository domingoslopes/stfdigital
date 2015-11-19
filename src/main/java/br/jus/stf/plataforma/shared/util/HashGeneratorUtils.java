package br.jus.stf.plataforma.shared.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashGeneratorUtils {

	private HashGeneratorUtils() {
		
	}
	
	public static String generateMD5(String message) throws IllegalArgumentException {
        return hashString(message, "MD5");
    }
 
    public static String generateSHA1(String message) throws IllegalArgumentException {
        return hashString(message, "SHA-1");
    }
 
    public static String generateSHA256(String message) throws IllegalArgumentException {
        return hashString(message, "SHA-256");
    }
 
    private static String hashString(String message, String algorithm)
            throws IllegalArgumentException {
 
        try {
            MessageDigest digest = MessageDigest.getInstance(algorithm);
            byte[] hashedBytes = digest.digest(message.getBytes("UTF-8"));
 
            return convertByteArrayToHexString(hashedBytes);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            throw new IllegalArgumentException(
                    "Não foi possível gerar o hash da String", ex);
        }
    }
    
    public static String generateMD5(File file) throws IllegalArgumentException {
        return hashFile(file, "MD5");
    }
     
    public static String generateSHA1(File file) throws IllegalArgumentException {
        return hashFile(file, "SHA-1");
    }
     
    public static String generateSHA256(File file) throws IllegalArgumentException {
        return hashFile(file, "SHA-256");
    }
 
    private static String convertByteArrayToHexString(byte[] arrayBytes) {
        StringBuilder stringBuilder = new StringBuilder();
        
        for (int i = 0; i < arrayBytes.length; i++) {
        	stringBuilder.append(Integer.toString((arrayBytes[i] & 0xff) + 0x100, 16)
                    .substring(1));
        }
        
        return stringBuilder.toString();
    }
    
    private static String hashFile(File file, String algorithm)
    		throws IllegalArgumentException {
		try (FileInputStream inputStream = new FileInputStream(file)) {
			MessageDigest digest = MessageDigest.getInstance(algorithm);

			byte[] bytesBuffer = new byte[1024];
			int bytesRead = -1;

			while ((bytesRead = inputStream.read(bytesBuffer)) != -1) {
				digest.update(bytesBuffer, 0, bytesRead);
			}

			byte[] hashedBytes = digest.digest();

			return convertByteArrayToHexString(hashedBytes);
		} catch (NoSuchAlgorithmException | IOException ex) {
			throw new IllegalArgumentException (
	                "Não foi possível gerar o hash do arquivo", ex);
		}
	}

}
