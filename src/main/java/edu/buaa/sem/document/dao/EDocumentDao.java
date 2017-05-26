package edu.buaa.sem.document.dao;

import edu.buaa.sem.common.BaseDao;
import edu.buaa.sem.common.IDao;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class EDocumentDao extends BaseDao<EDocument> implements IDao<EDocument> {

    @Override
    public void updateByKeys(EDocument pojo, String key, long[] ids) {

    }

    public EDocumentDao() {
        this.logger = Logger.getLogger(this.getClass());
    }

    public List findByParamsForPagination(EDocument eDocument, Map<String, Object> conditionString, String rows,
                                          String page, String order, String sort) {
        try {
            int intPage = Integer.parseInt((page == null || page.equals("0")) ? DEFAULT_PAGE_START : page);
            int intRows = Integer.parseInt((rows == null || rows.equals("0")) ? DEFAULT_PAGE_ROWS : rows);
            String queryString = "from EDocument as a where 1=1 ";
            Query query = contrustString(eDocument, conditionString, queryString, sort, order);
            query.setFirstResult((intPage - 1) * intRows);
            query.setMaxResults(intRows);
            List pojos = query.list();
            logger.debug("findByParamsForPagination successfully.");
            return pojos;
        } catch (RuntimeException re) {
            logger.error("findByParamsForPagination failed");
            throw re;
        }
    }

    public long countByParams(EDocument eDocument, Map<String, Object> conditionString, String rows, String page,
                              String order, String sort) {
        try {
            String queryString = "select count(*) from EDocument as a where 1=1 ";
            Query query = contrustString(eDocument, conditionString, queryString, sort, order);
            List pojos = query.list();
            logger.debug("countByParams successfully.");
            return (Long) pojos.get(0);
        } catch (RuntimeException re) {
            logger.error("findByParamsForPagination failed");
            throw re;
        }
    }

    private Query contrustString(EDocument eDocument, Map<String, Object> conditionString, String queryString,
                                 String sort, String order) {
        if (eDocument.getDownloadTimes() != null) {
            queryString += " and a.downloadTimes = :downloadTimes";
        }
        if (eDocument.getEncryptCode() != null && !eDocument.getEncryptCode().isEmpty()) {
            queryString += " and a.encryptCode = :encryptCode";
        }
        if (eDocument.getEncryptMethod() != null && !eDocument.getEncryptMethod().isEmpty()) {
            queryString += " and a.encryptMethod = :encryptMethod";
        }
        if (0 != eDocument.getId()) {
            queryString += " and a.id = :id";
        }
        if (eDocument.getName() != null && !eDocument.getName().isEmpty()) {
            queryString += " and a.name like :name";
        }
        if (eDocument.getNumber() != null) {
            queryString += " and a.number = :number";
        }
        if (eDocument.getPath() != null && !eDocument.getPath().isEmpty()) {
            queryString += " and a.path = :path";
        }
        if (eDocument.getSize() != null && !eDocument.getSize().isEmpty()) {
            queryString += " and a.size = :size";
        }
        if (eDocument.getType() != null && !eDocument.getType().isEmpty()) {
            queryString += " and a.type = :type";
        }
        if (eDocument.getUploadTime() != null) {
            queryString += " and a.uploadTime = :uploadTime";
        }
        if (eDocument.getViewTimes() != null) {
            queryString += " and a.viewTimes = :viewTimes";
        }
        if (eDocument.getAbstracts() != null && !eDocument.getAbstracts().isEmpty()) {
            queryString += " and a.abstracts = :abstracts";
        }
        if (eDocument.getEditionId() != null) {
            queryString += " and a.editionId = :editionId";
        }
        if (sort != null && !sort.isEmpty()) {
            queryString += " order by a." + sort + "  " + order;
        }

        Query query = sessionFactory.getCurrentSession().createQuery(queryString);
        // ---------------------下面是赋值操作------------------------

        if (eDocument.getDownloadTimes() != null) {
            query.setInteger("downloadTimes", eDocument.getDownloadTimes());
        }
        if (eDocument.getEncryptCode() != null && !eDocument.getEncryptCode().isEmpty()) {
            query.setString("encryptCode", eDocument.getEncryptCode());
        }
        if (eDocument.getEncryptMethod() != null && !eDocument.getEncryptMethod().isEmpty()) {
            query.setString("encryptMethod", eDocument.getEncryptMethod());
        }
        if (0 != eDocument.getId()) {
            query.setLong("id", eDocument.getId());
        }
        if (eDocument.getName() != null && !eDocument.getName().isEmpty()) {
            query.setString("name", "%" + eDocument.getName() + "%");
        }
        if (eDocument.getNumber() != null) {
            query.setString("number", eDocument.getNumber());
        }
        if (eDocument.getPath() != null && !eDocument.getPath().isEmpty()) {
            query.setString("path", eDocument.getPath());
        }
        if (eDocument.getSize() != null && !eDocument.getSize().isEmpty()) {
            query.setString("size", eDocument.getSize());
        }
        if (eDocument.getType() != null && !eDocument.getType().isEmpty()) {
            query.setString("type", eDocument.getType());
        }
        if (eDocument.getUploadTime() != null) {
            query.setDate("uploadTime", eDocument.getUploadTime());
        }
        if (eDocument.getViewTimes() != null) {
            query.setInteger("viewTimes", eDocument.getViewTimes());
        }
        if (eDocument.getAbstracts() != null && !eDocument.getAbstracts().isEmpty()) {
            query.setString("abstracts", eDocument.getAbstracts());
        }
        if (eDocument.getEditionId() != null) {
            query.setLong("editionId", eDocument.getEditionId());
        }
        return query;
    }

    /**
     * 根据number和path查找Document
     *
     * @param number
     * @param path
     * @return
     */
    public List<EDocument> findDocumentByInfCompanyNumberAndUrl(String number, String path) {
        try {
            String queryString = "from EDocument where 1=1 ";
            if (number != null && !number.isEmpty()) {
                queryString += " and number = :number ";
            }
            if (path != null && !path.isEmpty()) {
                queryString += " and path = :path ";
            }
            Query query = sessionFactory.getCurrentSession().createQuery(queryString);
            if (number != null && !number.isEmpty()) {
                query.setString("number", number);
            }
            if (path != null && !path.isEmpty()) {
                query.setString("path", path);
            }
            List<EDocument> list = query.list();
            logger.debug("findDocumentByInfCompanyNumberAndUrl successful");
            return list;
        } catch (HibernateException e) {
            logger.error("findDocumentByInfCompanyNumberAndUrl failed", e);
            throw e;
        }
    }
}
