# Conversor de Vídeo

Projeto de seleção de novo funcionário, desenvolvendo um conversor de vídeo para a SambaTech.

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


