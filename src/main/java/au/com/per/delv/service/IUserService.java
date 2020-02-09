package au.com.per.delv.service;

import java.util.Optional;

import au.com.per.delv.persistence.entity.UserInfo;

public interface IUserService extends IPersistenceService<UserInfo>{

	public Optional<UserInfo> getByEmail(String email);
	public Optional<UserInfo> getById(Integer id);
	public Optional<UserInfo> getByUsername(String username);

	public void deleteByUsername(String username);
}
