## Montagem do Ambiente
Baixe e instale o **node.js** disponível no site:

    https://nodejs.org/
	
Instale **gulp** e **bower** executando o comando abaixo no Prompt de Comando:

    $ npm -g install gulp bower

Baixe a última versão do código, entre no diretório da aplicação e instale as dependências do node e do bower:

	$ git clone https://github.com/supremotribunalfederal/stfdigital.git
	$ cd stfdigital
    $ npm install
    $ bower install
    
Agora, rode a aplicação em modo de desenvolvimento:

    $ gulp serve

Se tudo correu bem, a aplicação estará rodando no endereço: **http://127.0.0.1:3000**.

### <a name="thebuildsystem">O Sistema de Build</a>

Existem algumas `tasks` disponíveis no arquivo `gulpfile.js`:

* **gulp serve** - Quando essa task for executada, o sistema de build passará a monitorar os arquivos do front-end. Sempre que houver mudanças no diretório `src/main/resources/static/`, o processo de build recompilará todos os arquivos e atualizará o browser com as novas alterações.
* **gulp serve:tdd** - Semelhante à `gulp serve`, porém irá executar os testes unitários continuamente.
* **gulp test:unit** - Apenas executa os testes unitários disponíveis em `src/main/resources/static/application/test/unit`.
* **gulp test:e2e** - Executa os testes end-to-end disponíveis em `src/main/resources/static/application/test/e2e`.

**Você vai precisar que a aplicação esteja rodando para executar os testes e2e. Você pode fazer isso usando uma outra instância de terminal.**
