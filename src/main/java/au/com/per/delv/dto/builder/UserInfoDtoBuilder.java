package au.com.per.delv.dto.builder;

import au.com.per.delv.dto.UserInfoDto;

public class UserInfoDtoBuilder {

	private Integer id;
	private String username;
	private String email;
	private String password;
	private Integer active;
	private String errorMessage;
	
	public UserInfoDtoBuilder() {}
	
	public UserInfoDtoBuilder(Integer id, String username, String email, String password, Integer active,
			String errorMessage) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.active = active;
		this.errorMessage = errorMessage;
	}
	
	public UserInfoDtoBuilder setId(Integer id) {
		this.id = id;
		return this;
	}
	
	public UserInfoDtoBuilder setUsername(String username) {
		this.username = username;
		return this;
	}
	
	public UserInfoDtoBuilder setEmail(String email) {
		this.email = email;
		return this;
	}
	
	public UserInfoDtoBuilder setPassword(String password) {
		this.password = password;
		return this;
	}
	
	public UserInfoDtoBuilder setActive(Integer active) {
		this.active = active;
		return this;
	}
	
	public UserInfoDtoBuilder setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
		return this;
	}
	
	public UserInfoDto build() {
		return new UserInfoDto(id, username, email, password, active, errorMessage);
	}
}
