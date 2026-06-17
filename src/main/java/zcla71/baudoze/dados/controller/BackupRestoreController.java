package zcla71.baudoze.dados.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import zcla71.baudoze.biblia.model.repository.BibliaRepository;
import zcla71.baudoze.dados.model.Download;

@RequiredArgsConstructor
@Controller
@RequestMapping("/backupRestore")
public class BackupRestoreController {
	private final BibliaRepository bibliaRepository;

	@GetMapping("/download")
	@ResponseBody
	public Download download() {
		return new Download(bibliaRepository);
	}
}
