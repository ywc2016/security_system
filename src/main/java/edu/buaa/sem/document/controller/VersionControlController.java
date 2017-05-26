package edu.buaa.sem.document.controller;

import edu.buaa.sem.document.service.EdocumentVersionService;
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
@RequestMapping(value = "/admin/documentVersion")
public class VersionControlController {

	@Autowired
	private EdocumentVersionService edocumentVersionService;

	@RequestMapping(value = "/versionControl", method = RequestMethod.GET)
	public String versionControl() {
		return "/admin/document/documentVersion";
	}

	@RequestMapping(value = "/findByParamsForPagination", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> findByParamsForPagination(@Valid EDocumentVersionManagement eDocumentVersionManagement,
			@RequestParam(value = "rows", required = false, defaultValue = "20") String rows,
			@RequestParam(value = "page", required = false, defaultValue = "1") String page,
			@RequestParam(value = "order", required = false, defaultValue = "asc") String order,
			@RequestParam(value = "sort", required = false, defaultValue = "id") String sort) {

		Map<String, Object> conditionString = new HashMap<>();
		Map<String, Object> responseJson = edocumentVersionService.findByParamsForPagination(eDocumentVersionManagement,
				conditionString, rows, page, order, sort);
		return responseJson;

	}
}
