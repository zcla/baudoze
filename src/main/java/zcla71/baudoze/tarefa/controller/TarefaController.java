package zcla71.baudoze.tarefa.controller;

import org.springframework.lang.NonNull;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import zcla71.baudoze.auth_user.model.entity.AuthUser;
import zcla71.baudoze.common.controller.BauBaseController;
import zcla71.baudoze.common.controller.BauModelAndView;
import zcla71.baudoze.tarefa.model.entity.Tarefa;
import zcla71.baudoze.tarefa.model.service.TarefaService;
import zcla71.baudoze.tarefa.model.service.TarefaServiceException;
import zcla71.baudoze.tarefa.view.service.TarefaViewService;

@RequiredArgsConstructor
@Controller
@RequestMapping("/tarefa")
public class TarefaController extends BauBaseController {
	// Services

	final private TarefaService tarefaService;
	final private TarefaViewService tarefaViewService;

	// Tela: index

	@GetMapping({"", "/"})
	public ModelAndView index(@AuthenticationPrincipal AuthUser authUser) {
		ModelAndView result = getModelAndView("/tarefa/index", authUser);

		result.addObject("tarefas", tarefaViewService.listaTarefas());

		return result;
	}

	// Utilitários: preparação para edição

	private BauModelAndView getEditarModelAndView(Tarefa tarefa, @AuthenticationPrincipal AuthUser authUser) {
		BauModelAndView result = getModelAndView("/tarefa/editar", authUser);

		result.addObject("tarefa", tarefa);
		result.addObject("tarefasMae", tarefaViewService.listaTarefasMaePossiveis(tarefa));

		return result;
	}

	private BauModelAndView getEditarModelAndView(Tarefa tarefa, TarefaServiceException ex, BindingResult bindingResult, @AuthenticationPrincipal AuthUser authUser) {
		BauModelAndView result = getEditarModelAndView(tarefa, authUser);

		if (ex.getContexto() == null) {
			result.addMensagem("danger", ex.getMessage());
		} else {
			result.addFieldError(bindingResult, ex.getContexto(), ex.getMessage());
		}

		return result;
	}

	// Tela: incluir

	@GetMapping("/incluir")
	public ModelAndView incluir(@AuthenticationPrincipal AuthUser authUser) {
		return getEditarModelAndView(tarefaService.novaTarefa(), authUser);
	}

	// Tela: alterar

	@GetMapping("/{id}/alterar")
	public ModelAndView alterar(
			@AuthenticationPrincipal AuthUser authUser,
			@NonNull @PathVariable Long id) {
		try {
			return getEditarModelAndView(tarefaService.buscar(id), authUser);
		} catch (TarefaServiceException ex) {
			BauModelAndView result = getModelAndView("/tarefa/editar", authUser);
			result.addMensagem("danger", ex.getMessage());
			return result;
		}
	}

	// Ação: salvar

	@PostMapping("/salvar")
	public ModelAndView salvar(
			@AuthenticationPrincipal AuthUser authUser,
			@NonNull @Valid @ModelAttribute("tarefa") Tarefa tarefa,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return getEditarModelAndView(tarefa, authUser);
		}

		try {
			tarefaService.salvar(tarefa);
			return redirect("/tarefa");
		} catch (TarefaServiceException ex) {
			return getEditarModelAndView(tarefa, ex, bindingResult, authUser);
		}
	}
}
