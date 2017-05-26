package edu.buaa.sem.system.service;

import edu.buaa.sem.common.BaseService;
import edu.buaa.sem.system.dao.SysRoleAuthorityDao;
import edu.buaa.sem.system.dao.SysRoleDao;
import edu.buaa.sem.system.model.DatagridModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysRoleService extends BaseService {

	@Autowired
	private SysRoleDao sysRoleDao;
	@Autowired
	private SysRoleAuthorityDao sysRoleAuthorityDao;

	public Map<String, Object> findByParamsForPagination(SysRole sysRole, String page, String rows, String sort,
														 String order) {
		Map<String, Object> responseJson = new HashMap<>();
		responseJson.put("rows", sysRoleDao.findRoleForRecommendation(page, rows, sort, order));
		responseJson.put("total", sysRoleDao.countRoleForRecommendation());
		return responseJson;
	}

	public List<SysRole> findByExampleForPagination(SysRole pojo, String page, String rows, String sort, String order) {
		List<SysRole> pojos;
		if (sort != null && !sort.equals("") && order != null && !order.equals("")) {
			pojos = sysRoleDao.findByExampleForPagination(pojo, true, page, rows, sort, order);
		} else {
			pojos = sysRoleDao.findByExampleForPagination(pojo, true, page, rows, "name", "asc");
		}
		return pojos;
	}

	public long countByExample(SysRole pojo) {
		long count = sysRoleDao.countByExample(pojo, true);
		return count;
	}

	/**
	 * 对所有SysRole按照关联实体SysUserRole进行匹配标记
	 * 
	 */
	public List<HashMap<String, Object>> matchRoleUser(List<SysRole> pojos, List<SysUserRole> jointPojos) {
		List<HashMap<String, Object>> pojoList = new ArrayList<>();
		for (int i = 0; i < pojos.size(); i++) {
			int flag = 0;// 用于标注此role是否已经与user关联，关联则为1，否则为0。每次对roleList循环后要将flag重新赋值
			long relevanceId = 0;// 用于记录关联上的role与user关系的记录的自增id。如果最后放入json中为0，说明对于这个role，这个指定的user没有与他关联，所以在日后的save时，就根据relevanceId，判断是要插入还是更新。

			SysRole pojo = pojos.get(i);
			for (int j = 0; j < jointPojos.size() && flag == 0; j++) {
				SysUserRole jointPojo = jointPojos.get(j);

				if (jointPojo.getSysRoleId().equals(pojo.getId())) {
					// 已经关联上，则将标志改为1
					flag = 1;
					// 已经关联上，则将关联记录的id记录下来，并放入json（以便在日后的存储是可以判断是进行插入还是更新）
					relevanceId = jointPojo.getId();
				}
			}

			HashMap<String, Object> hashMap = new HashMap<>();
			hashMap.put("id", pojo.getId());
			hashMap.put("name", pojo.getName());
			hashMap.put("description", pojo.getDescription());
			hashMap.put("relevance", flag);
			hashMap.put("relevanceId", relevanceId);

			pojoList.add(hashMap);
		}
		return pojoList;
	}

	public Map<String, Object> findRoleAuthorityByAuthorityIdForPagination(DatagridModel model, SysRole pojo,
			long authorityId) {
		// 查询相应的分页role列表，以供特定authority勾选
		List<SysRole> pojos = findByExampleForPagination(pojo, model.getPage(), model.getRows(), model.getSort(),
				model.getOrder());
		long count = countByExample(pojo);

		// 查询特定role与authority的关联情况
		List<SysRoleAuthority> jointPojos = sysRoleAuthorityDao.findByPropertyEqual("sysAuthorityId",
				String.valueOf(authorityId), "long");

		// 遍历pojos（roleList）,对每个role查看是否已与选定的authority关联上了，再将需要的属性放入hashMap中
		List<HashMap<String, Object>> pojoList = matchRoleAuthority(pojos, jointPojos);

		HashMap<String, Object> responseJson = new HashMap<String, Object>();
		responseJson.put("total", count);
		responseJson.put("rows", pojoList);

		return responseJson;
	}

	/**
	 * 对所有SysRole按照关联实体SysRoleAuthority进行匹配标记
	 * 
	 */
	public List<HashMap<String, Object>> matchRoleAuthority(List<SysRole> pojos, List<SysRoleAuthority> jointPojos) {
		List<HashMap<String, Object>> pojoList = new ArrayList<>();
		for (int i = 0; i < pojos.size(); i++) {
			int flag = 0;// 用于标注此role是否已经与authority关联，关联则为1，否则为0。每次对roleList循环后要将flag重新赋值
			long relevanceId = 0;// 用于记录关联上的role与authority关系的记录的自增id。如果最后放入json中为0，说明对于这个role，这个指定的authority没有与他关联，所以在日后的save时，就根据relevanceId，判断是要插入还是更新。

			SysRole pojo = pojos.get(i);
			for (int j = 0; j < jointPojos.size() && flag == 0; j++) {
				SysRoleAuthority jointPojo = jointPojos.get(j);

				if (jointPojo.getSysRoleId().equals(pojo.getId())) {
					// 已经关联上，则将标志改为1
					flag = 1;
					// 已经关联上，则将关联记录的id记录下来，并放入json（以便在日后的存储是可以判断是进行插入还是更新）
					relevanceId = jointPojo.getId();
				}
			}

			HashMap<String, Object> hashMap = new HashMap<>();
			hashMap.put("id", pojo.getId());
			hashMap.put("name", pojo.getName());
			hashMap.put("description", pojo.getDescription());
			hashMap.put("relevance", flag);
			hashMap.put("relevanceId", relevanceId);

			pojoList.add(hashMap);

		}
		return pojoList;
	}

}
