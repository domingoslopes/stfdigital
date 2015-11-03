package br.jus.stf.processamentoinicial.autuacao.infra;

import static com.itextpdf.text.Element.ALIGN_CENTER;
import static com.itextpdf.text.Element.ALIGN_LEFT;
import static com.itextpdf.text.Element.ALIGN_RIGHT;
import static java.lang.String.format;
import static java.time.LocalDate.now;
import static java.time.format.DateTimeFormatter.ofPattern;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import br.jus.stf.processamentoinicial.autuacao.domain.PecaDevolucaoBuilder;
import br.jus.stf.processamentoinicial.autuacao.domain.model.TipoDevolucao;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * Implementação <i>iText</i> da interface {@link PecaDevolucaoBuilder}.
 * 
 * @author Rodrigo Barreiros
 *
 * @since 1.0.0.M3
 * @since 09.10.2015
 */
@Component
public class PecaDevolucaoITextBuilder implements PecaDevolucaoBuilder {
	
	private static final String ASSUNTO_PATTERN = "Devolve/encaminha Petição/STF n. %s";
	
	private static final String NUMERO_PATTERN = "Ofício n. %s/SEJ";
	
	private static final String DESTINO = "Ao(À) Senhor(a) Secretário(a) Judiciário(a) do Superior Tribunal de Justiça";
	
	private static final String RESPONSAVEL = "João Bosco Marcial de Castro";
	
	private static final String TITULO = "Secretário Judiciário";
	
	private static final String TIME_PATTERN = "dd 'de' MMMM 'de' yyyy";
	
	private static final Locale PT_BR = new Locale("pt", "BR");
	
	/**
	 * @see br.jus.stf.processamentoinicial.autuacao.domain.PecaDevolucaoBuilder#build(br.jus.stf.processamentoinicial.autuacao.domain.model.Peticao, br.jus.stf.processamentoinicial.autuacao.domain.model.TipoDevolucao, java.lang.Long)
	 */
	@Override
	public byte[] build(String identificacao, TipoDevolucao tipoDevolucao, Long numero) {
		// O path do template é determinado pelo tipo de devolução. TODO: Analisar a possibilidade de usar o banco para armazenamento
		String templatePath = format("templates/devolucao/%s.pdf", tipoDevolucao.nome());
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		Document document = new Document();
		
		try {
			// Primeiro devemos abrir um arquivo PDF em branco, sem conteúdo
			PdfWriter writer = PdfWriter.getInstance(document, os);
			document.open();
	
			// Depois recuperamos o template PDF que será utilizado como base, de acordo com o tipo de devolução
			InputStream is = new ClassPathResource(templatePath).getInputStream();

			// Agora temos que jogar o conteúdo do template dentro de um objeto PDF manunipulável
			PdfReader reader = new PdfReader(is);
			PdfImportedPage page = writer.getImportedPage(reader, 1);

			// Então adicionamos o conteúdo do template ao documento PDF que acabamos de abrir
			PdfContentByte conteudo = writer.getDirectContent();
			conteudo.addTemplate(page, 0, 0);

			// Adicionamos os parâmetros nas posições específicas do template
			conteudo.beginText();
			Font helvetica = new Font(FontFamily.HELVETICA, 12);
			conteudo.setFontAndSize(helvetica.getCalculatedBaseFont(false), 12);
			conteudo.showTextAligned(ALIGN_RIGHT, format(NUMERO_PATTERN, numero), 191, 680, 0);
			conteudo.showTextAligned(ALIGN_RIGHT, format(ASSUNTO_PATTERN, identificacao), 376, 583, 0);
			conteudo.showTextAligned(ALIGN_LEFT, now().format(ofPattern(TIME_PATTERN, PT_BR)), 430, 650, 0);
			conteudo.showTextAligned(ALIGN_CENTER, RESPONSAVEL, 300, 265, 0);
			conteudo.showTextAligned(ALIGN_CENTER, TITULO, 300, 250, 0);
			conteudo.showTextAligned(ALIGN_LEFT, DESTINO, 100, 80, 0);
			conteudo.endText();
		} catch (IOException e) {
			throw new RuntimeException(format("O template [%s] não foi encontrado ou não é possível ler seu conteúdo.", templatePath), e);
		} catch (DocumentException e) {
			throw new RuntimeException("Não foi possível instanciar um arquivo PDF em branco.", e);
		} finally {
			document.close();
		}
		
		// Por fim, apenas retorna o arquivo em um array de bytes
		return os.toByteArray();
	}

}
