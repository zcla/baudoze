package zcla71.baudoze.view.atividades;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import zcla71.baudoze.controller.BauDoZe;

@Controller
public class AtividadesView {
    @GetMapping("/atividades")
    public String atividadesGet(Model model) throws Exception {
        BauDoZe bauDoZe = BauDoZe.getInstance();
        model.addAttribute("atividades", bauDoZe.getAtividades());
        return "atividades";
    }
}
