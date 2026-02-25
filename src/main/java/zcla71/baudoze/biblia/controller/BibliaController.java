package zcla71.baudoze.biblia.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import zcla71.baudoze.biblia.service.BibliaService;
import zcla71.baudoze.controller.BauBaseController;

@Controller
public class BibliaController extends BauBaseController {
	@Autowired
	private BibliaService bibliaService;

	// Controller

	@GetMapping("/biblia")
	public ModelAndView listar(@AuthenticationPrincipal OidcUser user) {
		ModelAndView result = new ModelAndView("/biblia/lista");
		addAuthInfo(result, user);
		Map<String, Object> data = new HashMap<>();
		data.put("biblias", bibliaService.listar());
		result.addObject("data", data);
		return result;
	}
}
