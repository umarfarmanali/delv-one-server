package au.com.per.delv.persistence.rep;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import au.com.per.delv.persistence.entity.UserInfo;

public interface IUserInfoRepository extends CrudRepository<UserInfo, Integer>{

//	public static final String SQL_DEL_BY_USERNAME = "delete from user_info where username=?1";
	
	//public static final String SQL_SLC_ALL = "select id, username, email, encrypted_password, active from user_info";
//	public static final String SQL_SLC_BY_EMAIL = "select id, username, email, encrypted_password, active from user_info where email=?1";
//	public static final String SQL_SLC_BY_USERNAME = "select id, username, email, encrypted_password, active from user_info where username=?1";
	
	public Long deleteByUsername(String username);
	
//	@Query(value = SQL_SLC_ALL, nativeQuery = true)
//	public List<UserInfo> find();
	
//	@Query(value = SQL_SLC_BY_EMAIL, nativeQuery = true)
	public Optional<UserInfo> findByEmail(String email);
	
//	@Query(value = SQL_SLC_BY_USERNAME, nativeQuery = true)
	public Optional<UserInfo> findByUsername(String username);
}
