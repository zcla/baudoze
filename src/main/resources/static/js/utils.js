"use strict";

class DateUtils {
    static formatYYYYMMDDHHNNSS(date) {
        return new Date(date.getTime() - (date.getTimezoneOffset() * 60000)).toISOString().replace('T', '-').replaceAll(':', '-').split('.')[0];
    }
}

class FrontendUtils {
    static addMensagem(tipo, titulo, texto) {
        $("#mensagens").append(`
            <div class="alert alert-${tipo} alert-dismissible fade show" role="alert">
                <strong>${titulo}</strong>
                <span>${texto}</span>
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Fechar"></button>
            </div>
        `);
    }
}

class StringUtils {
    static downloadString(str, fileName) {
        const downloader = document.createElement('a');
        downloader.style.display = 'none';
        downloader.href = 'data:attachment/text,' + encodeURIComponent(str);
        downloader.target = '_blank';
        downloader.download = fileName;
        downloader.click();
    }
}
