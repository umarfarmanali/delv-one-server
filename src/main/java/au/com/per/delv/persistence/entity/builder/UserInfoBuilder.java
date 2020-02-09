package au.com.per.delv.persistence.entity.builder;

import au.com.per.delv.persistence.entity.UserInfo;

public class UserInfoBuilder {

	private Integer id;
	private String username;
	private String email;
	private byte[] encryptedPassword;
	private String password;
	private byte[] salt;
	private Integer active;
	
	public static UserInfoBuilder builder() {
        return new UserInfoBuilder();
    }
	
	public UserInfo build() {
        
		UserInfo userInfo = new UserInfo();
		userInfo.setId(id);
		userInfo.setUsername(username);
		userInfo.setEmail(email);
		userInfo.setEncryptedPassword(encryptedPassword);
		userInfo.setPassword(password);
		userInfo.setSalt(salt);
		userInfo.setActive(active);
		
		return userInfo;
    }
	
	public UserInfoBuilder setId(Integer id) {
		this.id = id;
		return this;
	}
	
	public UserInfoBuilder setUsername(String username) {
		this.username = username;
		return this;
	}
	
	public UserInfoBuilder setEmail(String email) {
		this.email = email;
		return this;
	}
	
	public UserInfoBuilder setEncryptedPassword(byte[] encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
		return this;
	}
	
	public UserInfoBuilder setPassword(String password) {
		this.password = password;
		return this;
	}
	
	public UserInfoBuilder setSalt(byte[] salt) {
		this.salt = salt;
		return this;
	}
	
	public UserInfoBuilder setActive(Integer active) {
		this.active = active;
		return this;
	}
}
