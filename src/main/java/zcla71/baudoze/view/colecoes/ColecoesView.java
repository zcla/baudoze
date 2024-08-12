package zcla71.baudoze.view.colecoes;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import zcla71.baudoze.controller.BauDoZe;

@Controller
public class ColecoesView {
    @GetMapping("/colecoes")
    public String colecoesGet(Model model) throws Exception {
        BauDoZe bauDoZe = BauDoZe.getInstance();
        model.addAttribute("colecoes", bauDoZe.getColecoes());
        return "colecoes";
    }
}
