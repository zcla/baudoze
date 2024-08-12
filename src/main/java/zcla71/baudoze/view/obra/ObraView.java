package zcla71.baudoze.view.obra;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import zcla71.baudoze.controller.BauDoZe;
import zcla71.baudoze.view.Pagina.Estado;

@Controller
public class ObraView {
    @GetMapping("/obra")
    public String obraGet(Model model) throws Exception {
        BauDoZe bauDoZe = BauDoZe.getInstance();

        ObraPagina obra = bauDoZe.getObra(null);
        obra.setEstadoPagina(Estado.CREATE);

        model.addAttribute("obra", obra);

        return "obra";
    }

    @GetMapping("/obra/{id}")
    public String obraGetId(Model model, @PathVariable String id) throws Exception {
        BauDoZe bauDoZe = BauDoZe.getInstance();

        ObraPagina obra = bauDoZe.getObra(id);

        model.addAttribute("obra", obra);

        return "obra";
    }

    @DeleteMapping("/obra/{id}")
    public String obraDeleteId(Model model, @PathVariable String id) throws Exception {
        BauDoZe bauDoZe = BauDoZe.getInstance();

        bauDoZe.deleteObra(id);

        return "redirect:/obras";
    }

    @PostMapping("/obra")
    public String obraPostId(Model model, @ModelAttribute ObraForm form, RedirectAttributes redirectAttributes) throws Exception {
        BauDoZe bauDoZe = BauDoZe.getInstance();

        ObraPagina obra = bauDoZe.setObra(null, form);

        model.addAttribute("obra", obra);

        if (obra.getExceptionMap().isEmpty()) {
            return "redirect:/obra/" + obra.getId();
        } else {
            return "obra";
        }
    }

    @PutMapping("/obra/{id}")
    public String obraPutId(Model model, @PathVariable String id, @ModelAttribute ObraForm form) throws Exception {
        BauDoZe bauDoZe = BauDoZe.getInstance();

        ObraPagina obra = bauDoZe.setObra(id, form);

        model.addAttribute("obra", obra);

        if (obra.getExceptionMap().isEmpty()) {
            return "redirect:/obra/" + obra.getId();
        } else {
            return "obra";
        }
    }
}
