package br.jus.stf.shared;

import org.apache.commons.lang.StringUtils;


/**
 * Validador de CPF e CNPJ.
 * 
 * @author Anderson.Araujo
 * @since 22.03.2016
 *
 */
public class ValidadorCPFCNPJ {
	public static boolean isCPFValido(String numeroCPF)  {
		String cpf = removerCaracteresEspeciais(StringUtils.deleteWhitespace(numeroCPF));
		int[] pesoCPF = { 11, 10, 9, 8, 7, 6, 5, 4, 3, 2 };
		
		if (!StringUtils.isNumeric(cpf))
			return false;
		
		if ((cpf == null) || (cpf.length() != 11))
			return false;

		Integer digito1 = calcularDigito(cpf.substring(0, 9), pesoCPF);
		Integer digito2 = calcularDigito(cpf.substring(0, 9) + digito1, pesoCPF);
		
		return cpf.equals(cpf.substring(0, 9) + digito1.toString() + digito2.toString());
	}
	
	public static boolean isCNPJValido(String numeroCNPJ)  {
		String cnpj = removerCaracteresEspeciais(StringUtils.deleteWhitespace(numeroCNPJ));
			
		int[] pesoCNPJ = { 6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2 };
		
		if ((cnpj == null) || (cnpj.length() != 14))
			return false;

		Integer digito1 = calcularDigito(cnpj.substring(0, 12), pesoCNPJ);
		Integer digito2 = calcularDigito(cnpj.substring(0, 12) + digito1, pesoCNPJ);
		
		return cnpj.equals(cnpj.substring(0, 12) + digito1.toString() + digito2.toString());
	}
	
	public static String removerCaracteresEspeciais(String str) {
		if (str != null)
			return str.replaceAll("\\p{Punct}", "");
		
		return null;
	}
	
	private static int calcularDigito(String str, int[] peso) {
		int soma = 0;
		for (int indice = str.length() - 1, digito; indice >= 0; indice--) {
			digito = Integer.parseInt(str.substring(indice, indice + 1));
			soma += digito * peso[peso.length - str.length() + indice];
		}
		soma = 11 - soma % 11;
		return soma > 9 ? 0 : soma;
	}
}
