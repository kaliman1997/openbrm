package com.cboss.util;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class BarcodeImage {
	public static void decodeImage(String barcodeImage, String path) throws Exception{
		
		
		System.out.println("CBOSS:: decodeImage path" + path);
		System.out.println("CBOSS:: decodeImage " + barcodeImage);
		
			byte[] data = Base64.getDecoder().decode(
				barcodeImage.getBytes(StandardCharsets.UTF_8));
		OutputStream stream = new FileOutputStream(path);
		stream.write(data);		
	}
}
