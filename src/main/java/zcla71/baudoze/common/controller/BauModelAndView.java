package zcla71.baudoze.common.controller;

import java.util.ArrayList;

import org.springframework.web.servlet.ModelAndView;

import zcla71.baudoze.auth_user.model.entity.AuthUser;
import zcla71.baudoze.common.model.BauAuthUser;
import zcla71.baudoze.common.model.BauMensagem;

public class BauModelAndView extends ModelAndView {
	private ArrayList<BauMensagem> mensagens;

	public BauModelAndView(String viewName, AuthUser authUser) {
		super(viewName);
		if (authUser != null) {
			addObject("_authUser", new BauAuthUser(authUser.getNome(), authUser.getImagem() == null ? authUser.getUrlImagem() : "/auth_user/" + authUser.getId() + "/imagem/"));
		}
		this.mensagens = new ArrayList<>();
		addObject("_mensagens", this.mensagens);
	}

	public void addMensagem(String tipo, String texto) {
		this.mensagens.add(new BauMensagem(tipo, texto));
	}
}
