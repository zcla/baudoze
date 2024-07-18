package zcla71.baudoze.view.obras;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;

import zcla71.baudoze.controller.BauDoZe;

@Controller
public class ObrasView {
    @GetMapping("/obras")
    public String obrasGet(Model model) throws StreamReadException, DatabindException, IOException {
        BauDoZe bauDoZe = BauDoZe.getInstance();
        model.addAttribute("obras", bauDoZe.getObras());
        return "obras";
    }
}
