package com.odea.services;

/**
 * User: pbergonzi
 * Date: 11/10/12
 * Time: 10:52
 */
public interface EncodingService {
    public String encode(String plainText);

    public byte[] encode(byte[] plainByteArray);
}
