package app.upload.backend.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class APIController {
  @PostMapping("/api/upload")
  public ResponseEntity<String> uploadToS3(@RequestBody String entity) {
      
      return new ResponseEntity<String>("OK", HttpStatus.OK);
  }
  
}
