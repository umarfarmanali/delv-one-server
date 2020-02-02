package au.com.per.delv.service;

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

import au.com.per.delv.dto.UserInfoDto;
import au.com.per.delv.dto.builder.UserInfoDtoBuilder;
import au.com.per.delv.persistence.entity.UserInfo;
import au.com.per.delv.persistence.rep.IUserInfoRepository;
import au.com.per.delv.util.PasswordUtil;

import static au.com.per.delv.persistence.PersistenceError.*;

@Service
public class UserServiceImpl implements IUserService{

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	public UserServiceImpl(IUserInfoRepository userInfoRepository) {
		 this.userInfoRepository = userInfoRepository; 
	}
	
	private IUserInfoRepository userInfoRepository;
	
	@Override
	public List<UserInfoDto> getList() {
		
		List<UserInfoDto> list = new ArrayList<>();
		userInfoRepository.findAll().forEach(obj -> list.add(convertFromEntityToDto(obj)));
		return list;
	}
	
	@Override
	public UserInfoDto getByEmail(String email) {
		
		if(email == null) throw new IllegalArgumentException(PARAMETER_NULL.toString() + " : email");
		
		UserInfo userInfo = userInfoRepository.findByEmail(email);
		if(userInfo != null) {
			return convertFromEntityToDto(userInfo);
		}
		return new UserInfoDtoBuilder().setErrorMessage(NOT_FOUND.toString() + " | email: " + email).build();
	}

	@Override
	public UserInfoDto getByUsername(String username) {
		
		if(username == null) throw new IllegalArgumentException(PARAMETER_NULL.toString() + " : username");
		
		UserInfo userInfo = userInfoRepository.findByUsername(username);
		if(userInfo != null) {
			return convertFromEntityToDto(userInfo);
		}
		return new UserInfoDtoBuilder().setErrorMessage(NOT_FOUND.toString() + " | username: " + username).build();
	}
	
	@Override
	@Transactional
	public Optional<UserInfoDto> add(UserInfoDto dto) {
		
		if(dto == null) 
			throw new IllegalArgumentException(PARAMETER_NULL.toString() + " : dto");
		
		if(getByUsername(dto.getUsername()).getErrorMessage() == null) 
			return Optional.of(new UserInfoDtoBuilder().setErrorMessage(ALREADY_EXIST.toString() + " | username: " + dto.getUsername()).build());
		if(getByEmail(dto.getEmail()).getErrorMessage() == null) 
			return Optional.of(new UserInfoDtoBuilder().setErrorMessage(ALREADY_EXIST.toString() + " | email: " + dto.getEmail()).build()); 
			
		try {
			return Optional.of(convertFromEntityToDto((userInfoRepository.save(convertFromDtoToEntity(dto)))));
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			log.warn("Unable to convert from dto to entity | Ex: " + e.getMessage());
			return Optional.empty();
		}
	}

	@Override
	@Transactional
	public Optional<UserInfoDto> update(UserInfoDto obj) {
		return Optional.empty();
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		
		if(id == null) throw new IllegalArgumentException(PARAMETER_NULL.toString() + " : id");
		userInfoRepository.deleteById(id);
	}
	
	@Transactional
	public void deleteByUsername(String username) {
		 
		if(username == null) throw new IllegalArgumentException(PARAMETER_NULL.toString() + " : username");
		userInfoRepository.deleteByUsername(username);
	}

	@Override
	public UserInfo convertFromDtoToEntity(UserInfoDto dto) throws NoSuchAlgorithmException, InvalidKeySpecException {
		byte[] salt = PasswordUtil.generateSalt();
		byte[] encryptedPassword = PasswordUtil.encryptPassword(dto.getPassword(), salt);
		
		return new UserInfo(dto.getUsername(), dto.getEmail(), encryptedPassword, salt, dto.getActive());
	}

	@Override
	public UserInfoDto convertFromEntityToDto(UserInfo entity) {
		
		return new UserInfoDto(entity.getId(), entity.getUsername(), entity.getEmail(), entity.getActive());
	}
}