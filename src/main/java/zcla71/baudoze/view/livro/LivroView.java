package zcla71.baudoze.view.livro;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;

import zcla71.baudoze.controller.BauDoZe;
import zcla71.baudoze.repository.model.RepositoryException;
import zcla71.baudoze.view.Pagina.Estado;

@Controller
public class LivroView {
    @GetMapping("/livro")
    public String livroGet(Model model) throws StreamReadException, DatabindException, IOException {
        BauDoZe bauDoZe = BauDoZe.getInstance();

        LivroPagina livro = bauDoZe.getLivro(null);
        livro.setEstadoPagina(Estado.CREATE);

        model.addAttribute("livro", livro);

        return "livro";
    }

    @GetMapping("/livro/{id}")
    public String livroGetId(Model model, @PathVariable String id) throws StreamReadException, DatabindException, IOException {
        BauDoZe bauDoZe = BauDoZe.getInstance();

        LivroPagina livro = bauDoZe.getLivro(id);

        model.addAttribute("livro", livro);

        return "livro";
    }

    @DeleteMapping("/livro/{id}")
    public String livroDeleteId(Model model, @PathVariable String id) throws StreamReadException, DatabindException, IOException, RepositoryException {
        BauDoZe bauDoZe = BauDoZe.getInstance();

        bauDoZe.deleteLivro(id);

        return "redirect:/livros";
    }

    @PostMapping("/livro")
    public String livroPostId(Model model, @ModelAttribute LivroForm form, RedirectAttributes redirectAttributes) throws StreamReadException, DatabindException, IOException, RepositoryException {
        BauDoZe bauDoZe = BauDoZe.getInstance();

        LivroPagina livro = bauDoZe.setLivro(null, form);

        model.addAttribute("livro", livro);

        if (livro.getExceptionMap().isEmpty()) {
            return "redirect:/livro/" + livro.getId();
        } else {
            return "livro";
        }
    }

    @PutMapping("/livro/{id}")
    public String livroPutId(Model model, @PathVariable String id, @ModelAttribute LivroForm form) throws StreamReadException, DatabindException, IOException, RepositoryException {
        BauDoZe bauDoZe = BauDoZe.getInstance();

        LivroPagina livro = bauDoZe.setLivro(id, form);

        model.addAttribute("livro", livro);

        if (livro.getExceptionMap().isEmpty()) {
            return "redirect:/livro/" + livro.getId();
        } else {
            return "livro";
        }
    }
}
