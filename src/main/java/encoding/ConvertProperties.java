package conversorvideo.encoding;

import org.springframework.boot.context.properties.ConfigurationProperties;

/*
Propriedades para o serviço Zencoder.

Ele armazena a chave de acesso completo do Zencoder, usada dentro do Aplicativo
para criar trabalhos de codificação.
A tecla de leitura do Zencoder, a ser passada para o final da fonte para monitorar
progresso de codificação.
Por fim, o URL de trabalho do Zencoder da API do Zencoder.
*/
@ConfigurationProperties("encoding.zencoder")
public class ConvertProperties {

  private String full_key = "4e50c38f9c9b1d7724e4296b775b2ce5";
  private String read_key = "5deca53e10a60d64ae22ba0ecfac49f8";
  private String zencoder_job_url = "https://app.zencoder.com/api/v2/jobs";

  public String getConvertUrl() {
    return this.zencoder_job_url;
  } 
  public void setZencoderJobUrl(String name) {
    this.zencoder_job_url = name;
  }

  public String getFullKey() {
    return this.full_key;
  }
  public void setFullKey(String name) {
    this.full_key = name;
  }

  public String getReadKey() {
    return this.read_key;
  }

  public void setReadKey(String name) {
    this.read_key = name;
  }
}
