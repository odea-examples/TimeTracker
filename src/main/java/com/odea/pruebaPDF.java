package com.odea;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.odea.components.documentInlineFrame.DocumentInlineFrame;

public class pruebaPDF extends BasePage {
	
	public pruebaPDF() {
		DocumentInlineFrame dif = new DocumentInlineFrame("dif") {
			
			@Override
			public byte[] getPdfBytes() {
				return pruebaPDF.this.getPdfbytes();
			}
		};
		dif.setOutputMarkupId(true);
		
		add(dif);
	}
	
	protected byte[] getPdfbytes() {
		try {
			return bytes(this.getClass().getResourceAsStream("latin9.pdf"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static  byte[] bytes(InputStream is) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		copy(is, out);
		return out.toByteArray();
	}
	
	public static void copy(InputStream is, OutputStream os) throws IOException {
		byte[] buf = new byte[1024];
		while (true) {
			int tam = is.read(buf);
			if (tam == -1) {
				return;
			}
			os.write(buf, 0, tam);
		}
	}
	
}
