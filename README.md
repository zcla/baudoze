# Baú do Zé

## Módulos

* biblia (wip)
* planodevida (esboço)
* tarefa (feito de forma inicial, como piloto; falta muito para ficar funcional)
* texto (esboço)

## Modelo de execução

* `<aplicação>`
  * `common`
    * Classes que não estão ligados a nenhum módulo
    * `controller`
      * Classes com afinidade com a camada controller.
    * `model`
      * Classes com afinidade com a camada model.
    * `view`
      * Classes com afinidade com a camada view.

  * `<módulo>`
    * `controller`
      * Controllers do módulo.
    * `model`
      * Pacote de classes relativas ao modelo (tabelas de banco).
      * `entity`
        * DTOs (@Entity).
      * `repository`
        * Repositories (extends Repository<T, ID>).
      * `service`
        * Classes de serviço (@Service), para serem usadas externamente.
    * `view`
      * Pacote de classes relativas ao view (views de banco).
      * `entity`
        * DTOs (@Entity).
      * `repository`
        * Repositories (extends Repository<T, ID>).
      * `service`
        * Classes de serviço (@Service), para serem usadas pelo controller.

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
