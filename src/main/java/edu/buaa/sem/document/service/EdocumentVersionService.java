package edu.buaa.sem.document.service;

import edu.buaa.sem.document.dao.EDocumentVersionManagementDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EdocumentVersionService {

    @Autowired
    private EDocumentVersionManagementDao eDocumentVersionManagementDao;

    public Map<String, Object> findByParamsForPagination(EDocumentVersionManagement eDocumentVersionManagement,
                                                         Map<String, Object> conditionString, String rows, String page, String order, String sort) {
        Map<String, Object> responseJson = new HashMap<>();
        List list = eDocumentVersionManagementDao.findByParamsForPagination(eDocumentVersionManagement, conditionString,
                rows, page, order, sort);
        responseJson.put("rows", list);
        responseJson.put("total", eDocumentVersionManagementDao.countByParams(eDocumentVersionManagement,
                conditionString, rows, page, order, sort));
        return responseJson;
    }

}
