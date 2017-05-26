package edu.buaa.sem.system.service;

import edu.buaa.sem.common.BaseService;
import edu.buaa.sem.system.dao.AuthorityDao;
import edu.buaa.sem.system.dao.SysRoleAuthorityDao;
import edu.buaa.sem.system.model.DatagridModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AuthorityService extends BaseService {

	@Autowired
	private AuthorityDao sysAuthorityDao;
	@Autowired
	private SysRoleAuthorityDao sysRoleAuthorityDao;

	public List<SysAuthority> findByExampleForPagination(SysAuthority pojo, String page, String rows, String sort,
														 String order) {
		List<SysAuthority> pojos;
		if (sort != null && !sort.equals("") && order != null && !order.equals("")) {
			pojos = sysAuthorityDao.findByExampleForPagination(pojo, true, page, rows, sort, order);
		} else {
			pojos = sysAuthorityDao.findByExampleForPagination(pojo, true, page, rows, "name", "asc");
		}
		return pojos;
	}

	public long countByExample(SysAuthority pojo) {
		long count = sysAuthorityDao.countByExample(pojo, true);
		return count;
	}

	public void save(SysAuthority pojo) {
		sysAuthorityDao.save(pojo);
	}

	public void update(SysAuthority pojo) {
		sysAuthorityDao.update(pojo);
	}

	/**
	 * 对所有SysAuthority按照关联实体SysRoleAuthority进行匹配标记
	 * 
	 */
	public List<HashMap<String, Object>> matchAuthorityRole(List<SysAuthority> pojos,
			List<SysRoleAuthority> jointPojos) {
		List<HashMap<String, Object>> pojoList = new ArrayList<>();
		for (int i = 0; i < pojos.size(); i++) {
			int flag = 0;// 用于标注此authority是否已经与role关联，关联则为1，否则为0。每次对authorityList循环后要将flag重新赋值
			long relevanceId = 0;// 用于记录关联上的authority与role关系的记录的自增id。如果最后放入json中为0，说明对于这个authority，这个指定的role没有与他关联，所以在日后的save时，就根据relevanceId，判断是要插入还是更新。

			SysAuthority pojo = pojos.get(i);
			for (int j = 0; j < jointPojos.size() && flag == 0; j++) {
				SysRoleAuthority jointPojo = jointPojos.get(j);

				if (jointPojo.getSysAuthorityId().equals(pojo.getId())) {
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

	/**
	 * 对所有SysAuthority按照关联实体SysAuthorityResource进行匹配标记
	 * 
	 */
	public List<HashMap<String, Object>> matchAuthorityResource(List<SysAuthority> pojos,
			List<SysAuthorityResource> jointPojos) {
		List<HashMap<String, Object>> pojoList = new ArrayList<>();
		for (int i = 0; i < pojos.size(); i++) {
			int flag = 0;// 用于标注此authority是否已经与resource关联，关联则为1，否则为0。每次对authorityList循环后要将flag重新赋值
			long relevanceId = 0;// 用于记录关联上的authority与resource关系的记录的自增id。如果最后放入json中为0，说明对于这个authority，这个指定的resource没有与他关联，所以在日后的save时，就根据relevanceId，判断是要插入还是更新。

			SysAuthority pojo = pojos.get(i);
			for (int j = 0; j < jointPojos.size() && flag == 0; j++) {
				SysAuthorityResource jointPojo = jointPojos.get(j);

				if (jointPojo.getSysAuthorityId().equals(pojo.getId())) {
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

	public List<HashMap<String, String>> matchAuthorityResource(List<SysRoleAuthority> jointPojos) {
		List<HashMap<String, String>> pojoList = new ArrayList<>();
		for (int i = 0; i < jointPojos.size(); i++) {
			SysAuthority pojo = sysAuthorityDao.findByKey(jointPojos.get(i).getSysAuthorityId());
			HashMap<String, String> hashMap = new HashMap<>();
			hashMap.put("name", pojo.getName());
			hashMap.put("description", pojo.getDescription());
			pojoList.add(hashMap);
		}
		return pojoList;
	}

	public Map<String, Object> findRoleAuthorityByRoleIdForPagination(DatagridModel model, SysAuthority pojo,
			long roleId) {
		// 查询相应的分页authority列表，以供特定role勾选
		List<SysAuthority> pojos = findByExampleForPagination(pojo, model.getPage(), model.getRows(), model.getSort(),
				model.getOrder());
		long count = countByExample(pojo);

		// 查询特定role与authority的关联情况
		List<SysRoleAuthority> jointPojos = sysRoleAuthorityDao.findByPropertyEqual("sysRoleId", String.valueOf(roleId),
				"long");

		// 遍历pojos（authorityList）,对每个authority查看是否已与选定的role关联上了，再将需要的属性放入hashMap中
		List<HashMap<String, Object>> pojoList = matchAuthorityRole(pojos, jointPojos);

		HashMap<String, Object> responseJson = new HashMap<>();
		responseJson.put("total", count);
		responseJson.put("rows", pojoList);

		return responseJson;
	}
}
