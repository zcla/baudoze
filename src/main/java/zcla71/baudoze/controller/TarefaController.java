package zcla71.baudoze.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import zcla71.baudoze.BauDoZeException;
import zcla71.baudoze.model.TarefaModel;
import zcla71.baudoze.model.service.TarefaService;
import zcla71.baudoze.model.service.TarefaService.Contexto;
import zcla71.baudoze.model.service.ValidationException;
import zcla71.baudoze.view.TarefaEditarView;
import zcla71.baudoze.view.TarefaEditarViewTarefaMae;
import zcla71.baudoze.view.TarefaListarView;
import zcla71.baudoze.view.TarefaListarViewTarefa;
import zcla71.baudoze.view.model.TarefaViewModelEditarOk;

@Controller
public class TarefaController {
	@Autowired
	private TarefaService tarefaService;

	// Métodos de apoio

	private List<TarefaListarViewTarefa> listaTarefasHierarquicamente() {
		List<TarefaListarViewTarefa> result = new ArrayList<>();

		List<TarefaModel> tarefas = this.sort(tarefaService.listar(), null);
		for (TarefaModel tarefa : tarefas) {
			Integer indent = 0;
			TarefaModel temp = tarefa;
			while (temp.getIdMae() != null) {
				final TarefaModel finalTemp = temp;
				temp = tarefas.stream()
						.filter(t -> finalTemp.getIdMae().equals(t.getId()))
						.findFirst()
						.get();
				indent++;
			}
			result.add(new TarefaListarViewTarefa(tarefa, indent));
		}

		return result;
	}

	private List<TarefaModel> sort(List<TarefaModel> tarefas, Long idMae) {
		List<TarefaModel> result = new ArrayList<>();

		List<TarefaModel> filhas = tarefas.stream().filter(t -> {
			if (idMae == null) {
				return t.getIdMae() == null;
			} else {
				return idMae.equals(t.getIdMae());
			}
		}).collect(Collectors.toList());

		filhas.sort(new TarefaModel.TarefaModelComparator());

		for (TarefaModel filha : filhas) {
			result.add(filha);
			result.addAll(sort(tarefas, filha.getId()));
		}

		return result;
	}

	private List<TarefaEditarViewTarefaMae> getTarefasMae() {
		List<TarefaEditarViewTarefaMae> result = new ArrayList<>();
		result.add(new TarefaEditarViewTarefaMae()); // Primeiro item vazio, para servir de "null"
		List<TarefaListarViewTarefa> tarefas = this.listaTarefasHierarquicamente();
		for (TarefaListarViewTarefa tarefa : tarefas) {
			result.add(new TarefaEditarViewTarefaMae(tarefa));
		}
		return result;
	}

	// Controller

	@GetMapping("/tarefa")
	public ModelAndView listar() {
		TarefaListarView data = new TarefaListarView();
		data.setTarefas(this.listaTarefasHierarquicamente());

		ModelAndView mav = new ModelAndView("/tarefa/lista");
		mav.addObject("data", data);
		return mav;
	}

	@GetMapping("/tarefa/incluir")
	public ModelAndView incluir() {
		ModelAndView mav = new ModelAndView("/tarefa/detalhe");
		mav.addObject("contexto", Contexto.INCLUIR);
		mav.addObject("data", new TarefaEditarView(TarefaModel.nova(), getTarefasMae()));
		return mav;
	}

	@PostMapping("/tarefa/incluir_ok")
	public ModelAndView incluirOk(@ModelAttribute TarefaViewModelEditarOk tarefa) {
		try {
			this.tarefaService.incluir(tarefa.getTarefa());
			return new ModelAndView("redirect:/tarefa");
		} catch (ValidationException e) {
			ModelAndView mav = new ModelAndView("/tarefa/detalhe");
			mav.addObject("contexto", Contexto.INCLUIR);
			mav.addObject("data", new TarefaEditarView(tarefa.getTarefa(), getTarefasMae()));
			mav.addObject("validation", e.getValidations());
			return mav;
		}
	}

	@GetMapping("/tarefa/{id}")
	public ModelAndView mostrar(@PathVariable Long id) throws BauDoZeException {
		ModelAndView mav = new ModelAndView("/tarefa/detalhe");
		mav.addObject("contexto", Contexto.MOSTRAR);
		mav.addObject("data", new TarefaEditarView(tarefaService.getById(id).orElse(null), getTarefasMae()));
		return mav;
	}

	@GetMapping("/tarefa/{id}/alterar")
	public ModelAndView alterar(@PathVariable Long id) throws BauDoZeException {
		ModelAndView mav = new ModelAndView("/tarefa/detalhe");
		mav.addObject("contexto", Contexto.ALTERAR);
		mav.addObject("data", new TarefaEditarView(tarefaService.getById(id).orElse(null), getTarefasMae()));
		return mav;
	}

	@PostMapping("/tarefa/alterar_ok")
	public ModelAndView alterarOk(@ModelAttribute TarefaViewModelEditarOk tarefa) {
		try {
			this.tarefaService.alterar(tarefa.getTarefa());
			return new ModelAndView("redirect:/tarefa");
		} catch (ValidationException e) {
			ModelAndView mav = new ModelAndView("/tarefa/detalhe");
			mav.addObject("contexto", Contexto.ALTERAR);
			mav.addObject("data", new TarefaEditarView(tarefa.getTarefa(), getTarefasMae()));
			mav.addObject("validation", e.getValidations());
			return mav;
		}
	}

	@GetMapping("/tarefa/{id}/excluir")
	public ModelAndView excluir(@PathVariable Long id) throws BauDoZeException {
		ModelAndView mav = new ModelAndView("/tarefa/detalhe");
		mav.addObject("contexto", Contexto.EXCLUIR);
		mav.addObject("data", new TarefaEditarView(tarefaService.getById(id).orElse(null), getTarefasMae()));
		return mav;
	}

	@PostMapping("/tarefa/excluir_ok")
	public ModelAndView excluirOk(@ModelAttribute TarefaViewModelEditarOk tarefa) {
		try {
			this.tarefaService.excluir(tarefa.getTarefa());
			return new ModelAndView("redirect:/tarefa");
		} catch (ValidationException e) {
			ModelAndView mav = new ModelAndView("/tarefa/detalhe");
			mav.addObject("contexto", Contexto.EXCLUIR);
			mav.addObject("data", new TarefaEditarView(tarefa.getTarefa(), getTarefasMae()));
			mav.addObject("validation", e.getValidations());
			return mav;
		}
	}
}
