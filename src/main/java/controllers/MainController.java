package videoconverter.controllers;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;
import videoconverter.storage.StorageFileNotFoundException;
import videoconverter.storage.StorageService;
import videoconverter.encoding.ConvertService;
import org.json.JSONObject;

/* The controller will take care of the routing pages.

It provides the functionality of uploading through a POST request a
file into the storage service, it initiates an encoding job with
the encoding service and allows the user to watch a video from a url
from the storage service.
*/
@Controller
public class MainController {

    private final StorageService storageAWSS3;
    private final ConvertService encodingZenCoder;

    @Autowired
    public MainController(StorageService storageAWSS3, ConvertService encodingZenCoder) {
        this.storageAWSS3 = storageAWSS3;
        this.encodingZenCoder = encodingZenCoder;
    }

    /* Make sure the upload page will be reached if the user enter with simple '/' url */
    @GetMapping("/")
    public String initialAccess() {
        return "redirect:/upload";
    }

    /* Make sure the upload page will be reached if the user enter with simple '/' url */
/*    @GetMapping("/error")
    public String initial() {
        return "redirect:/upload";
    }*/


    /* Regular upload page */ 
    @GetMapping("/upload")
    public String uploadFile() {
        return "upload";
    }

    /* When the upload page has been called, handle the request to store on AWS-S3, and call the encoding view/page.  */
    @PostMapping("/encode")
    public String handleFileUpload(
        @RequestParam("file") MultipartFile file,
        Model model)
    {
        try {
            /* Call function to Store the files passed on AWS-S3. */
            storageAWSS3.storeFile(file, "input");
            String input_Name = file.getOriginalFilename();
            String output_Name = FilenameUtils.removeExtension(input_Name) + ".webm";

            /* Handle the enconding process */
            String response = encodingZenCoder.encodeFile(
              input_Name, "input",
              output_Name, "output"
            );

            /* Add the response attributes to our model. */
            JSONObject response_JSON = new JSONObject(response);
            model.addAttribute("input_id", response_JSON.get("input_id"));
            model.addAttribute("output_id", response_JSON.get("output_id"));
            model.addAttribute("output_link", "/watch/" + output_Name);
            model.addAttribute("zencoder_key", response_JSON.get("zencoder_key"));

            return "encoding";
        } catch(Exception e) {
            return "upload";
        }
    }

    /* Call the watch view/page so the user can watch the video in web-based format. */
    @GetMapping("/watch/{fileName:.+}")
    public String watchFile(@PathVariable String fileName, Model model) {
        String link = storageAWSS3.returnPathAWSS3(fileName, "output");
        model.addAttribute("source", link);
        return "watch";
    }

}
