package zcla71.baudoze.biblia;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zcla71.baudoze.biblia.model.Biblia;

@Service
public class BibliaService {
	@Autowired
	private BibliaRepository bibliaRepository;
	
	public void incluir(Biblia biblia) {
		bibliaRepository.save(biblia);
	}

	public Biblia buscaPorCodigo(String codigo) {
		return bibliaRepository.findByCodigo(codigo);
	}

	public List<Biblia> listar() {
		List<Biblia> result = new ArrayList<>();
		this.bibliaRepository.findAll().forEach(result::add);
		return result;
	}
}
