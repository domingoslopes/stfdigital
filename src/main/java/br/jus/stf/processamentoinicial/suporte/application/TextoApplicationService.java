package br.jus.stf.processamentoinicial.suporte.application;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.processamentoinicial.suporte.domain.DocumentoAdapter;
import br.jus.stf.processamentoinicial.suporte.domain.PeticaoAdapter;
import br.jus.stf.processamentoinicial.suporte.domain.model.Modelo;
import br.jus.stf.processamentoinicial.suporte.domain.model.ModeloRepository;
import br.jus.stf.processamentoinicial.suporte.domain.model.Texto;
import br.jus.stf.processamentoinicial.suporte.domain.model.TextoRepository;
import br.jus.stf.processamentoinicial.suporte.interfaces.commands.SubstituicaoTagTexto;
import br.jus.stf.shared.DocumentoId;
import br.jus.stf.shared.ModeloId;
import br.jus.stf.shared.PeticaoId;
import br.jus.stf.shared.TextoId;

@Component
@Transactional
public class TextoApplicationService {

	@Autowired
	private ModeloRepository modeloRepository;
	
	@Autowired
	private DocumentoAdapter documentoAdapter;
	
	@Autowired
	private TextoRepository textoRepository;
	
	@Autowired
	private PeticaoAdapter peticaoAdapter;
	
	public Texto gerarTextoDePeticao(PeticaoId peticaoId, ModeloId modeloId, List<SubstituicaoTagTexto> substituicoes) {
		Modelo modelo = modeloRepository.findOne(modeloId);
		DocumentoId documentoId = documentoAdapter.gerarDocumentoComTags(modelo.documento(), substituicoes);
		
		TextoId textoId = textoRepository.nextId();
		Texto texto = new Texto(textoId, documentoId);
		
		texto = textoRepository.save(texto);
		
		peticaoAdapter.associarTextoDevolucao(peticaoId, texto.id(), modeloId);
		
		return texto;
	}

}
