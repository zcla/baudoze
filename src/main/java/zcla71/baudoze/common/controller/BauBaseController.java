package zcla71.baudoze.common.controller;

import org.springframework.web.servlet.ModelAndView;

import zcla71.baudoze.auth_user.model.entity.AuthUser;
import zcla71.baudoze.common.model.BauAuthUser;

public abstract class BauBaseController {
	protected void addAuthInfo(ModelAndView mav, AuthUser authUser) {
		mav.addObject("_authUser", new BauAuthUser(authUser.getNome(), authUser.getImagem() == null ? authUser.getUrlImagem() : "/auth_user/" + authUser.getId() + "/imagem/"));
	}
}
