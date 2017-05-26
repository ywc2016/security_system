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
public class EDocumentVersionManagementDao extends BaseDao<EDocumentVersionManagement>
        implements IDao<EDocumentVersionManagement> {

    @Override
    public void updateByKeys(EDocumentVersionManagement pojo, String key, long[] ids) {

    }

    public EDocumentVersionManagementDao() {
        this.logger = Logger.getLogger(this.getClass());
    }

    public List findByParamsForPagination(EDocumentVersionManagement eDocumentVersionManagement,
                                          Map<String, Object> conditionString, String rows, String page, String order, String sort) {
        try {
            int intPage = Integer.parseInt((page == null || page.equals("0")) ? DEFAULT_PAGE_START : page);
            int intRows = Integer.parseInt((rows == null || rows.equals("0")) ? DEFAULT_PAGE_ROWS : rows);
            String queryString = "from EDocumentVersionManagement as a where 1=1 ";
            Query query = contrustString(eDocumentVersionManagement, conditionString, queryString, sort, order);
            query.setFirstResult((intPage - 1) * intRows);
            query.setMaxResults(intRows);
            List pojos = query.list();
            logger.debug("findByParamsForPagination successfully");
            return pojos;
        } catch (RuntimeException re) {
            logger.error("findByParamsForPagination failed");
            throw re;
        }
    }

    public long countByParams(EDocumentVersionManagement eDocumentVersionManagement,
                              Map<String, Object> conditionString, String rows, String page, String order, String sort) {
        try {
            String queryString = "select count(*) from EDocumentVersionManagement as a where 1=1 ";
            Query query = contrustString(eDocumentVersionManagement, conditionString, queryString, sort, order);
            List pojos = query.list();
            logger.debug("countByParams successfully");
            return (Long) pojos.get(0);
        } catch (RuntimeException re) {
            logger.error("countByParams failed");
            throw re;
        }
    }

    private Query contrustString(EDocumentVersionManagement eDocumentVersionManagement,
                                 Map<String, Object> conditionString, String queryString, String sort, String order) {
        if (eDocumentVersionManagement.getId() != 0) {
            queryString += " and a.id = :id ";
        }
        if (eDocumentVersionManagement.getNumber() != null && !eDocumentVersionManagement.getNumber().isEmpty()) {
            queryString += " and a.number = :number ";
        }
        if (eDocumentVersionManagement.getDownloadTimes() != null) {
            queryString += " and a.downloadTimes = :downloadTimes";
        }
        if (eDocumentVersionManagement.getEarliestUploadTime() != null) {
            queryString += " and a.earliestUploadTime = :earliestUploadTime";
        }
        if (eDocumentVersionManagement.getExistEditions() != null) {
            queryString += " and a.existEditions = :existEditions ";
        }
        if (eDocumentVersionManagement.getLatestUploadTime() != null) {
            queryString += " and a.latestUploadTime = :latestUploadTime";
        }
        if (eDocumentVersionManagement.getName() != null && !eDocumentVersionManagement.getName().isEmpty()) {
            queryString += " and a.name = :name";
        }
        if (eDocumentVersionManagement.getType() != null && !eDocumentVersionManagement.getType().isEmpty()) {
            queryString += " and a.type = :type";
        }
        if (eDocumentVersionManagement.getViewTimes() != null) {
            queryString += " and a.viewTimes = :viewTimes";
        }
        if (sort != null && !sort.isEmpty()) {
            queryString += " order by a." + sort + "  " + order;
        }

        Query query = sessionFactory.getCurrentSession().createQuery(queryString);
        // ---------------------下面是赋值操作------------------------

        if (eDocumentVersionManagement.getId() != 0) {
            query.setLong("id", eDocumentVersionManagement.getId());
        }
        if (eDocumentVersionManagement.getDownloadTimes() != null) {
            query.setInteger("downloadTimes", eDocumentVersionManagement.getDownloadTimes());
        }
        if (eDocumentVersionManagement.getEarliestUploadTime() != null) {
            query.setDate("earliestUploadTime", eDocumentVersionManagement.getEarliestUploadTime());
        }
        if (eDocumentVersionManagement.getExistEditions() != null) {
            query.setInteger("existEditions", eDocumentVersionManagement.getExistEditions());
        }
        if (eDocumentVersionManagement.getLatestUploadTime() != null) {
            query.setDate("latestUploadTime", eDocumentVersionManagement.getLatestUploadTime());
        }
        if (eDocumentVersionManagement.getName() != null && !eDocumentVersionManagement.getName().isEmpty()) {
            query.setString("name", eDocumentVersionManagement.getName());
        }
        if (eDocumentVersionManagement.getNumber() != null && !eDocumentVersionManagement.getNumber().isEmpty()) {
            query.setString("number", eDocumentVersionManagement.getNumber());
        }
        if (eDocumentVersionManagement.getType() != null && !eDocumentVersionManagement.getType().isEmpty()) {
            query.setString("type", eDocumentVersionManagement.getType());
        }
        if (eDocumentVersionManagement.getViewTimes() != null) {
            query.setInteger("viewTimes", eDocumentVersionManagement.getViewTimes());
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
