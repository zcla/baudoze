package zcla71.baudoze.common.controller;

import java.util.ArrayList;
import java.util.Objects;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.servlet.ModelAndView;

import zcla71.baudoze.auth_user.model.entity.AuthUser;
import zcla71.baudoze.common.config.UserContext;
import zcla71.baudoze.common.model.BauAuthUser;
import zcla71.baudoze.common.model.BauMensagem;

public class BauModelAndView extends ModelAndView {
	private ArrayList<BauMensagem> mensagens;

	public BauModelAndView(String viewName) {
		super(viewName);
		AuthUser authUser = UserContext.getUser();
		if (authUser != null) {
			addObject("_authUser", new BauAuthUser(authUser.getNome(), authUser.getImagem() == null ? authUser.getUrlImagem() : "/auth_user/" + authUser.getId() + "/imagem/"));
		}
		this.mensagens = new ArrayList<>();
		Object modelMensagens = this.getModel().get("_mensagens");
		if (modelMensagens != null) {
			if (modelMensagens instanceof ArrayList arrayList) {
				for (Object object : arrayList) {
					if (object instanceof BauMensagem bauMensagem) {
						this.mensagens.add(bauMensagem);
					}
				}
			}
		}
		addObject("_mensagens", this.mensagens);
	}

	public void addFieldError(BindingResult bindingResult, String field, String message) {
		bindingResult.addError(new FieldError(bindingResult.getObjectName(), Objects.requireNonNull(field), Objects.requireNonNull(message)));
	}

	public void addMensagem(String tipo, String texto) {
		this.mensagens.add(new BauMensagem(tipo, texto));
	}
}
