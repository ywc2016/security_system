package edu.buaa.sem.utils;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.*;
import java.net.URLDecoder;

public class FileUtils {

	/**
	 * 获得根目录".../cib1/"
	 * 
	 * @return
	 */
	public static String getRootPath() {
		Class<FileUtils> clazz = FileUtils.class;
		String path = clazz.getResource("/").getPath();// 得到包含/tsp/WEB-INF/classes/的路径
		String osName = System.getProperty("os.name");
		if (osName.toLowerCase().endsWith("linux") || osName.toLowerCase().startsWith("mac")) {
			path = path.substring(0, path.indexOf("WEB-INF"));
		} else {
			path = path.substring(1, path.indexOf("WEB-INF"));
		}
		return path;
	}

	/**
	 * 某目录下所有文件
	 * 
	 * @param path
	 * @return
	 */
	public static String[] findAll(String path) {
		String filePath = getRealPath(path);
		File file = new File(filePath);
		return file.list();
	}

	/**
	 * 获取虚拟目录"/file"下的实际文件地址
	 * 
	 * @param path
	 *            以"/file/"开头
	 * @return
	 */
	public static String getRealPath(String path) {
		System.out.println("ppppath" + path);
		System.out.println("ppppath" + path.substring(5));
		return ContextLoader.getCurrentWebApplicationContext().getServletContext().getContext("/file")
				.getRealPath(path.substring(5));
	}

	/**
	 * 读取文件内容，UTF-8
	 * 
	 * @param path
	 * @return
	 */
	public static String getFileContent(String path) {
		StringBuffer buffer = new StringBuffer();
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			fis = new FileInputStream(path);
			isr = new InputStreamReader(fis, "UTF-8");
			br = new BufferedReader(isr);
			String line = "";
			while ((line = br.readLine()) != null) {
				buffer.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (isr != null) {
				try {
					isr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return buffer.toString();
	}

	/**
	 * 
	 * 将数据写入文件
	 * 
	 * @param directory
	 *            服务器上的文件夹,即WebContent下的文件夹,如WebContent下有文件夹images,
	 *            则directory="/images"
	 * @param fileName
	 *            文件名,包括扩展名
	 * @param content
	 *            要写入的数据
	 */
	public static void writeContentToFile(String directory, String fileName, String content) {
		String filePath = ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath(directory);
		fileName = filePath + "/" + fileName;
		File file = new File(fileName);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		FileOutputStream fileOutpurStream = null;
		OutputStreamWriter writer = null;
		try {
			fileOutpurStream = new FileOutputStream(fileName, false);
			writer = new OutputStreamWriter(fileOutpurStream, "UTF-8");
			writer.write(content);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fileOutpurStream != null) {
				try {
					fileOutpurStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static String uploadUUID(MultipartFile file, String savePath) {
		if (file != null && savePath != null && !savePath.isEmpty()) {
			// 文件真实路径
			String realPath = getRealPath(savePath);
			// 新文件名
			String newFileName = "";
			String fileName = java.util.UUID.randomUUID().toString();
			// 原文件后缀名
			String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));

			// 新文件名
			newFileName = fileName + suffix;

			try {
				file.transferTo(new File(realPath + "/" + newFileName));
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}

			String pathOnServer = savePath + newFileName;
			return pathOnServer;
		}
		return "error";
	}

	/**
	 * 上传文件方法
	 * 
	 * @param file
	 *            文件
	 * @param savePath
	 *            文件上传目录(如：/file/activity/images/)
	 * @param hasLastUnderline
	 *            新文件名后缀前是否含有下划线
	 * @return 返回 路径+新文件名 组成的字符串
	 */
	public static String uploadFileWithNewName(MultipartFile file, String savePath, boolean hasLastUnderline) {
		if (savePath != null && !savePath.isEmpty()) {
			// 文件真实路径
			String realPath = getRealPath(savePath);
			// 新文件名
			String newFileName = "";
			String fileName = file.getOriginalFilename();
			try {
				// 原文件后缀名
				String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
				// 原文件名称（不含后缀名）
				String fileNameWithoutSuffix = fileName.substring(0, fileName.lastIndexOf(".")).replaceAll(" ", "");
				// 新文件名
				if (hasLastUnderline) {
					newFileName = fileNameWithoutSuffix + "-" + new java.util.Date().getTime() + "_." + suffix;// 这里新文件名：原文件名_当时毫秒数_.后缀名
				} else {
					newFileName = fileNameWithoutSuffix + "-" + new java.util.Date().getTime() + "." + suffix;// 这里新文件名：原文件名_当时毫秒数.后缀名
				}
				file.transferTo(new File(realPath + "/" + newFileName));
				String pathOnServer = savePath + newFileName;
				return pathOnServer;
			} catch (Exception e) {
				e.printStackTrace();
				return "error";
			}
		}
		return "error";
	}

	/**
	 * 上传图片方法，根据desWidth和desHeight强制拉伸或缩放
	 * 
	 * @param file
	 * @param savePath
	 * @param hasLastUnderline
	 * @param desHeight
	 *            目标图片的高度，若为0，则默认为图片原高度
	 * @param desWidth
	 *            目标图片的宽度，若为0，则默认为图片原高度
	 * @return
	 */
	public static String uploadImageToFixedHeightAndWidth(MultipartFile file, String savePath, boolean hasLastUnderline,
			int desHeight, int desWidth) {
		String uploadFileName = uploadFileWithNewName(file, savePath, hasLastUnderline);
		if (uploadFileName != null && !uploadFileName.equals("error")) {
			// 文件上传后的路径
			String realPath = getRealPath(uploadFileName);
			try {
				File sourceImgFile = new File(realPath);
				BufferedImage bi = ImageIO.read(sourceImgFile);
				int srcWidth = bi.getWidth();
				int srcHeight = bi.getHeight();
				int destWidth = desWidth == 0 ? srcWidth : desWidth;
				int destHeight = desHeight == 0 ? srcHeight : desHeight;
				return zoomImageByWidthAndHeight(uploadFileName, destWidth, destHeight);
			} catch (Exception e) {
				e.printStackTrace();
				// 若异常，则删除该图片
				deleteFileByPath(uploadFileName);
				return "error";
			}
		}
		// 若异常，则删除该图片
		deleteFileByPath(uploadFileName);
		return "error";
	}

	/**
	 * 根据制定的高度和宽度，以高度比和宽度比两者中的较大比例，拉伸或压缩图片
	 * 
	 * @param sourcePath
	 * @param height
	 *            目标图片的高度
	 * @param width
	 *            目标图片的宽度
	 * @return
	 */
	public static String scaleImageByWidthAndHeight(String sourcePath, int width, int height) {
		String realPath = getRealPath(sourcePath);
		try {
			File sourceImgFile = new File(realPath);
			BufferedImage bi = ImageIO.read(sourceImgFile);
			int srcWidth = bi.getWidth();
			int srcHeight = bi.getHeight();

			if (srcHeight > width || srcWidth > height) {
				double ratioHeight = (double) height / srcHeight;
				double ratioWight = (double) width / srcWidth;
				double ratio = ratioHeight > ratioWight ? ratioWight : ratioHeight;
				srcHeight = (int) Math.round(srcHeight * ratio);
				srcWidth = (int) Math.round(srcWidth * ratio);
			}

			return zoomImageByWidthAndHeight(sourcePath, srcWidth, srcHeight);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	/**
	 * 按比例拉伸或缩放图片到指定高度,若指定高度为0,则不缩放
	 * 
	 * @param sourcePath
	 * @param height
	 *            目标图片的高度
	 * @return
	 */
	public static String scaleImageByHeight(String sourcePath, int height) {
		String realPath = getRealPath(sourcePath);
		try {
			File sourceImgFile = new File(realPath);
			BufferedImage bi = ImageIO.read(sourceImgFile);
			int srcWidth = bi.getWidth();
			int srcHeight = bi.getHeight();
			int destWidth = srcWidth;
			int destHeight = height == 0 ? srcHeight : height;

			double ratio = (double) destHeight / srcHeight;
			destHeight = (int) Math.round(srcHeight * ratio);
			destWidth = (int) Math.round(srcWidth * ratio);

			return zoomImageByWidthAndHeight(sourcePath, destWidth, destHeight);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	/**
	 * 按比例拉伸或缩放图片到指定宽度,若指定宽度为0,则不缩放
	 * 
	 * @param sourcePath
	 * @param width
	 *            目标图片的宽度
	 * @return
	 */
	public static String scaleImageByWidth(String sourcePath, int width) {
		String realPath = getRealPath(sourcePath);
		try {
			File sourceImgFile = new File(realPath);
			BufferedImage bi = ImageIO.read(sourceImgFile);
			int srcWidth = bi.getWidth();
			int srcHeight = bi.getHeight();
			int destWidth = width == 0 ? srcWidth : width;
			int destHeight = srcHeight;

			double ratio = (double) destWidth / srcWidth;
			destHeight = (int) Math.round(srcHeight * ratio);
			destWidth = (int) Math.round(srcWidth * ratio);

			return zoomImageByWidthAndHeight(sourcePath, destWidth, destHeight);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	/**
	 * 裁剪图片
	 * 
	 * @param sourcePath
	 *            图片源路径（格式：/file/images/142020405464_.jpg）
	 * @param x1
	 *            裁剪参数
	 * @param x2
	 *            裁剪参数
	 * @param y1
	 *            裁剪参数
	 * @param y2
	 *            裁剪参数
	 * @param imageScale
	 *            图片缩放比例，实际裁剪的图片的长宽为目标矩形的长宽乘以缩放比例
	 * @return 返回新文件名，会自动删除掉原有路径上的图片
	 */
	public static String cutImage(String sourcePath, int x1, int x2, int y1, int y2, int imageScale,
			boolean hasLastUnderline) {
		try {
			if (sourcePath != null && !sourcePath.isEmpty()) {
				String sourceRealPath = getRealPath(sourcePath);
				Image img;
				ImageFilter cropFilter;
				File sourceImgFile = new File(sourceRealPath);
				BufferedImage bi = ImageIO.read(sourceImgFile);
				int srcWidth = bi.getWidth();
				int srcHeight = bi.getHeight();
				int destWidth = x2 - x1;
				int destHeight = y2 - y1;
				// 如果没裁剪，默认整个图片都有，保持正方形
				if (x1 == 0 && x2 == 0 && y1 == 0 && y2 == 0) {
					destWidth = srcWidth > srcHeight ? srcHeight : srcWidth;
					destHeight = destWidth;
				}
				if (srcWidth >= destWidth && srcHeight >= destHeight) {
					Image image = bi.getScaledInstance(srcWidth, srcHeight, Image.SCALE_DEFAULT);
					cropFilter = new CropImageFilter(x1, y1, destWidth, destHeight);
					img = Toolkit.getDefaultToolkit()
							.createImage(new FilteredImageSource(image.getSource(), cropFilter));
					BufferedImage tag = new BufferedImage(destWidth, destHeight, BufferedImage.TYPE_INT_RGB);
					Graphics2D g = tag.createGraphics();
					// 源文件的后缀名
					String suffix = sourcePath.substring(sourcePath.lastIndexOf(".") + 1);
					if (!suffix.equals("png")) {
						tag = g.getDeviceConfiguration().createCompatibleImage(destWidth, destHeight);
					} else
						tag = g.getDeviceConfiguration().createCompatibleImage(destWidth, destHeight,
								Transparency.TRANSLUCENT);
					g = tag.createGraphics();
					g.drawImage(img, 0, 0, null);
					g.dispose();
					String newPath = "";
					if (sourcePath.lastIndexOf("-origin") != -1) {
						sourcePath = sourcePath.substring(0, sourcePath.lastIndexOf("-origin"))
								+ sourcePath.substring(sourcePath.lastIndexOf("-origin") + 7, sourcePath.length());
					}
					if (hasLastUnderline) {
						newPath = sourcePath.substring(0, sourcePath.lastIndexOf("-") + 1)
								+ new java.util.Date().getTime() + "_." + suffix;
					} else {
						newPath = sourcePath.substring(0, sourcePath.lastIndexOf("-") + 1)
								+ new java.util.Date().getTime() + "." + suffix;
					}
					String desRealPath = getRealPath(newPath);
					// 输出内存中的图像
					ImageIO.write(tag, suffix, new File(desRealPath));
					return newPath;
				} else {
					return "error";
				}
			}
			return "error";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	public static String zoomImageForThinktank(String sourcePath) {
		return zoomImageForMark(sourcePath);
	}

	public static String zoomImageForDemand(String sourcePath) {
		return zoomImageForMark(sourcePath);
	}

	public static String zoomImageForHomePage(String sourcePath) {
		return zoomImageForMark(sourcePath);
	}

	public static String zoomImageForMarkImage(String sourcePath) {
		return zoomImageForMark(sourcePath);
	}

	public static String zoomImageForThinktankAd(String sourcePath) {
		return zoomImageForAd(sourcePath);
	}

	public static String zoomImageForDiscussionAd(String sourcePath) {
		return zoomImageForAd(sourcePath);
	}

	public static String zoomImageForDemandAd(String sourcePath) {
		return zoomImageForAd(sourcePath);
	}

	public static String zoomImageForTopicAd(String sourcePath) {
		return zoomImageForAd(sourcePath);
	}

	public static String zoomImageForPersonalAd(String sourcePath) {
		return zoomImageForAd(sourcePath);
	}

	public static String zoomImageForActivityAd(String sourcePath) {
		return zoomImageForAd(sourcePath);
	}

	public static String zoomImageForMark(String sourcePath) {
		// return zoomImageByPath(sourcePath, 600, 400); 美工的比例
		return zoomImageByWidthAndHeight(sourcePath, 770, 360);
	}

	public static String zoomImageForAd(String sourcePath) {
		return scaleImageByWidth(sourcePath, 350);
	}

	/**
	 * 拉伸或缩放图片方法，根据desWidth和desHeight强制拉伸或缩放
	 * 
	 * @param sourcePath
	 * @param height
	 *            目标图片的高度，若为0，则默认为图片原高度
	 * @param width
	 *            目标图片的宽度，若为0，则默认为图片原高度
	 * @return
	 */
	public static String zoomImageByWidthAndHeight(String sourcePath, int width, int height) {
		String realPath = getRealPath(sourcePath);
		String suffix = sourcePath.substring(sourcePath.lastIndexOf(".") + 1);
		try {
			File sourceImgFile = new File(realPath);
			BufferedImage bi = ImageIO.read(sourceImgFile);
			BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB); // 缩放图像
			Image image = bi.getScaledInstance(width, height, Image.SCALE_SMOOTH);
			Graphics2D g = tag.createGraphics();
			if (!suffix.equals("png")) {
				tag = g.getDeviceConfiguration().createCompatibleImage(width, height);
			} else
				tag = g.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
			g = tag.createGraphics();
			g.drawImage(image, 0, 0, null); // 绘制目标图
			g.dispose();
			// 输出内存中的图像，以源文件的文件类型生成，生成的名字和源文件相同,路径也相同.
			ImageIO.write(tag, suffix, new File(realPath));
			return sourcePath;
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	/**
	 * 将文件重命名为原文件名_.原文件后缀名 即：将原文件加上下划线，所有文件必须加上下划线才是有用的文件，否则可能被认为是无效文件而被清理
	 * 
	 * @param filePath
	 *            文件路径
	 * @return 返回新文件的路径（由原文件重命名而来）
	 */
	public static String renameFileWithUnderline(EDocument eDocument,
			EDocumentVersionManagement eDocumentVersionManagement) {
		String filePath = eDocument.getPath();
		filePath = StringEscapeUtils.unescapeHtml(filePath);
		if (filePath != null && !filePath.isEmpty()) {
			String fileRealPath = getRealPath(filePath);
			if (fileRealPath == null || fileRealPath.isEmpty()) {
				return "error";
			}
			File file = new File(fileRealPath);

			// 去除后缀后的名字
			// String fileNameWithoutSuffix = filePath.substring(0,
			// filePath.lastIndexOf("."));
			String fileNameWithoutSuffix = filePath.substring(0, filePath.lastIndexOf("/") + 1) + eDocument.getName()
					+ "-" + eDocument.getUploadTime().getTime();
			// 后缀
			String suffix = filePath.substring(filePath.lastIndexOf("."));
			String newPath = fileNameWithoutSuffix + "_" + (eDocumentVersionManagement.getExistEditions()) + suffix;
			String newRealPath = getRealPath(newPath);
			file.renameTo(new File(newRealPath));
			return newPath;

		}
		return "error";
	}

	/**
	 * 根据路径删除文件
	 * 
	 * @param path
	 */
	public static void deleteFileByPath(String path) {

		if (path == null || path.isEmpty() || path.contains("default")) {
			return;
		}
		try {
			String filePath = getRealPath(path);
			File file = new File(filePath);
			if (file.isFile() && file.exists()) {
				file.delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 上传文件时重命名文件
	 * 
	 * @param savePath
	 * @param multiFile
	 * @return
	 */
	public static String uploadWithRename(String savePath, MultipartFile multiFile) {
		if (savePath != null && !savePath.isEmpty()) {
			String fileName = multiFile.getOriginalFilename();
			String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
			// 原文件名称（不含后缀名）
			String fileNameWithoutSuffix = fileName.substring(0, fileName.lastIndexOf("."));
			String newFileName = fileNameWithoutSuffix + "-" + new java.util.Date().getTime() + "." + suffix;
			String newFilePath = savePath + newFileName;
			String realPath = getRealPath(newFilePath);
			try {
				multiFile.transferTo(new File(realPath));
				return newFilePath;
			} catch (IllegalStateException e) {
				e.printStackTrace();
				return "error";
			} catch (IOException e) {
				e.printStackTrace();
				return "error";
			}
		} else {
			return "error";
		}
	}

	/**
	 * 上传文件时重命名文件并解码
	 * 
	 * @param savePath
	 * @param multiFile
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String uploadWithRenameAndDecode(String savePath, MultipartFile multiFile, String decodeType)
			throws UnsupportedEncodingException {
		if (savePath != null && !savePath.isEmpty()) {
			String fileName = URLDecoder.decode(multiFile.getOriginalFilename(), decodeType);
			String suffix = fileName.substring(fileName.indexOf(".") + 1);
			// 原文件名称（不含后缀名）
			String fileNameWithoutSuffix = fileName.substring(0, fileName.lastIndexOf("."));
			String newFileName = fileNameWithoutSuffix + "-" + new java.util.Date().getTime() + "." + suffix;
			String newFilePath = savePath + newFileName;
			String realPath = getRealPath(newFilePath);
			try {
				multiFile.transferTo(new File(realPath));
				return newFilePath;
			} catch (IllegalStateException e) {
				e.printStackTrace();
				return "error";
			} catch (IOException e) {
				e.printStackTrace();
				return "error";
			}
		} else {
			return "error";
		}
	}

	public static String renameFileWithOrigin(String sourcePath, String newPath) {
		if (sourcePath != null && !sourcePath.isEmpty()) {
			String sourceRealPath = getRealPath(sourcePath);
			File sourceImgFile = new File(sourceRealPath);
			String newPathFirst = newPath.substring(0, newPath.lastIndexOf("-"));
			String newPathLast = newPath.substring(newPath.lastIndexOf("-"), newPath.length());
			if (sourceImgFile != null && sourceImgFile.isFile() && sourceImgFile.exists()) {
				String sourceNewPath = newPathFirst + "-origin" + newPathLast;
				String sourceNewRealPath = getRealPath(sourceNewPath);
				sourceImgFile.renameTo(new File(sourceNewRealPath));
				return sourceNewPath;
			} else {
				return "error";
			}
		} else {
			return "error";
		}
	}

	/**
	 * 判断图片来源，是否来自图片库
	 * 
	 * @param imagepath
	 * @return
	 */
	public static boolean isFromPictureHouse(String imagepath) {
		if (imagepath.contains("/file/picturehouse")) {
			return true;
		}
		return false;

	}
}
