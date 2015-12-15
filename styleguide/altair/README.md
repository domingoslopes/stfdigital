# Styleguide

## Montagem do Ambiente
Baixe e instale o **node.js** disponível no site:

    https://nodejs.org/
	
Instale **gulp** e **bower** executando o comando abaixo no Prompt de Comando:

    $ npm -g install gulp bower

Baixe a última versão do código, entre no diretório da aplicação e instale as dependências do node e do bower:

	$ git clone https://github.com/supremotribunalfederal/stfdigital.git
	$ cd stfdigital/styleguide
    $ npm install
    $ bower install
    
Agora, rode a aplicação em modo de desenvolvimento:

    $ gulp build
    $ gulp serve

Se tudo correu bem, a aplicação estará rodando no endereço: **http://127.0.0.1:3000**.

Caso não funcione, tente executar o comando abaixo e rodar novamente os comandos `npm install` e `bower install`.

	$ npm cache clean –f

### <a name="thebuildsystem">O Sistema de Build</a>

Existem algumas `tasks` disponíveis no arquivo `gulpfile.js`. As principais tarefas são:

* **gulp dist** ou **gulp production** - Entra no modo de distribuição. Essa task é utilizada em conjunto com outras tasks. Quando utilizado, é importante que seja a primeira task após o comando `gulp`, para que as tasks seguintes tenham perfil de distribuição.
* **gulp clean** - Limpa os arquivos gerados pela última build/release
* **gulp build** - Constrói em modo de desenvolvimento. Isso significa que apenas o mínimo será processado: LESS e SASS serão convertidos para CSS, JADE para HTML e assets serão copiados.
* **gulp release** - Gera a release do código-fonte, é o mesmo que rodar `gulp dist build`. Os arquivos LESS e SASS serão convertidos para CSS; o JADE e HTML serão minificados e, através da notação de build dentro desses arquivos, os arquivos CSS e JS serão concatenados e minificados na ordem utilizada; as imagens serão otimizadas para Web.
* **gulp serve** - Cria um servidor Web com os arquivos na pasta `dist`. A aplicação deve abrir automaticamente no navegador padrão, no endereço **http://127.0.0.1:3000**. É possível acessar os arquivos originais do template no endereço **http://127.0.0.1:3000/altair-template**
* **gulp watch** - Observa por mudanças nos arquivos de desenvolvimento e recarrega o navegador após fazer mudanças, caso utilizado em conjunto com a task **serve** (`gulp serve watch`).