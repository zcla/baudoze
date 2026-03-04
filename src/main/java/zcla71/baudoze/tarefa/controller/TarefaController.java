package zcla71.baudoze.tarefa.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.validation.Valid;
import zcla71.baudoze.auth_user.model.entity.AuthUser;
import zcla71.baudoze.auth_user.model.service.AuthUserService;
import zcla71.baudoze.common.controller.BauBaseController;
import zcla71.baudoze.common.model.ValidacaoException;
import zcla71.baudoze.common.view.ContextoCrud;
import zcla71.baudoze.tarefa.model.entity.Tarefa;
import zcla71.baudoze.tarefa.model.entity.TarefaComparator;
import zcla71.baudoze.tarefa.model.service.TarefaService;
import zcla71.baudoze.tarefa.view.entity.TarefaEditarView;
import zcla71.baudoze.tarefa.view.entity.TarefaEditarViewOk;
import zcla71.baudoze.tarefa.view.entity.TarefaEditarViewTarefa;
import zcla71.baudoze.tarefa.view.entity.TarefaEditarViewTarefaMae;
import zcla71.baudoze.tarefa.view.entity.TarefaLista;
import zcla71.baudoze.tarefa.view.entity.TarefaListaView;
import zcla71.baudoze.tarefa.view.entity.TarefaListaViewTarefa;
import zcla71.baudoze.tarefa.view.service.TarefaViewService;
import zcla71.utils.Utils;

@Controller
public class TarefaController extends BauBaseController {
	@Autowired
	private TarefaService tarefaService;
	@Autowired
	private TarefaViewService tarefaViewService;

	// Métodos de apoio

	// private TarefaEditarViewTarefa entity2view(Tarefa tarefa) {
	// 	TarefaEditarViewTarefa result = new TarefaEditarViewTarefa();
	// 	Utils.copiaPropriedades(tarefa, result);
	// 	return result;
	// }

	// private Tarefa view2entity(TarefaEditarViewTarefa tarefa) {
	// 	Tarefa result = new Tarefa();
	// 	Utils.copiaPropriedades(tarefa, result);
	// 	return result;
	// }

	// private List<TarefaEditarViewTarefaMae> getTarefasMae() {
	// 	List<TarefaEditarViewTarefaMae> result = new ArrayList<>();
	// 	result.add(new TarefaEditarViewTarefaMae()); // Primeiro item vazio, para servir de "null"
	// 	List<TarefaListaViewTarefa> tarefas = this.listaTarefasHierarquicamente();
	// 	for (TarefaListaViewTarefa tarefa : tarefas) {
	// 		result.add(new TarefaEditarViewTarefaMae(tarefa));
	// 	}
	// 	return result;
	// }

	// private List<TarefaListaViewTarefa> listaTarefasHierarquicamente() {
	// 	List<TarefaListaViewTarefa> result = new ArrayList<>();

	// 	List<Tarefa> tarefas = this.sort(this.tarefaService.listar(), null);
	// 	for (Tarefa tarefa : tarefas) {
	// 		Integer indent = 0;
	// 		Tarefa temp = tarefa;
	// 		while (temp.getIdMae() != null) {
	// 			final Tarefa finalTemp = temp;
	// 			temp = tarefas.stream()
	// 					.filter(t -> finalTemp.getIdMae().equals(t.getId()))
	// 					.findFirst()
	// 					.get();
	// 			indent++;
	// 		}
	// 		result.add(new TarefaListaViewTarefa(
	// 				tarefa.getId(),
	// 				tarefa.getNome(),
	// 				tarefa.getNotas(),
	// 				tarefa.getCumprida(),
	// 				indent
	// 		));
	// 	}

	// 	return result;
	// }

	// private List<Tarefa> sort(List<Tarefa> tarefas, Long idMae) {
	// 	List<Tarefa> result = new ArrayList<>();

	// 	List<Tarefa> filhas = tarefas.stream().filter(t -> {
	// 		if (idMae == null) {
	// 			return t.getIdMae() == null;
	// 		} else {
	// 			return idMae.equals(t.getIdMae());
	// 		}
	// 	}).collect(Collectors.toList());

	// 	filhas.sort(new TarefaComparator());

	// 	for (Tarefa filha : filhas) {
	// 		result.add(filha);
	// 		result.addAll(sort(tarefas, filha.getId()));
	// 	}

	// 	return result;
	// }

	// private ModelAndView getModelAndViewTarefaDetalhe(OidcUser user, ContextoCrud contexto, TarefaEditarViewTarefa data) {
	// 	ModelAndView result = new ModelAndView("/tarefa/detalhe");
	// 	addAuthInfo(result, user);
	// 	result.addObject("contexto", contexto);
	// 	result.addObject("data", new TarefaEditarView(data, getTarefasMae()));
	// 	return result;
	// }

	// private ModelAndView getModelAndViewTarefaDetalhe(OidcUser user, ContextoCrud contexto, TarefaEditarViewTarefa data, Exception e) {
	// 	ModelAndView result = getModelAndViewTarefaDetalhe(user, contexto, data);
	// 	trataException(result, e);
	// 	return result;
	// }

	// private void trataException(ModelAndView mav, Exception e) {
	// 	if (e instanceof ValidacaoException v) {
	// 		mav.addObject("validation", v.getValidacoes());
	// 	} else {
	// 		mav.addObject("exception", e);
	// 	}
	// }

	// Controller

	@GetMapping("/tarefa")
	public ModelAndView listar(@AuthenticationPrincipal OidcUser oidcUser, Authentication authentication) {
		ModelAndView result = new ModelAndView("/tarefa/tarefas");
		addAuthInfo(result, oidcUser);
		AuthUser authUser = getAuthUser(oidcUser, authentication);
		result.addObject("tarefas", tarefaViewService.listaTarefas(authUser.getId()));
		return result;
	}

	private ModelAndView getEditarModelAndView(OidcUser oidcUser, Authentication authentication, Tarefa tarefa) {
		ModelAndView result = new ModelAndView("tarefa/tarefa");
		addAuthInfo(result, oidcUser);
		result.addObject("tarefa", tarefa);
		AuthUser authUser = getAuthUser(oidcUser, authentication);
		result.addObject("tarefasMae", tarefaViewService.listaTarefasMaePossiveis(authUser, tarefa));
		return result;
	}

	@GetMapping("/tarefa/incluir")
	public ModelAndView incluir(@AuthenticationPrincipal OidcUser oidcUser, Authentication authentication) {
		return getEditarModelAndView(oidcUser, authentication, tarefaService.novaTarefa());
	}

	@PostMapping("/tarefa/salvar")
	public ModelAndView salvar(@AuthenticationPrincipal OidcUser oidcUser, Authentication authentication, @NonNull @Valid @ModelAttribute("tarefa") Tarefa tarefa, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return getEditarModelAndView(oidcUser, authentication, tarefa);
		}
		AuthUser authUser = super.getAuthUser(oidcUser, authentication);
		this.tarefaService.salvar(tarefa, authUser);
		return new ModelAndView("redirect:/tarefa");
	}

	@GetMapping("/tarefa/{id}/alterar")
	public ModelAndView alterar(@AuthenticationPrincipal OidcUser oidcUser, Authentication authentication, @NonNull @PathVariable Long id) {
		return getEditarModelAndView(oidcUser, authentication, tarefaService.buscar(id));
	}

	@PostMapping("/tarefa/{id}/excluir")
	public ModelAndView excluir(@AuthenticationPrincipal OidcUser oidcUser, Authentication authentication, @NonNull @PathVariable Long id) {
		// TODO Verificar se tem filhos e o que fazer: impedir ou cascata
		AuthUser authUser = super.getAuthUser(oidcUser, authentication);
		this.tarefaService.excluir(id, authUser);
		return new ModelAndView("redirect:/tarefa");
	}
}
