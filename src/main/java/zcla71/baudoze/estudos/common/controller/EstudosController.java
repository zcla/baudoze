package zcla71.baudoze.estudos.common.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import zcla71.baudoze.common.controller.BauBaseController;
import zcla71.baudoze.estudos.biblia.concordancia.dto.Concordancia;
import zcla71.baudoze.estudos.biblia.concordancia.dto.Parte;
import zcla71.baudoze.estudos.biblia.concordancia.dto.TabelaLinha;
import zcla71.baudoze.estudos.biblia.concordancia.dto.Titulo;
import zcla71.baudoze.estudos.biblia.concordancia.service.ConcordanciaService;

@RequiredArgsConstructor 
@Controller
@RequestMapping("/estudos")
public class EstudosController extends BauBaseController {
	private final ConcordanciaService concordanciaService;

	@GetMapping({"", "/"})
	public ModelAndView index() {
		return getModelAndView("/estudos/index");
	}

	// ===== /biblia

	@GetMapping("/biblia")
	public ModelAndView biblia() {
		return getModelAndView("/estudos/biblia/index");
	}

	// ----- /biblia/concordancia

	@GetMapping("/biblia/concordancia")
	public ModelAndView concordancia() throws StreamReadException, DatabindException, IOException {
		ModelAndView result = getModelAndView("/estudos/biblia/concordancia/index");

		result.addObject("data", Map.of(
			"concordancia", concordanciaService.getTabela()
		));

		return result;
	}

	// ----- /biblia/estrutura

	@GetMapping("/biblia/estrutura/mt")
	public ModelAndView estruturaMt() {
		return getModelAndView("/estudos/biblia/estrutura/mt");
	}

	// ----- /biblia/minhas

	@GetMapping("/biblia/minhas")
	public ModelAndView bibliaMinhas() {
		return getModelAndView("/estudos/biblia/minhas/index");
	}

	@GetMapping("/biblia/minhas/CNBB2008")
	public ModelAndView bibliaMinhasCNBB2008() {
		return getModelAndView("/estudos/biblia/minhas/CNBB2008");
	}

	@GetMapping("/biblia/minhas/NVETA1986")
	public ModelAndView bibliaMinhasNVETA1986() {
		return getModelAndView("/estudos/biblia/minhas/NVETA1986");
	}

	// ----- /biblia/texto

	@GetMapping("/biblia/texto/mt-1")
	public ModelAndView textoMt1() {
		return getModelAndView("/estudos/biblia/texto/mt-1");
	}
}
