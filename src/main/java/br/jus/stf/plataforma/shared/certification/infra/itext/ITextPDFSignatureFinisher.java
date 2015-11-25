package br.jus.stf.plataforma.shared.certification.infra.itext;

import com.itextpdf.text.pdf.PdfSignatureAppearance;

import br.jus.stf.plataforma.shared.certification.domain.model.CertificateValidation;
import br.jus.stf.plataforma.shared.certification.domain.model.PDFSigningSpecification;
import br.jus.stf.plataforma.shared.certification.domain.model.PreSignature;
import br.jus.stf.plataforma.shared.certification.support.HashSignature;
import br.jus.stf.plataforma.shared.certification.support.SigningException;

public interface ITextPDFSignatureFinisher {

	PreSignature finishPreSignature(PDFSigningSpecification spec, CertificateValidation certificateValidation,
			PdfSignatureAppearance appearance) throws SigningException;

	void finishPostSignature(PDFSigningSpecification spec, CertificateValidation certificateValidation,
			PdfSignatureAppearance appearance, HashSignature signature) throws SigningException;

}
