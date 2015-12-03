package br.jus.stf.plataforma.shared.certification.infra.itext;

import com.itextpdf.text.pdf.PdfSignatureAppearance;

import br.jus.stf.plataforma.shared.certification.domain.model.certificate.CertificateValidation;
import br.jus.stf.plataforma.shared.certification.domain.model.signature.HashSignature;
import br.jus.stf.plataforma.shared.certification.domain.model.signature.PdfSigningSpecification;
import br.jus.stf.plataforma.shared.certification.domain.model.signature.PreSignature;
import br.jus.stf.plataforma.shared.certification.domain.model.signature.SigningException;

public interface ITextPdfSignatureFinisher {

	PreSignature finishPreSignature(PdfSigningSpecification spec, CertificateValidation certificateValidation,
			PdfSignatureAppearance appearance) throws SigningException;

	void finishPostSignature(PdfSigningSpecification spec, CertificateValidation certificateValidation,
			PdfSignatureAppearance appearance, HashSignature signature) throws SigningException;

}
