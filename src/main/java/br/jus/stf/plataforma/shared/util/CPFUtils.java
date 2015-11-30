package br.jus.stf.plataforma.shared.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author Rafael.Alencar
 *
 */
public class CPFUtils {
	
	private CPFUtils() {
		
	}

	/**
	 * @param cpf
	 * @return
	 */
	public static boolean isValido(String cpf) {
		if (cpf == null || cpf.length() != 11 || isCPFPadrao(cpf)) {
			return false;
		}

		try {
			Long.parseLong(cpf);
		} catch (NumberFormatException e) { // CPF não possui somente números
			return false;
		}

		return calculaDigitoVerificador(cpf.substring(0, 9)).equals(
				cpf.substring(9, 11));
	}
	
	/**
	 * @param cpf
	 * @return
	 */
	public static String aplicarMascara(String cpf) {
		Pattern pattern = Pattern.compile("(\\d{3})(\\d{3})(\\d{3})(\\d{2})");
		Matcher matcher = pattern.matcher(cpf);

		if (matcher.matches() && isValido(cpf)) {
			cpf = matcher.replaceAll("$1.$2.$3-$4");
		}

		return cpf;
	}

    /**
     * @param cpf
     * @return
     */
    private static boolean isCPFPadrao(String cpf) {
		if (cpf.equals("00000000000") || cpf.equals("11111111111")
				|| cpf.equals("22222222222") || cpf.equals("33333333333")
				|| cpf.equals("44444444444") || cpf.equals("55555555555")
				|| cpf.equals("66666666666") || cpf.equals("77777777777")
				|| cpf.equals("88888888888") || cpf.equals("99999999999")) {
			return true;
		}

		return false;
	}

	/**
	 * @param numero
	 * @return
	 */
	private static String calculaDigitoVerificador(String numero) {
		Integer primeiroDigito, segundoDigito;
		int soma = 0, peso = 10;

		for (int i = 0; i < numero.length(); i++) {
			soma += Integer.parseInt(numero.substring(i, i + 1)) * peso--;
		}

		if (soma % 11 == 0 | soma % 11 == 1) {
			primeiroDigito = new Integer(0);
		} else {
			primeiroDigito = new Integer(11 - (soma % 11));
		}

		soma = 0;
		peso = 11;
		for (int i = 0; i < numero.length(); i++) {
			soma += Integer.parseInt(numero.substring(i, i + 1)) * peso--;
		}

		soma += primeiroDigito.intValue() * 2;
		if (soma % 11 == 0 | soma % 11 == 1) {
			segundoDigito = new Integer(0);
		} else {
			segundoDigito = new Integer(11 - (soma % 11));
		}

		return primeiroDigito.toString() + segundoDigito.toString();
	}
}