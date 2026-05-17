package zcla71.baudoze.tarefa.model.service;

import zcla71.baudoze.common.service.BauServiceException;
import zcla71.baudoze.common.service.BauServiceTipoException;

public class TarefaServiceException extends BauServiceException {
	public TarefaServiceException(BauServiceTipoException motivo, String message) {
		super(motivo, message);
	}

	public TarefaServiceException(BauServiceTipoException motivo, String message, String contexto) {
		super(motivo, message, contexto);
	}
}
