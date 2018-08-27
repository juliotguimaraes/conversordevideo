package webencoder.encoding;

import org.junit.Before;
import org.junit.Test;

import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;
import org.json.JSONArray;


import webencoder.encoding.ZencoderProperties;
import webencoder.storage.S3StorageProperties;

import static org.junit.Assert.*;

public class ZencoderS3ServiceTests {

    private ZencoderProperties zencoder_properties = new ZencoderProperties();
    private S3StorageProperties s3_properties = new S3StorageProperties();
    private ZencoderS3Service service;


    @Before
    public void init() {
        s3_properties.setRegion("us-east-2");
        zencoder_properties.setFullKey("4e50c38f9c9b1d7724e4296b775b2ce5");
        zencoder_properties.setReadKey("5deca53e10a60d64ae22ba0ecfac49f8");
        zencoder_properties.setZencoderJobUrl("https://app.zencoder.com/api/v2/jobs");
        this.service = new ZencoderS3Service(zencoder_properties, s3_properties);
        this.service.init();
    }

    @Test
    public void encodeRequestReceived() {
        try {
            String response = service.encode("sample.dv", "test_input",
                                             "sample.webm", "test_output");
            JSONObject r_json = new JSONObject(response);

            assertTrue(r_json.has("input_id"));
            assertTrue(r_json.has("output_id"));
            assertTrue(r_json.has("output_url"));
            assertEquals(r_json.get("zencoder_key"), zencoder_properties.getReadKey());
        } catch (Exception e){
            fail(e.getMessage());
        }
    }

}
