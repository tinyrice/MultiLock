package com.appsforwearable.multilock;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
/**
 * Created by Rice on 4/20/2015.
 */

/**
 * AES Coder<br/>
 * secret key length:   128bit, default:    128 bit<br/>
 * mode:    ECB/CBC/PCBC/CTR/CTS/CFB/CFB8 to CFB128/OFB/OBF8 to OFB128<br/>
 * padding: Nopadding/PKCS5Padding/ISO10126Padding/
 *
 */


public class CryptoModule {

    private static final String KEY_ALGORITHM = "AES";

    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";

    /**
     * initial key
     *
     * @return byte[] key
     * @throws Exception
     */

    public CryptoModule()
    {

    }

    public static byte[] initSecretKey() {
        //return KeyGenerator
        KeyGenerator kg = null;
        try {
            kg = KeyGenerator.getInstance(KEY_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return new byte[0];
        }

        //AES 128 bit key
        kg.init(128);
        //generate a key
        SecretKey  secretKey = kg.generateKey();
        return secretKey.getEncoded();
    }


    public static Key toKey(byte[] key){
        //change to binary key
        return new SecretKeySpec(key, KEY_ALGORITHM);
    }


    public static byte[] encrypt(byte[] data,Key key) throws Exception{
        Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(data);
    }


    public static byte[] decrypt(byte[] data,Key key) throws Exception{
        Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(data);
    }

    public static String  showByteArray(byte[] data){
        if(null == data){
            return null;
        }
        StringBuilder sb = new StringBuilder("{");
        for(byte b:data){
            sb.append(b).append(",");
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append("}");
        return sb.toString();
    }

    public static void main(String[] args) throws Exception {
        byte[] key = initSecretKey();
        System.out.println("key:"+showByteArray(key));

        Key k = toKey(key);

        String data ="AES data";
        System.out.println("data before encryption: string:"+data);
        System.out.println("data before encryption: byte[]:"+showByteArray(data.getBytes()));
        System.out.println();
        byte[] encryptData = encrypt(data.getBytes(), k);
        System.out.println("data after encryption: byte[]:"+showByteArray(encryptData));
        System.out.println();
        byte[] decryptData = decrypt(encryptData, k);
        System.out.println("data after decryption: byte[]:"+showByteArray(decryptData));
        System.out.println("data after decryption: string:"+new String(decryptData));

    }

}
