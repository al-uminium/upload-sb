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

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class APIController {
  @Autowired
  AmazonS3 s3;

  @PostMapping("/api/upload")
  public ResponseEntity<String> uploadToS3(@RequestParam("image") MultipartFile data,
      @RequestParam("fileName") String fileName) {
    // multiplied by 0.00000095367432 to get size in MB, originally returns size in
    // bytes in binary
    System.out.println(data.getSize() * 0.00000095367432);
    PutObjectRequest putReq;
    try {
      ObjectMetadata metadata = new ObjectMetadata();
      metadata.setContentLength(data.getSize());
      metadata.setContentType(data.getContentType());
      putReq = new PutObjectRequest("alzj-bucket", "test/%s".formatted(fileName), data.getInputStream(), metadata);
      putReq = putReq.withCannedAcl(CannedAccessControlList.PublicRead);
      s3.putObject(putReq);
      return new ResponseEntity<String>("OK", HttpStatus.OK);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return new ResponseEntity<String>("NOT OK", HttpStatus.NO_CONTENT);

  }

}
