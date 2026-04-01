package zcla71.baudoze.tarefa.controller;

import org.springframework.lang.NonNull;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import zcla71.baudoze.auth_user.model.entity.AuthUser;
import zcla71.baudoze.common.controller.BauBaseController;
import zcla71.baudoze.tarefa.model.entity.Tarefa;
import zcla71.baudoze.tarefa.model.service.TarefaService;
import zcla71.baudoze.tarefa.view.entity.TarefaLista;
import zcla71.baudoze.tarefa.view.service.TarefaViewService;

@RequiredArgsConstructor
@Controller
public class TarefaController extends BauBaseController {
	final private TarefaService tarefaService;
	final private TarefaViewService tarefaViewService;

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
		// A tarefa mãe não pode ser nem ela mesma nem nenhuma de suas filhas
		Long tarefaId = tarefa.getId();
		if ((tarefaId != null) && (tarefa.getTarefaMae() != null)) {
			Tarefa existente = tarefaService.buscar(tarefaId);
			if (existente == null) {
				throw new IllegalArgumentException("Tarefa não encontrada");
			}
			TarefaLista tarefaLista = tarefaViewService.listaTarefasMaePossiveis(authUser, existente).stream()
					.filter(t -> t.getId().equals(tarefa.getTarefaMae().getId()))
					.findAny()
					.orElse(null);
			if (tarefaLista == null) {
				throw new IllegalArgumentException("Tarefa não encontrada");
			}
			if (tarefaLista.getDisabled()) {
				bindingResult.addError(new FieldError(bindingResult.getObjectName(), "tarefaMae", "A tarefa mãe não pode ser nem ela mesma nem nenhuma de suas filhas"));
			}
		}

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
