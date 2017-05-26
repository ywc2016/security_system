package edu.buaa.sem.audit.controller;

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
@RequestMapping(value = "/admin/audit")
public class AuditManagementController {

	@Autowired
	private EDocumentService eDocumentService;

	@RequestMapping(value = "/auditManagement", method = RequestMethod.GET)
	public String documentView() {
		return "admin/audit/auditManagement";
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

	@RequestMapping(value = "/passAudit")
	@ResponseBody
	public void passAudit(@RequestParam(value = "id", required = true) String id) {
		eDocumentService.passAudit(id);
	}

	@RequestMapping(value = "/notPassAudit")
	@ResponseBody
	public void notPassAudit(@RequestParam(value = "id", required = true) String id) {
		eDocumentService.notPassAudit(id);
	}
}
