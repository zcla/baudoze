package zcla71.baudoze.common.controller;

import org.springframework.web.servlet.ModelAndView;

import zcla71.baudoze.auth_user.model.entity.AuthUser;

public abstract class BauBaseController {
	protected void addAuthInfo(ModelAndView mav, AuthUser authUser) {
		// TODO Criar um objeto só "_authUser"?
		mav.addObject("_authUserName", authUser.getNome());
		if (authUser.getImagem() == null) {
			mav.addObject("_authUserPicture", authUser.getUrlImagem());
		} else {
			mav.addObject("_authUserPicture", "/auth_user/" + authUser.getId() + "/imagem/");
		}
	}
}
