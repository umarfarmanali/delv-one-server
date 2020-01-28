package au.com.per.delv.service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import au.com.per.delv.dto.UserInfoDto;
import au.com.per.delv.dto.builder.UserInfoDtoBuilder;
import au.com.per.delv.persistence.entity.UserInfo;
import au.com.per.delv.persistence.rep.IUserInfoRepository;
import au.com.per.delv.util.PasswordUtil;

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
	public UserInfoDto getByUsername(String username) {
		
		if(username == null) throw new IllegalArgumentException("Paramater 'username' can't be null");
		
		UserInfo userInfo = userInfoRepository.findByUsername(username);
		if(userInfo != null) {
			return convertFromEntityToDto(userInfo);
		}
		return new UserInfoDtoBuilder().setErrorMessage("User '" + username + "' not found").build();
	}

	@Override
	@Transactional
	public Optional<UserInfoDto> add(UserInfoDto dto) {
		
		if(dto == null) throw new IllegalArgumentException("Paramater 'dto' can't be null");
		
		try {
			return Optional.of(convertFromEntityToDto((userInfoRepository.save(convertFromDtoToEntity(dto)))));
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			log.warn("Unable to convert from dto to entity | Ex: " + e.getMessage());
			return Optional.empty();
		} catch (DataIntegrityViolationException e) {
			return Optional.of(new UserInfoDtoBuilder().setErrorMessage("User already exists").build());
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
		
		if(id == null) throw new IllegalArgumentException("Paramater 'id' can't be null");
		userInfoRepository.deleteById(id);
	}
	
	@Transactional
	public void deleteByUsername(String username) {
		 
		if(username == null) throw new IllegalArgumentException("Paramater 'username' can't be null");
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