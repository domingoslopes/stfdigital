package br.jus.stf.plataforma.shared.certification.infra.itext;

import com.itextpdf.text.pdf.PdfSignatureAppearance;

import br.jus.stf.plataforma.shared.certification.domain.model.certificate.CertificateValidation;
import br.jus.stf.plataforma.shared.certification.domain.model.signature.HashSignature;
import br.jus.stf.plataforma.shared.certification.domain.model.signature.PDFSigningSpecification;
import br.jus.stf.plataforma.shared.certification.domain.model.signature.PreSignature;
import br.jus.stf.plataforma.shared.certification.domain.model.signature.SigningException;

public interface ITextPDFSignatureFinisher {

	PreSignature finishPreSignature(PDFSigningSpecification spec, CertificateValidation certificateValidation,
			PdfSignatureAppearance appearance) throws SigningException;

	void finishPostSignature(PDFSigningSpecification spec, CertificateValidation certificateValidation,
			PdfSignatureAppearance appearance, HashSignature signature) throws SigningException;

}
