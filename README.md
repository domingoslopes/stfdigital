## Ambiente

Você vai precisar ter o Git instalado. Após, na sua pasta de preferência, execute os comandos abaixo em um terminal:

    $ git clone https://github.com/supremotribunalfederal/stfdigital-microservices
    $ cd stfdigital-microservices
    $ ./clone-all.sh
    
Isso deve resultar na seguinte estrutura de diretórios:

<img src="assets/estrutura.png" width="180">
    
Cada componente deverá ser construído separadamente, portanto cada um tem seu próprio arquivo de construção. Usamos Gradle como ferramenta de montagem, então você precisa instalá-lo antes de prosseguir. A execução do script Gradle termina com a geração da imagem docker de cada componente, portanto você também vai precisar ter o docker instalado. Você pode instalá-lo seguindo as <a href="https://docs.docker.com/engine/installation">instruções para a sua plataforma</a>.

Para facilitar o acesso a <i>Docker Machine</i>, relacione seu IP ao alias "docker". Para identificar o IP da <i>Docker Machine</i>, execute o comando abaixo:

    $ docker-machine ip

No Linux, isso pode ser feito adicionando uma entrada no arquivo `/etc/hosts`, como no exemplo abaixo 

    192.168.99.100  docker

Você poderá confirmar se esse relacionamento está funcional executando o comando `ping` contra o alias criado.

    $ ping docker
    PING docker (192.168.99.100): 56 data bytes
    64 bytes from 192.168.99.100: icmp_seq=0 ttl=64 time=0.314 ms
    64 bytes from 192.168.99.100: icmp_seq=1 ttl=64 time=0.340 ms
    64 bytes from 192.168.99.100: icmp_seq=2 ttl=64 time=0.386 ms
    
Para facilitar o processo, temos um pequeno script que pode ser usado para construir todos os componentes de uma vez. Antes de prosseguir, certifique-se que você tenha Java SE 8 instalado. Você também precisa ter o Maven instalado e configurado adequadamente para que o Gradle reutilize as bibliotecas já existentes em seu repositório local.

    $ ./build-all.sh

Isso deverá resultar em <b>sete mensagens</b> iguais a mensagem de log abaixo:

	BUILD SUCCESSFUL
	
Neste ponto, todas as imagens docker já foram geradas.

    $ docker images
    REPOSITORY                   TAG                 IMAGE ID            CREATED             SIZE
    services                     latest              407ae34482fb        10 hours ago        326.2 MB
    gateway                      latest              dafb856acb19        10 hours ago        241.1 MB
    discovery                    latest              cd91ba415aa7        10 hours ago        260.5 MB
    distribuicao                 latest              16645d77429b        10 hours ago        325.8 MB
    autuacao                     latest              e072d2c45d2e        10 hours ago        325.8 MB
    recebimento                  latest              89f185501e17        10 hours ago        325.9 MB
    peticionamento               latest              eb5ada3f8d4c        10 hours ago        325.8 MB
    frolvlad/alpine-oraclejdk8   slim                a7754f3b301e        7 days ago          167.4 MB

Você poderá rodar todas elas de uma única vez usando <i>Docker Compose</i>:	

    $ docker-compose up -d
    
Você poderá acompanhar as mensagens de log pelo comando: 

    $ docker-compose logs
    
A medida que os serviços forem iniciados, eles serão registrados automaticamente no serviço de discovery. Acesse `http://docker:8761` para acompanhar o registro dos serviços. Quando todos estiverem registrados, você deverá ver a tabela como no exemplo abaixo:

<img src="assets/discovery.png" width="800">

A interface da plataforma não está em uma imagem docker. Ela deve ser executada separadamente. Em outro terminal, execute a sequência abaixo para instalar as dependências necessárias:

    $ cd plataforma/ui
    $ npm install
    $ npm install tslint
    
Após, basta rodar a aplicação:

    $ gulp serve
    
#### Livereload e Debug Remoto

Usamos <a href="https://spring.io/tools">Eclipse STS</a> como IDE. Certifique-se que você tem a última versão. Você vai precisar importar todos os componentes separadamente. Você pode importar cada projeto diretamente pelo Eclipse, como um Projeto Gradle, ou pode executar o comando abaixo, no diretório de cada projeto, para gerar os arquivos necessários antes de importá-lo como um projeto já existente. 

    $ gradle eclipse

Se optar por uma organização com <i>Working Sets</i>, você deverá ter uma estrutura de projetos como na imagem abaixo:

<img src="assets/eclipse.png" width="150">    

Para viabilizar o desenvolvimento local usando as imagens docker, usamos o <i>Spring Devtools</i>, que permite o `hot restart` de aplicações <i>Spring Boot</i> rodando dentro de um container docker. Os serviços da plataforma já estão configurados para viabilizar tal funcionalidade. No Eclipse, basta executar a sequência abaixo:

    - Com o botão direito, clique no projeto "services"
    - Em seguida, clique em "Run As" -> "Sprint Devtools Client"
    - Em "Remote Url" informe "http://docker:8081"
    - Em "Remote Secret" informe "stfdigital"
    - Click em "Run"

Isso vai iniciar uma conexão remota com a imagem docker correspondente. No terminal, acesse o log do container pelo comando:
 
    $ docker-compose logs services
    
Faça alguma alteração no arquivo "ProcessoRestResource" (por exemplo, altere de "Publicado" para "Distribuído"). Assim que você salvar o arquivo, as mensagens de log do restart devem começar a ser exibidas no terminal. Após concluído o restart, acesse o browser para confirmar se a label da tabela de pesquisa foi realmente alterada, de "Publicado" para "Distribuído".

Para realizar o debug, bastar acessar a mesma configuração usada acima, mas em modo Debug. Para confirmar, coloque um break point no arquivo "ProcessoRestResource" e acesse novamente a funcionalidade de pesquisa.

Para finalizar todos os serviços, basta executar o comando abaixo:
 
    $ docker-compose down


    
    

    
    