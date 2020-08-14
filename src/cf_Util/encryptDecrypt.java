package cf_Util;
import java.security.Key;

import javax.crypto.Cipher;

import sun.misc.BASE64Encoder;
import sun.misc.BASE64Decoder;

import javax.crypto.spec.SecretKeySpec;

import cf.Base.itemVerification;

public class encryptDecrypt extends itemVerification{

    private static final String ALGO = "AES";
    private static final byte[] keyValue =
            new byte[]{'T', 'h', 'e', 'B', 'e', 's', 't', 'S', 'e', 'c', 'r', 'e', 't', 'K', 'e', 'y'};
      
    public static void mainmethode(String [] args) throws Exception{
    	//String strDataToEncrypt =config_getproperty("adminPasswd");
    	//String encrypteddata= encrypt(strDataToEncrypt);
    	//System.out.println("encrypted data is : "+encrypteddata );
    	String strDataToDecrypt = config_getproperty("EncryptedStringPass");
		String decrpteddata=decrypt(strDataToDecrypt);
    //	System.out.println("decrypted password : "+ decrpteddata);
    	
    }
    public static String encrypt(String data) throws Exception {
        Key key = generateKey();
        Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] encVal = c.doFinal(data.getBytes());
        return new BASE64Encoder().encode(encVal);
    }
    
    public static String decrypt(String encryptedData) throws Exception {
        Key key = generateKey();
        Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.DECRYPT_MODE, key);
        byte[] decordedValue = new BASE64Decoder().decodeBuffer(encryptedData);
        byte[] decValue = c.doFinal(decordedValue);
        return new String(decValue);
    }

    /**
     * Generate a new encryption key.
     */
    private static Key generateKey() throws Exception {
        return new SecretKeySpec(keyValue, ALGO);
    }
}