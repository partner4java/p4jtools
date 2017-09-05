package com.partner4java.p4jtools.file;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.partner4java.p4jtools.IDGenerate;

/**
 * 二维码生成器
 * 
 * @author 王昌龙
 * 
 */
public class ErWeiMaGenerate {

	/**
	 * 生成二维码
	 * 
	 * @param content
	 *            二维码内容
	 * @param path
	 *            保存二维码图片目录
	 * @return 二维码图片名称
	 */
	public static String generate(String content, String path) {
		try {
			String fileName = IDGenerate.getUniqueID() + ".png";

			Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();

			hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

			BitMatrix matrix = matrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, 300, 300, hints);

			File file = new File(path + fileName);

			MatrixToImageWriter.writeToFile(matrix, "png", file);

			return fileName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static final int BLACK = 0xFF000000;
	private static final int WHITE = 0xFFFFFFFF;

	private static BufferedImage toBufferedImage(BitMatrix matrix) {
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
			}
		}
		return image;
	}

	private static void writeToFile(BitMatrix matrix, String format, File file) throws IOException {
		BufferedImage image = toBufferedImage(matrix);
		if (!ImageIO.write(image, format, file)) {
			throw new IOException("Could not write an image of format " + format + " to " + file);
		}
	}

	private static void writeToStream(BitMatrix matrix, String format, OutputStream stream) throws IOException {
		BufferedImage image = toBufferedImage(matrix);
		if (!ImageIO.write(image, format, stream)) {
			throw new IOException("Could not write an image of format " + format);
		}
	}
}
