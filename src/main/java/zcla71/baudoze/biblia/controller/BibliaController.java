package zcla71.baudoze.biblia.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import zcla71.baudoze.biblia.service.BibliaService;
import zcla71.baudoze.controller.BauBaseController;

@Controller
public class BibliaController extends BauBaseController {
	@Autowired
	private BibliaService bibliaService;

	// Controller

	@GetMapping("/biblia")
	public ModelAndView biblia(@AuthenticationPrincipal OidcUser user) {
		ModelAndView result = new ModelAndView("/biblia/biblias");
		addAuthInfo(result, user);
		Map<String, Object> data = new HashMap<>();
		data.put("biblias", bibliaService.listarBiblias());
		result.addObject("data", data);
		return result;
	}

	@GetMapping("/biblia/{idBiblia}/livro")
	public ModelAndView livro(@AuthenticationPrincipal OidcUser user, @PathVariable @NonNull Long idBiblia) {
		ModelAndView result = new ModelAndView("/biblia/livros");
		addAuthInfo(result, user);
		Map<String, Object> data = new HashMap<>();
		data.put("biblia", bibliaService.listarBiblias().stream()
				.filter(b -> b.getId() == idBiblia)
				.findFirst()
				.orElse(null)
		);
		data.put("livros", bibliaService.listarLivros(idBiblia));
		result.addObject("data", data);
		return result;
	}
}
