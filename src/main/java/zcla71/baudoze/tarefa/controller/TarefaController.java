package zcla71.baudoze.tarefa.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import zcla71.baudoze.common.controller.BauBaseController;
import zcla71.baudoze.common.model.ValidacaoException;
import zcla71.baudoze.common.view.ContextoCrud;
import zcla71.baudoze.tarefa.service.TarefaEntity;
import zcla71.baudoze.tarefa.service.TarefaEntityComparator;
import zcla71.baudoze.tarefa.service.TarefaService;
import zcla71.baudoze.tarefa.view.TarefaEditarView;
import zcla71.baudoze.tarefa.view.TarefaEditarViewOk;
import zcla71.baudoze.tarefa.view.TarefaEditarViewTarefa;
import zcla71.baudoze.tarefa.view.TarefaEditarViewTarefaMae;
import zcla71.baudoze.tarefa.view.TarefaListaView;
import zcla71.baudoze.tarefa.view.TarefaListaViewTarefa;
import zcla71.utils.Utils;

@Controller
public class TarefaController extends BauBaseController {
	@Autowired
	private TarefaService tarefaService;

	// Métodos de apoio

	private TarefaEditarViewTarefa entity2view(TarefaEntity tarefa) {
		TarefaEditarViewTarefa result = new TarefaEditarViewTarefa();
		Utils.copiaPropriedades(tarefa, result);
		return result;
	}

	private TarefaEntity view2entity(TarefaEditarViewTarefa tarefa) {
		TarefaEntity result = new TarefaEntity();
		Utils.copiaPropriedades(tarefa, result);
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

		List<TarefaEntity> tarefas = this.sort(this.tarefaService.listar(), null);
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

	private ModelAndView getModelAndViewTarefaDetalhe(OidcUser user, ContextoCrud contexto, TarefaEditarViewTarefa data) {
		ModelAndView result = new ModelAndView("/tarefa/detalhe");
		addAuthInfo(result, user);
		result.addObject("contexto", contexto);
		result.addObject("data", new TarefaEditarView(data, getTarefasMae()));
		return result;
	}

	private ModelAndView getModelAndViewTarefaDetalhe(OidcUser user, ContextoCrud contexto, TarefaEditarViewTarefa data, Exception e) {
		ModelAndView result = getModelAndViewTarefaDetalhe(user, contexto, data);
		trataException(result, e);
		return result;
	}

	private void trataException(ModelAndView mav, Exception e) {
		if (e instanceof ValidacaoException v) {
			mav.addObject("validation", v.getValidacoes());
		} else {
			mav.addObject("exception", e);
		}
	}

	// Controller

	@GetMapping("/tarefa")
	public ModelAndView listar(@AuthenticationPrincipal OidcUser user) {
		ModelAndView result = new ModelAndView("/tarefa/lista");
		addAuthInfo(result, user);
		result.addObject("data", new TarefaListaView(this.listaTarefasHierarquicamente()));
		return result;
	}

	@GetMapping("/tarefa/{id}")
	public ModelAndView mostrar(@AuthenticationPrincipal OidcUser user, @PathVariable Long id) {
		return getModelAndViewTarefaDetalhe(user, ContextoCrud.MOSTRAR, this.entity2view(this.tarefaService.buscar(id)));
	}

	@GetMapping("/tarefa/incluir")
	public ModelAndView incluir(@AuthenticationPrincipal OidcUser user) {
		return getModelAndViewTarefaDetalhe(user, ContextoCrud.INCLUIR, this.entity2view(TarefaEntity.nova()));
	}

	@PostMapping("/tarefa/incluir_ok")
	public ModelAndView incluirOk(@AuthenticationPrincipal OidcUser user, @ModelAttribute TarefaEditarViewOk tarefa) {
		try {
			this.tarefaService.incluir(this.view2entity(tarefa.getTarefa()));
			return new ModelAndView("redirect:/tarefa");
		} catch (Exception e) {
			return getModelAndViewTarefaDetalhe(user, ContextoCrud.INCLUIR, tarefa.getTarefa(), e);
		}
	}

	@GetMapping("/tarefa/{id}/alterar")
	public ModelAndView alterar(@AuthenticationPrincipal OidcUser user, @PathVariable Long id) {
		return getModelAndViewTarefaDetalhe(user, ContextoCrud.ALTERAR, this.entity2view(this.tarefaService.buscar(id)));
	}

	@PostMapping("/tarefa/alterar_ok")
	public ModelAndView alterarOk(@AuthenticationPrincipal OidcUser user, @ModelAttribute TarefaEditarViewOk tarefa) {
		try {
			this.tarefaService.alterar(this.view2entity(tarefa.getTarefa()));
			return new ModelAndView("redirect:/tarefa");
		} catch (Exception e) {
			return getModelAndViewTarefaDetalhe(user, ContextoCrud.ALTERAR, tarefa.getTarefa(), e);
		}
	}

	@GetMapping("/tarefa/{id}/excluir")
	public ModelAndView excluir(@AuthenticationPrincipal OidcUser user, @PathVariable Long id) {
		return getModelAndViewTarefaDetalhe(user, ContextoCrud.EXCLUIR, this.entity2view(this.tarefaService.buscar(id)));
	}

	@PostMapping("/tarefa/excluir_ok")
	public ModelAndView excluirOk(@AuthenticationPrincipal OidcUser user, @ModelAttribute TarefaEditarViewOk tarefa) {
		try {
			this.tarefaService.excluir(this.view2entity(tarefa.getTarefa()));
			return new ModelAndView("redirect:/tarefa");
		} catch (Exception e) {
			return getModelAndViewTarefaDetalhe(user, ContextoCrud.EXCLUIR, tarefa.getTarefa(), e);
		}
	}
}
