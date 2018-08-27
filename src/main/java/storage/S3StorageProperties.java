package conversorvideo.storage;

import org.springframework.boot.context.properties.ConfigurationProperties;

/*
Propriedades para o serviço de armazenamento do S3.

Ele armazena o nome do bucket S3, bem como a chave pública e privada para
acessando S3 e a região selecionada para o bucket.
*/
@ConfigurationProperties("storage.s3")
public class S3StorageProperties {

  private String bucket_name = "videocvrt";
  private String key = System.getenv("AWS_ACCESS_KEY_ID");
  private String private_key = System.getenv("AWS_SECRET_ACCESS_KEY");
  private String region = "us-east-2";

  public String getRegion() {
    return this.region;
  }
  public void setRegion(String name) {
    this.region = name;
  }

  public String getKey() {
    return this.key;
  }
  public void setKey(String name) {
    this.key = name;
  }

  public String getPrivateKey() {
    return this.private_key;
  }

  public void setPrivateKey(String name) {
    this.private_key = name;
  }

  public String getBucketName() {
    return this.bucket_name;
  }

  public void setBucketName(String name) {
    this.bucket_name = name;
  }

}
