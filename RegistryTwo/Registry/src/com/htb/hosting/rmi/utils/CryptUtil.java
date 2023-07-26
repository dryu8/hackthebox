package com.htb.hosting.rmi.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

public class CryptUtil {
  public static CryptUtil instance = new CryptUtil();
  
  Cipher encCipher;
  
  Cipher decCipher;
  
  public static CryptUtil getInstance() {
    return instance;
  }
  
  byte[] salt = new byte[] { -87, -101, -56, 50, 86, 53, -29, 3 };
  
  int iterationCount = 19;
  
  String secretKey = "48gREsTkb1evb3J8UfP7";
  
  public String encrypt(String plainText) {
    try {
      KeySpec keySpec = new PBEKeySpec(this.secretKey.toCharArray(), this.salt, this.iterationCount);
      SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);
      AlgorithmParameterSpec paramSpec = new PBEParameterSpec(this.salt, this.iterationCount);
      this.encCipher = Cipher.getInstance(key.getAlgorithm());
      this.encCipher.init(1, key, paramSpec);
      String charSet = "UTF-8";
      byte[] in = plainText.getBytes("UTF-8");
      byte[] out = this.encCipher.doFinal(in);
      String encStr = Base64.getUrlEncoder().encodeToString(out);
      return encStr;
    } catch (Exception e) {
      throw new RuntimeException(e);
    } 
  }
  
  public String decrypt(String encryptedText) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, IOException {
    KeySpec keySpec = new PBEKeySpec(this.secretKey.toCharArray(), this.salt, this.iterationCount);
    SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);
    AlgorithmParameterSpec paramSpec = new PBEParameterSpec(this.salt, this.iterationCount);
    this.decCipher = Cipher.getInstance(key.getAlgorithm());
    this.decCipher.init(2, key, paramSpec);
    byte[] enc = Base64.getUrlDecoder().decode(encryptedText);
    byte[] utf8 = this.decCipher.doFinal(enc);
    String charSet = "UTF-8";
    return new String(utf8, "UTF-8");
  }
}
