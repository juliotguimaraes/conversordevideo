package conversorvideo.encoding;

/*
Interface para o serviço de codificação,

Essa interface declara funções necessárias para um serviço de codificação.
Esse serviço deve poder, a partir de um arquivo, criar uma versão codificada
dele em um caminho especificado com um nome definido.
*/

public interface ConvertService {

    void init();

    String encodeFile(
        String input_filename,
        String input_path,
        String output_filename,
        String output_path
    );
}
