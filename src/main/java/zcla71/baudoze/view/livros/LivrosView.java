package zcla71.baudoze.view.livros;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;

import zcla71.baudoze.controller.BauDoZe;

@Controller
public class LivrosView {
    @GetMapping("/livros")
    public String livrosGet(Model model) throws StreamReadException, DatabindException, IOException {
        BauDoZe bauDoZe = BauDoZe.getInstance();
        model.addAttribute("livros", bauDoZe.getLivros());
        return "livros";
    }
}
