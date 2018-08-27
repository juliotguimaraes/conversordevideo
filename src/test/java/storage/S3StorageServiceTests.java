package conversorvideo.storage;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.core.io.Resource;
import java.io.InputStream;
import org.apache.commons.io.IOUtils;
import java.nio.charset.StandardCharsets;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import java.io.IOException;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.*;

public class S3StorageServiceTests {

    private StorageProperties properties = new StorageProperties();
    private S3StorageProperties s3_properties = new S3StorageProperties();
    private S3StorageService service;

    @Before
    public void init() {
        properties.setLocation("https://s3.us-east-2.amazonaws.com");
        s3_properties.setBucketName("videocvrt");
        s3_properties.setKey(System.getenv("AWS_ACCESS_KEY_ID"));
        s3_properties.setPrivateKey(System.getenv("AWS_SECRET_ACCESS_KEY"));
        s3_properties.setRegion("us-east-2");
        service = new S3StorageService(properties, s3_properties);
        service.init();
    }

    @Test
    public void testLoad() throws IOException {
        Resource file = service.loadVideo("test.txt", "test_input");
        InputStream output_stream = file.getInputStream();
        String output = IOUtils.toString(output_stream, StandardCharsets.UTF_8);
        assertTrue("Output not equal to test file", output.equals("test_input"));
    }



    @Test
    public void testsetKeyNull() throws IOException {
        s3_properties.setKey(System.getenv("AWS_ACCESS_KEY_ID"));
        assertFalse("*** TEST SET KEY != NULL FAIL *** Insted of getting from ENV var AWS_ACCESS_KEY_ID,  key was assigned to null", s3_properties.getKey().equals(null));
    }

    @Test
    public void testsetPrivateKeyNull() throws IOException {
        s3_properties.setPrivateKey(System.getenv("AWS_SECRET_ACCESS_KEY"));
        assertFalse("*** TEST SET SECRET_KEY != NULL FAIL *** Insted of getting from ENV var AWS_SECRET_ACCESS_KEY, private_key was assigned to null", s3_properties.getPrivateKey().equals(null));
    }

       @Test
    public void testgetKey() throws IOException {
        s3_properties.setKey(System.getenv("AWS_ACCESS_KEY_ID"));
        String key = System.getenv("AWS_ACCESS_KEY_ID");
        assertTrue("*** TEST GET KEY FAIL *** ENV var AWS_ACCESS_KEY_ID wasn't set to s3.Prop.key correctly", key.equals(s3_properties.getKey()));        
    }

       @Test
    public void testgetPrivateKey() throws IOException {
        s3_properties.setPrivateKey(System.getenv("AWS_SECRET_ACCESS_KEY"));
        String secret_key = System.getenv("AWS_SECRET_ACCESS_KEY");
        assertTrue("*** TEST SET SECRET_KEY FAIL *** AWS_SECRET_ACCESS_KEY wasn't set to s3.Prop.secret_key correctly", secret_key.equals(s3_properties.getPrivateKey()));   
    }

    @Test
    public void testPath() {

        String path = service.returnPathAWSS3("sample.dv", "test_input");
        assertEquals(path, "https://s3.us-east-2.amazonaws.com/videocvrt/test_input/sample.dv");
    }
/*
        @Test
    public void testStore() {
      MockMultipartFile mockMultipartFile = new MockMultipartFile(
       "test.txt",                //filename
       "test_input".getBytes()); //content
       service.storeFile(mockMultipartFile, "test_ouput");    
    }
*/

}
