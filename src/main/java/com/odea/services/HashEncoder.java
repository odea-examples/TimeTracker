package com.odea.services;

import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * User: pbergonzi
 * Date: 11/10/12
 * Time: 10:53
 */
@Service
public class HashEncoder implements EncodingService {
    public static final String ALGORITHM = "MD5";
    public static final String CHARSET = "UTF-8";

    @Override
    public String encode(String plainText) {
        try {
            byte[] stringBytes = plainText.getBytes(CHARSET);
            return new String(this.encode(stringBytes), CHARSET);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public byte[] encode(byte[] plainByteArray) {
        try {
            MessageDigest md = MessageDigest.getInstance(ALGORITHM);
            return md.digest(plainByteArray);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
