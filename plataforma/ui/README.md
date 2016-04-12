## Montagem do Ambiente

Você vai precisar ter o Git instalado antes de iniciar. Após, na sua pasta de preferência, execute o comando abaixo em um terminal:

    $ git clone https://github.com/supremotribunalfederal/stfdigital.git
    $ cd stfdigital
    $ git checkout -b servicos
    
Isso deve resultar na seguinte estrutura de diretórios:

![alt tag](estrutura.png)
    
Cada componente é construído separadamente, portanto cada um tem seu próprio arquivo de construção. Usamos Gradle como ferramenta de construção, então você precisa instalá-lo antes de prosseguir. A execução do script Gradle termina com a geração da imagem docker de cada componente, portanto você também vai precisar ter o docker instalado. Você pode instalar o Docker seguinte as instruções para a sua plataforma.

Para facilitar o acesso a Docker Machine, relacione seu IP ao alias "docker". No Linux isso pode ser feito adicionando uma entrada no arquivo /etc/hosts. Para identificar o IP da Docker Machina, execute o comando abaixo:

    $ docker-machine ip
    
Para facilitar o processo, temos um pequeno script que pode ser usado para construir todos os componentes de uma vez. Antes de prosseguir certifique-se que você tenha Java SE 8, Gradle e Docker instalados. Você também precisa ter o Maven instalado e configurado adequadamente para que o Gradle reutilize as bibliotecas já existentes em seu repositório local.    

    $ ./build-all.sh

Isso deverá resultar em <b>oito mensagens</b> iguais a mensagem de log abaixo:

	BUILD SUCCESSFUL
	
Neste ponto, todas as imagens docker já foram geradas. Você poderá rodar todas elas de uma única fez usando Docker Compose:	

    $ docker-compose up -d
    
    
	
	
	

