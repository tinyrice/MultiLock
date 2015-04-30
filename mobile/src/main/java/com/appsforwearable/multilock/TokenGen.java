package com.appsforwearable.multilock;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.Key;
/**
 * Created by Xiewen on 4/30/2015.
 */
public class TokenGen extends CryptoModule{
    public String ENCODING_FORMAT = "ISO-8859-1";
    public byte[] secretKey;
    public byte[] msgAsbyteArray;
    public byte[] encryptedData;
    public int token;

    public TokenGen(String ENCODING_FORMAT){
        this.ENCODING_FORMAT = ENCODING_FORMAT;
        this.secretKey = super.initSecretKey();
//        System.out.println("Key at constructor = "+showByteArray(secretKey));
//        this.msgAsbyteArray = (counter+"").getBytes();
//        Log.d("TokenGen","Key= "+super.showByteArray(secretKey));

    }


    public TokenGen(String msg, String ENCODING_FORMAT) throws UnsupportedEncodingException {
        this.ENCODING_FORMAT = ENCODING_FORMAT;
        this.secretKey = super.initSecretKey();
        this.msgAsbyteArray = msg.getBytes(ENCODING_FORMAT);

    }

    public TokenGen(int counter, String ENCODING_FORMAT) throws UnsupportedEncodingException {
        this.ENCODING_FORMAT = ENCODING_FORMAT;
        this.secretKey = super.initSecretKey();
        this.msgAsbyteArray = (counter + "").getBytes(ENCODING_FORMAT);

    }


    public TokenGen(String secretKeyAsString, String msg, String ENCODING_FORMAT) throws UnsupportedEncodingException {
        this.ENCODING_FORMAT = ENCODING_FORMAT;

        this.msgAsbyteArray = msg.getBytes(ENCODING_FORMAT);
        this.secretKey = secretKeyAsString.getBytes(ENCODING_FORMAT);
    }

    public TokenGen(String secretKeyAsString, int counter, String ENCODING_FORMAT) throws UnsupportedEncodingException {
        this.ENCODING_FORMAT = ENCODING_FORMAT;

        this.msgAsbyteArray = (counter + "").getBytes(ENCODING_FORMAT);

        this.secretKey = secretKeyAsString.getBytes(ENCODING_FORMAT);
    }

    public byte[] genToken() throws Exception {
        Key k = toKey(secretKey);
//        Log.d("TokenGen",showByteArray(msgAsbyteArray));
//        System.out.println("encrypt class " + showByteArray(msgAsbyteArray));
        this.encryptedData = encrypt(msgAsbyteArray, k);
        return encryptedData;
    }

    public int getToken() throws Exception {
        this.genToken();
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

    public String getkey() throws UnsupportedEncodingException {
        System.out.println("getkey class " + showByteArray(secretKey));
        String keyAs = new String(secretKey, ENCODING_FORMAT);
//        System.out.println("getkey class " + showByteArray(keyAs.getBytes(ENCODING_FORMAT)));
        return keyAs;
    }

    public String showKey() {
        return super.showByteArray(secretKey);
    }

    public String showEncryptedData() {
        return super.showByteArray(encryptedData);
    }

    public String showToken() {
        return token + "";
    }

    public static void main(String[] args) throws Exception {
        String ENCODING_FORMAT = "ISO-8859-1";
        TokenGen mTokenGen = new TokenGen(100, ENCODING_FORMAT);
        mTokenGen.getToken();
        System.out.println(mTokenGen.showKey());
        System.out.println(showByteArray(mTokenGen.secretKey));
        System.out.println(mTokenGen.showToken());
        System.out.println(mTokenGen.token);

        String keyAS = new TokenGen(ENCODING_FORMAT).getkey();
        System.out.println(showByteArray(keyAS.getBytes(ENCODING_FORMAT)));
        TokenGen mTokenGen2 = new TokenGen(keyAS, 100, ENCODING_FORMAT);
        System.out.println(mTokenGen2.showKey());
        System.out.println(mTokenGen2.getToken());


    }

}
