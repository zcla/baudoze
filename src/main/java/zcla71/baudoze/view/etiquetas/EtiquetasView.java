package zcla71.baudoze.view.etiquetas;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import zcla71.baudoze.controller.BauDoZe;

@Controller
public class EtiquetasView {
    @GetMapping("/etiquetas")
    public String etiquetasGet(Model model) throws Exception {
        BauDoZe bauDoZe = BauDoZe.getInstance();
        model.addAttribute("etiquetas", bauDoZe.getEtiquetas());
        return "etiquetas";
    }
}
