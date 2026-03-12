package zcla71.baudoze.tarefa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.validation.Valid;
import zcla71.baudoze.auth_user.model.entity.AuthUser;
import zcla71.baudoze.common.controller.BauBaseController;
import zcla71.baudoze.tarefa.model.entity.Tarefa;
import zcla71.baudoze.tarefa.model.service.TarefaService;
import zcla71.baudoze.tarefa.view.service.TarefaViewService;

@Controller
public class TarefaController extends BauBaseController {
	@Autowired
	private TarefaService tarefaService;
	@Autowired
	private TarefaViewService tarefaViewService;

	@GetMapping("/tarefa")
	public ModelAndView listar(@AuthenticationPrincipal AuthUser authUser) {
		ModelAndView result = new ModelAndView("/tarefa/tarefas");
		addAuthInfo(result, authUser);
		result.addObject("tarefas", tarefaViewService.listaTarefas(authUser.getId()));
		return result;
	}

	private ModelAndView getEditarModelAndView(@AuthenticationPrincipal AuthUser authUser, Tarefa tarefa) {
		ModelAndView result = new ModelAndView("tarefa/tarefa");
		addAuthInfo(result, authUser);
		result.addObject("tarefa", tarefa);
		result.addObject("tarefasMae", tarefaViewService.listaTarefasMaePossiveis(authUser, tarefa));
		return result;
	}

	@GetMapping("/tarefa/incluir")
	public ModelAndView incluir(@AuthenticationPrincipal AuthUser authUser) {
		return getEditarModelAndView(authUser, tarefaService.novaTarefa());
	}

	@PostMapping("/tarefa/salvar")
	public ModelAndView salvar(@AuthenticationPrincipal AuthUser authUser, @NonNull @Valid @ModelAttribute("tarefa") Tarefa tarefa, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return getEditarModelAndView(authUser, tarefa);
		}
		this.tarefaService.salvar(tarefa, authUser);
		return new ModelAndView("redirect:/tarefa");
	}

	@GetMapping("/tarefa/{id}/alterar")
	public ModelAndView alterar(@AuthenticationPrincipal AuthUser authUser, @NonNull @PathVariable Long id) {
		return getEditarModelAndView(authUser, tarefaService.buscar(id));
	}

	@PostMapping("/tarefa/{id}/excluir")
	public ModelAndView excluir(@AuthenticationPrincipal AuthUser authUser, @NonNull @PathVariable Long id) {
		// TODO Verificar se tem filhos e o que fazer: impedir ou cascata
		this.tarefaService.excluir(id, authUser);
		return new ModelAndView("redirect:/tarefa");
	}
}
