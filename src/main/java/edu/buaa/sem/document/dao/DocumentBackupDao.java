package edu.buaa.sem.document.dao;

import edu.buaa.sem.common.BaseDao;
import edu.buaa.sem.common.IDao;
import edu.buaa.sem.po.EDocumentBackup;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DocumentBackupDao extends BaseDao<EDocumentBackup> implements IDao<EDocumentBackup> {

    public DocumentBackupDao() {
        this.logger = Logger.getLogger(EDocumentBackup.class);
    }

    @Override
    public void updateByKeys(EDocumentBackup pojo, String key, long[] ids) {
        // TODO Auto-generated method stub

    }

    public List<EDocumentBackup> findByParamsForPagination(EDocumentBackup eDocumentBackup, String page, String rows,
                                                           String sort, String order) {

        int intRows, intPage;
        if (rows == null || rows.isEmpty()) {
            intRows = Integer.valueOf(DEFAULT_PAGE_ROWS);
        } else {
            intRows = Integer.valueOf(rows);
        }

        if (page == null || page.isEmpty()) {
            intPage = Integer.valueOf(DEFAULT_PAGE_START);
        } else {
            intPage = Integer.valueOf(page);
        }

        String queryString = "from EDocumentBackup as a where 1=1 ";
        Query query = constructQuery(eDocumentBackup, queryString, order, sort);
        query.setFirstResult((intPage - 1) * intRows);
        query.setMaxResults(intRows);
        List<EDocumentBackup> pojos = query.list();
        logger.debug("findByParamsForPagination successfully.");
        return pojos;
    }

    public long countByParams(EDocumentBackup eDocumentBackup) {
        String queryString = "select count(*) from EDocumentBackup as a where 1=1 ";
        Query query = constructQuery(eDocumentBackup, queryString, null, null);
        List pojos = query.list();
        logger.debug("countByParams successfully.");
        return (Long) pojos.get(0);
    }

    private Query constructQuery(EDocumentBackup eDocumentBackup, String queryString, String order, String sort) {

        if (eDocumentBackup.getAbstracts() != null && !eDocumentBackup.getAbstracts().isEmpty()) {
            queryString += " and a.abstracts = : abstracts";
        }
        if (eDocumentBackup.getBackupTime() != null) {
            queryString += " and a.backupTime = :backupTime";
        }
        if (eDocumentBackup.getDownloadTimes() != null) {
            queryString += " and a.downloadTimes = :downloadTimes";
        }
        if (eDocumentBackup.getEncryptCode() != null && !eDocumentBackup.getEncryptCode().isEmpty()) {
            queryString += " and a.encryptCode = :encryptCode";
        }
        if (eDocumentBackup.getEncryptionStatus() != null && !eDocumentBackup.getEncryptionStatus().isEmpty()) {
            queryString += " and a.encryptionStatus = :encryptionStatus";
        }
        if (eDocumentBackup.getEncryptMethod() != null && !eDocumentBackup.getEncryptMethod().isEmpty()) {
            queryString += " and a.encryptMethod = :encryptMethod";
        }
        if (0 != eDocumentBackup.getId()) {
            queryString += " and a.id = :id";
        }
        if (eDocumentBackup.getName() != null && !eDocumentBackup.getName().isEmpty()) {
            queryString += " and a.name = :name";
        }
        if (eDocumentBackup.getNumber() != null && !eDocumentBackup.getNumber().isEmpty()) {
            queryString += " and a.number = :number";
        }
        if (eDocumentBackup.getPath() != null && !eDocumentBackup.getPath().isEmpty()) {
            queryString += " and a.path = :path";
        }
        if (eDocumentBackup.getSize() != null && !eDocumentBackup.getSize().isEmpty()) {
            queryString += " and a.size =:size";
        }
        if (sort != null && !sort.isEmpty()) {
            queryString += " order by a." + sort + "  ";
        }
        if (order != null && !order.isEmpty()) {
            queryString += " " + order;
        }
        Query query = sessionFactory.getCurrentSession().createQuery(queryString);
        // ---------------------下面是赋值操作------------------------
        if (eDocumentBackup.getAbstracts() != null && !eDocumentBackup.getAbstracts().isEmpty()) {
            query.setString("abstracts", eDocumentBackup.getAbstracts());
        }
        if (eDocumentBackup.getBackupTime() != null) {
            query.setDate("backupTime", eDocumentBackup.getBackupTime());
        }
        if (eDocumentBackup.getDownloadTimes() != null) {
            query.setInteger("downloadTimes", eDocumentBackup.getDownloadTimes());
        }
        if (eDocumentBackup.getEncryptCode() != null && !eDocumentBackup.getEncryptCode().isEmpty()) {
            query.setString("encryptCode", eDocumentBackup.getEncryptCode());
        }
        if (eDocumentBackup.getEncryptionStatus() != null && !eDocumentBackup.getEncryptionStatus().isEmpty()) {
            query.setString("encryptionStatus", eDocumentBackup.getEncryptionStatus());
        }
        if (eDocumentBackup.getEncryptMethod() != null && !eDocumentBackup.getEncryptMethod().isEmpty()) {
            query.setString("encryptMethod", eDocumentBackup.getEncryptMethod());
        }
        if (0 != eDocumentBackup.getId()) {
            query.setLong("id", eDocumentBackup.getId());
        }
        if (eDocumentBackup.getName() != null && !eDocumentBackup.getName().isEmpty()) {
            query.setString("name", eDocumentBackup.getName());
        }
        if (eDocumentBackup.getNumber() != null && !eDocumentBackup.getNumber().isEmpty()) {
            query.setString("number", eDocumentBackup.getNumber());
        }
        if (eDocumentBackup.getPath() != null && !eDocumentBackup.getPath().isEmpty()) {
            query.setString("path", eDocumentBackup.getPath());
        }
        if (eDocumentBackup.getSize() != null && !eDocumentBackup.getSize().isEmpty()) {
            query.setString("size", eDocumentBackup.getSize());
        }
        return query;
    }

}
