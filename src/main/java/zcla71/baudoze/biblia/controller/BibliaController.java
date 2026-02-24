package zcla71.baudoze.biblia.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import zcla71.baudoze.biblia.BibliaService;
import zcla71.baudoze.biblia.model.Biblia;
import zcla71.baudoze.biblia.view.BibliaListaView;
import zcla71.baudoze.biblia.view.BibliaListaViewBiblia;
import zcla71.baudoze.controller.BauBaseController;

@Controller
public class BibliaController extends BauBaseController {
	@Autowired
	private BibliaService bibliaService;

	private List<BibliaListaViewBiblia> listaBiblias() {
		List<BibliaListaViewBiblia> result = new ArrayList<>();
		List<Biblia> biblias = this.bibliaService.listar();
		for (Biblia biblia : biblias) {
			result.add(new BibliaListaViewBiblia(
					biblia.getId(),
					biblia.getNome(),
					biblia.getIdioma(),
					0
			));
		}
		return result;
	}

	// Controller

	@GetMapping("/biblia")
	public ModelAndView listar(@AuthenticationPrincipal OidcUser user) {
		ModelAndView result = new ModelAndView("/biblia/lista");
		addAuthInfo(result, user);
		result.addObject("data", new BibliaListaView(this.listaBiblias()));
		return result;
	}
}
