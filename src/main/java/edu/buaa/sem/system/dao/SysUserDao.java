package edu.buaa.sem.system.dao;

import edu.buaa.sem.common.BaseDao;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public class SysUserDao extends BaseDao<SysUser> {
	public SysUserDao() {
		logger = Logger.getLogger(SysUserDao.class);
	}

	public void updateByKeys(SysUser pojo, String key, long[] ids) {
		try {
			boolean updateFlag = false;
			String queryString = "update SysUser set";
			if (pojo.getPassword() != null && !pojo.getPassword().isEmpty()) {
				queryString += " password = :password,";
				updateFlag = true;
			}
			if (pojo.getDescription() != null && !pojo.getDescription().isEmpty()) {
				queryString += " description = :description,";
				updateFlag = true;
			}
			if (pojo.getEnabled() != null && !pojo.getEnabled().isEmpty()) {
				queryString += " enabled = :enabled,";
				updateFlag = true;
			}
			if (pojo.getCheckStatus() != null && !pojo.getCheckStatus().isEmpty()) {
				queryString += " checkStatus = :checkStatus,";
				updateFlag = true;
			}

			if (StringUtils.isNotEmpty(pojo.getUserType())) {
				queryString += " userType = :userType,";
				updateFlag = true;
			}

			if (updateFlag) {
				queryString = StringUtils.removeEnd(queryString, ",");
				queryString += " where";
				for (int i = 0; i < ids.length; i++) {
					queryString += " " + key + "= :key" + i + " or";
				}

				queryString = StringUtils.removeEnd(queryString, "or");
				Query query = sessionFactory.getCurrentSession().createQuery(queryString);

				if (pojo.getPassword() != null && !pojo.getPassword().isEmpty()) {
					query.setString("password", pojo.getPassword());
				}
				if (pojo.getDescription() != null && !pojo.getDescription().isEmpty()) {
					query.setString("description", pojo.getDescription());
				}
				if (pojo.getEnabled() != null && !pojo.getEnabled().isEmpty()) {
					query.setString("enabled", pojo.getEnabled());
				}
				if (pojo.getCheckStatus() != null && !pojo.getCheckStatus().isEmpty()) {
					query.setString("checkStatus", pojo.getCheckStatus());
				}
				if (StringUtils.isNotEmpty(pojo.getUserType())) {
					query.setString("userType", pojo.getUserType());
				}
				for (int i = 0; i < ids.length; i++) {
					query.setLong("key" + i, ids[i]);
				}

				query.executeUpdate();
				logger.debug("updateByKeys successful");
			}
		} catch (RuntimeException re) {
			logger.error("updateByKeys failed", re);
			throw re;
		}
	}

	public List<SysUser> findAllForSearch() {
		try {
			Criteria criteria = sessionFactory.getCurrentSession().createCriteria(typeClass());
			criteria.add(Restrictions.ne("userType", "游客"));
			criteria.add(Restrictions.ne("enabled", "否"));
			List<SysUser> pojos = criteria.list();
			logger.debug("findAllForSearch successful");
			return pojos;
		} catch (RuntimeException re) {
			logger.error("findAllForSearch failed", re);
			throw re;
		}
	}

	public List<SysUser> findByParamsForPagination(SysUser pojo, HashMap conditionString, String page, String rows,
			String sort, String order) {
		try {
			int intPage = Integer.parseInt((page == null || page.equals("0")) ? DEFAULT_PAGE_START : page);
			int intRows = Integer.parseInt((rows == null || rows.equals("0")) ? DEFAULT_PAGE_ROWS : rows);
			String queryString = "from SysUser as a  where 1 = 1";
			Query query = contrustString(pojo, conditionString, queryString, sort, order);
			query.setFirstResult((intPage - 1) * intRows);
			query.setMaxResults(intRows);
			List pojos = query.list();
			logger.debug("findByParamsForPagination successful");
			return pojos;
		} catch (RuntimeException re) {
			logger.error("findByParamsForPagination failed", re);
			throw re;
		}
	}

	public long countByParams(SysUser pojo, HashMap conditionString) {
		try {
			String queryString = "select count(*) from SysUser as a  where 1 = 1";
			Query query = contrustString(pojo, conditionString, queryString, null, null);
			List<Object> countList = query.list();
			long count = 0;
			if (countList != null) {
				count = (Long) countList.get(0);
			}
			logger.debug("countByParamsForPagination successful");
			return count;
		} catch (HibernateException e) {
			logger.error("countByParamsForSize failed", e);
			throw e;
		}
	}

	public Query contrustString(SysUser pojo, HashMap conditionString, String queryString, String sort, String order) {
		if (0 != pojo.getId()) {
			queryString += " and a.id =:id";
		}
		if (pojo.getName() != null && !pojo.getName().isEmpty()) {
			queryString += " and a.name like:name";
		}
		if (pojo.getEmail() != null && !pojo.getEmail().isEmpty()) {
			queryString += " and a.email like:email";
		}
		if (pojo.getDescription() != null && !pojo.getDescription().isEmpty()) {
			queryString += " and a.description like:description";
		}
		if (pojo.getCreatTime() != null) {
			queryString += " and a.creatTime =:creatTime ";
		}
		if (pojo.getUserType() != null && !pojo.getUserType().isEmpty()) {
			queryString += " and a.userType =:userType ";
		}
		if (pojo.getCheckStatus() != null && pojo.getCheckStatus().isEmpty()) {
			queryString += " and a.checkStatus like:checkStatus ";
		}
		if (pojo.getSelfIntroduction() != null && !pojo.getSelfIntroduction().isEmpty()) {
			queryString += " and a.selfIntroduction like:selfIntroduction ";
		}
		if (pojo.getEnabled() != null && !pojo.getEnabled().isEmpty()) {
			queryString += " and a.enabled =:enabled";
		}
		if (pojo.getHeadImageUrl() != null && !pojo.getHeadImageUrl().isEmpty()) {
			queryString += " and a.headImageUrl like:headImageUrl";
		}
		if (sort != null && !sort.isEmpty()) {
			queryString += " order by a." + sort + "  " + order;
		}
		Query query = sessionFactory.getCurrentSession().createQuery(queryString);
		// ---------------------下面是赋值操作------------------------

		if (0 != pojo.getId()) {
			query.setLong("id", pojo.getId());
		}
		if (pojo.getName() != null && !pojo.getName().isEmpty()) {
			query.setString("name", '%' + pojo.getName() + '%');
		}
		if (pojo.getEmail() != null && !pojo.getEmail().isEmpty()) {
			query.setString("email", '%' + pojo.getEmail() + '%');
		}
		if (pojo.getDescription() != null && !pojo.getDescription().isEmpty()) {
			query.setString("description", '%' + pojo.getDescription() + '%');
		}
		if (pojo.getCreatTime() != null) {
			query.setDate("creatTime", pojo.getCreatTime());
		}
		if (pojo.getUserType() != null && !pojo.getUserType().isEmpty()) {
			query.setString("userType", pojo.getUserType());
		}
		if (pojo.getCheckStatus() != null && pojo.getCheckStatus().isEmpty()) {
			query.setString("checkStatus", '%' + pojo.getCheckStatus() + '%');
		}
		if (pojo.getSelfIntroduction() != null && !pojo.getSelfIntroduction().isEmpty()) {
			query.setString("selfIntroduction", '%' + pojo.getSelfIntroduction() + '%');
		}
		if (pojo.getEnabled() != null && !pojo.getEnabled().isEmpty()) {
			query.setString("enabled", pojo.getEnabled());
		}
		if (pojo.getHeadImageUrl() != null && !pojo.getHeadImageUrl().isEmpty()) {
			query.setString("headImageUrl", '%' + pojo.getHeadImageUrl() + '%');
		}
		/*
		 * if(conditionString !=null && !conditionString.isEmpty()){
		 * if(conditionString.containsKey("gender")){
		 * query.setString("gender",'%'+
		 * conditionString.get("gender").toString()+'%'); }
		 * if(conditionString.containsKey("idCard")){ query.setString("idCard",
		 * '%'+conditionString.get("idCard").toString()+'%'); }
		 * if(conditionString.containsKey("realName")){
		 * query.setString("realName",
		 * '%'+conditionString.get("realName").toString()+'%'); }
		 * if(conditionString.containsKey("mobile")){ query.setString("mobile",
		 * '%'+conditionString.get("mobile").toString()+'%'); }
		 * if(conditionString.containsKey("workPhone")){
		 * query.setString("workPhone",
		 * '%'+conditionString.get("workPhone").toString()+'%'); } }
		 */
		return query;
	}

	public List findRolesByStaffId(long staffId) {
		String queryString = "select b.sysRoleId from  SysUser as a,SysUserRole as b where b.sysUserId = a.id  and a.staffRelate = :staffId ";
		Query query = sessionFactory.getCurrentSession().createQuery(queryString);
		query.setLong("staffId", staffId);
		List roleIds = query.list();
		return roleIds;
	}

}