package zcla71.baudoze.common.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import zcla71.baudoze.common.model.BackupRestore;
import zcla71.baudoze.tarefa.model.service.TarefaService;

@Controller
public class BackupRestoreController {
	@Autowired
	private TarefaService tarefaService;

	@GetMapping("/backupRestore/exportar")
	@ResponseBody
	public BackupRestore listar() {
		BackupRestore result = new BackupRestore(this.tarefaService);
		return result;
	}
}

// O ChatGPT sugeriu o código abaixo, que não me parece necessário:
// @Configuration
// @EnableWebSecurity
// public class SecurityConfig {

//   @Bean
//   SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//     http
//       .authorizeHttpRequests(auth -> auth
//         .requestMatchers("/", "/css/**", "/js/**", "/images/**").permitAll()
//         .anyRequest().authenticated()
//       )
//       .oauth2Login(oauth -> { /* default ok */ })
//       .logout(logout -> logout.logoutSuccessUrl("/").permitAll());

//     return http.build();
//   }
// }
