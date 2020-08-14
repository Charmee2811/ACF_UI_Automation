package cf_Util;

import java.io.File;
import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.glacier.model.InitiateMultipartUploadRequest;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.util.StringUtils;
import com.bettercloud.vault.SslConfig;
import com.bettercloud.vault.Vault;
import com.bettercloud.vault.VaultConfig;
import com.bettercloud.vault.VaultException;
import com.bettercloud.vault.response.*;

import cf.Base.TestBase;

public class awsReportsUplod extends TestBase {
	
	   final static String bucketName     = "acf-ui-automation";
       			
	    public static String keyName;                    
	    public static String keySeceret;       
        public static String filePathReports = System.getProperty("user.dir") + "\\CustomReports\\backup";
        public static String filePathScrenShots = System.getProperty("user.dir") + "\\ZipScreenShots";
        public static String repName ; 
        public static String screenShotName;
        public static  Boolean isReport;
        public static  Boolean isScreenShot;
      
    
       @Test(priority =1)
       public static void uploS3Bucket() throws IOException, VaultException
        {    
    	   
           //final VaultConfig config = new VaultConfig().address("https://10.4.2.16:8200").token("e97c6601-2986-2acc-83ef-e5e134d6fe7e").build();
                                
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
           TestLog.info("Reading S3 Bucket Keys from Vault");
           final Map<String, String> value = vault.logical().read("secret/aws-clients/acf-devtest/shared/automation/ui").getData();
         	  
           String keyName = value.get("s3_access_key_id"); 
           String keySeceret = value.get("s3_secret_access_key");
               	  
    	   screenShotName =  getFileName(filePathScrenShots);
    	   repName = getFileName(filePathReports);
    	  
    	   
    	   String uploadFileScrenShotsPath = filePathScrenShots + "\\" + screenShotName;
    	   String uploadFileReportsPath = filePathReports + "\\" + repName;
    	   
    	   TestLog.info("Connecting to s3 bucket \n");
    	   
                AWSCredentials credentials = new BasicAWSCredentials(keyName, keySeceret);
                AmazonS3 s3client = new AmazonS3Client(credentials);
             
            
                try {
                	TestLog.info("Uploading a new object to S3 from a file\n");
                      File fileRepo = new File(uploadFileReportsPath);
                      File fileSC = new File(uploadFileScrenShotsPath);
                     
                      s3client.putObject(new PutObjectRequest(bucketName,repName, fileRepo));
                      s3client.putObject(new PutObjectRequest(bucketName,screenShotName, fileSC));
                     
                    TestLog.info("Reports Screenshots uploded\n");
                 } catch (AmazonServiceException ase) 
                   {
                	 TestLog.info("Caught an AmazonServiceException,Means UR request was made to Amazon S3, but was rejected with an error response");
                           
                    TestLog.info("Error Message:    " + ase.getMessage());
                    TestLog.info("HTTP Status Code: " + ase.getStatusCode());
                    TestLog.info("AWS Error Code:   " + ase.getErrorCode());
                    TestLog.info("Error Type:       " + ase.getErrorType());
                    TestLog.info("Request ID:       " + ase.getRequestId());
                } catch (AmazonClientException ace) 
                 {
                	TestLog.info("Caught an AmazonClientException, Not able to connect as not being able to access the network");
                	TestLog.info("Error Message: " + ace.getMessage());
                 }
                
       //for comparing backup Reports
                TestLog.info("listing objects for backup reports");
       ObjectListing objectListing1 = s3client.listObjects(new ListObjectsRequest() 
    		                         .withBucketName(bucketName)
    		                         .withPrefix("bck"));
                   
       for (S3ObjectSummary objectSummary : objectListing1.getObjectSummaries())
       { 
           if (objectSummary.getKey().equals(repName))
           {
        	   isReport = true;
        	   TestLog.info("Latest reports uploaded on s3 bucket" + objectSummary.getKey());   
           }      		                          
        }
           
       //for comparing screenshots
        TestLog.info("listing objects for screenshots");
       ObjectListing objectListing2 = s3client.listObjects(new ListObjectsRequest() 
               .withBucketName(bucketName)
               .withPrefix("Scr"));

       for (S3ObjectSummary objectSummary : objectListing2.getObjectSummaries())
        { 
          if (objectSummary.getKey().equals(screenShotName))
           {
        	  TestLog.info("Latest Zip for screenshots uploaded on S3 bucket " + objectSummary.getKey());
             isScreenShot = true;
           }  
       }
       
       //Verify both reports are uploaded
       if (isReport && isScreenShot)
       {
    	   TestLog.info("reports and Schrrenshots uploaded :- Verified");
    	   awsTerminateVM.stopVM();
       }
       else
    	   TestLog.info("reports and Schrrenshots are NOT uploaded");  		   
   }
}

