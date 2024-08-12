package zcla71.baudoze.view.editoras;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import zcla71.baudoze.controller.BauDoZe;

@Controller
public class EditorasView {
    @GetMapping("/editoras")
    public String editorasGet(Model model) throws Exception {
        BauDoZe bauDoZe = BauDoZe.getInstance();
        model.addAttribute("editoras", bauDoZe.getEditoras());
        return "editoras";
    }
}
