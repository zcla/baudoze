package zcla71.baudoze.auth_user.controller;

import java.util.Objects;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import zcla71.baudoze.auth_user.model.entity.AuthUser;
import zcla71.baudoze.common.controller.BauBaseController;

@Controller
public class AuthUserController extends BauBaseController {
	@GetMapping("/auth_user/{id}/imagem/")
	public ResponseEntity<byte[]> listar(@AuthenticationPrincipal AuthUser authUser, @PathVariable Long id) {
		return ResponseEntity
				.ok()
				.contentType(MediaType.parseMediaType(Objects.requireNonNull(authUser.getImagemContentType())))
				.body(authUser.getImagem());
	}
}
