package com.example.demo.utils;

import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.MessageDigest;

/**
 * 단방향 암호화 알고리즘인 SHA_256 암호화를 지원하는 클래스
 */
@Component("SHA_256")
public class SHA_256 {
    /**
     * 평문을 SHA_256으로 단방향 암호화 한다
     *
     * @param planText the plan text
     * @return 암호화된 문자열
     * @throws UnsupportedEncodingException 키값의 길이가 16이하일 경우 발생!!
     */
    public String encrypt(String planText) {
		
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(planText.getBytes(Charset.forName("UTF-8")));
            byte byteData[] = md.digest();

            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }

            StringBuffer hexString = new StringBuffer();
            for (int i=0;i<byteData.length;i++) {
                String hex=Integer.toHexString(0xff & byteData[i]);
                if(hex.length()==1){
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
	
}
