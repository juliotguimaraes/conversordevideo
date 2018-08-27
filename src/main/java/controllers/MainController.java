package conversorvideo.controllers;

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
import conversorvideo.storage.StorageFileNotFoundException;
import conversorvideo.storage.StorageService;
import conversorvideo.encoding.ConvertService;
import org.json.JSONObject;

/* O controlador cuidará das páginas de roteamento.

Ele fornece a funcionalidade de fazer upload por meio de uma solicitação POST
arquivo no serviço de armazenamento, ele inicia um trabalho de codificação com
o serviço de codificação e permite ao usuário assistir a um vídeo de um URL
do serviço de armazenamento.
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

    /* Certifique-se de que a página de upload será alcançada se o usuário digitar com um simples URL '/' */
    @GetMapping("/")
    public String initialAccess() {
        return "redirect:/upload";
    }

    
    /* Pagina de upload regular */ 
    @GetMapping("/upload")
    public String uploadFile() {
        return "upload";
    }

    /* Quando a página de upload for chamada, manipule a solicitação para armazenar no AWS-S3 e chame a visualização / página de codificação. */
    @PostMapping("/encode")
    public String handleFileUpload(
        @RequestParam("file") MultipartFile file,
        Model model)
    {
        try {
            /* Função de chamada para armazenar os arquivos transmitidos no AWS-S3. */
            storageAWSS3.storeFile(file, "input");
            String input_Name = file.getOriginalFilename();
            String output_Name = FilenameUtils.removeExtension(input_Name) + ".webm";

            /* Lidar com o processo de codificação */
            String response = encodingZenCoder.encodeFile(
              input_Name, "input",
              output_Name, "output"
            );

            /* Adicione os atributos de resposta ao nosso modelo. */
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

    /* Chama para a view/page para que o usuário possa assistir ao vídeo em formato baseado na Web.*/
    @GetMapping("/watch/{fileName:.+}")
    public String watchFile(@PathVariable String fileName, Model model) {
        String link = storageAWSS3.returnPathAWSS3(fileName, "output");
        model.addAttribute("source", link);
        return "watch";
    }

}
