package br.jus.stf.shared;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Teste unitário do validador de CPF e CNPJ.
 * 
 * @author Anderson.Araujo
 * @since 22.03.2016
 *
 */
public class ValidadorCPFCNPJUnitTest {
	
	@Test
	public void criarCPFInvalidoComCaracteresAlfanumericos(){
		String numeroCPF = "123hfh";
		Boolean cpfValido = ValidadorCPFCNPJ.isCPFValido(numeroCPF);
		
		assertFalse(cpfValido);
	}
	
	@Test
	public void criarCPFInvalidoComMenos11Digitos(){
		String numeroCPF = "85462536";
		Boolean cpfValido = ValidadorCPFCNPJ.isCPFValido(numeroCPF);
		
		assertFalse(cpfValido);
	}
	
	@Test
	public void criarCPFInvalidoComMais11Digitos(){
		String numeroCPF = "8546253654857";
		Boolean cpfValido = ValidadorCPFCNPJ.isCPFValido(numeroCPF);
		
		assertFalse(cpfValido);
	}
	
	@Test
	public void criarCPFInvalidoCom11Digitos(){
		String numeroCPF = "49862486181";
		Boolean cpfValido = ValidadorCPFCNPJ.isCPFValido(numeroCPF);
		
		assertFalse(cpfValido);
	}
	
	@Test
	public void criarCPFValido(){
		String numeroCPF = "49862486180";
		Boolean cpfValido = ValidadorCPFCNPJ.isCPFValido(numeroCPF);
		
		assertTrue(cpfValido);
	}
	
	@Test
	public void criarCPFValidoComCaracteresEspeciais(){
		String numeroCPF = "498.624.861-80";
		Boolean cpfValido = ValidadorCPFCNPJ.isCPFValido(numeroCPF);
		
		assertTrue(cpfValido);
	}
	
	@Test
	public void criarCPFValidoComEspacosBranco(){
		String numeroCPF = "498 624 861 80";
		Boolean cpfValido = ValidadorCPFCNPJ.isCPFValido(numeroCPF);
		
		assertTrue(cpfValido);
	}
	
	@Test
	public void criarCNPJInvalidoComCaracteresAlfanumericos(){
		String numeroCNPJ = "123hfh";
		Boolean cnpjValido = ValidadorCPFCNPJ.isCNPJValido(numeroCNPJ);
		
		assertFalse(cnpjValido);
	}
	
	@Test
	public void criarCNPJInvalidoComMenos14Digitos(){
		String numeroCNPJ = "85462536";
		Boolean cnpjValido = ValidadorCPFCNPJ.isCNPJValido(numeroCNPJ);
		
		assertFalse(cnpjValido);
	}
	
	@Test
	public void criarCNPJInvalidoComMais14Digitos(){
		String numeroCNPJ = "8210131700018910";
		Boolean cnpjValido = ValidadorCPFCNPJ.isCNPJValido(numeroCNPJ);
		
		assertFalse(cnpjValido);
	}
	
	@Test
	public void criarCNPJInvalidoCom14Digitos(){
		String numeroCNPJ = "82101317000188";
		Boolean cnpjValido = ValidadorCPFCNPJ.isCNPJValido(numeroCNPJ);
		
		assertFalse(cnpjValido);
	}
	
	@Test
	public void criarCNPJValido(){
		String numeroCNPJ = "82101317000189";
		Boolean cnpjValido = ValidadorCPFCNPJ.isCNPJValido(numeroCNPJ);
		
		assertTrue(cnpjValido);
	}
	
	@Test
	public void criarCNPJValidoComCaracteresEspeciais(){
		String numeroCNPJ = "82.101.317/0001-89";
		Boolean cnpjValido = ValidadorCPFCNPJ.isCNPJValido(numeroCNPJ);
		
		assertTrue(cnpjValido);
	}
	
	@Test
	public void criarCNPJValidoComEspacosBranco(){
		String numeroCNPJ = "82  101 317 0001 89";
		Boolean cnpjValido = ValidadorCPFCNPJ.isCNPJValido(numeroCNPJ);
		
		assertTrue(cnpjValido);
	}
}
