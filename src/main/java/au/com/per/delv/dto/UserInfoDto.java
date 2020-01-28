package au.com.per.delv.dto;

public class UserInfoDto implements IDto{

	private Integer id;
	
	private String username;
	private String email;
	private String password;
	
	private Integer active;
	
	private String errorMessage;
	
	public UserInfoDto() {}
	
	public UserInfoDto(Integer id, String username, String email, String password, Integer active, String errorMessage) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.active = active;
		this.errorMessage = errorMessage;
	}
	
	public UserInfoDto(Integer id, String username, String email, Integer active) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.active = active;
	}
	
	public UserInfoDto(String username, String email, String password, Integer active) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.active = active;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getActive() {
		return active;
	}

	public void setActive(Integer active) {
		this.active = active;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}