package app.upload.backend.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.model.S3Object;

import app.upload.backend.Constants;
import app.upload.backend.service.S3UploadService;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class APIController implements Constants{

  @Autowired
  S3UploadService uploadService;

  @PostMapping("/api/upload")
  public ResponseEntity<String> uploadToS3(@RequestParam("image") MultipartFile data,
     @RequestParam("fileName") String fileName) {
    // multiplied by 0.00000095367432 to get size in MB, originally returns size in
    // bytes in binary
    System.out.println(data.getSize() * 0.00000095367432);
    try {
      uploadService.uploadToS3(data,fileName);
      return new ResponseEntity<String>(FOLDER_PATH.formatted(fileName), HttpStatus.OK);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return new ResponseEntity<String>("NOT OK", HttpStatus.NO_CONTENT);

  }

}
