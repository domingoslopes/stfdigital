## Montagem do Ambiente

Você vai precisar ter o Git instalado. Após, na sua pasta de preferência, execute o comando abaixo em um terminal:

    $ git clone https://github.com/supremotribunalfederal/stfdigital.git
    $ cd stfdigital
    $ git checkout -b servicos
    
Isso deve resultar na seguinte estrutura de diretórios:

![alt tag](file:///Users/Barreiros/Downloads/estrutura.png)
    
Cada componente é construído separadamente, portanto cada componente tem seu próprio arquivo de construção. Usamos Gradle como ferramenta de construção, então você precisa instalá-lo antes de prosseguir. A execução do script Gradle termina com a geraão da imagem docker de cada componente, portanto você vai precisar ter o docker instalado. Você pode instalar o Docker seguinte as instruções para a sua plataforma.

Para facilitar o acesso a Docker Machine, relacione seu IP ao alias "docker". Você pode  easier access to the Docker machine. First get the IP address of the Linux server that runs all the Docker containers:

 Para facilitar o processo, temos um pequeno script que pode ser usado para construir todos os componentes de uma vez. Antes de prosseguir certifique-se que você tenhainstalado Java SE 8, Gradle e Docker instalados. Você também precisa ter o Maven instalado e configurado adequadamente.

Cada serviço é empacotado em uma imagem docker, então você precisar iniciar a Docker Machine antes de executar o comando abaixo:    

    $ docker-machine start default
    
    

    $ ./build-all.sh

Isso deveria resultar em <b>oito mensagens</b> iguais a mensagem de log abaixo:

	BUILD SUCCESSFUL
	
	
	

