package edu.buaa.sem.document.service;

import static org.mockito.Matchers.longThat;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import edu.buaa.sem.common.BaseService;
import edu.buaa.sem.document.dao.EDocumentDao;
import edu.buaa.sem.document.dao.EDocumentVersionManagementDao;
import edu.buaa.sem.utils.FileUtils;

@Service
public class UploadDocumentService extends BaseService {
	@Autowired
	private EDocumentDao eDocumentDao;

	@Autowired
	private EDocumentVersionManagementDao eDocumentVersionManagementDao;

	private Logger logger;

	public UploadDocumentService() {
		this.logger = Logger.getLogger(this.getClass());
	}

	/**
	 * 多文件上传，为保障安全性，需事先进行文件格式和大小验证
	 * 
	 * @param savePath
	 *            文件保存的路径
	 * @param fileData
	 *            文件
	 * @return
	 */
	public HashMap<String, Object> uploadFiles(String savePath, MultipartFile fileData) {
		HashMap<String, Object> responseJson = new HashMap<String, Object>();
		String fileName = fileData.getOriginalFilename();// 获取原文件名
		String fileNameWithoutSuffix = fileName.substring(0, fileName.lastIndexOf(".")).replace(" ", "");
		String suffix = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
		boolean judge = false;// 标识文件格式是否正确
		String[] docType = { "doc", "docx", "ppt", "pptx", "xls", "xlsx", "pdf", "rtf" };
		String[] imageType = { "jpg", "jpeg", "bmp", "gif", "png" };
		for (String s : docType) {
			if (suffix.equals(s)) {
				judge = true;
				break;
			}
		}
		for (String s : imageType) {
			if (suffix.equals(s)) {
				judge = true;
				break;
			}
		}

		// TODO 不验证文件类型
		judge = true;

		SysUser sysUser = getCurrentUser();

		String path = "error";
		if (judge && fileData.getSize() < 20 * 1024 * 1024) {
			// 重命名文件
			logger.info(sysUser.getName() + "--" + sysUser.getIp() + "--upload file start");
			path = FileUtils.uploadWithRename(savePath, fileData);
		}
		if (!path.equals("error")) {
			responseJson.put("success", true);
			responseJson.put("fileTitle", fileNameWithoutSuffix);
			responseJson.put("suffix", suffix);
			responseJson.put("filePath", path);
			responseJson.put("msg", "上传成功");
			logger.info(sysUser.getName() + "--" + sysUser.getIp() + "--upload file " + path + " success");
		} else {
			responseJson.put("success", false);
			responseJson.put("msg", "文件过大或格式错误！");
			logger.info(sysUser.getName() + "--" + sysUser.getIp() + "--upload file " + path + " failed");

		}
		return responseJson;
	}

	public String deleteFiles(String filePath) throws UnsupportedEncodingException {
		SysUser sysUser = getCurrentUser();
		// encodeURIComponent解码
		filePath = URLDecoder.decode(filePath, "UTF-8");
		FileUtils.deleteFileByPath(filePath);
		logger.info(sysUser.getName() + "--" + sysUser.getIp() + "--delete file failed");

		return E.SUCCESS();
	}

	public HashMap<String, Object> saveDocument(String[] urls, String[] names, String[] abstracts)
			throws UnsupportedEncodingException {
		SysUser sysUser = getCurrentUser();
		HashMap<String, Object> reponseJson = new HashMap<>();
		for (int i = 0; i < urls.length; i++) {
			EDocument eDocument = new EDocument();
			eDocument.setUploadTime(new Date());
			eDocument.setNumber("DOC-" + eDocument.getUploadTime().getTime());
			eDocument.setDownloadTimes(0); // 前台编码,后台需要解码
			eDocument.setName(java.net.URLDecoder.decode(names[i], "UTF-8").trim());
			eDocument.setAbstracts(java.net.URLDecoder.decode(abstracts[i], "UTF-8").trim());
			eDocument.setPath(java.net.URLDecoder.decode(urls[i], "UTF-8").trim());
			eDocument.setDownloadTimes(0);
			eDocument.setUploadTime(new Date());
			eDocument.setEncryptionStatus("plain");
			eDocument.setAuditStatus("待审核");
			eDocument.setShareStatus("未共享");
			eDocument.setUploadUserId(sysUser.getId());
			// 记录文件大小和类型
			File file = new File(FileUtils.getRealPath(eDocument.getPath()));
			eDocument.setSize("" + file.length() + "bytes");
			eDocument.setType(eDocument.getPath().substring(eDocument.getPath().lastIndexOf(".")));
			String result = saveOrUpdateDocument(eDocument, eDocument.getNumber());
			if (result.equals(E.SUCCESS())) {
				reponseJson.put("isSuccessful", true);
				reponseJson.put("msg", "添加成功.");
				logger.debug(sysUser.getName() + "--" + sysUser.getIp() + "--saveDocument successfully");

			} else {
				reponseJson.put("isSuccessful", false);
				reponseJson.put("msg", "操作失败.");
				logger.debug(sysUser.getName() + "--" + sysUser.getIp() + "--saveDocument failed");
			}
		}
		return reponseJson;
	}

	/**
	 * 检验附件记录是否存在，如果存在，则更新记录，不存在则插入一条新的记录
	 * 
	 * 
	 * @param pojo
	 * @param number再保附件编号
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String saveOrUpdateDocument(EDocument pojo, String number) throws UnsupportedEncodingException {
		if (pojo.getPath() != null) {
			// 保存一个版本
			EDocumentVersionManagement eDocumentVersionManagement = new EDocumentVersionManagement();
			eDocumentVersionManagement.setDownloadTimes(0);
			eDocumentVersionManagement.setEarliestUploadTime(pojo.getUploadTime());
			eDocumentVersionManagement.setExistEditions(1);
			eDocumentVersionManagement.setLatestUploadTime(new Date());
			eDocumentVersionManagement.setNumber("VER-" + eDocumentVersionManagement.getEarliestUploadTime());
			eDocumentVersionManagement.setViewTimes(0);
			eDocumentVersionManagement.setName(pojo.getName());
			eDocumentVersionManagement.setType(pojo.getPath().substring(pojo.getPath().lastIndexOf(".")));
			eDocumentVersionManagementDao.saveOrUpdate(eDocumentVersionManagement);

			pojo.setEditionId(eDocumentVersionManagement.getId());
			// 为文件地址加上下划线，表示有效文件
			String path = FileUtils.renameFileWithUnderline(pojo, eDocumentVersionManagement);
			if (path.equals("error")) {
				return E.ERROR();
			}
			pojo.setPath(path);

			// 检验附件记录是否存在，如果存在，则更新记录，不存在则插入一条新的记录
			List<EDocument> pojos = eDocumentDao.findDocumentByInfCompanyNumberAndUrl(number, pojo.getPath());
			if (pojos != null && pojos.size() > 0) {
				pojo.setId(pojos.get(0).getId());
			}
			eDocumentDao.saveOrUpdate(pojo);
			return E.SUCCESS();
		}
		return E.ERROR();
	}

	/**
	 * 根据文档id下载文档
	 * 
	 * @param id
	 */
	public void download(String id, HttpServletRequest request, HttpServletResponse response) {

		SysUser sysUser = getCurrentUser();
		EDocument eDocument = eDocumentDao.findByKey(Long.valueOf(id));
		File file = new File(FileUtils.getRealPath(eDocument.getPath()));
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
			eDocument.setDownloadTimes(eDocument.getDownloadTimes() + 1);
			eDocumentDao.update(eDocument);
			logger.info(sysUser.getName() + "--" + sysUser.getLoginIp() + "--downloadDocument successfully");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public HashMap<String, Object> addNewVersion(String[] urls, String[] names, String[] abstracts, String versionId)
			throws UnsupportedEncodingException {
		SysUser sysUser = getCurrentUser();
		HashMap<String, Object> reponseJson = new HashMap<>();
		for (int i = 0; i < urls.length; i++) {
			EDocument eDocument = new EDocument();
			eDocument.setUploadTime(new Date());
			eDocument.setNumber("DOC-" + eDocument.getUploadTime().getTime());
			eDocument.setDownloadTimes(0); // 前台编码,后台需要解码
			eDocument.setName(java.net.URLDecoder.decode(names[i], "UTF-8").trim());
			eDocument.setAbstracts(java.net.URLDecoder.decode(abstracts[i], "UTF-8").trim());
			eDocument.setPath(java.net.URLDecoder.decode(urls[i], "UTF-8").trim());
			eDocument.setDownloadTimes(0);
			eDocument.setUploadTime(new Date());
			eDocument.setEncryptionStatus("plain");
			eDocument.setAuditStatus("待审核");
			eDocument.setShareStatus("未共享");
			eDocument.setUploadUserId(sysUser.getId());
			// 记录文件大小和类型
			File file = new File(FileUtils.getRealPath(eDocument.getPath()));
			eDocument.setSize("" + file.length() + "bytes");
			eDocument.setType(eDocument.getPath().substring(eDocument.getPath().lastIndexOf(".")));
			String result = saveOrUpdateDocument(eDocument,
					eDocumentVersionManagementDao.findByKey(Long.valueOf(versionId)));
			if (result.equals(E.SUCCESS())) {
				reponseJson.put("isSuccessful", true);
				reponseJson.put("msg", "添加成功.");
			} else {
				reponseJson.put("isSuccessful", false);
				reponseJson.put("msg", "操作失败.");
			}
		}
		return reponseJson;

	}

	/**
	 * 添加一个新版本
	 * 
	 * @param eDocument
	 * @param eDocumentVersionManagement
	 * @return
	 */
	private String saveOrUpdateDocument(EDocument eDocument, EDocumentVersionManagement eDocumentVersionManagement) {

		if (eDocument.getPath() != null) {
			// 保存一个版本
			eDocumentVersionManagement.setExistEditions(eDocumentVersionManagement.getExistEditions() + 1);
			eDocumentVersionManagement.setLatestUploadTime(eDocument.getUploadTime());
			eDocumentVersionManagementDao.saveOrUpdate(eDocumentVersionManagement);
			eDocument.setEditionId(eDocumentVersionManagement.getId());
			// 为文件地址加上下划线，表示有效文件
			String path = FileUtils.renameFileWithUnderline(eDocument, eDocumentVersionManagement);
			if (path.equals("error")) {
				return E.ERROR();
			}
			eDocument.setPath(path);

			// 检验附件记录是否存在，如果存在，则更新记录，不存在则插入一条新的记录
			List<EDocument> pojos = eDocumentDao.findDocumentByInfCompanyNumberAndUrl(eDocument.getNumber(),
					eDocument.getPath());
			if (pojos != null && pojos.size() > 0) {
				eDocument.setId(pojos.get(0).getId());
			}
			eDocumentDao.saveOrUpdate(eDocument);
			return E.SUCCESS();
		}
		return E.ERROR();
	}

	public void deleteDocumentRecord(String id) {
		long _id = Long.valueOf(id);
		EDocument eDocument = eDocumentDao.findByKey(_id);
		if (eDocument != null) {
			eDocumentDao.delete(eDocument);
		}
	}

}
