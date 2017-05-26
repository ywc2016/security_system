package edu.buaa.sem.system.service;

import edu.buaa.sem.common.BaseService;
import edu.buaa.sem.common.MyUser;
import edu.buaa.sem.system.dao.SysRoleDao;
import edu.buaa.sem.system.dao.SysUserDao;
import edu.buaa.sem.system.dao.SysUserRoleDao;
import edu.buaa.sem.system.model.DatagridModel;
import edu.buaa.sem.utils.EncryptionUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

@Service
public class SysUserService extends BaseService {

    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private SysRoleDao sysRoleDao;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysUserRoleDao sysUserRoleDao;

    /**
     * 根据邮箱生成用户名
     *
     * @param email
     * @return
     */
    public String generateUserNameByEmail(String email) {
        int i = email.indexOf('@');
        String name = "";
        if (i > 0) {
            name = email.substring(0, i);
        }

        while (findByUserName(name) != null) {
            Random random = new Random();
            int suffix = 1000 + random.nextInt(1000);
            name += suffix;
        }

        return name;
    }

    public List<SysUser> findByExampleForPagination(SysUser pojo, String page, String rows, String sort, String order) {
        List<SysUser> pojos;
        if (sort != null && !sort.equals("") && order != null && !order.equals("")) {
            pojos = sysUserDao.findByExampleForPagination(pojo, true, page, rows, sort, order);
        } else {
            pojos = sysUserDao.findByExampleForPagination(pojo, true, page, rows, "name", "asc");
        }
        return pojos;
    }

    public long countByExample(SysUser pojo) {
        long count = sysUserDao.countByExample(pojo, true);
        return count;
    }

    public HashMap<String, Object> findByParamsForPagination(SysUser pojo, HashMap conditionString, String page,
                                                             String rows, String sort, String order) {
        List list;
        if (sort != null && !sort.equals("") && order != null && !order.equals("")) {
            list = sysUserDao.findByParamsForPagination(pojo, conditionString, page, rows, sort, order);
        } else {
            list = sysUserDao.findByParamsForPagination(pojo, conditionString, page, rows, "name", "asc");
        }
        long count = sysUserDao.countByParams(pojo, conditionString);
        List<HashMap> pojoList = new ArrayList<HashMap>();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Object[] obj = (Object[]) it.next();
            SysUser sysUser = (SysUser) obj[0];
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("id", sysUser.getId());
            map.put("name", sysUser.getName());
            map.put("email", sysUser.getEmail());
            map.put("password", sysUser.getPassword());
            map.put("checkStatus", sysUser.getCheckStatus());
            map.put("selfIntroduction", sysUser.getSelfIntroduction());
            map.put("creatTime", sysUser.getCreatTime());
            map.put("headImageUrl", sysUser.getHeadImageUrl());
            map.put("userType", sysUser.getUserType());
            map.put("description", sysUser.getDescription());
            map.put("enabled", sysUser.getEnabled());
            pojoList.add(map);
        }
        HashMap<String, Object> responseJson = new HashMap<String, Object>();
        responseJson.put("total", count);
        responseJson.put("rows", pojoList);
        return responseJson;
    }

    public void save(SysUser pojo) {
        sysUserDao.save(pojo);
    }

    public void update(SysUser pojo) {
        sysUserDao.update(pojo);
    }

    public void merge(SysUser pojo) {
        sysUserDao.merge(pojo);
    }

    public void cancelByKeys(String idCommaString) {

        Logger logger = Logger.getLogger(SysUserService.class);
        SysUser userCurrent = this.getCurrentUser();
        if (idCommaString != null && !idCommaString.equals("")) {
            String[] userids = idCommaString.split(",");
            for (int i = 0; i < userids.length; i++) {
                SysUser pojo = this.findByKey(Long.parseLong(userids[i]));
                String userName = pojo.getName();
                String userEmail = pojo.getEmail();
                // pojo.setName("**(已注销)");
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String cancelTime = df.format(new Date());
                pojo.setDescription(cancelTime + "_" + userName + "_" + userEmail + "_被注销");
                pojo.setEnabled("否");
                pojo.setEmail(RandomStringUtils.randomAlphanumeric(32));
                sysUserDao.save(pojo);
                logger.info(userCurrent.getName() + " cancel commonUser id=" + pojo.getId() + " and email="
                        + userEmail + " successful");

            }
        }
    }

    public void updateByKeys(SysUser pojo, String idCommaString) {
        sysUserDao.updateByKeys(pojo, "id", handleToIdLongArray(idCommaString));
    }

    public List<SysUser> findSysUserByExample(SysUser pojo) {
        return sysUserDao.findByExample(pojo, false);
    }

    public List<SysUser> findSysUserByExampleEnableLike(SysUser pojo) {
        return sysUserDao.findByExample(pojo, true);
    }

    public SysUser findByKey(long id) {
        return sysUserDao.findByKey(id);
    }

    /**
     * 对所有SysUser按照关联实体SysUserRole进行匹配标记
     */
    public List<HashMap<String, Object>> matchUserRole(List<SysUser> pojos, List<SysUserRole> jointPojos) {
        List<HashMap<String, Object>> pojoList = new ArrayList<>();
        for (int i = 0; i < pojos.size(); i++) {
            int flag = 0;// 用于标注此user是否已经与role关联，关联则为1，否则为0。每次对userList循环后要将flag重新赋值
            long relevanceId = 0;// 用于记录关联上的user与role关系的记录的自增id。如果最后放入json中为0，说明对于这个user，这个指定的role没有与他关联，所以在日后的save时，就根据relevanceId，判断是要插入还是更新。

            SysUser pojo = pojos.get(i);
            for (int j = 0; j < jointPojos.size() && flag == 0; j++) {
                SysUserRole jointPojo = jointPojos.get(j);

                if (jointPojo.getSysUserId().equals(pojo.getId())) {
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
     * 前台表单验证函数
     *
     * @param pojo
     * @return
     */
    public String checkRegistForm(SysUser pojo) {
        if (pojo.getName() == null || pojo.getName().trim().length() == 0 || pojo.getName().trim().length() > 30) {
            return "用户名不正确";
        }

        String regex = "^[a-z0-9A-Z_\\.]{6,30}$";// 密码只能是字母、数字、点和下划线，长度6至30
        if (pojo.getPassword() == null || !Pattern.matches(regex, pojo.getPassword().trim())) {
            return "密码不正确";
        }

        if (!EmailValidator.getInstance().isValid(pojo.getEmail().trim())) {
            return "邮箱不正确";
        }

        if (pojo.getEmail().trim().length() > 30) {
            return "邮箱不正确";
        }

        if (findByUserName(pojo.getName().trim().toLowerCase()) != null) {
            return "该用户名已被注册";
        }

        if (findByEmail(pojo.getEmail().trim()) != null) {
            return "该邮箱已被注册";
        }

        return "pass";
    }

    /**
     * 保存用户头像进入数据库和SpringSecuritySession
     */
    public boolean saveHeadImgPath(String headImgPath, SysUser pojo) {
        if (pojo != null) {
            pojo.setHeadImageUrl(headImgPath);
            Object userDetails = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (userDetails instanceof UserDetails) {
                ((MyUser) userDetails).setHeadUrl(headImgPath);
            } else {
                return false;
            }
            this.save(pojo);
            return true;
        } else {
            return false;
        }
    }

    public SysUser findByUserName(String name) {
        List<SysUser> pojos = sysUserDao.findByPropertyEqual("name", name, "String");
        if (pojos != null && pojos.size() == 1) {
            return pojos.get(0);
        } else {
            return null;
        }
    }

    public SysUser findByEmail(String email) {
        List<SysUser> pojos = sysUserDao.findByPropertyEqual("email", email, "String");
        if (pojos != null && pojos.size() == 1) {
            return pojos.get(0);
        } else {
            return null;
        }
    }

    public SysUser findByEmailCode(String emailCode) {
        List<SysUser> pojos = sysUserDao.findByPropertyEqual("emailCode", emailCode, "String");
        if (pojos != null && pojos.size() == 1) {
            return pojos.get(0);
        } else {
            return null;
        }
    }

    public List<SysUser> findAll() {
        return sysUserDao.findAll();
    }

    /**
     * 生成邮件校验码，用户重置密码
     *
     * @return
     */
    public String generateEmailCode() {
        String code = RandomStringUtils.randomAlphanumeric(8);

        while (findByEmailCode(code) != null) {
            code = RandomStringUtils.randomAlphanumeric(8);
        }

        return code;
    }

    /**
     * 根据员工Id查询是否为业务员或者录入员
     *
     * @param staffId 员工Id
     * @return
     */
    public String finduseRoleByStaffId(long staffId) {
        List roleIds = sysUserDao.findRolesByStaffId(staffId);
        String roleName = "";
        if (roleIds.contains(5l) && roleIds.contains(6l)) {
            roleName = "业务员/录入员";

        } else if (roleIds.contains(5l)) {
            roleName = "业务员";
        } else if (roleIds.contains(6l)) {
            roleName = "录入员";
        }
        return roleName;
    }

    public Map<String, Object> findByParamsForPagination(SysUser pojo, String page, String rows, String sort,
                                                         String order) {
        long count = sysUserDao.countByParams(pojo, null);
        List<SysUser> pojos = sysUserDao.findByParamsForPagination(pojo, null, page, rows, sort, order);
        HashMap<String, Object> responseJson = new HashMap<String, Object>();
        responseJson.put("total", count);
        responseJson.put("rows", pojos);
        return responseJson;
    }

    public void add(SysUser sysUser) {
        sysUser.setPassword(EncryptionUtils.getMD5(sysUser.getPassword()));
        sysUserDao.save(sysUser);
    }

    public void disabled(String idStr) {
        String[] strings = idStr.split(",");
        for (String string : strings) {
            long id = Long.valueOf(string);
            SysUser sysUser = sysUserDao.findByKey(id);
            sysUser.setEnabled("否");
            sysUserDao.update(sysUser);
        }
    }

    public Map<String, Object> findUserRoleByUserIdForPagination(DatagridModel model, SysRole pojo, long userId) {
        // 查询相应的分页role列表，以供特定user勾选
        List<SysRole> pojos = sysRoleService.findByExampleForPagination(pojo, model.getPage(), model.getRows(),
                model.getSort(), model.getOrder());
        long count = sysRoleService.countByExample(pojo);

        // 查询特定user与role的关联情况
        List<SysUserRole> jointPojos = sysUserRoleDao.findByPropertyEqual("sysUserId", String.valueOf(userId), "long");

        // 遍历pojos（roleList）,对每个role查看是否已与选定的user关联上了，再将需要的属性放入hashMap中
        List<HashMap<String, Object>> pojoList = sysRoleService.matchRoleUser(pojos, jointPojos);

        HashMap<String, Object> responseJson = new HashMap<String, Object>();
        responseJson.put("total", count);
        responseJson.put("rows", pojoList);

        return responseJson;
    }

    /**
     * 添加用户角色关联
     *
     * @param sysUserId
     * @param sysRoleId
     * @param enabled
     */
    public void addUserRole(String sysUserId, String sysRoleId, String enabled) {
        SysUserRole sysUserRole = new SysUserRole();
        sysUserRole.setEnabled(enabled);
        sysUserRole.setSysRoleId(Long.valueOf(sysRoleId));
        sysUserRole.setSysUserId(Long.valueOf(sysUserId));
        sysUserRoleDao.saveOrUpdate(sysUserRole);
    }

    public void deleteUserRole(String idCommaString) {
        sysUserRoleDao.delete(sysUserRoleDao.findByKey(Long.valueOf(idCommaString)));
    }

}
