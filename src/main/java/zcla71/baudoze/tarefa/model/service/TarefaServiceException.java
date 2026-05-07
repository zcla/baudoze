package zcla71.baudoze.tarefa.model.service;

import zcla71.baudoze.common.service.BauServiceException;

public class TarefaServiceException extends BauServiceException {
	public TarefaServiceException(String message) {
		super(message);
	}

	public TarefaServiceException(String message, String contexto) {
		super(message, contexto);
	}
}
