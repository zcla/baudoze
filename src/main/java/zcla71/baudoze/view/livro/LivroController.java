package zcla71.baudoze.view.livro;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;

import zcla71.baudoze.controller.BauDoZe;
import zcla71.baudoze.repository.model.RepositoryException;
import zcla71.baudoze.view.Pagina.Estado;

@Controller
public class LivroController {
    @GetMapping("/livro/{id}")
    public String livroGet(Model model, @PathVariable String id) throws StreamReadException, DatabindException, IOException {
        BauDoZe bauDoZe = BauDoZe.getInstance();

        LivroPagina livro = bauDoZe.getLivro(id);

        model.addAttribute("livro", livro);

        return "livro";
    }

    @PutMapping("/livro/{id}")
    public String livroPut(Model model, @PathVariable String id, @ModelAttribute LivroForm form) throws StreamReadException, DatabindException, IOException, RepositoryException {
        BauDoZe bauDoZe = BauDoZe.getInstance();

        LivroPagina livro = bauDoZe.getLivro(id);
        livro.setTitulo(form.getTitulo());
        livro.setObras(form.getObras());
        if (livro.dadosValidos()) {
            bauDoZe.setLivro(livro);
            livro.setEstadoPagina(null);
        } else {
            livro.setEstadoPagina(Estado.UPDATE);
        }

        model.addAttribute("livro", livro);

        return "livro";
    }
}
