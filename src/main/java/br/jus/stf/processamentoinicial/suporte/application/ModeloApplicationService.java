package br.jus.stf.processamentoinicial.suporte.application;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.processamentoinicial.suporte.domain.ModeloFactory;
import br.jus.stf.processamentoinicial.suporte.domain.model.Modelo;
import br.jus.stf.processamentoinicial.suporte.domain.model.ModeloRepository;
import br.jus.stf.processamentoinicial.suporte.domain.model.TipoModelo;
import br.jus.stf.processamentoinicial.suporte.domain.model.TipoModeloRepository;
import br.jus.stf.shared.DocumentoId;
import br.jus.stf.shared.ModeloId;
import br.jus.stf.shared.TipoModeloId;

@Component
@Transactional
public class ModeloApplicationService {

	@Autowired
	private TipoModeloRepository tipoModeloRepository;

	@Autowired
	private ModeloRepository modeloRepository;

	@Autowired
	private ModeloFactory modeloFactory;

	public Modelo criarModelo(TipoModeloId tipoModeloId, String nome) {
		TipoModelo tipoModelo = tipoModeloRepository.findOne(tipoModeloId);
		ModeloId modeloId = modeloRepository.nextId();
		DocumentoId documentoId = modeloFactory.criarDocumentoModeloPadrao(tipoModeloId, nome);
		Modelo modelo = new Modelo(modeloId, tipoModelo, nome, documentoId);

		return modeloRepository.save(modelo);
	}

}
