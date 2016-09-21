package com.example.loginsdk.security;

/**
 * Created by mitnick.cheng on 2016/9/20.
 */

import com.example.loginsdk.bean.AppInfo;
import com.example.loginsdk.bean.SLoginRequest;
import com.example.loginsdk.bean.SecurityRequest;
import com.google.gson.Gson;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * DESCoder.java
 *
 * @author Techzero
 * @Email techzero@163.com
 * @Time 2013-12-12 下午2:22:58
 */
public class DESCoder extends Coder{
    /**
     * 加密
     *
     * @param content
     *            待加密内容
     * @param key
     *            加密的密钥
     * @return
     */
    public static String encrypt(String content, String key) {
        try {
            SecureRandom random = new SecureRandom();
            DESKeySpec desKey = new DESKeySpec(key.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(desKey);
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
            byte[] result = cipher.doFinal(content.getBytes());

            return parseByte2HexStr(result);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密
     *
     * @param content
     *            待解密内容
     * @param key
     *            解密的密钥
     * @return
     */
    public static String decrypt(String content, String key) {
        try {
            byte[] contentByte = parseHexStr2Byte(content);
            SecureRandom random = new SecureRandom();
            DESKeySpec desKey = new DESKeySpec(key.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(desKey);
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, securekey, random);
            byte[] result = cipher.doFinal(contentByte);
            return new String(result);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws Exception{
        AppInfo appInfo = new AppInfo();
        appInfo.setAppId("101");
        appInfo.setAppKey("cszb");
        appInfo.setAppSecret("AiR1JAHTWGf+5Y0DfWCKGg==");
        appInfo.setMd5Key("9t70pMVJgCa6CllrA8Dhbg==");
        appInfo.setPublicKey("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCxcGL75WekoM0R/9PmO5kk4PgT+n2Q1NyRviKNnOwLjcBTsYMacFPWW7xrCEB6booLoho83V6UKDIFBfgD4+mmSKS89uPCBRQOCUtNZHRRLaPqMSUG/VLXdlalmWcLC2u5mOvKK57Vfs9q75La0K/NMSoKkrsgqOMAJMSiFhGxkwIDAQAB");
        appInfo.setPrivateKey("MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALFwYvvlZ6SgzRH/0+Y7mSTg+BP6fZDU3JG+Io2c7AuNwFOxgxpwU9ZbvGsIQHpuiguiGjzdXpQoMgUF+APj6aZIpLz248IFFA4JS01kdFEto+oxJQb9Utd2VqWZZwsLa7mY68orntV+z2rvktrQr80xKgqSuyCo4wAkxKIWEbGTAgMBAAECgYEAlE0hffVrnY4EN9IBUgFn6wnYbiJDBmTSgUFP8dpA8xsjrw/DSrqtZ5C+txprgaiX2kc+9LX0kZHS1J37f4ziv4IqEl/j2MdCWwwNgRP6js86DdzsZSJeps0MYHImGeFzbP9ow/LGqReKISh8m/UZ+ED08Sfs/FMUeMOMREehmqkCQQDi57Ues6as8OKnuLIQIkQDsodbMydAbPfYvs2xjY4UtlokCpUBEIDlNin4C1TFd+AqDH1+bGMJZHIGDpG555NlAkEAyDDs+JlHpAqv92hl9WLUs8njdbLmly/ogZ2hueB4b+Bh7M6cB/Q4vnfZC4sJP7ydP6lD3fKQxjPdtSzNyL0tlwJAe7EwLPJmM/SEukWW2/Cx/wn9e5vhE9/TJFavLtEp2OSnMuqJ27Cxc0IZktwqdBMrIgD5EEfpnR8igWbDzgINeQJBALP/ObK0rzOR4HIFzlb6i+Ezz27OcoLRsq6IOxfT+rYs/B9eDfEg57xqKh803hYO0xygZsMzDHUJwXjbVhP99PUCQDxEMfqozrHHQq/P1NEYpEOd4x1wHd6i4MnuqXv3Q9I0MA+UvZGBN/+7SsgwXmR2LIwGduoqslqobqiTi8js41c=");

        System.out.println("-------------客户端加密开始------------");
        SLoginRequest sLoginRequest = new SLoginRequest(appInfo.getAppId(),appInfo.getAppKey(),"18202745852","e10adc3949ba59abbe56e057f20f883e");
        String data = new Gson().toJson(sLoginRequest);
        System.out.println("data："+data);
        String sign = "";
        try {
            sign = RSAUtils.sign(data.getBytes(),appInfo.getPrivateKey());
            System.out.println("sign："+sign);
        }catch (Exception e){
            e.printStackTrace();
        }
        String securityData = DESCoder.encrypt(data,appInfo.getAppSecret());
        System.out.println("securityData："+ securityData);

        SecurityRequest securityRequest = new SecurityRequest(appInfo.getAppId(),sign,securityData);
        String securityStr = new Gson().toJson(securityRequest);
        System.out.println("securityRequest："+ securityStr);
        System.out.println("-------------客户端加密结束------------"+"\n");

        System.out.println("-------------服务端解密开始------------");
        String securityData_temp = (String) securityRequest.getData();
        String data_temp = DESCoder.decrypt(securityData_temp,appInfo.getAppSecret());
        System.out.println("DES解密后:\t" + data_temp);
        boolean status = RSAUtils.verify(data_temp.getBytes(), appInfo.getPublicKey(), sign);
        System.out.println("验证sign:\t" + status);
        System.out.println("-------------服务端解密结束------------");
    }
}

