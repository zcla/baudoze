package zcla71.baudoze.tarefa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
