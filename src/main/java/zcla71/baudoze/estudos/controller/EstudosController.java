package zcla71.baudoze.estudos.controller;

import java.util.Objects;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import zcla71.baudoze.auth_user.model.entity.AuthUser;
import zcla71.baudoze.common.controller.BauBaseController;

@Controller
@RequestMapping("/estudos")
public class EstudosController extends BauBaseController {
	@GetMapping({"", "/"})
	public ModelAndView index(@AuthenticationPrincipal AuthUser authUser) {
		ModelAndView result = getModelAndView("/estudos/index", authUser);
		return result;
	}

	@GetMapping("/**")
    public ModelAndView texto(@AuthenticationPrincipal AuthUser authUser, HttpServletRequest request) {
        String path = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        String bestMatch = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        String restante = new AntPathMatcher().extractPathWithinPattern(Objects.requireNonNull(bestMatch), Objects.requireNonNull(path));
        return getModelAndView("estudos/" + restante.replace(".html", ""), authUser);
    }
}
