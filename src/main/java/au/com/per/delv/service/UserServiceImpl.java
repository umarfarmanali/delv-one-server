package au.com.per.delv.service;

import static au.com.per.delv.EnumError.*;

import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import au.com.per.delv.persistence.entity.UserInfo;
import au.com.per.delv.persistence.rep.IUserInfoRepository;
import au.com.per.delv.util.PasswordUtil;

@Service
public class UserServiceImpl implements IUserService{

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	public UserServiceImpl(IUserInfoRepository repository) {
		 this.repository = repository; 
	}
	
	private IUserInfoRepository repository;
	
	@Override
	public List<UserInfo> getAll() {
		
		List<UserInfo> list = new ArrayList<>();
		repository.findAll().forEach(list::add);
		return list;
	}
	
	@Override
	public Optional<UserInfo> getByEmail(String email) {
		
		if(email == null) throw new IllegalArgumentException(PARAMETER_NULL.toString() + " : email");
		
		return repository.findByEmail(email);
	}
	
	public Optional<UserInfo> getById(Integer id) {
		
		if(id == null) throw new IllegalArgumentException(PARAMETER_NULL.toString() + " : id");
		
		return repository.findById(id);
	}

	@Override
	public Optional<UserInfo> getByUsername(String username) {
		
		if(username == null) throw new IllegalArgumentException(PARAMETER_NULL.toString() + " : username");
		
		return repository.findByUsername(username);
	}
	
	@Override
	@Transactional
	public UserInfo add(UserInfo obj) throws GeneralSecurityException {
		
		if(obj == null) 
			throw new IllegalArgumentException(PARAMETER_NULL.toString() + " : dto");
		
		try {
			obj.setSalt(PasswordUtil.generateSalt());
			obj.setEncryptedPassword(PasswordUtil.encryptPassword(obj.getPassword(), obj.getSalt()));
			obj.setPassword(null);
			
			return repository.save(obj);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			log.warn("Unable to convert from dto to entity | Ex: " + e.getMessage());
			throw new GeneralSecurityException("Unable to encrypt password");
		}
	}

	@Override
	@Transactional
	public UserInfo update(UserInfo obj) {
		return null;
	}

	@Override
	@Transactional
	public void deleteById(Integer id) {
		
		if(id == null) throw new IllegalArgumentException(PARAMETER_NULL.toString() + " : id");
		repository.deleteById(id);
	}
	
	@Transactional
	public void deleteByUsername(String username) {
		 
		if(username == null) throw new IllegalArgumentException(PARAMETER_NULL.toString() + " : username");
		repository.deleteByUsername(username);
	}
}