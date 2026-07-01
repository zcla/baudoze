package zcla71.baudoze.common.controller;

import java.util.ArrayList;

import org.springframework.lang.NonNull;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import zcla71.baudoze.common.dto.BauMensagem;

public abstract class BauBaseController {
	protected BauModelAndView getModelAndView(@NonNull String viewName) {
		return new BauModelAndView(viewName);
	}

	public ModelAndView redirect(String viewName) {
		return new ModelAndView("redirect:" + viewName);
	}

	public ModelAndView redirect(String viewName, RedirectAttributes redirectAttrs, BauMensagem bauMensagem) {
		ArrayList<BauMensagem> mensagens = new ArrayList<>();
		mensagens.add(bauMensagem);
		redirectAttrs.addFlashAttribute("_flash_mensagens", mensagens);
		return this.redirect(viewName);
	}
}
