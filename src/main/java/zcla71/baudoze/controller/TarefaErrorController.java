package zcla71.baudoze.controller;

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
public class TarefaErrorController implements ErrorController {
    private final ErrorAttributes errorAttributes;

    // public TarefaErrorController() {
    //     super();
    //     this.errorAttributes = null;
    // }

    public TarefaErrorController(ErrorAttributes errorAttributes) {
        super();
        this.errorAttributes = errorAttributes;
    }

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
