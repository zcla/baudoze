# Baú do Zé

## Outras notas em .md

* [devcontainer.md](.devcontainer/devcontainer.md) Configurações que não podem estar no Git.
* [fontes.md](src/main/java/zcla71/baudoze/texto/fontes.md) Fontes de textos encontrados na internet, para uso futuro.

## Modelo de execução
TODO Ver se não foi mudado

* `<aplicação>`
  * `common`
    * Classes comuns que não estão ligados a nenhum módulo específico.
    * `controller`
      * Classes com afinidade com a camada controller.
    * `model`
      * Classes com afinidade com a camada model.
    * `view`
      * Classes com afinidade com a camada view.

  * `<módulo>`
    * `controller`
      * Controllers do módulo. Não trata de regra de negócio e tem que criar try/catch dos exceptions lançados pelos serviços, para jogar pra tela através de BauModelAndView.addMensagem().
    * `model`
      * Pacote de classes relativas ao modelo (tabelas de banco).
      * `entity`
        * DTOs (@Entity).
      * `repository`
        * Repositories (extends Repository<T, ID>).
      * `service`
        * Classes de serviço, para serem usadas externamente. Deve fazer validação das regras de negócio e lançar exceções de negócio, derivadas de RuntimeException.
    * `view`
      * Pacote de classes relativas ao view (views de banco).
      * `entity`
        * DTOs (@Entity).
      * `repository`
        * Repositories (extends Repository<T, ID>).
      * `service`
        * Classes de serviço, para serem usadas externamente. Busca os dados já no formato exigido pelas telas.

## TODO-List

* biblia: mostrar origem

* biblia: https://www.oraetlabora.com.br/desenvolvedores
  * https://www.oraetlabora.com.br/api/catecismo

* biblia: backup do banco

* tarefa: Resolver TODOs e finalizar

* infra: Mudar banco geral para PostGreSQL

* infra: Mudar banco do usuário para SQLite

* biblia: definir uma Bíblia base e fazer rotina de edição (CNBB2019? Comprar uma 6ª edição?)

* biblia: conectar as Bíblias para permitir visão lado a lado

## Outras anotações

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
