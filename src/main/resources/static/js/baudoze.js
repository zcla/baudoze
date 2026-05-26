// Configuração global das chamadas ajax com segurança
const csrfToken = $('meta[name="_csrf"]').attr('content');
const csrfHeader = $('meta[name="_csrf_header"]').attr('content');
$.ajaxSetup({
	beforeSend: function (xhr) {
		xhr.setRequestHeader(csrfHeader, csrfToken);
	},
	timeout: 5000
});

// Classe com utilitários de ui
class Ui {
	static async confirma(message) {
		return new Promise((resolve) => {
			const modalId = "modal-" + crypto.randomUUID();
			const $modal = $(`
				<div class="modal fade" id="${modalId}" tabindex="-1">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<h5 class="modal-title">Confirmação</h5>
								<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
							</div>
							<div class="modal-body">${message}</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
								<button type="button" class="btn btn-danger btn-confirm">Confirmar</button>
							</div>
						</div>
					</div>
				</div>
			`);
			$("body").append($modal);

			const modal = new bootstrap.Modal($modal[0]);
			let confirmed = false;
			$modal.find(".btn-confirm").on("click", function () {
				confirmed = true;
				modal.hide();
			});
			$modal.on("hidden.bs.modal", function () {
				$modal.remove();
				resolve(confirmed);
			});
			modal.show();
		});
	}

	static erro(message) {
		Ui.#mensagem(message, "danger");
	}

	static sucesso(message) {
		Ui.#toast(message, "success");
	}

	static async #mensagem(mensagem, tipo) {
		$("#mensagens").append(`
			<div class="alert alert-${tipo} alert-dismissible fade show" role="alert">
				<span>${mensagem}</span>
				<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Fechar"></button>
			</div>
		`);
	}

	static #toast(message, type) {
		let $container = $("#toast-container");
		if ($container.length === 0) {
			$container = $(`<div id="toast-container" class="toast-container position-fixed top-0 end-0 p-3"> </div>`);
			$("body").append($container);
		}
		const toastId = "toast-" + crypto.randomUUID();
		const $toast = $(`
			<div id="${toastId}" class="toast align-items-center text-bg-${type} border-0">
				<div class="d-flex">
					<div class="toast-body">${message}</div>
					<button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast"></button>
				</div>
			</div>
		`);
		$container.append($toast);
		const toast = new bootstrap.Toast($toast[0], {
			delay: 3000
		});

		$toast.on("hidden.bs.toast", function () {
			$toast.remove();
		});
		toast.show();
	}

	static showLoading(message = "Aguarde...") {
		// Evita criar múltiplos overlays
		if (document.getElementById("global-loading-overlay")) {
			return;
		}

		const overlay = document.createElement("div");

		overlay.id = "global-loading-overlay";

		overlay.className = [
			"position-fixed",
			"top-0",
			"start-0",
			"w-100",
			"h-100",
			"d-flex",
			"justify-content-center",
			"align-items-center"
		].join(" ");

		overlay.style.backgroundColor = "rgba(255, 255, 255, 0.7)";
		overlay.style.zIndex = "9999";
		overlay.style.backdropFilter = "blur(2px)";

		overlay.innerHTML = `
			<div class="text-center">
				<div
					class="spinner-border text-primary"
					role="status"
					style="width: 4rem; height: 4rem;"
				>
					<span class="visually-hidden">Carregando...</span>
				</div>

				<div class="mt-3 fw-semibold text-dark">
					${message}
				</div>
			</div>
		`;

		document.body.appendChild(overlay);

		// Impede scroll/interação indireta
		document.body.style.overflow = "hidden";
	}

	static hideLoading() {
		const overlay = document.getElementById("global-loading-overlay");

		if (overlay) {
			overlay.remove();
		}

		document.body.style.overflow = "";
	}
}
