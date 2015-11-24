package br.jus.stf.plataforma.shared.certification.infra.itext;

import com.itextpdf.text.pdf.PdfSignatureAppearance;

import br.jus.stf.plataforma.shared.certification.domain.model.PDFSigningSpecification;
import br.jus.stf.plataforma.shared.certification.domain.model.PreSignature;
import br.jus.stf.plataforma.shared.certification.support.SignatureException;

public interface ITextPDFSignatureFinisher {

	PreSignature finishPreSignature(PDFSigningSpecification spec, PdfSignatureAppearance appearance) throws SignatureException;

}
