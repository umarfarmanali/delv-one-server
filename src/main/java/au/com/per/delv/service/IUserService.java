package au.com.per.delv.service;

import au.com.per.delv.dto.UserInfoDto;
import au.com.per.delv.persistence.entity.UserInfo;

public interface IUserService extends IRootService<UserInfoDto, UserInfo>{

	public UserInfoDto getByUsername(String username);
	public UserInfoDto getByEmail(String email);
	public void deleteByUsername(String username);
}
