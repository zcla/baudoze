package zcla71.baudoze.view.pessoas;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import zcla71.baudoze.controller.BauDoZe;

@Controller
public class PessoaView {
    @GetMapping("/pessoas")
    public String pessoasGet(Model model) throws Exception {
        BauDoZe bauDoZe = BauDoZe.getInstance();
        model.addAttribute("pessoas", bauDoZe.getPessoas());
        return "pessoas";
    }
}
