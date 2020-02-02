package au.com.per.delv.persistence.rep;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import au.com.per.delv.persistence.entity.UserInfo;

public interface IUserInfoRepository extends CrudRepository<UserInfo, Integer>{

	public static final String SQL_DEL_BY_USERNAME = "delete from user_info where username=?1";
	public static final String SQL_SLC_BY_EMAIL = "select id, username, email, password, salt, active from user_info where email=?1";
	public static final String SQL_SLC_BY_USERNAME = "select id, username, email, password, salt, active from user_info where username=?1";
	
	public Long deleteByUsername(String username);
	
	@Query(value = SQL_SLC_BY_EMAIL, nativeQuery = true)
	public UserInfo findByEmail(String email);
	
	@Query(value = SQL_SLC_BY_USERNAME, nativeQuery = true)
	public UserInfo findByUsername(String username);
}
