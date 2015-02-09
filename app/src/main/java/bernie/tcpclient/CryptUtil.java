package bernie.tcpclient;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


public class CryptUtil {

    SecretKey key;
    IvParameterSpec ivParameterSpec;

    public CryptUtil(String keyString, String ivpString){
        ivParameterSpec = new IvParameterSpec(ivpString.getBytes());
        byte[] keyBytes = keyString.getBytes();
        try {
            ivParameterSpec = new IvParameterSpec(ivpString.getBytes("UTF-8"));
            keyBytes = keyString.getBytes("UTF-8");
        }
        catch(IOException ioe){

        }
        this.key = new SecretKeySpec(keyBytes,"AES");
    }

    public byte[] encrypt(String s){

        try	{
            // Encrypt cipher
            Cipher encryptCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            try {
                encryptCipher.init(Cipher.ENCRYPT_MODE, key,ivParameterSpec);
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            }
            // Encrypt
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            CipherOutputStream cipherOutputStream = new CipherOutputStream(outputStream, encryptCipher);
            cipherOutputStream.write(s.getBytes("UTF-8"));
            cipherOutputStream.flush();
            cipherOutputStream.close();

            ivParameterSpec = new IvParameterSpec(encryptCipher.getIV());
            return outputStream.toByteArray();
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public String encryptInUTF8(String s){
        byte[] encrB = encrypt(s);
        String ret ="";
        try {
            ret = new String(encrB,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            System.out.println("UTF-8 does not exist!");
        }
        return ret;
    }

    public String decrypt(byte[] msg){
        try{
            Cipher decr = Cipher.getInstance("AES/CBC/PKCS5Padding");
            decr.init(Cipher.DECRYPT_MODE, key, ivParameterSpec);

            ByteArrayInputStream in = new ByteArrayInputStream(msg);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            CipherInputStream ciphIn = new CipherInputStream(in, decr);
            byte[] buf = new byte[32];
            int bytesRead;
            while ((bytesRead = ciphIn.read(buf)) >= 0) {
                out.write(buf, 0, bytesRead);
            }
            ciphIn.close();
            out.close();
            in.close();

            return new String(out.toByteArray());

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
