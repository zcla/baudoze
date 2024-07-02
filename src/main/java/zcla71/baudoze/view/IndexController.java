package zcla71.baudoze.view;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;

@Controller
public class IndexController {
    @GetMapping("/")
    public String index(Model model) throws StreamReadException, DatabindException, IOException {
        return "forward:/stats";
    }
}
