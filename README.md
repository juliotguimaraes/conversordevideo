# Conversor de Vídeo

Projeto de seleção de novo funcionário, desenvolvendo um conversor de vídeo para a SambaTech.


### Problema encontrados

* A aplicação final não funciona como desejada, uma vez que o único problema que não consegui solucionar, foi a realização do upload do arquivo para a plataforma S3 da amazom.
* A aplicação consegue pegar o arquivo da Amazon e enviar para o Zencoder para a codificação do video em um formato compatível e enviar esse novo vídeo para o S3 para que seja possível o stream.



### Projeto

O desafio é desenvolver uma aplicação web que possibilita a conversão de vídeos de formatos especificos, que não são compativeis com a WEB, para um formato que seja compatível.
Com isso a aplicação terá uma interface web que permite o envio de um vídeo no formato não compatível com a web, este será convertido e depois dará a possíbilidade de ser assistido pelo usuário na própria interface. 

### Estrutura 

O código fonte é encontrado dentro do diretorio raiz src/main/java
O arquivo principal é encontrado nessa pasta com o nome 'Application.java"

* No diretório src/main/java/controllers é encontrado o controlador principal para este projeto. Ele fornece toda a lógica para a experiência do usuário.

* No diretório src/main/java/storage é encontrado a interface StorageService e a implementação do S3StorageService que opera no serviço S3 da Amazon.

* No diretório src/main/java/encoding é encontrado  a interface EncodingService e a implementação do ZencoderS3Service que opera no serviço S3 da Amazon e emite trabalhos de codificação para o Zencoder.

No diretório src/main/resources é encontrado o restante dos arquivos necessários para criar a página web e realizar as funções pedidas pelo usuário, que envolvem as classes descritas anteriormente.


### Tecnologias utilizadas

* GitHub:
O repositório do GitHub foi usado para que o código esteja disponível e permite manter todo o processo de desenvolvimento registrado.

*Heroku:
O aplicativo está hospedado na plataforma Heroku. O link para nosso aplicativo é:

https://videocvrt.herokuapp.com/

* Amazon AWS-S3:
O armazenamento sugerido foi os serviços de armazenamento da Amazon, projetados para oferecer mais de 99,9% de durabilidade e o serviço de armazenamento em nuvem mais suportado disponível. O AWS-S3 será responsável por armazenar o vídeo carregado (não baseado na Web) e, depois de convertê-lo em um formato baseado na Web, ele também será armazenado no AWS-S3. Devemos ter em mente que nosso plano é gratuito, portanto, a quantidade de dados será limitada, embora seja suficiente para os propósitos deste projeto.


* Zencoder:
O processo de conversão do aplicativo é tratado pela API do site http://zencoder.com. O vídeo armazenado no AWS-S3 será enviado ao zencoder, convertido para um formato compatível com aplicação WEB e armazenado novamente no AWS-S3 para que possa ser reproduzido no navegador.

* Java:
A linguagem de programação usada foi java v1.8.0_162. Além disso, o Spring Boot Framework v1.5.10 e o Apache Maven v3.3.9 também foram usados, por isso é mais conveniente e fácil instalá-lo e executá-lo em outros computadores.



### Instalando

Em primeiro lugar, verifique se você tem as ferramentas listadas na seção Tecnologias utilizadas. O uso do framework permite uma fácil instalação das dependências. Para fazer isso, digite em um terminal (para todos os comandos a seguir, você precisa estar no diretório raiz do projeto):

*************
mvn install;

mvn package;
*************

Caso você queira executar os testes unitários para verificar a implementação, entre em um terminal com o comando:

*************
mvn test;
*************

Em seguida, depois que o .jar foi construído, você poderá executá-lo localmente para testar a aplicação. Para fazer isso, digite em um terminal:

*************
java -jar target/conversorvideo-0.1.0.jar;
*************

Em seguida, a aplicação web estará disponível em: http://localhost:8080.

Se você deseja implantar sua própria versão deste aplicativo em uma plataforma Heroku na nuvem, sinta-se à vontade para seguir as instruções em:

https://devcenter.heroku.com/articles/deploying-java


