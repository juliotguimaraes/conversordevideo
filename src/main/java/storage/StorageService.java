package conversorvideo.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

/*
Interface para o serviço de armazenamento,

Essa interface declara funções necessárias para um serviço de armazenamento.
Esse serviço deve poder, a partir de um arquivo MultipartFile,
armazene o arquivo inteiro em seu meio de armazenamento.
Ele deve ser capaz de carregar um arquivo e retornar o caminho de um arquivo dentro do
meio de armazenamento.
*/
public interface StorageService {

    void init();

    void storeFile(MultipartFile file, String path);

    Resource loadVideo(String filename, String local_path);

    String returnPathAWSS3(String filename, String local_path);

}
