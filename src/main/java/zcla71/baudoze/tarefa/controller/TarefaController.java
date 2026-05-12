package zcla71.baudoze.tarefa.controller;

import java.util.Objects;

import org.springframework.lang.NonNull;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import zcla71.baudoze.auth_user.model.entity.AuthUser;
import zcla71.baudoze.common.controller.BauBaseController;
import zcla71.baudoze.common.controller.BauModelAndView;
import zcla71.baudoze.common.model.BauMensagem;
import zcla71.baudoze.tarefa.model.entity.Tarefa;
import zcla71.baudoze.tarefa.model.service.TarefaService;
import zcla71.baudoze.tarefa.model.service.TarefaServiceException;
import zcla71.baudoze.tarefa.view.service.TarefaViewService;

@RequiredArgsConstructor
@Controller
public class TarefaController extends BauBaseController {
	// Services

	final private TarefaService tarefaService;
	final private TarefaViewService tarefaViewService;

	// Tela: index

	@GetMapping("/tarefa")
	public ModelAndView index(@AuthenticationPrincipal AuthUser authUser) {
		ModelAndView result = getModelAndView("/tarefa/index", authUser);

		result.addObject("tarefas", tarefaViewService.listaTarefas(authUser.getId()));

		return result;
	}

	// Utilitários: preparação para edição

	private BauModelAndView getEditarModelAndView(Tarefa tarefa) {
		BauModelAndView result = getModelAndView("/tarefa/editar", tarefa.getAuthUser());

		result.addObject("tarefa", tarefa);
		result.addObject("tarefasMae", tarefaViewService.listaTarefasMaePossiveis(tarefa));

		return result;
	}

	private BauModelAndView getEditarModelAndView(Tarefa tarefa, TarefaServiceException ex, BindingResult bindingResult) {
		BauModelAndView result = getEditarModelAndView(tarefa);

		if (ex.getContexto() == null) {
			result.addMensagem("danger", ex.getMessage());
		} else {
			result.addFieldError(bindingResult, ex.getContexto(), ex.getMessage());
		}

		return result;
	}

	// Tela: incluir

	@GetMapping("/tarefa/incluir")
	public ModelAndView incluir(@AuthenticationPrincipal AuthUser authUser) {
		return getEditarModelAndView(tarefaService.novaTarefa(authUser));
	}

	// Tela: alterar

	@GetMapping("/tarefa/{id}/alterar")
	public ModelAndView alterar(
			@AuthenticationPrincipal AuthUser authUser,
			@NonNull @PathVariable Long id) {
		try {
			return getEditarModelAndView(tarefaService.buscar(authUser, id));
		} catch (TarefaServiceException ex) {
			BauModelAndView result = getModelAndView("/tarefa/editar", authUser);
			result.addMensagem("danger", ex.getMessage());
			return result;
		}
	}

	// Ação: salvar

	@PostMapping("/tarefa/salvar")
	public ModelAndView salvar(
			@AuthenticationPrincipal AuthUser authUser,
			@NonNull @Valid @ModelAttribute("tarefa") Tarefa tarefa,
			BindingResult bindingResult) {

		if (tarefa.getAuthUser() == null) {
			tarefa.setAuthUser(authUser);
		}

		if (bindingResult.hasErrors()) {
			return getEditarModelAndView(tarefa);
		}

		try {
			tarefaService.salvar(tarefa);
			return redirect("/tarefa");
		} catch (TarefaServiceException ex) {
			return getEditarModelAndView(tarefa, ex, bindingResult);
		}
	}

	// Ação: excluir

	@PostMapping("/tarefa/{id}/excluir")
	public ModelAndView excluir(
			@AuthenticationPrincipal AuthUser authUser,
			@NonNull @PathVariable Long id,
			RedirectAttributes redirectAttrs) {
		try {
			tarefaService.excluir(Objects.requireNonNull(tarefaService.buscar(authUser, id)));
			return redirect("/tarefa");
		} catch (TarefaServiceException ex) {
			return redirect("/tarefa", redirectAttrs, new BauMensagem("danger", ex.getMessage()));
		}
	}

	@PostMapping("/tarefa/{id}/marcar")
	public ModelAndView marcar(
			@AuthenticationPrincipal AuthUser authUser,
			@NonNull @PathVariable Long id,
			RedirectAttributes redirectAttrs) {
		try {
			tarefaService.marcar(Objects.requireNonNull(tarefaService.buscar(authUser, id)));
		} catch (TarefaServiceException ex) {
			return redirect("/tarefa", redirectAttrs, new BauMensagem("danger", ex.getMessage()));
		}
		return redirect("/tarefa");
	}

	@PostMapping("/tarefa/{id}/desmarcar")
	public ModelAndView desmarcar(
			@AuthenticationPrincipal AuthUser authUser,
			@NonNull @PathVariable Long id,
			RedirectAttributes redirectAttrs) {
		try {
			tarefaService.desmarcar(Objects.requireNonNull(tarefaService.buscar(authUser, id)));
		} catch (TarefaServiceException ex) {
			return redirect("/tarefa", redirectAttrs, new BauMensagem("danger", ex.getMessage()));
		}
		return redirect("/tarefa");
	}
}
