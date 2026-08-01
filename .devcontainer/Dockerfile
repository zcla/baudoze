# TODO Testar versões mais recentes do Java e do Debian
FROM mcr.microsoft.com/devcontainers/java:1-21-bullseye

# Timezone
USER root
RUN ln -sf /usr/share/zoneinfo/America/Sao_Paulo /etc/localtime && \
    echo "America/Sao_Paulo" | tee /etc/timezone
USER vscode
