package edu.buaa.sem.document.service;

import edu.buaa.sem.common.BaseService;
import edu.buaa.sem.document.dao.EDocumentDao;
import edu.buaa.sem.utils.AES;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EDocumentService extends BaseService {
	@Autowired
	private EDocumentDao eDocumentDao;
	private Logger logger;

	public EDocumentService() {
		this.logger = Logger.getLogger(this.getClass());
	}

	public Map<String, Object> findByParamsForPagination(EDocument eDocument, Map<String, Object> conditionString,
														 String rows, String page, String order, String sort) {
		Map<String, Object> responseJson = new HashMap<>();
		List list = eDocumentDao.findByParamsForPagination(eDocument, conditionString, rows, page, order, sort);
		responseJson.put("rows", list);
		responseJson.put("total", eDocumentDao.countByParams(eDocument, conditionString, rows, page, order, sort));
		return responseJson;
	}

	/**
	 * 对文件服务器中的文件进行加密
	 * 
	 * @return
	 */
	public String encypt(String idStr) {
		SysUser sysUser = getCurrentUser();
		Long id = Long.parseLong(idStr);
		EDocument eDocument = eDocumentDao.findByKey(id);
		String path = eDocument.getPath();
		String realPath = edu.buaa.sem.utils.FileUtils.getRealPath(path);
		File file = new File(realPath);
		try {
			byte[] bytes = FileUtils.readFileToByteArray(file);
			AES aes = new AES();
			byte[] key = aes.getKey();
			eDocument.setEncryptionStatus("cipher");
			eDocument.setEncryptMethod("AES");

			System.out.println(Base64.encodeBase64String(key));
			eDocument.setEncryptCode(Base64.encodeBase64String(key));

			FileUtils.writeByteArrayToFile(file, aes.encrypt(bytes, key, key));
			// eDocument.setSize("" + file.length() + "bytes");
			eDocumentDao.update(eDocument);
			logger.info(sysUser.getName() + "--" + sysUser.getLoginIp() + "--encypt success.");
			return "success";
		} catch (IOException e) {
			logger.info(sysUser.getName() + "--" + sysUser.getLoginIp() + "--encypt failed.");
			e.printStackTrace();
			return "failed";
		}

	}

	public String decypt(String idStr) {
		SysUser sysUser = getCurrentUser();
		Long id = Long.parseLong(idStr);
		EDocument eDocument = eDocumentDao.findByKey(id);
		String path = eDocument.getPath();
		String realPath = edu.buaa.sem.utils.FileUtils.getRealPath(path);
		File file = new File(realPath);
		try {
			byte[] bytes = FileUtils.readFileToByteArray(file);
			AES aes = new AES();
			byte[] key = Base64.decodeBase64(eDocument.getEncryptCode());
			long length = Long.valueOf(eDocument.getSize().replaceAll("bytes", ""));
			byte[] plaintextByte = new byte[(int) length];// 此处long转成了int可能损失精度

			eDocument.setEncryptionStatus("plain");
			eDocument.setEncryptMethod("");
			eDocument.setEncryptCode("");
			eDocumentDao.update(eDocument);
			byte[] plain = aes.decrypt(bytes, key, key);
			System.arraycopy(plain, 0, plaintextByte, 0, (int) length);// 此处long转成了int可能损失精度
			FileUtils.writeByteArrayToFile(file, plaintextByte);
			logger.info(sysUser.getName() + "--" + sysUser.getLoginIp() + "--decypt success.");
			return "success";
		} catch (IOException e) {
			logger.info(sysUser.getName() + "--" + sysUser.getLoginIp() + "--decypt failed.");
			e.printStackTrace();
			return "failed";
		}

	}

	public void passAudit(String idStr) {
		EDocument eDocument = eDocumentDao.findByKey(Long.valueOf(idStr));
		eDocument.setAuditStatus("通过");
		eDocumentDao.update(eDocument);
	}

	public void notPassAudit(String idStr) {
		EDocument eDocument = eDocumentDao.findByKey(Long.valueOf(idStr));
		eDocument.setAuditStatus("未通过");
		eDocumentDao.update(eDocument);
	}

	public void shareDocument(String id) {
		long idLong = Long.valueOf(id);
		EDocument eDocument = eDocumentDao.findByKey(idLong);
		eDocument.setShareStatus("正在共享");
		eDocumentDao.update(eDocument);
	}

	public void cancelShareDocument(String id) {
		long idLong = Long.valueOf(id);
		EDocument eDocument = eDocumentDao.findByKey(idLong);
		eDocument.setShareStatus("未共享");
		eDocumentDao.update(eDocument);
	}

	public boolean validateUploadUser(String id) {
		SysUser sysUser = getCurrentUser();
		long idLong = Long.valueOf(id);
		EDocument eDocument = eDocumentDao.findByKey(idLong);
		if (eDocument.getUploadUserId() == sysUser.getId()) {
			return true;
		} else {
			return false;
		}
	}
}
