package com.management.webservice.configuration;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import com.management.webservice.Entity.User;

public class CurrentUser implements UserDetails {

	private long id;
	
	private String username;
	
	private String password;
	
	private boolean enabled;
	
	
	public CurrentUser(User user) {
		this.id = user.getId();
		this.username = user.getUsername();
		this.password = user.getPassword();
		this.enabled = user.isActive();
	}
	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	@Override
	public boolean isEnabled() {
		return enabled;
	}
	
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.username;
	}
	
	@Override
	public String getPassword() {
		return this.password;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return AuthorityUtils.createAuthorityList("ROLE_USER"); //Role diye bir prefix olmak zorunda.
	}

}
