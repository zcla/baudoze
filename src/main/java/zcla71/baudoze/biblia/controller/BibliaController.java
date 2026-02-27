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

import zcla71.baudoze.biblia.model.Livro;
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

	@GetMapping("/biblia/{idBiblia}")
	public ModelAndView bibliaId(@AuthenticationPrincipal OidcUser user, @PathVariable @NonNull Long idBiblia) {
		ModelAndView result = new ModelAndView("/biblia/biblia");
		addAuthInfo(result, user);
		Map<String, Object> data = new HashMap<>();
		data.put("biblia", bibliaService.listarBiblias().stream()
				.filter(b -> b.getId().equals(idBiblia))
				.findFirst()
				.orElse(null)
		);
		data.put("livros", bibliaService.listarLivros(idBiblia));
		result.addObject("data", data);
		return result;
	}

	@GetMapping("/biblia/livro/{idLivro}")
	public ModelAndView livroId(@AuthenticationPrincipal OidcUser user, @PathVariable @NonNull Long idLivro) {
		ModelAndView result = new ModelAndView("/biblia/livro");
		addAuthInfo(result, user);
		Map<String, Object> data = new HashMap<>();
		Livro livro = bibliaService.buscaLivroPorId(idLivro);
		data.put("biblia", bibliaService.listarBiblias().stream()
				.filter(b -> b.getId().equals(livro.getBiblia().getId()))
				.findFirst()
				.orElse(null)
		);
		data.put("livro", bibliaService.listarLivros(livro.getBiblia().getId()).stream()
				.filter(l -> l.getId().equals(idLivro))
				.findFirst()
				.orElse(null)
		);
		data.put("capitulos", bibliaService.listarCapitulos(idLivro));
		result.addObject("data", data);
		return result;
	}
}
