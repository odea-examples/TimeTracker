package com.odea;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.PBEParametersGenerator;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.generators.PKCS12ParametersGenerator;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.PKCS7Padding;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.junit.Assert;
import org.junit.Test;


public class DSTest {
    int iterationCount = 20;

    @Test
    public void testP() throws Exception, NoSuchAlgorithmException, InvalidKeyException, IOException, SignatureException, IllegalBlockSizeException, InvalidKeySpecException, NoSuchPaddingException, BadPaddingException, InvalidAlgorithmParameterException {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

        KeyPair pair = genKeys();
        PrivateKey priv = pair.getPrivate();
        PublicKey pub = pair.getPublic();

        //firmo algo
        byte[] data = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74};
        byte[] data2 = {65, 66, 67, 68, 69, 70, 71, 72, 73, 75};

        Signature dsa = Signature.getInstance("SHA1withDSA", "BC");
        dsa.initSign(priv);
        dsa.update(data);
        byte[] dSign = dsa.sign();
        //verificacion ok
        Assert.assertTrue(verify(data, dSign, pub));
        //datos alterados
        Assert.assertFalse(verify(data2, dSign, pub));
        //key incorrecta
        KeyPair keyPair = genKeys();
        PublicKey wrongPub = keyPair.getPublic();
        Assert.assertFalse(verify(data, dSign, wrongPub));

        //encrypto y desencripto en AES 256
        String datoSecreto = "algosecreto";
        String passwd = "passwdsecreto";
        byte[] salt = generateSalt();

        byte[] encData = encrypt(datoSecreto.getBytes(), passwd.toCharArray(), salt, iterationCount);
        byte[] plainData = decrypt(encData, passwd.toCharArray(), salt, iterationCount);
        Assert.assertEquals(datoSecreto, new String(plainData));

        //encryptar privatekey
        byte[] salt1 = generateSalt();
        String pass = "pass";
        KeyPair keyPair2 = genKeys();
        PrivateKey pkey = keyPair2.getPrivate();
        KeyFactory keyFactory = KeyFactory.getInstance("DSA", "BC");

        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(pkey.getEncoded());

        byte[] encPkey = encrypt(pkcs8EncodedKeySpec.getEncoded(), pass.toCharArray(), salt1, iterationCount);
        byte[] plainKey = decrypt(encPkey, pass.toCharArray(), salt1, iterationCount);

        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(plainKey);
        PrivateKey pkey2 = keyFactory.generatePrivate(privateKeySpec);

        Assert.assertEquals(pkey, pkey2);

    }
   /*
    @Test
    public void testCert() throws Exception {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

        KeyPair kp = genKeys();
        PrivateKey priv = kp.getPrivate();
        PublicKey pub = kp.getPublic();
        org.bouncycastle.operator.ContentSigner sigGen = new JcaContentSignerBuilder("SHA1withRSA").setProvider("BC").build(priv);
        SubjectPublicKeyInfo subjectPublicKeyInfo = new SubjectPublicKeyInfo(ASN1Sequence.getInstance(pub.getEncoded()));

        Date startDate = new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000);
        Date endDate = new Date(System.currentTimeMillis() + 365 * 24 * 60 * 60 * 1000);

        X509v1CertificateBuilder v1CertGen = new X509v1CertificateBuilder(
                new X500Name("CN=Test"),
                BigInteger.ONE,
                startDate, endDate,
                new X500Name("CN=Test"),
                subjectPublicKeyInfo);

        X509CertificateHolder certHolder = v1CertGen.build(sigGen);

        //verifico ok
        ContentVerifierProvider contentVerifierProvider = new JcaContentVerifierProviderBuilder().setProvider("BC").build(pub);
        Assert.assertTrue(certHolder.isSignatureValid(contentVerifierProvider));

        //pub key erronea
        PublicKey pub2 = genKeys().getPublic();
        ContentVerifierProvider contentVerifierProvider1 = new JcaContentVerifierProviderBuilder().setProvider("BC").build(pub2);
        Assert.assertTrue(certHolder.isSignatureValid(contentVerifierProvider1));

    }  */
    public static KeyPair genKeys() throws NoSuchProviderException, NoSuchAlgorithmException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA", "BC");
        SecureRandom random = new SecureRandom();
        keyGen.initialize(1024, random);
        return keyGen.generateKeyPair();
    }

    public static boolean verify(byte[] data, byte[] signature, PublicKey publicKey) throws SignatureException, InvalidKeyException, NoSuchProviderException, NoSuchAlgorithmException {
        Signature signer = Signature.getInstance("SHA1withDSA", "BC");
        signer.initVerify(publicKey);
        signer.update(data);
        return signer.verify(signature);
    }

    private static byte[] generateSalt() throws NoSuchAlgorithmException {
        byte salt[] = new byte[8];
        SecureRandom saltGen = SecureRandom.getInstance("SHA1PRNG");
        saltGen.nextBytes(salt);
        return salt;
    }

    private static byte[] encrypt(byte[] data, char[] password, byte[] salt, int iterationCount) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, NoSuchProviderException {
        PBEParameterSpec pbeParamSpec = new PBEParameterSpec(salt, iterationCount);

        PBEKeySpec pbeKeySpec = new PBEKeySpec(password);
        SecretKeyFactory keyFac = SecretKeyFactory.getInstance("PBEWithSHA256And256BitAES-CBC-BC", "BC");
        SecretKey pbeKey = keyFac.generateSecret(pbeKeySpec);

        Cipher encryptionCipher = Cipher.getInstance("PBEWithSHA256And256BitAES-CBC-BC", "BC");
        encryptionCipher.init(Cipher.ENCRYPT_MODE, pbeKey, pbeParamSpec);

        return encryptionCipher.doFinal(data);
    }

    private byte[] decryptWithLWCrypto(byte[] cipher, String password, byte[] salt, final int iterationCount) throws Exception {
        PKCS12ParametersGenerator pGen = new PKCS12ParametersGenerator(new SHA256Digest());
        char[] passwordChars = password.toCharArray();
        final byte[] pkcs12PasswordBytes = PBEParametersGenerator.PKCS12PasswordToBytes(passwordChars);
        pGen.init(pkcs12PasswordBytes, salt, iterationCount);
        CBCBlockCipher aesCBC = new CBCBlockCipher(new AESEngine());
        ParametersWithIV aesCBCParams = (ParametersWithIV) pGen.generateDerivedParameters(256, 128);
        aesCBC.init(false, aesCBCParams);
        PaddedBufferedBlockCipher aesCipher = new PaddedBufferedBlockCipher(aesCBC, new PKCS7Padding());
        byte[] plainTemp = new byte[aesCipher.getOutputSize(cipher.length)];
        int offset = aesCipher.processBytes(cipher, 0, cipher.length, plainTemp, 0);
        int last = aesCipher.doFinal(plainTemp, offset);
        final byte[] plain = new byte[offset + last];
        System.arraycopy(plainTemp, 0, plain, 0, plain.length);
        return plain;
    }

    private static byte[] decrypt(final byte[] bytes, final char[] password, final byte[] salt, final int iterationCount) throws DataLengthException, IllegalStateException, InvalidCipherTextException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {

        final PBEParametersGenerator keyGenerator = new PKCS12ParametersGenerator(new SHA256Digest());
        keyGenerator.init(PKCS12ParametersGenerator.PKCS12PasswordToBytes(password), salt, iterationCount);
        final CipherParameters keyParams = keyGenerator.generateDerivedParameters(256, 128);

        final BufferedBlockCipher cipher = new PaddedBufferedBlockCipher(new CBCBlockCipher(new AESEngine()), new PKCS7Padding());
        cipher.init(false, keyParams);

        final byte[] processed = new byte[cipher.getOutputSize(bytes.length)];
        int outputLength = cipher.processBytes(bytes, 0, bytes.length, processed, 0);
        outputLength += cipher.doFinal(processed, outputLength);

        final byte[] results = new byte[outputLength];
        System.arraycopy(processed, 0, results, 0, outputLength);
        return results;
    }
}
