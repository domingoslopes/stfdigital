package br.jus.stf.processamentoinicial.suporte.interfaces;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.jus.stf.jurisprudencia.controletese.domain.model.Assunto;
import br.jus.stf.jurisprudencia.controletese.domain.model.AssuntoRepository;
import br.jus.stf.processamentoinicial.suporte.interfaces.dto.AssuntoDto;
import br.jus.stf.processamentoinicial.suporte.interfaces.dto.AssuntoDtoAssembler;
import br.jus.stf.shared.AssuntoId;

/**
 * API REST de consulta de assuntos.
 * 
 * @author Anderson.Araújo
 */
@RestController
@RequestMapping("/api/assuntos")
public class AssuntoRestResource {
	
	@Autowired
	private AssuntoRepository assuntoRepository;
	
	@Autowired
	private AssuntoDtoAssembler assuntoDtoAssembler;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<AssuntoDto> listar(@RequestParam("termo") String termo) {
		List<AssuntoDto> assuntos = new ArrayList<AssuntoDto>();
		
		if (NumberUtils.isNumber(termo)) {
			Assunto assunto = assuntoRepository.findOne(new AssuntoId(termo));
			if (assunto != null) {
				assuntos.add(assuntoDtoAssembler.toDto(assunto));
			}
		} else {
			assuntos = getArvoreAssuntos(assuntoRepository.findAssuntoByDescricao(termo.toUpperCase()));
		}
		
		return assuntos;
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public AssuntoDto consultar(@PathVariable String id) {
		return assuntoDtoAssembler.toDto(assuntoRepository.findOne(new AssuntoId(id)));
	}
	
	public List<AssuntoDto> getArvoreAssuntos(List<Assunto> assuntos){
		if (assuntos == null || assuntos.isEmpty()) {
			return new ArrayList<AssuntoDto>();
		}
		
		Map<String,AssuntoDto> coletor = new HashMap<String,AssuntoDto>(0);
		coletor.put("raiz", new AssuntoDto());
		for (Assunto assunto : assuntos) {
			adicionarNo(assunto, coletor);
		}
		
		return coletor.get("raiz").getAssuntosFilhos();
	}

	private void adicionarNo(Assunto assunto, Map<String, AssuntoDto> coletor) {
		if (coletor.get(assunto.id().toString()) != null) {
			return;
		}
		
		AssuntoDto dto = assuntoDtoAssembler.toDto(assunto);
		Assunto pai = assunto.assuntoPai();
		
		if (pai != null) {
			//Tenta recuperar o nó-pai da lista.
			AssuntoDto paiDto = coletor.get(pai.id().toString());
			
			//Se conseguiu recuperá-lo
			if (paiDto != null) {
				//Adiciona o nó em questão à lsita de nós-filhos do pai.
				paiDto.getAssuntosFilhos().add(dto);
			} else {
				adicionarNo(pai, coletor);
				coletor.get(pai.id().toString()).getAssuntosFilhos().add(dto);
			}
		} else {
			coletor.get("raiz").getAssuntosFilhos().add(dto);
		}
		
		coletor.put(assunto.id().toString(), dto);
	}
}
