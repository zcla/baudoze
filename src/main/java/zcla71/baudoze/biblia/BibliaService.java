package zcla71.baudoze.biblia;

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
}
