# Fontes de textos

* Obs.: tudo o que está no readme dos projetos [`doccat`](https://github.com/zcla/doccat) e [`wikimaker`](https://github.com/zcla/wikimaker) foi copiado pra cá em 13/02/2026.

## Ferramentas

* Para abrir arquivos sqlite (bíblias no formato MyBible, por exemplo): https://inloop.github.io/sqlite-viewer/

## Bíblias

### Já importadas

* Ver [application.yml](/src/main/resources/application.yml)

### Já no projeto [`wikimaker`](https://github.com/zcla/wikimaker); é só adaptar

* [`a12_com_biblia`](https://www.a12.com/biblia) Bíblia de Aparecida
* [`biblia_parresia_com_bible`](https://claretianos.com.br/biblia-ave-maria-online/) Bíblia Ave-Maria
* [`biblia_paulus_com_br_biblia`](https://biblia.paulus.com.br/) Bíblia Sagrada Edição Pastoral
* [`liturgiadashoras_online_biblia`](https://liturgiadashoras.online/biblia/biblia-jerusalem/) Bíblia de Jerusalém

### Não importar

* https://github.com/pepaulo/Open-Source-Catholic-Bible - são as mesmas do site ph4.org

### Sugestões do ChatGPT

* https://chatgpt.com/c/69a0c566-2220-832c-a7e9-1dbb58f34156 (conversa)
* https://chatgpt.com/share/69a38832-5e6c-8004-8b30-533c2ddcd6ec (link de compartilhamento)

### Candidatas

* github.com/Dancrf/biblia-db - Matos Soares 1956 em formato JSON
  * `json` [pt] https://github.com/Dancrf/biblia-db/raw/refs/heads/main/biblia.json

* die-bibel.de German Bible Society (também há traduções)
  * `html` [he] https://www.die-bibel.de/en/bible/BHS Biblia Hebraica Stuttgartensia (BHS)
  * `html` [el] https://www.die-bibel.de/en/bible/NA28 Novum Testamentum Graece
  * `html` [el] https://www.die-bibel.de/en/bible/UBS5 Greek New Testament
  * `html` [el] https://www.die-bibel.de/en/bible/LXX Septuaginta
  * `html` [el] https://www.die-bibel.de/en/bible/LXXA Septuaginta - Alternative Texte (5 livros)
  * `html` [la] https://www.die-bibel.de/en/bible/VUL Biblia Sacra Vulgata
  * `html` [la] https://www.die-bibel.de/en/bible/VULA Biblia Sacra Vulgata – Alternative Texte (só Sl)

* github.com/scrollmapper - dezenas de versões em dezenas de línguas; não sei se há católicas.
  * `json` [várias] https://github.com/scrollmapper/bible_databases/tree/master/sources
  * `json` [la] https://github.com/scrollmapper/bible_databases/tree/master/sources/la - várias versões da vulgata

* github.com/syncbible - não sei se há católicas.
  * `json` [várias] https://github.com/syncbible/syncbible/tree/gh-pages/bibles

* romanliturgy.org
  * `xml` [la] https://www.romanliturgy.org/xml/include/nova-vulgata/

* perseus.tufts.edu
  * `xml` [la] https://www.perseus.tufts.edu/hopper/text?doc=Perseus%3atext%3a1999.02.0060 (Jerome. Vulgate Bible.)
  * `xml` [el] https://www.perseus.tufts.edu/hopper/text?doc=Perseus%3atext%3a1999.01.0155 (The New Testament in the original Greek.)
  * `xml` [en] https://www.perseus.tufts.edu/hopper/text?doc=Perseus%3atext%3a1999.01.0156 (World English Bible.)

* scaife.perseus.org
  * `xml` [el/en] https://scaife.perseus.org/library/urn:cts:greekLit:tlg0527/ (AT Septuaginta / World English Bible)
  * `xml` [el/en] https://scaife.perseus.org/library/urn:cts:greekLit:tlg0031/ (The New Testament in the original Greek / World English Bible)

* sacredbible.org
  * [vários] https://www.sacredbible.org/ *MUITA* coisa interessante aqui.

* oraetlabora.com.br
  * API: https://www.oraetlabora.com.br/desenvolvedores
  * A Bíblia já está importada
  * Catecismo (parece faltar do 1 ao 25): https://www.oraetlabora.com.br/catecismo
  * Documentos da Igreja: https://www.oraetlabora.com.br/documentos

* ecclesia.pt
  * [pt(-br?)] Compêndio da Doutrina Social da Igreja https://sites.ecclesia.pt/dsi/

* usccb.org
  * [en-us] New American Bible, Revised Edition https://bible.usccb.org/bible
  * [en-us] (os links parecem cair no mesmo lugar dos que estão no link acima) https://www.usccb.org/offices/new-american-bible/books-bible

* conferenciaepiscopal.pt
  * [pt-pt] Bíblia oficial portuguesa (WIP!) https://conferenciaepiscopal.pt/biblia/index.php/Main_Page

* clerus.org - Já importei em outro projeto, se não me engano, usando PowerShell; talvez o código sirva para todas as outras bíblias que há no mesmo site.
  * [pt] https://www.clerus.org/bibliaclerusonline/pt/66c.htm
  * [pt] https://www.clerus.org/bibliaclerusonline/pt/index.htm Catecismos e outros livros

* bibliacatolica.com.br - Parece conter captchas; talvez o código sirva para todas as outras bíblias que há no mesmo site. Há código a aproveitar no projeto `doccat`, pasta `/powershell/old`
  * [pt] https://www.bibliacatolica.com.br/biblia-ave-maria/genesis/1/ Ave Maria
  * [pt] https://www.bibliacatolica.com.br/biblia-matos-soares-1956/genesis/1/ Matos Soares 1956

* salvaimerainha.org.br - Há uma Bíblia católica aqui, não sei qual
  * [pt] https://salvaimerainha.org.br/biblia-online/

* minhabibliasagrada.com.br - Há uma Bíblia católica aqui, não sei qual
  * [pt] https://minhabibliasagrada.com.br/vc/

* vatican.va https://www.vatican.va/archive/bible/index_po.htm
  * [cn] https://www.vatican.va/chinese/bibbia.htm
  * [en] https://www.vatican.va/archive/ENG0839/_INDEX.HTM
  * [es] https://www.vatican.va/archive/ESL0506/_INDEX.HTM
  * [it] https://www.vatican.va/archive/ITA0001/_INDEX.HTM

* biblegateway.com
  * [en] https://www.biblegateway.com/passage/?search=Genesis%201&version=NCB (New Catholic Bible)
  * [en] https://www.biblegateway.com/passage/?search=Genesis%201&version=RSVCE (Revised Standard Version Catholic Edition)
  * [en] https://www.biblegateway.com/passage/?search=Genesis%201&version=NRSVCE (New Revised Standard Version Catholic Edition) parece ser a base da Ignatius Bible

* biblesupersearch.com - download; não parece haver versões católicas, mas há duas versões em grego que talvez sejam úteis: "Textus Receptus NT" e "Textus Receptus Parsed NT"
  * [várias] https://www.biblesupersearch.com/bible-downloads/

* bibliacatolica.com.br
  * [várias]

* bibliaonline.com.br
  * https://www.bibliaonline.com.br/vc/livros "Versão católica"

* catholicbible.online
  * https://catholicbible.online/ Vulgate + Douay-Rheims + Knox, lado a lado.

* clerus.org https://www.clerus.org/bibliaclerusonline/pt/index.htm
  * [de] https://www.clerus.org/bibliaclerusonline/de/66c.htm
  * [en] https://www.clerus.org/bibliaclerusonline/en/66c.htm
  * [es] https://www.clerus.org/bibliaclerusonline/es/66c.htm
  * [fr] https://www.clerus.org/bibliaclerusonline/fr/66c.htm
  * [it] https://www.clerus.org/bibliaclerusonline/it/66c.htm

* ebible.org Muitas bíblias disponíveis para download em vários formatos: VPL (texto sem formatação; facílimo de importar), USFX (xml com texto, estrutura e comentários), USFM	(formato esquisito, mas que dá pra ler, com texto, estrutura e comentários), browserBible (html, não muito bom pra importar, mas ótimo para visualizar a estrutura dos dados)
  * [la] https://ebible.org/find/details.php?id=latVUC Clementine Vulgate of 1598 with Glossa Ordinaria Migne edition 1880 in Latin
  * [el] https://ebible.org/find/details.php?id=grcbrent The Greek Septuagint with Apocrypha, compiled by Sir Lancelot C. L. Brenton
  * [várias] https://ebible.org/find/ (não achei nenhuma católica em português; há versões em grego, tem que verificar se são confiáveis e úteis)

* intratext.com
  * [várias] https://www.intratext.com/BIBLE/
    * https://www.intratext.com/IXT/POR0013/ Edição IntraText
    * [la] https://www.intratext.com/IXT/LAT0001/ Vulgata de São Jerônimo
    * [la] https://www.intratext.com/IXT/LAT0669/ Neovulgata
    * [pt] https://www.intratext.com/y/POR0013.HTM A Bíblia
    * [pt] https://www.intratext.com/y/POR0097.HTM Bíblia Sagrada Edição Pastoral 2002
  * https://www.intratext.com/EVI/ Outros livros

* liturgiacatolica.com
  * https://www.liturgiacatolica.com/ (só alguns livros, mas também Catecismo e alguns documentos oficiais da Igreja)

* newadvent.org
  * [el/en/la] https://www.newadvent.org/bible/ Greek + Knox + Latin, lado a lado

* paroquiadasgracas.com
  * [pt-br] https://www.paroquiadasgracas.com/biblia Parece ser a CNBB antiga

* paroquias.org
  * [pt-pt] https://www.paroquias.org/biblia/ Difusora Bíblica (Capuchinhos)

* paulus.com.br - Bíblia Pastoral. Os textos parecem ser idênticos; ambos contêm notas.
  * [pt] https://www.paulus.com.br/biblia-pastoral/_INDEX.HTM Deve ser igual à `biblia_paulus_com_br_biblia`

* tuapalavra.com.br - **MUITAS** versões; não consegui encontrar os deuterocanônicos.
  * [pt] https://amp.tuapalavra.com.br/pt-BR/CNBB CNBB antiga (1987?); talvez o código sirva para todas as outras dezenas de bíblias que há no mesmo site. Não consegui encontrar os deuterocanônicos.
  * [pt] https://amp.tuapalavra.com.br/pt-BR
  * [ar] https://amp.tuapalavra.com.br/ar-SA
  * [cn] https://amp.tuapalavra.com.br/zh-CN
  * [de] https://amp.tuapalavra.com.br/de-DE
  * [en] https://amp.tuapalavra.com.br/en-US
  * [es] https://amp.tuapalavra.com.br/es-ES
  * [fr] https://amp.tuapalavra.com.br/fr-FR
  * [hi] https://amp.tuapalavra.com.br/hi-IN
  * [it] https://amp.tuapalavra.com.br/it-IT

* get.bible Links para vários downloads de bíblias
  * https://get.bible/blog/post/how-to-access-the-bible-via-api-or-download-the-bible-as-data

## Catecismo

* http://www.clerus.org/bibliaclerusonline/pt/on.htm
* http://www.clerus.org/pls/clerus/cn_clerus.h_centro?dicastero=2&tema=3&argomento=15&sottoargomento=0&lingua=4&Classe=1&operazione=ges_doc&vers=2&rif=12460&rif1=12460domenica
* https://www.vatican.va/archive/ccc/index_po.htm
* https://catecismo-az.tripod.com/conteudo/cic/ig.html
  * https://angelgireh.tripod.com/
* https://liturgiadashoras.online/catechismus/

## Código de Direito Canônico

* https://liturgiadashoras.online/iuris-canonici/

## Liturgia diária

* https://api-liturgia.edicoescnbb.com.br/contents/in/date/1971-10-05

## Outros livros

* São Josemaria Escrivá
  * https://escriva.org/pt-br/

* Suma teológica
  * https://permanencia.org.br/ (link Suma Teológica, em partes)

# Anotações sobre diferenças

## Vulgata vs Neovulgata

* Sb 12,18
  * Neovulgata: "cum magna indulgentia"
	  * https://www.vatican.va/archive/bible/nova_vulgata/documents/nova-vulgata_vt_sapientiae_lt.html#12
	  * \\wsl.localhost\Ubuntu\home\zecla\git\baudoze\data\files\NVETA1986.pdf
    * https://www.clerus.org/bibliaclerusonline/pt/ksy.htm#iy
  * Vulgata: "cum magna reverentia"
	  * https://www.clerus.org/bibliaclerusonline/pt/ks2.htm#i5
	  * https://www.bibliacatolica.com.br/vulgata-clementina/liber-sapientiae/12/
