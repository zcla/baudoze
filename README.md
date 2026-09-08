# Baú do Zé

## Outras notas em .md

* [devcontainer.md](.devcontainer/devcontainer.md) Configurações que não podem estar no Git.
* [fontes.md](src/main/java/zcla71/baudoze/texto/fontes.md) Fontes de textos encontrados na internet, para uso futuro.

## TODO-List

* tarefa: Resolver TODOs e finalizar.

* Não chamar /error na API.

* backup do banco
  * geral (biblia)
    * backup
    * restore
  * pessoal (tarefa)
    * backup
    * restore

* Colocar na internet
  * https://hostingtutorials.dev/blog/free-spring-boot-host-with-render

* Bíblia "padrão": Nova Vulgata:
  * Adaptar a importação já feita (tabela nova_vulgata em [schema.sql](src/main/resources/schema.sql)).
  * Conectar as Bíblias para permitir visão lado a lado (tabela versiculo_nova_vulgata em [schema.sql](src/main/resources/schema.sql)).

* Outros documentos: https://www.oraetlabora.com.br/desenvolvedores
  * https://www.oraetlabora.com.br/api/catecismo
  * https://www.oraetlabora.com.br/api/documentos

## Outras anotações

* biblia
  * Consulta
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
