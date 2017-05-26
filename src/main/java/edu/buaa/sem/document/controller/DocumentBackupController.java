package edu.buaa.sem.document.controller;

import edu.buaa.sem.document.service.DocumentBackupService;
import edu.buaa.sem.po.EDocumentBackup;
import edu.buaa.sem.system.model.DatagridModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Map;

@Controller
@RequestMapping(value = "admin/document/backup")
public class DocumentBackupController {

    @Autowired
    private DocumentBackupService documentBackupService;

    @RequestMapping(value = "/backupManagement")
    public String backupManagement() {
        return "admin/document/backupManagement";
    }

    @RequestMapping(value = "/findByParamsForPagination")
    @ResponseBody
    public Map<String, Object> findByParamsForPagination(@Valid DatagridModel datagridModel,
                                                         @Valid EDocumentBackup eDocumentBackup) {
        Map<String, Object> responseJson = documentBackupService.findByParamsForPagination(eDocumentBackup,
                datagridModel.getRows(), datagridModel.getPage(), datagridModel.getOrder(), datagridModel.getSort());
        return responseJson;
    }

    /**
     * 新建备份
     *
     * @param documentDir
     * @param backupDir
     */
    @RequestMapping(value = "/newBackup")
    @ResponseBody
    public void newBackup(
            @RequestParam(value = "documentDir", required = false, defaultValue = "/file/document") String documentDir,
            @RequestParam(value = "backupDir", required = false, defaultValue = "/file/backup") String backupDir) {
        documentBackupService.newBackup(documentDir, backupDir);
    }

    /**
     * 恢复备份
     *
     * @param backupId
     */
    @RequestMapping(value = "/recoverBackup")
    @ResponseBody
    public void recoverBackup(@RequestParam(value = "backupId", required = true) String backupId) {
        documentBackupService.recoverBackup(backupId);
    }

    /**
     * 下载备份
     *
     * @param id
     * @param request
     * @param response
     */
    @RequestMapping(value = "/download")
    @ResponseBody
    public void download(@RequestParam(value = "id", required = true) String id, HttpServletRequest request,
                         HttpServletResponse response) {
        documentBackupService.download(id, request, response);
    }

    /**
     * 删除备份
     *
     * @param id
     */
    @RequestMapping(value = "/deleteBackup")
    @ResponseBody
    public void deleteBackup(@RequestParam(value = "id", required = true) String id) {
        documentBackupService.deleteBackup(id);
    }

    /**
     * 备份加密
     *
     * @param id
     */
    @RequestMapping(value = "/encrypt")
    @ResponseBody
    public void encrypt(@RequestParam(value = "id", required = true) String id) {
        documentBackupService.encrypt(id);
    }

    /**
     * 备份解密
     *
     * @param id
     */
    @RequestMapping(value = "/decrypt")
    @ResponseBody
    public void decrypt(@RequestParam(value = "id", required = true) String id) {
        documentBackupService.decrypt(id);
    }

}
