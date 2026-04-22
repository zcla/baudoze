package zcla71.baudoze.common.controller;

import org.springframework.lang.NonNull;
import org.springframework.web.servlet.ModelAndView;

import zcla71.baudoze.auth_user.model.entity.AuthUser;

public abstract class BauBaseController {
	protected BauModelAndView getModelAndView(@NonNull String viewName, AuthUser authUser) {
		return new BauModelAndView(viewName, authUser);
	}

	public ModelAndView redirect(String viewName) {
		return new ModelAndView("redirect:" + viewName);
	}
}
