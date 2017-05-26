package edu.buaa.sem.document.controller;

import edu.buaa.sem.document.service.EDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/admin/document")
public class EDocumentViewController {

	@Autowired
	private EDocumentService eDocumentService;

	@RequestMapping(value = "/documentView", method = RequestMethod.GET)
	public String documentView() {
		return "admin/document/documentView";
	}

	@RequestMapping(value = "/findByParamsForPagination", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> findByParamsForPagination(@Valid EDocument eDocument,
			@RequestParam(value = "rows", required = false, defaultValue = "20") String rows,
			@RequestParam(value = "page", required = false, defaultValue = "1") String page,
			@RequestParam(value = "order", required = false, defaultValue = "asc") String order,
			@RequestParam(value = "sort", required = false, defaultValue = "id") String sort) {

		Map<String, Object> conditionString = new HashMap<>();
		Map<String, Object> responseJson = eDocumentService.findByParamsForPagination(eDocument, conditionString, rows,
				page, order, sort);
		return responseJson;

	}

	/**
	 * 根据文档id对文档进行加密
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/encrypt", method = RequestMethod.POST)
	@ResponseBody
	public String encrypt(@RequestParam(value = "id", required = true) String id) {
		String result = eDocumentService.encypt(id);
		return result;
	}

	@RequestMapping(value = "/decrypt", method = RequestMethod.POST)
	@ResponseBody
	public String decrypt(@RequestParam(value = "id", required = true) String id) {
		String result = eDocumentService.decypt(id);
		return result;
	}

	@RequestMapping(value = "/shareDocument", method = RequestMethod.POST)
	@ResponseBody
	public String shareDocument(@RequestParam(value = "id", required = true) String id) {
		if (!eDocumentService.validateUploadUser(id)) {
			return "no authority";
		}
		eDocumentService.shareDocument(id);
		return "success";
	}

	@RequestMapping(value = "/cancelShareDocument", method = RequestMethod.POST)
	@ResponseBody
	public String cancelShareDocument(@RequestParam(value = "id", required = true) String id) {
		if (!eDocumentService.validateUploadUser(id)) {
			return "no authority";
		}
		eDocumentService.cancelShareDocument(id);
		return "success";
	}
}
