package zcla71.baudoze.common.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import zcla71.utils.Utils;

@ControllerAdvice
public class BauExceptionHandler {
	@ExceptionHandler(Exception.class)
	public String handleAllExceptions(Exception ex, HttpServletRequest request, Model model) {
        model.addAttribute("statusCode", request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE));
		model.addAttribute("exception", ex.getClass().getCanonicalName());
		model.addAttribute("message", ex.getMessage());
		model.addAttribute("trace", Utils.stackTraceToString(ex));
        return "/_erro/erro";
	}
}
