package edu.buaa.sem.document.service;

import static org.mockito.Matchers.longThat;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.buaa.sem.document.dao.DocumentBackupDao;
import edu.buaa.sem.utils.AES;
import edu.buaa.sem.utils.FileUtils;

@Service
public class DocumentBackupService {
	@Autowired
	private DocumentBackupDao documentBackupDao;

	public Map<String, Object> findByParamsForPagination(EDocumentBackup eDocumentBackup, String rows, String page,
			String order, String sort) {
		Map<String, Object> responseJson = new HashMap<String, Object>();
		responseJson.put("rows", documentBackupDao.findByParamsForPagination(eDocumentBackup, page, rows, sort, order));
		responseJson.put("total", documentBackupDao.countByParams(eDocumentBackup));
		return responseJson;
	}

	public void newBackup(String documentDir, String backupDir) {
		EDocumentBackup eDocumentBackup = new EDocumentBackup();
		Date date = new Date();
		eDocumentBackup.setBackupTime(date);
		eDocumentBackup.setDownloadTimes(0);
		eDocumentBackup.setEncryptionStatus("plain");
		eDocumentBackup.setName("备份_" + date.getTime());
		eDocumentBackup.setNumber("BAC-" + date.getTime());
		eDocumentBackup.setPath(backupDir + "/" + eDocumentBackup.getName() + ".zip");

		String filesPath = FileUtils.getRealPath(documentDir);
		String zipPath = FileUtils.getRealPath(eDocumentBackup.getPath());
		ZipMultiFile(filesPath, zipPath);
		File zipFile = new File(zipPath);
		eDocumentBackup.setSize(zipFile.length() + "bytes");
		documentBackupDao.saveOrUpdate(eDocumentBackup);
	}

	/**
	 * 压缩并备份文件
	 * 
	 * @param filespath
	 * @param zippath
	 */
	public void ZipMultiFile(String filespath, String zippath) {
		try {
			File file = new File(filespath);// 要被压缩的文件夹
			File zipFile = new File(zippath);
			InputStream input = null;
			ZipOutputStream zipOut = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFile)));
			if (file.isDirectory()) {
				File[] files = file.listFiles();
				for (int i = 0; i < files.length; ++i) {
					System.out.println("正在压缩:" + files[i].getName());
					input = new FileInputStream(files[i]);
					BufferedInputStream bis = new BufferedInputStream(input);
					zipOut.putNextEntry(new ZipEntry(file.getName() + File.separator + files[i].getName()));
					byte[] buf = new byte[1024];
					int length = 0;
					while ((length = bis.read(buf)) > 0) {
						zipOut.write(buf, 0, length);
					}
					input.close();
				}
			}
			zipOut.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void ZipContraMultiFile(String zippath, String outzippath) {
		try {
			File file = new File(zippath);
			File outFile = null;
			ZipFile zipFile = new ZipFile(file);
			ZipInputStream zipInput = new ZipInputStream(new BufferedInputStream(new FileInputStream(file)));
			ZipEntry entry = null;
			InputStream input = null;
			OutputStream output = null;
			while ((entry = zipInput.getNextEntry()) != null) {
				System.out.println("解压缩" + entry.getName() + "文件");
				outFile = new File(
						outzippath + File.separator + entry.getName().substring(entry.getName().lastIndexOf("\\") + 1));
				if (!outFile.getParentFile().exists()) {
					outFile.getParentFile().mkdir();
				}
				if (!outFile.exists()) {
					outFile.createNewFile();
				}
				input = new BufferedInputStream(zipFile.getInputStream(entry));
				output = new BufferedOutputStream(new FileOutputStream(outFile));
				int lenth = 0;
				byte[] bs = new byte[1024];
				while ((lenth = input.read(bs)) > 0) {
					output.write(bs, 0, lenth);
				}
				input.close();
				output.close();
			}
			zipInput.close();
			zipFile.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void recoverBackup(String backupId) {
		// 自动创建备份
		newBackup("/file/document", "/file/backup");
		// 先删除/file/document下的所有文件
		String dir = FileUtils.getRealPath("/file/document");
		deleteByDir(dir);
		// 恢复备份
		ZipContraMultiFile(FileUtils.getRealPath(documentBackupDao.findByKey(Long.valueOf(backupId)).getPath()), dir);
	}

	public void deleteByDir(String dir) {
		File file = new File(dir);
		if (!file.isDirectory()) {
			System.out.println(dir + "不是文件夹");
			return;
		}

		try {
			org.apache.commons.io.FileUtils.deleteDirectory(file);
			file.mkdir();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void download(String id, HttpServletRequest request, HttpServletResponse response) {
		long idLong = Long.valueOf(id);
		EDocumentBackup eDocumentBackup = documentBackupDao.findByKey(idLong);
		File file = new File(FileUtils.getRealPath(eDocumentBackup.getPath()));
		try {
			InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

			response.setContentType("text/html;charset=UTF-8");
			request.setCharacterEncoding("UTF-8");
			response.setHeader("Content-disposition",
					"attachment;filename=" + new String(file.getName().trim().getBytes("utf-8"), "iso8859-1")); // 返回头
			// 文件名
			response.setHeader("Content-Length", "" + file.length()); // 返回头
			// 文件大小
			response.setContentType("application/octet-stream"); // 设置数据种类获取返回体输出权
			OutputStream os = new BufferedOutputStream(response.getOutputStream());

			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = inputStream.read(buff, 0, buff.length))) {
				os.write(buff, 0, bytesRead);
			}
			inputStream.close();
			os.close();
			eDocumentBackup.setDownloadTimes(eDocumentBackup.getDownloadTimes() + 1);
			documentBackupDao.update(eDocumentBackup);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void deleteBackup(String id) {
		long idLong = Long.valueOf(id);
		EDocumentBackup eDocumentBackup = documentBackupDao.findByKey(idLong);
		// 从文件服务器中删除
		FileUtils.deleteFileByPath(eDocumentBackup.getPath());

		documentBackupDao.delete(eDocumentBackup);
	}

	/**
	 * 对文件服务器中的备份进行加密
	 * 
	 * @return
	 */
	public String encrypt(String idStr) {
		Long id = Long.parseLong(idStr);
		EDocumentBackup eDocumentBackup = documentBackupDao.findByKey(id);
		String path = eDocumentBackup.getPath();
		String realPath = edu.buaa.sem.utils.FileUtils.getRealPath(path);
		File file = new File(realPath);
		try {
			byte[] bytes = org.apache.commons.io.FileUtils.readFileToByteArray(file);
			AES aes = new AES();
			byte[] key = aes.getKey();
			eDocumentBackup.setEncryptionStatus("cipher");
			eDocumentBackup.setEncryptMethod("AES");

			System.out.println(Base64.encodeBase64String(key));
			eDocumentBackup.setEncryptCode(Base64.encodeBase64String(key));

			org.apache.commons.io.FileUtils.writeByteArrayToFile(file, aes.encrypt(bytes, key, key));
			documentBackupDao.update(eDocumentBackup);

			return "success";
		} catch (IOException e) {
			e.printStackTrace();
			return "failed";
		}

	}

	public String decrypt(String idStr) {
		Long id = Long.parseLong(idStr);
		EDocumentBackup eDocumentBackup = documentBackupDao.findByKey(id);
		String path = eDocumentBackup.getPath();
		String realPath = edu.buaa.sem.utils.FileUtils.getRealPath(path);
		File file = new File(realPath);
		try {
			byte[] bytes = org.apache.commons.io.FileUtils.readFileToByteArray(file);
			AES aes = new AES();
			byte[] key = Base64.decodeBase64(eDocumentBackup.getEncryptCode());
			long length = Long.valueOf(eDocumentBackup.getSize().replaceAll("bytes", ""));
			byte[] plaintextByte = new byte[(int) length];// 此处long转成了int可能损失精度

			eDocumentBackup.setEncryptionStatus("plain");
			eDocumentBackup.setEncryptMethod("");
			eDocumentBackup.setEncryptCode("");
			documentBackupDao.update(eDocumentBackup);
			byte[] plain = aes.decrypt(bytes, key, key);
			System.arraycopy(plain, 0, plaintextByte, 0, (int) length);// 此处long转成了int可能损失精度
			org.apache.commons.io.FileUtils.writeByteArrayToFile(file, plaintextByte);
			return "success";
		} catch (IOException e) {
			e.printStackTrace();
			return "failed";
		}

	}
}
