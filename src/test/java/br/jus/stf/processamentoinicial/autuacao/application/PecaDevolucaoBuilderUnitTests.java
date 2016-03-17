package br.jus.stf.processamentoinicial.autuacao.application;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;

import br.jus.stf.processamentoinicial.autuacao.domain.PecaDevolucaoBuilder;
import br.jus.stf.processamentoinicial.autuacao.domain.model.MotivoDevolucao;
import br.jus.stf.processamentoinicial.autuacao.infra.PecaDevolucaoITextBuilder;

/**
 * Testes Unitários para validação das regras de geração dos ofícios de devolução de
 * petições indevidamente encaminhadas ao STF.
 * 
 * @author Rodrigo Barreiros
 *
 * @since 1.0.0.M3
 * @since 05.10.2015
 */
public class PecaDevolucaoBuilderUnitTests {
	
	private static final String NOME_OFICIO_PATTERN = "target/%s.pdf";

	private PecaDevolucaoBuilder builder = new PecaDevolucaoITextBuilder();
	
	/**
	 * Testa a geração do ofício de "Remessa Indevida".
	 * 
	 * @throws IOException caso não consiga gerar o ofício
	 */
	@Test
	public void testBuilderRemessaIndevida() throws IOException {
		MotivoDevolucao motivoDevolucao = new MotivoDevolucao(1L, "remessa_indevida");
		byte[] oficio = builder.build("06/2015", motivoDevolucao, 33445L);
		
		Files.write(Paths.get(String.format(NOME_OFICIO_PATTERN, motivoDevolucao.descricao())), oficio);
	}

	/**
	 * Testa a geração do ofício de "Transitado".
	 * 
	 * @throws IOException caso não consiga gerar o ofício
	 */
	@Test
	public void testBuilderTransitado() throws IOException {
		MotivoDevolucao motivoDevolucao = new MotivoDevolucao(2L, "transitado");
		byte[] oficio = builder.build("06/2015", motivoDevolucao, 33445L);
		
		Files.write(Paths.get(String.format(NOME_OFICIO_PATTERN, motivoDevolucao.descricao())), oficio);
	}

	/**
	 * Testa a geração do ofício de "Baixado".
	 * 
	 * @throws IOException caso não consiga gerar o ofício
	 */
	@Test
	public void testBuilderBaixado() throws IOException {
		MotivoDevolucao motivoDevolucao = new MotivoDevolucao(2L, "baixado");
		byte[] oficio = builder.build("06/2015", motivoDevolucao, 33445L);
		
		Files.write(Paths.get(String.format(NOME_OFICIO_PATTERN, motivoDevolucao.descricao())), oficio);
	}

}
