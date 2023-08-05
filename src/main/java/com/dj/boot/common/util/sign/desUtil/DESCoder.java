package com.dj.boot.common.util.sign.desUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.Key;


public class DESCoder {
    private static Logger logger= LoggerFactory.getLogger(DESCoder.class);
    private static final String ALGORITHM="des";

    public static String encryption(String value, String key){
        try {
            Key desKey = getKey(key);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, desKey);
            return CoderUtil.encryptBASE64(cipher.doFinal(value.getBytes()));
        }catch (Exception e){
            logger.error("加密失败 e:{}",e);
            return null;
        }
    }

    public static String decryption(String value, String key) {
        try {
            Key desKey = getKey(key);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, desKey);
            return new String(cipher.doFinal(CoderUtil.decryptBASE64(value)));
        }catch (Exception e){
            logger.error("解密失败 e:{}",e);
            return null;
        }
    }

    private static Key getKey(String key) throws Exception {
        DESKeySpec desKey=new DESKeySpec(CoderUtil.decryptBASE64(key)) ;
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
        SecretKey secretKey = keyFactory.generateSecret(desKey);
        return secretKey;
    }
}