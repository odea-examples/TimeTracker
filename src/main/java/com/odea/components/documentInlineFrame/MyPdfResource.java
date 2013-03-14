package com.odea.components.documentInlineFrame;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.wicket.IResourceListener;
import org.apache.wicket.request.resource.ByteArrayResource;
import org.apache.wicket.request.resource.IResource.Attributes;

public class MyPdfResource extends ByteArrayResource implements IResourceListener {

	private static final long serialVersionUID = 1L;

	static int BUFFER_SIZE = 10*1024;

	
	public MyPdfResource() {
		super("application/pdf");
	}
	
	
	
	@Override
	protected byte[] getData(Attributes attributes) {
		try {
			return bytes(MyPdfResource.class.getResourceAsStream("test.pdf"));
		} catch (IOException e) {
			throw new RuntimeException(e);
//			return null;
		}
	}
	
	public static  byte[] bytes(InputStream is) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		copy(is, out);
		return out.toByteArray();
	}
	
	public static void copy(InputStream is, OutputStream os) throws IOException {
		byte[] buf = new byte[BUFFER_SIZE];
		while (true) {
			int tam = is.read(buf);
			if (tam == -1) {
				return;
			}
			os.write(buf, 0, tam);
		}
	}

	@Override
	public void onResourceRequested() {
		
	}

	
}

