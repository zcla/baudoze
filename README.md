# Baú do Zé

## Módulos

* biblia (wip)
* planodevida (esboço)
* tarefa (feito de forma inicial, como piloto; falta muito para ficar funcional)
* texto (esboço)

## Modelo de execução

* `<aplicação>`
  * `config`
    * `AppConfig.java`
      <br>Responsável por guardar as configurações da aplicação.
  * `controller`
    * `BauErrorController.java`
      <br>Responsável por mostrar uma view genérica para quando houver erros.
  * `<módulo>`
    * `controller`
      * `<módulo>Controller.java`
        <br>Responsável por mapear as urls de um módulo, e para cada uma delas:
        <br>1. fazer os processamentos necessários;
        <br>2. determinar a view que será chamada;
        <br>3. prover todos os dados necessários à view.
    * `service`
      * `<módulo>Service.java`
        <br>Responsável por implementar os métodos e retornar os dados do serviço, funcionando como uma caixa-preta.
    * `view`
      * `<módulo><view>View[<detalhe>].java`
        <br>DTO com os valores necessários à view.

## .devcontainer/.env
DATABASE_HOST=mysql_db
DATABASE_PORT=3306
DATABASE_DB=baudoze
DATABASE_USER=ze
DATABASE_PASSWORD=senhaDoZe
GOOGLE_AUTH_CLIENT_ID=?
GOOGLE_AUTH_CLIENT_SECRET=?

## Outras notas

* Adicionando um container com o mysql (Dockerfile, docker-compose.yml, .env e como configurar o banco)
  * https://dev.to/cmiles74/really-using-visual-studio-development-containers-561e
