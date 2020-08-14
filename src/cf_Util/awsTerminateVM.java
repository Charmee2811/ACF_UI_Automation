package cf_Util;

import java.io.File;
import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.StopInstancesRequest;
import com.amazonaws.services.ec2.model.TerminateInstancesRequest;
import com.amazonaws.services.ec2.model.TerminateInstancesResult;
import com.amazonaws.services.glacier.model.InitiateMultipartUploadRequest;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.util.StringUtils;
import com.bettercloud.vault.SslConfig;
import com.bettercloud.vault.Vault;
import com.bettercloud.vault.VaultConfig;
import com.bettercloud.vault.VaultException;
import com.bettercloud.vault.response.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


import cf.Base.TestBase;
public class awsTerminateVM extends TestBase {

	public static String output;
	
    
	@SuppressWarnings("deprecation")
	@Test(priority=1)
	public static void  stopVM() throws VaultException
	{
		                      
         final VaultConfig configSSL =
       		    new VaultConfig()
       		        .address(vaultIp)      //vault IP         
       		        .token(vaultRootToken)  //root token
       		        .openTimeout(5)                                 
       		        .readTimeout(30)                                
       		        .sslConfig(new SslConfig().verify(false).build())   //skyping ssl certifications          
       		        .build();
         
         final Vault vault = new Vault(configSSL);
          
         // Read operation
         TestLog.info("Reading AWS Keys from Vault");
         final Map<String, String> value = vault.logical().read("secret/aws-clients/acf-devtest/shared/automation/ui").getData();
       		 
         String keyName = value.get("access_key_id"); 
         String keySeceret = value.get("secret_access_key");
           
         TestLog.info("Setting AWS creentials");
  
              AWSCredentials credentials = new BasicAWSCredentials(keyName, keySeceret);
              AmazonEC2 ec2 = new AmazonEC2Client(credentials);
                        
		  try {

			URL url = new URL("http://169.254.169.254/latest/meta-data/instance-id");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			//conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));

			TestLog.info("Output response from Server : insatnce IP is :- " + output  );
			while ((output = br.readLine()) != null) {
				System.out.println(output);
				
				TestLog.info("Terminating instance  :- " + output);
				
			//stopping the instance	
				StopInstancesRequest request = new StopInstancesRequest().withInstanceIds(output);
				ec2.stopInstances(request);
	       
			}
			conn.disconnect();
		  } catch (MalformedURLException e) {
			e.printStackTrace();
		  } catch (IOException e) {
			e.printStackTrace();
		  }
	}
	
 }
