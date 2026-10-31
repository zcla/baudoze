package zcla71.baudoze.common.controller;

import java.io.IOException;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import zcla71.utils.Utils;

@ControllerAdvice
public class BauExceptionHandler {
@ExceptionHandler(NoSuchElementException.class)
    public String handleNotFound(NoSuchElementException ex, HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.NOT_FOUND.value());
        return "/_erro/404";
    }

	@ExceptionHandler(Exception.class)
	public String handleAllExceptions(Exception ex, HttpServletRequest request, Model model) {
        model.addAttribute("statusCode", request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE));
		model.addAttribute("exception", ex.getClass().getCanonicalName());
		model.addAttribute("message", ex.getMessage());
		model.addAttribute("trace", Utils.stackTraceToString(ex));
        if (ex instanceof MethodArgumentNotValidException manvex) {
            model.addAttribute("bindingErrors", manvex.getBindingResult().getAllErrors());
        }
        return "/_erro/erro";
	}
}
