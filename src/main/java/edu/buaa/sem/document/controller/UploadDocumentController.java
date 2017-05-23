package edu.buaa.sem.document.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import edu.buaa.sem.document.service.UploadDocumentService;
import edu.buaa.sem.system.service.SysUserService;

@Controller
@RequestMapping(value = "/admin/document")
public class UploadDocumentController {
	@Autowired
	private UploadDocumentService uploadDocumentService;
	@Autowired
	private SysUserService sysUserService;

	/**
	 * 上传多个文件
	 * 
	 * @param savePath
	 * @param Filedata
	 * @return String类型 拼接的JSON字符串
	 */
	@RequestMapping(value = "/uploadFiles", produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String uploadFiles(
			@RequestParam(value = "savePath", required = false, defaultValue = "/file/document/") String savePath,
			@RequestParam(value = "Filedata", required = false) MultipartFile Filedata) {
		HashMap<String, Object> responseJson = new HashMap<String, Object>();
		responseJson = uploadDocumentService.uploadFiles(savePath, Filedata);
		String jsonStr = "{";
		Set<String> keySet = responseJson.keySet();
		for (Object key : keySet) {
			jsonStr += "\"" + key + "\":\"" + responseJson.get(key) + "\",";
		}
		jsonStr = jsonStr.substring(0, jsonStr.length() - 1);
		jsonStr += "}";
		return jsonStr;
	}

	@RequestMapping("/deleteDocument")
	@ResponseBody
	public String deleteDocument(@RequestParam(value = "filePath", required = true) String filePath,
			@RequestParam(value = "id", required = false) String id) throws UnsupportedEncodingException {
		uploadDocumentService.deleteFiles(filePath);
		if (id != null) {// 需要在数据库中删除记录
			uploadDocumentService.deleteDocumentRecord(id);
		}
		return "success";
	}

	@RequestMapping(value = "/saveDocument", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveDocument(
			@RequestParam(value = "upload_file_name_commaString", required = false) String upload_file_name_commaString,
			@RequestParam(value = "upload_file_path_commaString", required = false) String upload_file_path_commaString,
			@RequestParam(value = "upload_file_abstracts_commaString", required = false) String upload_file_abstracts_commaString,
			@RequestParam(value = "versionId", required = false) String versionId) throws UnsupportedEncodingException {

		String[] urls = upload_file_path_commaString.split(",");
		String[] names = upload_file_name_commaString.split(",");
		String[] abstracts = upload_file_abstracts_commaString.split(",");
		HashMap<String, Object> reponseJson = null;
		if (versionId == null || versionId.isEmpty()) {// 新的文档
			reponseJson = uploadDocumentService.saveDocument(urls, names, abstracts);
		} else {// 新的版本
			reponseJson = uploadDocumentService.addNewVersion(urls, names, abstracts, versionId);
		}
		return reponseJson;
	}

	/**
	 * 根据文档id下载文档
	 * 
	 * @param id
	 */
	@RequestMapping(value = "/download", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView download(@RequestParam(value = "id", required = false) String id, HttpServletRequest request,
			HttpServletResponse response) {
		if (sysUserService.getCurrentUser() == null) {
			return null;
		}
		uploadDocumentService.download(id, request, response);
		return null;
	}

}
