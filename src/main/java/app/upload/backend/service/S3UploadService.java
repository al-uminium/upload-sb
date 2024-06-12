package app.upload.backend.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

import app.upload.backend.Constants;

@Service
public class S3UploadService implements Constants{
  @Autowired AmazonS3 s3;
  
  public void uploadToS3(MultipartFile data, String fileName) throws IOException{
    PutObjectRequest putReq;
    ObjectMetadata metadata = new ObjectMetadata();
    metadata.setContentLength(data.getSize());
    metadata.setContentType(data.getContentType());
    putReq = new PutObjectRequest(BUCKET_NAME, FOLDER_PATH.formatted(fileName), data.getInputStream(), metadata);
    putReq = putReq.withCannedAcl(CannedAccessControlList.PublicRead);
    s3.putObject(putReq);
  }

  public S3Object getObjectFromS3(String fileName){
    GetObjectRequest getReq = new GetObjectRequest(BUCKET_NAME, FOLDER_PATH.formatted(fileName));
    return s3.getObject(getReq);
  }
}
