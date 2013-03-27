/*
 * This class is part of the book "iText in Action - 2nd Edition"
 * written by Bruno Lowagie (ISBN: 9781935182610)
 * For more info, go to: http://itextpdf.com/examples/
 * This example only works with the AGPL version of iText.
 */
package com.odea.signature;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.Certificate;
import java.util.Properties;

import javax.crypto.Cipher;


import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfSignatureAppearance;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfWriter;



public class Signatures {

    /** The resulting PDF */
    public static String ORIGINAL = "/home/gmazzei/keys/hello.pdf";
    /** The resulting PDF */
    public static String SIGNED1 = "/home/gmazzei/keys/signature_final.pdf";
    /** The resulting PDF */
    public static String SIGNED2 = "/home/gmazzei/keys/signature_2.pdf";
    /** Info after verification of a signed PDF */
    public static String VERIFICATION = "/home/gmazzei/keys/verify.txt";
    /** The resulting PDF */
    public static String REVISION = "/home/gmazzei/keys/revision_1.pdf";

    /**
     * A properties file that is PRIVATE.
     * You should make your own properties file and adapt this line.
     */
    public static String PATH = "/home/gmazzei/keys/key.properties";
    /** Some properties used when signing. */
    public static Properties properties = new Properties();
    /** One of the resources. */
    public static final String RESOURCE = "resources/img/logo.gif";
    
    /**
     * Creates a PDF document.
     * @param filename the path to the new PDF document
     * @throws DocumentException 
     * @throws IOException 
     */
    public void createPdf(String filename) throws IOException, DocumentException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(filename));
        document.open();
        document.add(new Paragraph("Hello World!"));
        document.close();
    }
    
    /**
     * Manipulates a PDF file src with the file dest as result
     * @param src the original PDF
     * @param dest the resulting PDF
     * @throws IOException
     * @throws DocumentException
     * @throws GeneralSecurityException 
     */
    public void signPdfFirstTime(String src, String dest)
        throws IOException, DocumentException, GeneralSecurityException {
        String path = properties.getProperty("PRIVATE");
        String keystore_password = properties.getProperty("PASSWORD");
        String key_password = properties.getProperty("PASSWORD");
        KeyStore ks = KeyStore.getInstance("pkcs12","BC");
        //chekeo si tengo security unlimited power mega power super groso
        int maxKeyLen = Cipher.getMaxAllowedKeyLength("AES");
        System.out.println(maxKeyLen);
        
        ks.load(new FileInputStream(path), keystore_password.toCharArray());
        String alias = (String)ks.aliases().nextElement();
        
        PrivateKey pk = (PrivateKey) ks.getKey(alias, key_password.toCharArray());
        Certificate[] chain = ks.getCertificateChain(alias);
        // reader and stamper
        PdfReader reader = new PdfReader(src);
        FileOutputStream os = new FileOutputStream(dest);
        PdfStamper stamper = PdfStamper.createSignature(reader, os, '\0');
        // appearance
     
        PdfSignatureAppearance appearance = stamper.getSignatureAppearance();
        //appearance.setImage(Image.getInstance(RESOURCE));
        
        appearance.setReason("I've written this.");
        appearance.setLocation("Foobar");
        // digital signature 
        appearance.setCrypto(pk, chain, null, PdfSignatureAppearance.SELF_SIGNED);
        appearance.setVisibleSignature(new Rectangle(72, 732, 144, 780), 1,    "first");
        stamper.close();
       // PrivateKeySignature pks = new PrivateKeySignature(pk, "SHA-256", "BC");
       /* ExternalDigest digest = new ExternalDigest(){

			@Override
			public MessageDigest getMessageDigest(String arg0)
					throws GeneralSecurityException {
				d.
				return null;
			}
        	
        }
        		
        MakeSignature.signDetached(appearance, digest, es, chain, null, null, null, 0, CryptoStandard.CMS);
    */
    }
    
    /**
     * Manipulates a PDF file src with the file dest as result
     * @param src the original PDF
     * @param dest the resulting PDF
     * @throws IOException
     * @throws DocumentException
     * @throws GeneralSecurityException 
     */
   
    public static void main(String[] args)
        throws IOException, DocumentException, GeneralSecurityException {
        Security.addProvider(new BouncyCastleProvider());
        properties.load(new FileInputStream(PATH));
        Signatures signatures = new Signatures();
        signatures.createPdf(ORIGINAL);
        signatures.signPdfFirstTime(ORIGINAL, SIGNED1);
    }
}
