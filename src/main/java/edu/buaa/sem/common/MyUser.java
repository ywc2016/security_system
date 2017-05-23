package edu.buaa.sem.common;

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * 扩展SpringSecurity的用户模型，添加特有属性
 * username中存放email
 * nickname中存放name
 */
public class MyUser extends User {

	private List<Long> cmsColumnIds;// 用户能操作的通讯栏目id列表，用于通讯模块的动态权限控制

	private String headUrl;// 用户头像地址
	private BigInteger id;
	private String nickname;
	private String staffName;

	public MyUser(String username, String password, boolean enabled,
			boolean accountNonExpired, boolean credentialsNonExpired,
			boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired,
				credentialsNonExpired, accountNonLocked, authorities);
	}
	public List<Long> getCmsColumnIds() {
		return cmsColumnIds;
	}

	public void setCmsColumnIds(List<Long> cmsColumnIds) {
		this.cmsColumnIds = cmsColumnIds;
	}

	public String getHeadUrl() {
		return headUrl;
	}

	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

}
