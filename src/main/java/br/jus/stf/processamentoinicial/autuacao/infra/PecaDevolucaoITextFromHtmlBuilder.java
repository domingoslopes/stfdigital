package br.jus.stf.processamentoinicial.autuacao.infra;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

import br.jus.stf.processamentoinicial.autuacao.domain.PecaDevolucaoFromHtmlBuilder;
import br.jus.stf.processamentoinicial.autuacao.domain.model.MotivoDevolucao;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.css.CssFile;
import com.itextpdf.tool.xml.css.StyleAttrCSSResolver;
import com.itextpdf.tool.xml.html.CssAppliers;
import com.itextpdf.tool.xml.html.CssAppliersImpl;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.parser.XMLParser;
import com.itextpdf.tool.xml.pipeline.css.CSSResolver;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import com.itextpdf.tool.xml.pipeline.html.AbstractImageProvider;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;

@Component
public class PecaDevolucaoITextFromHtmlBuilder implements PecaDevolucaoFromHtmlBuilder {

	public byte[] build(String html, String identificacao, MotivoDevolucao motivoDevolucao, Long numero) {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		Document document = new Document(PageSize.A4, 40, 20, 34, 20);
		try {
			PdfWriter writer = PdfWriter.getInstance(document, os);
//			writer.setInitialLeading(12.5f);
			
			document.open();
			
			CSSResolver cssResolver = new StyleAttrCSSResolver();
			CssFile cssFile = XMLWorkerHelper.getCSS(getClass().getClassLoader().getResourceAsStream("static/vendor/ckeditor/contents.css"));
			cssResolver.addCss(cssFile);
			cssFile = XMLWorkerHelper.getCSS(getClass().getClassLoader().getResourceAsStream("static/application/plataforma/support/ckeditor/stfcontents.css"));
			cssResolver.addCss(cssFile);
			
			XMLWorkerFontProvider fontProvider = new XMLWorkerFontProvider(XMLWorkerFontProvider.DONTLOOKFORFONTS);
			fontProvider.registerDirectory(getClass().getClassLoader().getResource("fonts/").getPath());
			
			CssAppliers cssAppliers = new CssAppliersImpl(fontProvider);
			
			HtmlPipelineContext htmlContext = new HtmlPipelineContext(cssAppliers);
			htmlContext.setTagFactory(Tags.getHtmlTagProcessorFactory());
			
			htmlContext.setImageProvider(new AbstractImageProvider() {
				@Override
				public String getImageRootPath() {
					return getClass().getClassLoader().getResource("static/").getPath();
				}
			});
			
			PdfWriterPipeline pdf = new PdfWriterPipeline(document, writer);
			HtmlPipeline htmlPipeline = new HtmlPipeline(htmlContext, pdf);
			CssResolverPipeline css = new CssResolverPipeline(cssResolver, htmlPipeline);
			
			XMLWorker worker = new XMLWorker(css, true);
			XMLParser p = new XMLParser(worker);
			p.parse(IOUtils.toInputStream(html));
		} catch (IOException e) {
			throw new RuntimeException("Erro ao fazer o parse do HTML", e);
		} catch (DocumentException e) {
			throw new RuntimeException("Não foi possível instanciar um arquivo PDF em branco.", e);
		} finally {
			document.close();
		}
		// Por fim, apenas retorna o arquivo em um array de bytes
		return os.toByteArray();
	}
}
