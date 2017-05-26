package edu.buaa.sem.common;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.math.BigInteger;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 该类的主要作用是为Spring Security提供一个经过用户认证后的UserDetails。
 * 
 */
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private SessionFactory sessionFactory;

	private Session session;

	@SuppressWarnings("unchecked")
	@Override
	// 登录默认会调用这里
	public UserDetails loadUserByUsername(String name) {
		System.out.println("=========================");
		session = sessionFactory.openSession();

		// email = email.trim().toLowerCase();

		List<Object[]> userObjects = session
				.createSQLQuery(
						"select id, password, head_image_url, name, email, staff_relate from sys_user where name= :name and enabled='是'")
				.setString("name", name).list();
		if (userObjects == null || userObjects.size() == 0) {
			session.close();
			throw new UsernameNotFoundException("用户不存在或者被锁定");
		}
		Object[] obj = (Object[]) userObjects.get(0);
		BigInteger id = (BigInteger) obj[0];
		String userPasswords = (String) obj[1];
		String headImageUrl = (String) obj[2];
		String nickname = (String) obj[3];
		String userEmail = (String) obj[4];
		BigInteger staffRelate = (BigInteger) obj[5];
		// 系统用户,密码不能为空
		if (userPasswords == null || userPasswords.equals("")) {
			session.close();
			throw new UsernameNotFoundException("用户不存在或者被锁定");
		} else {
			Set<GrantedAuthority> authSet = new HashSet<GrantedAuthority>();
			// 获取用户所属权限
			List<String> authorityNames = session
					.createSQLQuery(
							"select e.name "
									+ "from sys_user a, sys_user_role b, sys_role c, sys_role_authority d, "
									+ "sys_authority e where a.id=b.sys_user_id and b.sys_role_id = c.id "
									+ "and c.id=d.sys_role_id and d.sys_authority_id=e.id and a.name= :name "
									+ "and a.enabled='是' and b.enabled='是' and c.enabled='是' and d.enabled='是' and e.enabled='是'")
					.setString("name", name).list();
			for (String authorityName : authorityNames) {
				authSet.add(new SimpleGrantedAuthority(authorityName));
			}

			Collection<GrantedAuthority> grantedAuths = authSet;
			boolean enables = true;
			boolean accountNonExpired = true;
			boolean credentialsNonExpired = true;
			boolean accountNonLocked = true;
			MyUser userdetail = new MyUser(nickname, userPasswords, enables,
					accountNonExpired, credentialsNonExpired, accountNonLocked,
					grantedAuths);

			userdetail.setHeadUrl(headImageUrl);
			userdetail.setId(id);
			userdetail.setNickname(nickname);
			// 获取用户对应员工
			List<String> staffNames = session
					.createSQLQuery(
							"select name "
									+ "from inf_staff a where a.id=:id and a.enabled='是'")
					.setBigInteger("id", staffRelate).list();
			if (staffNames != null && staffNames.size() != 0) {
				userdetail.setStaffName(staffNames.get(0));
			} else
				userdetail.setStaffName(nickname);

			session.close();
			return userdetail;

		}
	}
}