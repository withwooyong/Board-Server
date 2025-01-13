package com.fastcampus.boardserver.utils;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Log4j2
@Component
public class SHA256Util {

    public static String encryptSHA256(String str) {
        String SHA;
        MessageDigest sh;
        try {
            sh = MessageDigest.getInstance("SHA-256");
            sh.update(str.getBytes());
            byte[] byteData = sh.digest();
            StringBuilder sb = new StringBuilder();
            for (byte byteDatum : byteData) {
                sb.append(Integer.toString((byteDatum & 0xff) + 0x100, 16).substring(1));
            }
            SHA = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("암호화 에러!", e);
        }
        return SHA;
    }
}
