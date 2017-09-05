package com.partner4java.p4jtools.image;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.openxml4j.opc.internal.FileHelper;
import org.springframework.util.StringUtils;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * 图片工具类
 * 
 *
 */
public class ImageUtil {
	private final static Log LOG = LogFactory.getLog(ImageUtil.class);

	public final static String NORMAL = "normal";
	public final static String SMALL = "small";
	public final static String MIN = "min";

	/** 支持的图片扩展名集合 */
	private static Set<String> extensions = new HashSet<String>();

	static {
		extensions.add("jpg");
		extensions.add("jpeg");
		extensions.add("gif");
		extensions.add("ico");
		extensions.add("png");
		extensions.add("bmp");
	}

	/**
	 * 获取支持的扩展名
	 * 
	 * @return
	 */
	public static Set<String> getEextensions() {
		return extensions;
	}

	/**
	 * 判断是否是图片文件
	 * 
	 * @param file
	 *            File对象
	 * @return 是：true,否：false
	 */
	public static boolean isImage(File file) {
		try {
			ImageInputStream iis = ImageIO.createImageInputStream(file);
			if (iis == null) {
				return false;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 判断图片文件扩展名是否合法
	 * 
	 * @param extension
	 *            扩展名
	 * @return
	 */
	public static boolean isExtensionLegal(String extension) {
		if (extension == null) {
			return false;
		}
		return extensions.contains(extension.toLowerCase());
	}

	/**
	 * 按照高度和宽度及压缩比进行压缩
	 * 
	 * @param image
	 *            压缩图片
	 * @param destPath
	 *            压缩输出路径
	 * @param w
	 *            宽度
	 * @param h
	 *            高度
	 * @param scale
	 *            压缩比，如值为0，保存图片为指定宽高，如为3，压缩为指定宽高的1/3
	 * @param flag
	 *            标识大图中图小图
	 */
	public static boolean resize(BufferedImage image, String destPath, int w, int h, int scale, ImgSize imgSize, String flag) {
		FileOutputStream out = null;
		File destFile = new File(destPath);
		try {
			// 计算压缩后宽高
			if (scale > 0) {
				w = w / scale;
				h = h / scale;
			}

			// 保存宽高
			if (NORMAL.equals(flag)) {
				imgSize.setSize(w, h);
			} else if (SMALL.equals(flag)) {
				imgSize.setSmallSize(w, h);
			} else if (MIN.equals(flag)) {
				imgSize.setMinSize(w, h);
			}

			BufferedImage buffer = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
			buffer.getGraphics().drawImage(image.getScaledInstance(w, h, Image.SCALE_SMOOTH), 0, 0, null);
			out = new FileOutputStream(destFile);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			encoder.encode(buffer);

			return true;
		} catch (IOException e) {
			e.printStackTrace();
			if (destFile != null && destFile.exists()) {
				destFile.delete();
			}
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	/**
	 * 压缩图片<br>
	 * <b>注意：</b>如不指定宽高，仅进行等比压缩；<br>
	 * 仅指定宽度，按照宽度进行等比压缩；仅指定高度，按高度进行等比压缩；<br>
	 * 同时指定宽高，将按照指定大小保存图片。
	 * 
	 * @param file
	 *            压缩图片文件
	 * @param destPath
	 *            压缩输出目录
	 * @param w
	 *            宽度
	 * @param h
	 *            高度
	 * @return
	 */
	public static ImgSize compress(File file, String destPath, int w, int h) {
		return compress(file, destPath, w, h, 0, 0);
	}

	/**
	 * 压缩图片<br>
	 * <b>注意：</b>如不指定宽高，仅进行等比压缩；<br>
	 * 仅指定宽度，按照宽度进行等比压缩；仅指定高度，按高度进行等比压缩；<br>
	 * 同时指定宽高，将按照指定大小进行等比压缩。
	 * 
	 * @param file
	 *            压缩图片文件
	 * @param destPath
	 *            压缩输出目录
	 * @param w
	 *            宽度
	 * @param h
	 *            高度
	 * @param smallScale
	 *            较小压缩比例，取倒数，0不保存压缩文件
	 * @param minScale
	 *            最小压缩比例，取倒数，0不保存压缩文件
	 * @return
	 */
	public static ImgSize compress(File file, String destPath, int w, int h, int smallScale, int minScale) {
		try {
			if (file == null || StringUtils.isEmpty(destPath)) {
				return null;
			}
			BufferedImage image = ImageIO.read(file);
			int width = image.getWidth();
			int height = image.getHeight();

			if (w == 0 && h == 0) {
				w = width;
				h = height;
			} else if (w > 0 && h == 0) {
				h = height * w / width;
			} else if (w == 0 && h > 0) {
				w = width * h / height;
			}

			ImgSize imgSize = new ImgSize();
			resize(image, destPath, w, h, 0, imgSize, NORMAL);

			if (smallScale > 0) {
				String path = getCompressPath(destPath, SMALL);
				if (path != null && !"".equals(path)) {
					resize(image, path, w, h, smallScale, imgSize, SMALL);
				}
			}

			if (minScale > 0) {
				String path = getCompressPath(destPath, MIN);
				resize(image, path, w, h, minScale, imgSize, MIN);
			}

			return imgSize;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 生成完整压缩文件目录，文件名后增加flag
	 * 
	 * @param destPath
	 *            原目录
	 * @param flag
	 *            标识
	 * @return
	 */
	private static String getCompressPath(String destPath, String flag) {
		int pIndex = destPath.lastIndexOf(".");
		String path = null;
		if (pIndex > 0) {
			String prev = destPath.substring(0, pIndex);
			String next = destPath.substring(pIndex);
			path = prev + flag + next;
		}
		return path;
	}

	/**
	 * 图片裁剪
	 * 
	 * @param inputPath
	 *            图片文件输入路径
	 * @param outPath
	 *            裁剪后图片文件输出路径
	 * @param type
	 *            图片文件扩展名（小写）
	 * @param x
	 *            裁剪开始横坐标（像素）
	 * @param y
	 *            裁剪开始纵坐标（像素）
	 * @param width
	 *            裁剪宽度（像素）
	 * @param height
	 *            参见高度（像素）
	 */
	public static boolean cutImage(String inputPath, String outPath, String type, int x, int y, int width, int height) {
		try {
			LOG.debug("裁剪源文件路径：" + inputPath);
			LOG.debug("裁剪输出文件路径" + outPath);
			LOG.debug("裁剪文件类型:" + type);
			File inputFile = new File(inputPath);
			if (inputFile.exists()) {
				InputStream inputStream = new FileInputStream(inputFile);
				OutputStream outputStream = new FileOutputStream(new File(outPath));
				return cutImage(inputStream, outputStream, type, x, y, width, height);
			} else {
				LOG.error("文件裁剪源文件不存在");
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e);
		}
		return false;
	}

	/**
	 * 图片裁剪
	 * 
	 * @param input
	 *            图片文件输入流
	 * @param out
	 *            裁剪后图片文件输出流
	 * @param type
	 *            图片文件扩展名（小写）
	 * @param x
	 *            裁剪开始横坐标（像素）
	 * @param y
	 *            裁剪开始纵坐标（像素）
	 * @param width
	 *            裁剪宽度（像素）
	 * @param height
	 *            参见高度（像素）
	 */
	public static boolean cutImage(InputStream input, OutputStream out, String type, int x, int y, int width, int height) {
		ImageInputStream imageStream = null;

		try {
			String imageType = (null == type || "".equals(type)) ? "jpg" : type;
			Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName(imageType);
			ImageReader reader = readers.next();
			imageStream = ImageIO.createImageInputStream(input);
			reader.setInput(imageStream, true);
			ImageReadParam param = reader.getDefaultReadParam();
			Rectangle rect = new Rectangle(x, y, width, height);
			param.setSourceRegion(rect);
			BufferedImage bi = reader.read(0, param);
			ImageIO.write(bi, imageType, out);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (imageStream != null) {
					imageStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return false;
	}

	/**
	 * 获取大图url
	 * 
	 * @param url
	 *            管他什么url
	 * @return
	 */
	public static String getNormalUrl(String url) {
		return getImageUrl(url, "");
	}

	/**
	 * 获取中图url
	 * 
	 * @param url
	 *            管他什么url
	 * @return
	 */
	public static String getSmallUrl(String url) {
		return getImageUrl(url, "small");
	}

	/**
	 * 获取小图url
	 * 
	 * @param url
	 *            管他什么url
	 * @return
	 */
	public static String getMinUrl(String url) {
		return getImageUrl(url, "min");
	}

	/**
	 * 获取指定类型url，大图、中图或者小图
	 * 
	 * @param url
	 *            原url
	 * @param type
	 *            大图"",中图"small",小图"min"
	 * @return
	 */
	private static String getImageUrl(String url, String type) {
		String normalUrl = null;
		if (!StringUtils.isEmpty(url)) {
			if (url.contains("yahoo")) {
				return url;
			}
			
			if(url.toLowerCase().endsWith(".gif")){
				return url;
			}

			// 寺库商品
			if (url.contains("http://pic.secoo.com/product/")) {
				if ("min".equals(type)) {
					url = url.replace("/500/500/", "/200/200/").replace("/300/300/", "/200/200/");
				} else if ("small".equals(type)) {
					url = url.replace("/500/500/", "/300/300/").replace("/200/200/", "/300/300/");
				}
				return url;
			}
			// 寺库其他图片
			else if (url.contains("http://pic.secoo.com/")) {
				return url;
			}
			// 我卖我拍自己的图片
			else {
				int pi = url.lastIndexOf(".");
				if (pi > 0) {
					String prev = url.substring(0, pi);
					String extension = url.substring(pi);
					if (prev.endsWith("small")) {
						prev = prev.substring(0, prev.length() - 5);
					} else if (prev.endsWith("min")) {
						prev = prev.substring(0, prev.length() - 3);
					}
					return prev + type + extension;
				}
			}
		}

		return normalUrl;
	}

	/**
	 * 判断是否是动图
	 * 
	 * @param s
	 *            文件名或者扩展名
	 * @return
	 */
	public static boolean isGif(String s) {
		if (s != null && !"".equals(s.trim())) {
			int i = s.lastIndexOf(".");
			if (i > -1) {
				s = s.substring(i + 1);
			}
			return "gif".equals(s.trim().toLowerCase());
		}

		return false;
	}

	/**
	 * 拷贝图片
	 * 
	 * @param img
	 *            源图片文件
	 * @param destImg
	 *            目标图片文件
	 * @return
	 */
	public static ImgSize copyImg(File img, File destImg) {
		try {
			BufferedImage image = ImageIO.read(img);
			int width = image.getWidth();
			int height = image.getHeight();

			ImgSize imgSize = new ImgSize();
			imgSize.setSize(width, height);

			FileHelper.copyFile(img, destImg);
			return imgSize;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
