package zcla71.baudoze.tarefa.controller;

import java.util.Objects;

import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;
import zcla71.baudoze.auth_user.entity.AuthUser;
import zcla71.baudoze.tarefa.dto.TarefaApiResponse;
import zcla71.baudoze.tarefa.service.TarefaService;
import zcla71.baudoze.tarefa.service.TarefaServiceException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/tarefa")
public class TarefaApiController {
	final private TarefaService tarefaService;

	@DeleteMapping("/{id}")
	public ResponseEntity<TarefaApiResponse> excluir(
			@AuthenticationPrincipal AuthUser authUser,
			@NonNull @PathVariable Long id,
			RedirectAttributes redirectAttrs) {
		try {
			tarefaService.excluir(Objects.requireNonNull(tarefaService.buscar(id)));
			return ResponseEntity
					.ok(new TarefaApiResponse(true, "ok"));
		} catch (TarefaServiceException ex) {
			return ex.getResponseEntity()
					.body(new TarefaApiResponse(false, ex.getMessage()));
		}
	}

	@PatchMapping("/{id}/marcar")
	public ResponseEntity<TarefaApiResponse> marcar(
			@AuthenticationPrincipal AuthUser authUser,
			@NonNull @PathVariable Long id,
			RedirectAttributes redirectAttrs) {
		try {
			tarefaService.marcar(Objects.requireNonNull(tarefaService.buscar(id)));
			return ResponseEntity
					.ok(new TarefaApiResponse(true, "ok"));
		} catch (TarefaServiceException ex) {
			return ex.getResponseEntity()
					.body(new TarefaApiResponse(false, ex.getMessage()));
		}
	}

	@PatchMapping("/{id}/desmarcar")
	public ResponseEntity<TarefaApiResponse> desmarcar(
			@AuthenticationPrincipal AuthUser authUser,
			@NonNull @PathVariable Long id,
			RedirectAttributes redirectAttrs) {
		try {
			tarefaService.desmarcar(Objects.requireNonNull(tarefaService.buscar(id)));
			return ResponseEntity
					.ok(new TarefaApiResponse(true, "ok"));
		} catch (TarefaServiceException ex) {
			return ex.getResponseEntity()
					.body(new TarefaApiResponse(false, ex.getMessage()));
		}
	}
}
