package zcla71.baudoze.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import zcla71.baudoze.model.BackupRestore;
import zcla71.baudoze.tarefa.service.TarefaService;

@Controller
public class BackupRestoreController {
	@Autowired
	private TarefaService tarefaService;

	@GetMapping("/backupRestore/exportar")
	@ResponseBody
	public BackupRestore listar() {
		BackupRestore result = new BackupRestore(tarefaService);
		return result;
	}
}
