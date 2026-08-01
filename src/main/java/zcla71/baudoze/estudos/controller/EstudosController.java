package zcla71.baudoze.estudos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import zcla71.baudoze.common.controller.BauBaseController;

@Controller
@RequestMapping("/estudos")
public class EstudosController extends BauBaseController {
	@GetMapping({"", "/"})
	public ModelAndView index() {
		return getModelAndView("/estudos/index");
	}

	// ===== /biblia

	@GetMapping("/biblia")
	public ModelAndView biblia() {
		return getModelAndView("/estudos/biblia/index");
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
