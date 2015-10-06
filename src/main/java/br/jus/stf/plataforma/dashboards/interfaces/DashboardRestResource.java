package br.jus.stf.plataforma.dashboards.interfaces;

import java.util.Arrays;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wordnik.swagger.annotations.ApiOperation;

import br.jus.stf.plataforma.dashboards.interfaces.dto.DashboardDto;

/**
 * Api REST para recuperar dashboards.
 * 
 * @author Tomas.Godoi
 */
@RestController
@RequestMapping("/api/dashboards")
public class DashboardRestResource {

	@ApiOperation("Recupera o dashboard padrão para o perfil atual do usuário")
	@RequestMapping(value = "/padrao", method = RequestMethod.GET)
	public DashboardDto recuperarPadrao() {
		DashboardDto dDto = new DashboardDto();
		dDto.setDashlets(Arrays.asList("grafico-distribuicao"));
		dDto.setLayout("padrao");
		return dDto;
	}
	
}
