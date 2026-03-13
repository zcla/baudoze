package zcla71.baudoze.biblia.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import zcla71.baudoze.auth_user.model.entity.AuthUser;
import zcla71.baudoze.biblia.model.entity.Capitulo;
import zcla71.baudoze.biblia.model.entity.Livro;
import zcla71.baudoze.biblia.model.service.BibliaService;
import zcla71.baudoze.biblia.view.service.BibliaViewService;
import zcla71.baudoze.common.controller.BauBaseController;

@Controller
public class BibliaController extends BauBaseController {
	@Autowired
	private BibliaService bibliaService;
	@Autowired
	private BibliaViewService bibliaViewService;

	// Controller

	@GetMapping("/biblia")
	public ModelAndView biblia(@AuthenticationPrincipal AuthUser authUser) {
		ModelAndView result = new ModelAndView("/biblia/biblias");
		addAuthInfo(result, authUser);
		result.addObject("data", Map.of(
			"biblias", bibliaViewService.listarBiblias()
		));
		return result;
	}

	@GetMapping("/biblia/{idBiblia}")
	public ModelAndView bibliaId(@AuthenticationPrincipal AuthUser authUser, @PathVariable @NonNull Long idBiblia) {
		ModelAndView result = new ModelAndView("/biblia/biblia");
		addAuthInfo(result, authUser);
		result.addObject("data", Map.of(
			"biblia", bibliaViewService.buscarBiblia(idBiblia),
			"livros", bibliaViewService.listarLivros(idBiblia)
		));
		return result;
	}

	@GetMapping("/biblia/livro/{idLivro}")
	public ModelAndView livroId(@AuthenticationPrincipal AuthUser authUser, @PathVariable @NonNull Long idLivro) {
		ModelAndView result = new ModelAndView("/biblia/livro");
		addAuthInfo(result, authUser);
		Livro livro = bibliaService.buscaLivroPorId(idLivro);
		Long idBiblia = livro.getBiblia().getId();
		result.addObject("data", Map.of(
			"biblia", bibliaViewService.buscarBiblia(idBiblia),
			"livro", bibliaViewService.buscarLivro(idBiblia, idLivro),
			"capitulos", bibliaViewService.listarCapitulos(idLivro)
		));
		return result;
	}

	@GetMapping("/biblia/livro/capitulo/{idCapitulo}")
	public ModelAndView capituloId(@AuthenticationPrincipal AuthUser authUser, @PathVariable @NonNull Long idCapitulo) {
		ModelAndView result = new ModelAndView("/biblia/capitulo");
		addAuthInfo(result, authUser);
		Capitulo capitulo = bibliaService.buscaCapituloPorId(idCapitulo);
		Long idLivro = capitulo.getLivro().getId();
		Long idBiblia = capitulo.getLivro().getBiblia().getId();
		result.addObject("data", Map.of(
			"biblia", bibliaViewService.buscarBiblia(idBiblia),
			"livro", bibliaViewService.buscarLivro(idBiblia, idLivro),
			"capitulo", bibliaViewService.buscarCapitulo(idLivro, idCapitulo),
			"versiculos", bibliaViewService.listarVersiculos(idCapitulo)
		));
		return result;
	}
}
