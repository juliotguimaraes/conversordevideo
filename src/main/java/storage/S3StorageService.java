package conversorvideo.storage;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.InputStreamResource;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import conversorvideo.storage.S3StorageProperties;
import conversorvideo.Application;

/*
Serviço de armazenamento que usa o S3 da Amazon como meio de armazenamento.

Ele implementa toda a interface StorageService usando apenas S3, inserindo e
lendo a partir dele. Ele usa ConfigurationProperties para armazenar informações confidenciais
como chave de acesso e região do bucket.
*/
@Service
@EnableConfigurationProperties(S3StorageProperties.class)
public class S3StorageService implements StorageService {

    private final AmazonS3 s3client;
    private final StorageProperties SProp;
	private final S3StorageProperties s3SProp;

    /*
    Construtor de classe.

     Configura o cliente amazon s3 para mais chamadas de interface.
    */
    @Autowired
    public S3StorageService(StorageProperties properties, S3StorageProperties s3Prop) {
        this.SProp = properties;
        this.s3SProp = s3Prop;

        AWSCredentials credentials = new BasicAWSCredentials(
                    this.s3SProp.getKey(),
                    this.s3SProp.getPrivateKey());

        // criar uma conexão de cliente com base em credenciais
        this.s3client = new AmazonS3Client(credentials);
    }

    /*
    Este serviço de armazenamento não requer computação inicial.
    */
    @Override
    public void init() {}

    /*
    Armazene o arquivo MultipartFile no armazenamento S3 da Amazon.

     Ele usa o caminho para resolver a pasta do arquivo dentro do S3
    */
    @Override
    public void storeFile(MultipartFile file, String path) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + filename);
            }
            if (filename.contains("..")) {
                // Esta é uma verificação de segurança
                throw new StorageException(
                        "Cannot store file with relative path outside current directory "
                                + filename);
            }

    		// upload file to s3
    		String fileName = path + "/" + filename;

    		this.s3client.putObject(new PutObjectRequest(this.s3SProp.getBucketName(), fileName,
    				file.getInputStream(), new ObjectMetadata())
                    .withCannedAcl(CannedAccessControlList.PublicRead));
            Application.logger.info("Stored "+fileName+" into S3");
        }
        catch (IOException e) {
            throw new StorageException("Failed to store file " + filename, e);
        }
    }

    /*
    Carregue e retorne como recurso o arquivo dentro da pasta do caminho no S3.
    */
    @Override
    public Resource loadVideo(String filename, String path) {
        S3Object s3_file = this.s3client.getObject(
            this.s3SProp.getBucketName(), path + "/" + filename
        );
        S3ObjectInputStream file_stream = s3_file.getObjectContent();

        Resource resource = new InputStreamResource(file_stream);
        if (resource.exists() || resource.isReadable()) {
            Application.logger.info("Loaded "+path + "/" + filename+" into S3");
            return resource;
        }
        else {
            throw new StorageFileNotFoundException(
                    "Could not read file: " + filename);
        }
    }

    @Override
    /*
  Retorna o caminho S3 para o nome do arquivo e o caminho recebido.
    */
    public String returnPathAWSS3(String filename, String local_path) {
        String root_location = this.SProp.getLocation();
        return root_location + "/" + this.s3SProp.getBucketName() + "/" + local_path + "/" + filename;
    }
}
