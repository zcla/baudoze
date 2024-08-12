package zcla71.baudoze.view.obras;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import zcla71.baudoze.controller.BauDoZe;

@Controller
public class ObrasView {
    @GetMapping("/obras")
    public String obrasGet(Model model) throws Exception {
        BauDoZe bauDoZe = BauDoZe.getInstance();
        model.addAttribute("obras", bauDoZe.getObras());
        return "obras";
    }
}
