package zcla71.baudoze.estudos.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import zcla71.baudoze.common.controller.BauBaseController;
import zcla71.baudoze.estudos.dto.concordancia.Concordancia;
import zcla71.baudoze.estudos.dto.concordancia.Parte;
import zcla71.baudoze.estudos.dto.concordancia.TabelaLinha;
import zcla71.baudoze.estudos.dto.concordancia.Titulo;

@Controller
@RequestMapping("/estudos")
public class EstudosController extends BauBaseController {
	@GetMapping({"", "/"})
	public ModelAndView index() {
		return getModelAndView("/estudos/index");
	}

	// ===== /biblia

	@GetMapping("/biblia")
	public ModelAndView biblia() {
		return getModelAndView("/estudos/biblia/index");
	}

	// ----- /biblia/concordancia

	@GetMapping("/biblia/concordancia")
	public ModelAndView concordancia() throws StreamReadException, DatabindException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		Concordancia concordancia = mapper.readValue(getClass().getResourceAsStream("/templates/estudos/biblia/concordancia/concordanciaDosSantosEvangelhos.json"), Concordancia.class);

		List<TabelaLinha> tabela = new ArrayList<>();
		for (Parte parte : concordancia.getPartes()) {
			String parteTitulo = parte.getTitulo();
			TabelaLinha linhaParte = null;

			for (Titulo titulo : parte.getTitulos()) {
				// TODO Tirar o if abaixo quando o estudo terminar
				if (titulo.getTitulo().length() == 0) {
					continue;
				}

				TabelaLinha linha = new TabelaLinha();
				if (linhaParte == null) {
					linhaParte = linha;
				}
				linhaParte.setParteRowspan(linhaParte.getParteRowspan() + 1);

				linha.setParte(parteTitulo);
				parteTitulo = null;
				linha.setTitulo(titulo.getTitulo());
				for (String pericope : titulo.getPericopes()) {
					String sigla = pericope.split(" ")[0];
					String texto = pericope.substring(sigla.length() + 1);
					switch (sigla) {
						case "Mt":
							linha.setMt(texto);
							break;
						case "Mc":
							linha.setMc(texto);
							break;
						case "Lc":
							linha.setLc(texto);
							break;
						case "Jo":
							linha.setJo(texto);
							break;
						default:
							throw new RuntimeException("Sigla desconhecida");
					}
				}

				tabela.add(linha);
			}
		}

		ModelAndView result = getModelAndView("/estudos/biblia/concordancia/index");
		result.addObject("data", Map.of(
			"concordancia", tabela
		));

		return result;
	}

	// ----- /biblia/estrutura

	@GetMapping("/biblia/estrutura/mt")
	public ModelAndView estruturaMt() {
		return getModelAndView("/estudos/biblia/estrutura/mt");
	}

	// ----- /biblia/minhas

	@GetMapping("/biblia/minhas")
	public ModelAndView bibliaMinhas() {
		return getModelAndView("/estudos/biblia/minhas/index");
	}

	@GetMapping("/biblia/minhas/CNBB2008")
	public ModelAndView bibliaMinhasCNBB2008() {
		return getModelAndView("/estudos/biblia/minhas/CNBB2008");
	}

	@GetMapping("/biblia/minhas/NVETA1986")
	public ModelAndView bibliaMinhasNVETA1986() {
		return getModelAndView("/estudos/biblia/minhas/NVETA1986");
	}

	// ----- /biblia/texto

	@GetMapping("/biblia/texto/mt-1")
	public ModelAndView textoMt1() {
		return getModelAndView("/estudos/biblia/texto/mt-1");
	}
}
