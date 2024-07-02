package zcla71.baudoze.view;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;

import zcla71.baudoze.controller.BauDoZe;

@Controller
// TODO Criar controllers específicos, um para cada página, e juntar a eles os objetos Page (hoje dentro de model)
public class SpringController {
    @GetMapping("/")
    public String index(Model model) throws StreamReadException, DatabindException, IOException {
        return statsGet(model);
    }

    // stats

    @GetMapping("/stats")
    public String statsGet(Model model) throws StreamReadException, DatabindException, IOException {
        BauDoZe bauDoZe = BauDoZe.getInstance();
        model.addAttribute("stats", bauDoZe.getStats());
        return "stats";
    }
}
