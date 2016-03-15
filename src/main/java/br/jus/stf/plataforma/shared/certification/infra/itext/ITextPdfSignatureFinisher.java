package br.jus.stf.plataforma.shared.certification.infra.itext;

import br.jus.stf.plataforma.shared.certification.domain.model.signature.HashSignature;
import br.jus.stf.plataforma.shared.certification.domain.model.signature.PdfSigningSpecification;
import br.jus.stf.plataforma.shared.certification.domain.model.signature.PreSignature;
import br.jus.stf.plataforma.shared.certification.domain.model.signature.SigningException;
import br.jus.stf.plataforma.shared.certification.domain.model.validation.CertificateValidation;

import com.itextpdf.text.pdf.PdfSignatureAppearance;

public interface ITextPdfSignatureFinisher {

	PreSignature finishPreSignature(PdfSigningSpecification spec, CertificateValidation certificateValidation,
			PdfSignatureAppearance appearance) throws SigningException;

	void finishPostSignature(PdfSigningSpecification spec, CertificateValidation certificateValidation,
			PdfSignatureAppearance appearance, HashSignature signature) throws SigningException;

}
