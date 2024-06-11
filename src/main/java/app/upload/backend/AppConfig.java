package app.upload.backend;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class AppConfig {

  @Value("${aws.accessKey}") 
  private String accessKey;

  @Value("${aws.secretKey}")
  private String secretKey;

  @Value("${aws.serviceEndpoint}")
  private String svcEndpoint;

  @Value("${aws.region}") private String region;

  @Bean
  public AmazonS3 getS3Client() {
    BasicAWSCredentials cred = new BasicAWSCredentials(accessKey, secretKey);
    EndpointConfiguration epConfig = new EndpointConfiguration(svcEndpoint, region);
    return AmazonS3ClientBuilder.standard()
            .withEndpointConfiguration(epConfig)
            .withCredentials(new AWSStaticCredentialsProvider(cred))
            .build();
  }
}
