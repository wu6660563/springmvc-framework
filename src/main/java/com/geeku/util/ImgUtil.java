package com.geeku.util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.misc.BASE64Encoder;

import com.geeku.util.io.PropertiesUtil;

/**
 * @ClassName ImgUtil
 * @Description 图片工具类，用于生成图片缩略图等
 * @author Nick
 * @version 1.0
 * @Date 2016年7月18日 下午2:23:29
 */
public class ImgUtil {

	private static Logger logger = LoggerFactory.getLogger(ImgUtil.class);

	/**
	 * @Fields DEFAULT_THUMB_PREVIFX 压缩文件的前缀
	 */
	private static String DEFAULT_THUMB_PREVIFX = "thumb_";

	private static String DEFAULT_CUT_PREVFIX = "cut_";
	
	private static String partRealPath = "partRealPath";

	/**
	 * <p>
	 * Title: cutImage
	 * </p>
	 * <p>
	 * Description: 根据原图与裁切size截取局部图片
	 * </p>
	 * 
	 * @param srcImg
	 *            源图片
	 * @param output
	 *            图片输出流
	 * @param rect
	 *            需要截取部分的坐标和大小
	 */
	public void cutImage(File srcImg, OutputStream output,
			java.awt.Rectangle rect) {
		if (srcImg.exists()) {
			java.io.FileInputStream fis = null;
			ImageInputStream iis = null;
			try {
				fis = new FileInputStream(srcImg);
				// ImageIO 支持的图片类型 : [BMP, bmp, jpg, JPG, wbmp, jpeg, png, PNG,
				// JPEG, WBMP, GIF, gif]
				String types = Arrays.toString(ImageIO.getReaderFormatNames())
						.replace("]", ",");
				String suffix = null;
				// 获取图片后缀
				if (srcImg.getName().indexOf(".") > -1) {
					suffix = srcImg.getName().substring(
							srcImg.getName().lastIndexOf(".") + 1);
				}// 类型和图片后缀全部小写，然后判断后缀是否合法
				if (suffix == null
						|| types.toLowerCase().indexOf(
								suffix.toLowerCase() + ",") < 0) {
					logger.warn("Sorry, the image suffix is illegal. the standard image suffix is {}."
							+ types);
					return;
				}
				// 将FileInputStream 转换为ImageInputStream
				iis = ImageIO.createImageInputStream(fis);
				// 根据图片类型获取该种类型的ImageReader
				ImageReader reader = ImageIO.getImageReadersBySuffix(suffix)
						.next();
				reader.setInput(iis, true);
				ImageReadParam param = reader.getDefaultReadParam();
				param.setSourceRegion(rect);
				BufferedImage bi = reader.read(0, param);
				ImageIO.write(bi, suffix, output);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (fis != null)
						fis.close();
					if (iis != null)
						iis.close();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					fis = null;
					iis = null;
				}
			}
		} else {
			logger.warn("the src image is not exist.");
		}
	}

	public void cutImage(File srcImg, OutputStream output, int x, int y,
			int width, int height) {
		cutImage(srcImg, output, new java.awt.Rectangle(x, y, width, height));
	}

	public void cutImage(File srcImg, String destImgPath,
			java.awt.Rectangle rect) {
		File destImg = new File(destImgPath);
		if (destImg.exists()) {
			String p = destImg.getPath();
			try {
				if (!destImg.isDirectory())
					p = destImg.getParent();
				if (!p.endsWith(File.separator))
					p = p + File.separator;
				cutImage(srcImg,
						new java.io.FileOutputStream(p + DEFAULT_CUT_PREVFIX
								+ "_" + new java.util.Date().getTime() + "_"
								+ srcImg.getName()), rect);
			} catch (FileNotFoundException e) {
				logger.warn("the dest image is not exist.");
			}
		} else
			logger.warn("the dest image folder is not exist.");
	}

	public void cutImage(File srcImg, String destImg, int x, int y, int width,
			int height) {
		cutImage(srcImg, destImg, new java.awt.Rectangle(x, y, width, height));
	}

	public void cutImage(String srcImg, String destImg, int x, int y,
			int width, int height) {
		cutImage(new File(srcImg), destImg, new java.awt.Rectangle(x, y, width,
				height));
	}

	/**
	 * Title: 根据图片路径生成缩略图，返回缩略图的路径，如果已经存在，则直接路径
	 * 			例如数据库路径是：/imgload/exhibition/app1.png，会先去读取app.properties配置文件里面配置的
	 * @param imagePath
	 *            原图片路径
	 * @param w
	 *            缩略图宽
	 * @param h
	 *            缩略图高
	 * @param prevfix
	 *            生成缩略图的前缀
	 * @param force
	 *            是否强制按照宽高生成缩略图(如果为false，则生成最佳比例缩略图)，默认是false
	 */
	public static String thumbnailImage(String srcImg, int w, int h) {
		return thumbnailImage(srcImg, w, h, DEFAULT_THUMB_PREVIFX, false);
	}

	/**
	 * Title: thumbnailImage Description: 根据图片路径生成缩略图，返回缩略图的路径，如果已经存在，则直接路径
	 * 		例如数据库路径是：/imgload/exhibition/app1.png，会先去读取app.properties配置文件里面配置的
	 * @param imagePath
	 *            原图片路径
	 * @param w
	 *            缩略图宽
	 * @param h
	 *            缩略图高
	 * @param prevfix
	 *            生成缩略图的前缀
	 * @param force
	 *            是否强制按照宽高生成缩略图(如果为false，则生成最佳比例缩略图)，默认是false
	 */
	public static String thumbnailImage(String imgPath, int w, int h, String prevfix,
			boolean force) {
		FileOutputStream output = null;
		try {
			if(imgPath == null) {
				return imgPath;
			}
			String path = PropertiesUtil.readKey(partRealPath);
			File srcImg = new File(path + imgPath);
			if (srcImg != null && !srcImg.exists()) {
				logger.warn("the src image is not exist:"
						+ srcImg.getAbsolutePath());
				return imgPath;
			}
			if (srcImg.isDirectory()) {
				logger.warn("the src image is directory:"
						+ srcImg.getAbsolutePath());
				return imgPath;
			}

			String p = srcImg.getAbsolutePath();
			if (!srcImg.isDirectory())
				p = srcImg.getParent();
			if (!p.endsWith(File.separator))
				p = p + File.separator;

			/*
			 * 图片地址采用：路径 + 前缀 + 宽度 + 高度 + 图片原来名称
			 * 如：myproject.jpg得到缩略图就是：thumb_100_50_myproject.jpg
			 */
			String fileName = prevfix + w + "_" + h + "_"
					+ srcImg.getName();
			String thumbImg = p + fileName;
			
			String returnPath = imgPath.substring(0, imgPath.lastIndexOf(srcImg.getName())) + fileName;

			File thumbFile = new File(thumbImg);
			if (!thumbFile.exists() || !thumbFile.isFile()) {
				logger.info("开始处理缩略图："+thumbImg);
				
				/*
				 * ImageIO 支持的图片类型 : [BMP, bmp, jpg, JPG, wbmp, jpeg, png,
				 * PNG,JPEG, WBMP, GIF, gif]
				 */
				String types = Arrays.toString(ImageIO.getReaderFormatNames())
						.replace("]", ",");
				String suffix = null;
				// 获取图片后缀
				if (srcImg.getName().indexOf(".") > -1) {
					suffix = srcImg.getName().substring(
							srcImg.getName().lastIndexOf(".") + 1);
				}// 类型和图片后缀全部小写，然后判断后缀是否合法
				if (suffix == null
						|| types.toLowerCase().indexOf(
								suffix.toLowerCase() + ",") < 0) {
					logger.error("Sorry, the image suffix is illegal. the standard image suffix is {}."
							+ types);
					return imgPath;
				}
				output = new FileOutputStream(thumbImg);

				Image img = ImageIO.read(srcImg);
				// 根据原图与要求的缩略图比例，找到最合适的缩略图比例
				if (!force) {
					int width = img.getWidth(null);
					int height = img.getHeight(null);
					if ((width * 1.0) / w < (height * 1.0) / h) {
						if (width > w) {
							h = Integer.parseInt(new java.text.DecimalFormat(
									"0").format(height * w / (width * 1.0)));
						}
					} else {
						if (height > h) {
							w = Integer.parseInt(new java.text.DecimalFormat(
									"0").format(width * h / (height * 1.0)));
						}
					}
				}
				BufferedImage bi = new BufferedImage(w, h,
						BufferedImage.TYPE_INT_RGB);
				Graphics g = bi.getGraphics();
				g.drawImage(img, 0, 0, w, h, Color.LIGHT_GRAY, null);
				g.dispose();
				// 将图片保存在原目录并加上前缀
				ImageIO.write(bi, suffix, output);
			}
			return returnPath;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("generate thumbnail image failed.", e);
			return imgPath;
		} finally {
			try {
				if (output != null)output.close();
			} catch (IOException e) {
				e.printStackTrace();
				logger.error("img util not close.", e);
			} finally {
				output = null;
			}
		}
	}

	public String imgToBase64(String imgPath) {
		InputStream in = null;
		byte[] data = null;
		// 读取图片字节数组

		File file = new File(imgPath);
		if (!file.exists() || !file.isFile()) {
			logger.error("图片不存在，或者不是文件");
			return null;
		}
		try {
			in = new FileInputStream(imgPath);
			data = new byte[in.available()];
			in.read(data);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				in = null;
			}
		}
		// 对字节数组Base64编码
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(data);// 返回Base64编码过的字节数组字符串
	}

	public static void main(String[] args) throws IOException {
		 System.out.println(ImgUtil.thumbnailImage("/imgload/exhibition/app1.png", 100, 60));
		// new ImgUtil().cutImage("imgs/Tulips.jpg", "imgs", 250, 70, 300, 400);

	}

}
