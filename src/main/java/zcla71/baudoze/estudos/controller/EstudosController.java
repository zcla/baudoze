package zcla71.baudoze.estudos.controller;

import java.util.Objects;

import org.springframework.stereotype.Controller;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import zcla71.baudoze.common.controller.BauBaseController;

@Controller
@RequestMapping("/estudos")
public class EstudosController extends BauBaseController {
	@GetMapping({"", "/"})
	public ModelAndView index() {
		ModelAndView result = getModelAndView("/estudos/index");
		return result;
	}

	@GetMapping("/biblia")
	public ModelAndView biblia() {
		ModelAndView result = getModelAndView("/estudos/biblia/index");
		return result;
	}

	@GetMapping("/biblia/minhas")
	public ModelAndView bibliaMinhas() {
		ModelAndView result = getModelAndView("/estudos/biblia/minhas/index");
		return result;
	}

	@GetMapping("/**")
    public ModelAndView texto(HttpServletRequest request) {
        String path = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        String bestMatch = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        String restante = new AntPathMatcher().extractPathWithinPattern(Objects.requireNonNull(bestMatch), Objects.requireNonNull(path));
        return getModelAndView("estudos/" + restante.replace(".html", ""));
    }
}
