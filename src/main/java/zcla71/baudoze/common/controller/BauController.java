package zcla71.baudoze.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BauController extends BauBaseController {
	// Controller

	@GetMapping("/")
	public ModelAndView index() {
		ModelAndView result = getModelAndView("/index");
		return result;
	}
}
