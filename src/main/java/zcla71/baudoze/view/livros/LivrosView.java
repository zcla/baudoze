package zcla71.baudoze.view.livros;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import zcla71.baudoze.controller.BauDoZe;

@Controller
public class LivrosView {
    @GetMapping("/livros")
    public String livrosGet(Model model) throws Exception {
        BauDoZe bauDoZe = BauDoZe.getInstance();
        model.addAttribute("livros", bauDoZe.getLivros());
        return "livros";
    }
}
