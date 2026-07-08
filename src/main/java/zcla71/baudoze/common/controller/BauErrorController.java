package zcla71.baudoze.common.controller;

import java.util.Map;
import java.util.Map.Entry;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.request.ServletWebRequest;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class BauErrorController implements ErrorController {
    private final ErrorAttributes errorAttributes;

    public BauErrorController(ErrorAttributes errorAttributes) {
        super();
        this.errorAttributes = errorAttributes;
    }

    /* TODO Tratamentos diferentes para html e json

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
public class CustomErrorController implements ErrorController {

    private static final String ERROR_PATH = "/error";

    // 1. Handles Browser requests asking for HTML
    @RequestMapping(value = ERROR_PATH, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView handleErrorHtml(HttpServletRequest request) {
        HttpStatus status = getStatus(request);
        ModelAndView modelAndView = new ModelAndView();

        // Looks for templates named 404.html, 500.html, or error.html
        if (status == HttpStatus.NOT_FOUND) {
            modelAndView.setViewName("error/404");
        } else if (status == HttpStatus.INTERNAL_SERVER_ERROR) {
            modelAndView.setViewName("error/500");
        } else {
            modelAndView.setViewName("error/error");
        }

        modelAndView.addObject("status", status.value());
        modelAndView.addObject("error", status.getReasonPhrase());
        return modelAndView;
    }

    // 2. Handles API clients asking for JSON
    @RequestMapping(value = ERROR_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> handleErrorJson(HttpServletRequest request) {
        HttpStatus status = getStatus(request);
        
        Map<String, Object> body = new HashMap<>();
        body.add("status", status.value());
        body.add("error", status.getReasonPhrase());
        body.add("message", request.getAttribute(RequestDispatcher.ERROR_MESSAGE));
        body.add("path", request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI));

        return new ResponseEntity<>(body, status);
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (statusCode != null) {
            try {
                return HttpStatus.valueOf(statusCode);
            } catch (Exception ex) {
                // Fallback for uncommon error codes
            }
        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
     */

    @SuppressWarnings("null")
    @GetMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        model.addAttribute("statusCode", request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE));
        ServletWebRequest webRequest = new ServletWebRequest(request);
        if (this.errorAttributes != null) {
            Map<String, Object> errors = errorAttributes.getErrorAttributes(webRequest,
                    ErrorAttributeOptions.of(
                            ErrorAttributeOptions.Include.MESSAGE,
                            ErrorAttributeOptions.Include.EXCEPTION,
                            ErrorAttributeOptions.Include.BINDING_ERRORS,
                            ErrorAttributeOptions.Include.STACK_TRACE
                    )
            );
            for (Entry<String, Object> error : errors.entrySet()) {
                model.addAttribute(error.getKey(), error.getValue());
            }
        }
        return "/_erro/erro";
    }
}
