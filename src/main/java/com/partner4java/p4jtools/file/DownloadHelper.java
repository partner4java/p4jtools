package com.partner4java.p4jtools.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

/**
 * 下载助手
 */
public class DownloadHelper {
	
	public static void download(String fileName, String downName, HttpServletResponse response) {
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/x-excel");
			response.setHeader("Content-Disposition",
					"attachment;fileName=" + new String(downName.getBytes(), "iso-8859-1"));
			File f = new File(fileName);
			response.setHeader("Content-length", String.valueOf(f.length()));

			bis = new BufferedInputStream(new FileInputStream(f));
			bos = new BufferedOutputStream(response.getOutputStream());

			byte[] bytes = new byte[2048];
			int length = 0;
			while ((length = bis.read(bytes)) > 0) {
				bos.write(bytes, 0, length);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bos.close();
				bis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
