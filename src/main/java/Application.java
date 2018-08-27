package conversorvideo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import conversorvideo.storage.StorageProperties;
import conversorvideo.storage.StorageService;

/*
Main application file.

It runs the application and cunstructs the Logger.
*/
@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class Application {
    public static final Logger logger = LogManager.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner init(StorageService storageAWSS3) {
        return (args) -> {
            storageAWSS3.init();
        };
    }
}
