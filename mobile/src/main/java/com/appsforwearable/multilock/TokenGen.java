package com.appsforwearable.multilock;
import java.security.Key;
/**
 * Created by Xiewen on 4/30/2015.
 */
public class TokenGen extends CryptoModule{
    String ENCODING_FORMAT;
    byte[] secretKey;
    byte[] msgAsbyteArray ;
    byte[] encryptedData;
    int token = Integer.parseInt(null);

    public TokenGen(String ENCODING_FORMAT){
        secretKey = super.initSecretKey();

    }

    public TokenGen(byte[] secretKey, String msg,String ENCODING_FORMAT){

        this.msgAsbyteArray = (msg+"").getBytes();
        this.secretKey = secretKey;
    }

    public byte[] encrypt () throws Exception {
        Key k = super.toKey(secretKey);
         encryptedData=super.encrypt(msgAsbyteArray,k);
        return encryptedData;
    }

    public int getToken() throws Exception {
        this.encrypt();
        String encryptedDataAsString = new String(encryptedData, ENCODING_FORMAT);
        token = Math.abs(encryptedDataAsString.hashCode());
        return token;
    }

    public boolean verifyToken (int anotherToken){
        boolean pass = false;
        if (anotherToken == token){
            pass = true;
        }
        return pass;
    }



}
