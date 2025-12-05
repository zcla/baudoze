package zcla71.baudoze.tarefa.controller;

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

import zcla71.baudoze.model.service.ValidationException;
import zcla71.baudoze.tarefa.service.TarefaEntity;
import zcla71.baudoze.tarefa.service.TarefaEntityComparator;
import zcla71.baudoze.tarefa.service.TarefaService;
import zcla71.baudoze.tarefa.view.TarefaEditarView;
import zcla71.baudoze.tarefa.view.TarefaEditarViewOk;
import zcla71.baudoze.tarefa.view.TarefaEditarViewTarefa;
import zcla71.baudoze.tarefa.view.TarefaEditarViewTarefaMae;
import zcla71.baudoze.tarefa.view.TarefaListaView;
import zcla71.baudoze.tarefa.view.TarefaListaViewTarefa;
import zcla71.baudoze.view.ContextoCrud;

@Controller
public class TarefaController {
	@Autowired
	private TarefaService tarefaService;

	// Métodos de apoio

	// TODO Avaliar se seria possível haver uma superclasse abstrata pra facilitar essas conversões
	private TarefaEditarViewTarefa entity2view(TarefaEntity tarefa) {
		return new TarefaEditarViewTarefa(
				tarefa.getId(),
				tarefa.getNome(),
				tarefa.getNotas(),
				tarefa.getIdMae(),
				tarefa.getPeso(),
				tarefa.getCumprida()
		);
	}

	private TarefaEntity view2entity(TarefaEditarViewTarefa tarefa) {
		TarefaEntity result = new TarefaEntity(
				tarefa.getId(),
				tarefa.getNome(),
				tarefa.getNotas(),
				tarefa.getIdMae(),
				tarefa.getPeso(),
				tarefa.getCumprida()
		);
		return result;
	}

	private List<TarefaEditarViewTarefaMae> getTarefasMae() {
		List<TarefaEditarViewTarefaMae> result = new ArrayList<>();
		result.add(new TarefaEditarViewTarefaMae()); // Primeiro item vazio, para servir de "null"
		List<TarefaListaViewTarefa> tarefas = this.listaTarefasHierarquicamente();
		for (TarefaListaViewTarefa tarefa : tarefas) {
			result.add(new TarefaEditarViewTarefaMae(tarefa));
		}
		return result;
	}

	private List<TarefaListaViewTarefa> listaTarefasHierarquicamente() {
		List<TarefaListaViewTarefa> result = new ArrayList<>();

		List<TarefaEntity> tarefas = this.sort(tarefaService.listar(), null);
		for (TarefaEntity tarefa : tarefas) {
			Integer indent = 0;
			TarefaEntity temp = tarefa;
			while (temp.getIdMae() != null) {
				final TarefaEntity finalTemp = temp;
				temp = tarefas.stream()
						.filter(t -> finalTemp.getIdMae().equals(t.getId()))
						.findFirst()
						.get();
				indent++;
			}
			result.add(new TarefaListaViewTarefa(
					tarefa.getId(),
					tarefa.getNome(),
					tarefa.getNotas(),
					tarefa.getCumprida(),
					indent
			));
		}

		return result;
	}

	private List<TarefaEntity> sort(List<TarefaEntity> tarefas, Long idMae) {
		List<TarefaEntity> result = new ArrayList<>();

		List<TarefaEntity> filhas = tarefas.stream().filter(t -> {
			if (idMae == null) {
				return t.getIdMae() == null;
			} else {
				return idMae.equals(t.getIdMae());
			}
		}).collect(Collectors.toList());

		filhas.sort(new TarefaEntityComparator());

		for (TarefaEntity filha : filhas) {
			result.add(filha);
			result.addAll(sort(tarefas, filha.getId()));
		}

		return result;
	}

	private ModelAndView getModelAndViewTarefaDetalhe(ContextoCrud contexto, TarefaEditarViewTarefa data) {
		ModelAndView result = new ModelAndView("/tarefa/detalhe");
		result.addObject("contexto", contexto);
		result.addObject("data", new TarefaEditarView(data, getTarefasMae()));
		return result;
	}

	private ModelAndView getModelAndViewTarefaDetalhe(ContextoCrud contexto, TarefaEditarViewTarefa data, Exception e) {
		ModelAndView result = getModelAndViewTarefaDetalhe(contexto, data);
		trataException(result, e);
		return result;
	}

	private void trataException(ModelAndView mav, Exception e) {
		if (e instanceof ValidationException v) {
			mav.addObject("validation", v.getValidations());
		} else {
			mav.addObject("exception", e);
		}
	}

	// Controller

	@GetMapping("/tarefa")
	public ModelAndView listar() {
		ModelAndView result = new ModelAndView("/tarefa/lista");
		result.addObject("data", new TarefaListaView(this.listaTarefasHierarquicamente()));
		return result;
	}

	@GetMapping("/tarefa/{id}")
	public ModelAndView mostrar(@PathVariable Long id) {
		return getModelAndViewTarefaDetalhe(ContextoCrud.MOSTRAR, this.entity2view(tarefaService.buscar(id)));
	}

	@GetMapping("/tarefa/incluir")
	public ModelAndView incluir() {
		return getModelAndViewTarefaDetalhe(ContextoCrud.INCLUIR, this.entity2view(TarefaEntity.nova()));
	}

	@PostMapping("/tarefa/incluir_ok")
	public ModelAndView incluirOk(@ModelAttribute TarefaEditarViewOk tarefa) {
		try {
			this.tarefaService.incluir(this.view2entity(tarefa.getTarefa()));
			return new ModelAndView("redirect:/tarefa");
		} catch (Exception e) {
			return getModelAndViewTarefaDetalhe(ContextoCrud.INCLUIR, tarefa.getTarefa(), e);
		}
	}

	@GetMapping("/tarefa/{id}/alterar")
	public ModelAndView alterar(@PathVariable Long id) {
		return getModelAndViewTarefaDetalhe(ContextoCrud.ALTERAR, this.entity2view(tarefaService.buscar(id)));
	}

	@PostMapping("/tarefa/alterar_ok")
	public ModelAndView alterarOk(@ModelAttribute TarefaEditarViewOk tarefa) {
		try {
			this.tarefaService.alterar(this.view2entity(tarefa.getTarefa()));
			return new ModelAndView("redirect:/tarefa");
		} catch (Exception e) {
			return getModelAndViewTarefaDetalhe(ContextoCrud.ALTERAR, tarefa.getTarefa(), e);
		}
	}

	@GetMapping("/tarefa/{id}/excluir")
	public ModelAndView excluir(@PathVariable Long id) {
		return getModelAndViewTarefaDetalhe(ContextoCrud.EXCLUIR, this.entity2view(tarefaService.buscar(id)));
	}

	@PostMapping("/tarefa/excluir_ok")
	public ModelAndView excluirOk(@ModelAttribute TarefaEditarViewOk tarefa) {
		try {
			this.tarefaService.excluir(this.view2entity(tarefa.getTarefa()));
			return new ModelAndView("redirect:/tarefa");
		} catch (Exception e) {
			return getModelAndViewTarefaDetalhe(ContextoCrud.EXCLUIR, tarefa.getTarefa(), e);
		}
	}
}
