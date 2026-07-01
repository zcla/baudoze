package zcla71.baudoze.tarefa.controller;

import org.springframework.lang.NonNull;
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
import zcla71.baudoze.common.controller.BauBaseController;
import zcla71.baudoze.common.controller.BauModelAndView;
import zcla71.baudoze.tarefa.entity.Tarefa;
import zcla71.baudoze.tarefa.service.TarefaService;
import zcla71.baudoze.tarefa.service.TarefaServiceException;
import zcla71.baudoze.tarefa.service.TarefaListaService;

@RequiredArgsConstructor
@Controller
@RequestMapping("/tarefa")
public class TarefaController extends BauBaseController {
	// Services

	final private TarefaService tarefaService;
	final private TarefaListaService tarefaViewService;

	// Tela: index

	@GetMapping({"", "/"})
	public ModelAndView index() {
		ModelAndView result = getModelAndView("/tarefa/index");

		result.addObject("tarefas", tarefaViewService.listaTarefas());

		return result;
	}

	// Utilitários: preparação para edição

	private BauModelAndView getEditarModelAndView(Tarefa tarefa) {
		BauModelAndView result = getModelAndView("/tarefa/editar");

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

	@GetMapping("/incluir")
	public ModelAndView incluir() {
		return getEditarModelAndView(tarefaService.novaTarefa());
	}

	// Tela: alterar

	@GetMapping("/{id}/alterar")
	public ModelAndView alterar(@NonNull @PathVariable Long id) {
		try {
			return getEditarModelAndView(tarefaService.buscar(id));
		} catch (TarefaServiceException ex) {
			BauModelAndView result = getModelAndView("/tarefa/editar");
			result.addMensagem("danger", ex.getMessage());
			return result;
		}
	}

	// Ação: salvar

	@PostMapping("/salvar")
	public ModelAndView salvar(@NonNull @Valid @ModelAttribute("tarefa") Tarefa tarefa, BindingResult bindingResult) {
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
}
