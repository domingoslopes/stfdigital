### Montagem do Ambiente

Instale o **node.js**, **gulp** e **bower**, caso ainda não tenha.

    $ npm -g install gulp bower

Depois disso, baixe a última versão do código e então instale as dependências do node e do bower. Após, rode a aplicação em modo de desenvolvimento.

    $ npm install
    $ bower install
    $ gulp serve

Se tudo correu bem, a aplicação estará disponível em **http://127.0.0.1:3000**.

### <a name="thebuildsystem"></a>O Sistema de Build

Existem algumas `tasks` disponíveis no arquivo `gulpfile.js`.

Abaixo a descrição de cada task pública:

* **gulp serve** - Quando essa task é executada, o sistema de build passa a monitorar os arquivos do front-end. A cada mudança em um arquivo da basta `src/main/resources/static/`, o build recompila todos os arquivos e seu browser será atualizado automaticamente com suas alterações.
* **gulp serve:tdd** - Semelhante à `gulp serve` adicionando a capacidade de executar os testes unitários continuamente.
* **gulp test:unit** - Apenas executa os testes unitários disponíveis em `src/main/resources/static/application/test/unit`.
* **gulp test:e2e** - Executa os testes end-to-end disponíveis em `src/main/resources/static/application/test/e2e`..
**Você vai precisar que a aplicação esteja rodando para executar os testes e2e. Você pode fazer isso usando uma outra intência de terminal.**
