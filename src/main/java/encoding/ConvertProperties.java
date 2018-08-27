package conversorvideo.encoding;

import org.springframework.boot.context.properties.ConfigurationProperties;

/*
Properties for the Zencoder service.

It stores the Zencoder full access key, used within the Application
to create encoding jobs.
The Zencoder read key, to be passed to the font end in order to monitor
encoding progress.
Lastly, the Zencoder job url from Zencoder API.
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
