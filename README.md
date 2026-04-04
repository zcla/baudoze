# Baú do Zé

## Outras notas em .md

* [devcontainer.md](.devcontainer/devcontainer.md) Configurações que não podem estar no Git.
* [fontes.md](src/main/java/zcla71/baudoze/texto/fontes.md) Fontes de textos encontrados na internet, para uso futuro.

## Modelo de execução
TODO Ver se não foi mudado

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

## Módulos & Roadmap

### Em andamento

* tarefa (feito de forma inicial, como piloto; falta muito para ficar funcional)
* biblia
  * Consulta
    * Dados
      * Criar uma Bíblia padrão: https://www.vatican.va/archive/bible/index_po.htm &rarr; Latim em primeiro lugar; Espanhol e Inglês se tiverem **exatamente** a mesma estrutura.
      * Criar a correspondência das outras Bíblias a ela.
    * Importação
      * Fazer importação não só do texto, mas de tudo o que estiver disponível (notas, referências cruzadas, etc.)
    * Leitura
      * https://www.believersword.com/ -> Bom programa a imitar
    * Busca textual
  * Destaques
    * https://www.believersword.com/ -> Bom programa a imitar
  * Anotações
    * https://www.believersword.com/ -> Bom programa a imitar

### Esboço (modelagem)

* planodevida
* texto

### Ideias

* livros
  * APIs ISBN (tem mais; buscar no Google):
    * https://brasilapi.com.br/api/isbn/v1/<isbn>
    * https://openlibrary.org/isbn/<isbn>.json
* takeout (D:\dados\arquivo\takeout)
  * Google
  * Samsung Health
