package zcla71.baudoze.biblia.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import zcla71.baudoze.biblia.model.entity.Livro;
import zcla71.baudoze.biblia.model.service.BibliaService;
import zcla71.baudoze.biblia.view.service.BibliaViewService;
import zcla71.baudoze.controller.BauBaseController;

@Controller
public class BibliaController extends BauBaseController {
	@Autowired
	private BibliaService bibliaService;
	@Autowired
	private BibliaViewService bibliaViewService;

	// Controller

	@GetMapping("/biblia")
	public ModelAndView biblia(@AuthenticationPrincipal OidcUser user) {
		ModelAndView result = new ModelAndView("/biblia/biblias");
		addAuthInfo(result, user);
		result.addObject("data", Map.of(
			"biblias", bibliaViewService.listarBiblias()
		));
		return result;
	}

	@GetMapping("/biblia/{idBiblia}")
	public ModelAndView bibliaId(@AuthenticationPrincipal OidcUser user, @PathVariable @NonNull Long idBiblia) {
		ModelAndView result = new ModelAndView("/biblia/biblia");
		addAuthInfo(result, user);
		result.addObject("data", Map.of(
			"biblia", bibliaViewService.buscarBiblia(idBiblia),
			"livros", bibliaViewService.listarLivros(idBiblia)
		));
		return result;
	}

	@GetMapping("/biblia/livro/{idLivro}")
	public ModelAndView livroId(@AuthenticationPrincipal OidcUser user, @PathVariable @NonNull Long idLivro) {
		ModelAndView result = new ModelAndView("/biblia/livro");
		addAuthInfo(result, user);
		Livro livro = bibliaService.buscaLivroPorId(idLivro);
		Long idBiblia = livro.getBiblia().getId();
		result.addObject("data", Map.of(
			"biblia", bibliaViewService.buscarBiblia(idBiblia),
			"livro", bibliaViewService.buscarLivro(idBiblia, idLivro),
			"capitulos", bibliaViewService.listarCapitulos(idLivro)
		));
		return result;
	}
}
