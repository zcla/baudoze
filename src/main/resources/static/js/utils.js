"use strict";

class DateUtils {
    static formatYYYYMMDDHHNNSS(date) {
        return new Date(date.getTime() - (date.getTimezoneOffset() * 60000)).toISOString().replace('T', '-').replaceAll(':', '-').split('.')[0];
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
