package au.com.per.delv.service

import spock.lang.Specification

import org.springframework.beans.factory.annotation.Autowired

import au.com.per.delv.dto.UserInfoDto
import au.com.per.delv.service.IUserService
import au.com.per.delv.persistence.entity.UserInfo
import au.com.per.delv.persistence.rep.IUserInfoRepository

import java.util.Optional;
import java.lang.IllegalArgumentException

class UserServiceSpec extends Specification {

	@Autowired
	IUserService userService

	IUserInfoRepository repo = Mock(IUserInfoRepository)

	def setup(){
		userService = new UserServiceImpl(repo)
	}

	def "getList:: non empty list is returned when users exist"(){
		
		given: "a list exists"
		UserInfo entityUserInfo1 = new UserInfo()
		entityUserInfo1.setId(1)
		UserInfo entityUserInfo2 = new UserInfo()
		entityUserInfo2.setId(2)

		List<UserInfo> entityUserInfoList = [entityUserInfo1, entityUserInfo2]

		and: "repository always returns above list"
		repo.findAll() >> entityUserInfoList

		when: "user with username is fetched from service"
		List<UserInfoDto> dtoUserInfoList = userService.getList()

		then:
		dtoUserInfoList.size == 2
	}

	def "getList:: empty list is returned when users don't exist"(){
		
		given: "an empty list exists"
		List<UserInfo> entityUserInfoList = []

		and: "repository always returns above list"
		repo.findAll() >> entityUserInfoList

		when: "user with username is fetched from service"
		List<UserInfoDto> dtoUserInfoList = userService.getList()

		then:
		dtoUserInfoList.size == 0
	}

	def "getByUsername:: IllegalArgumentException is thrown when null parameter is passed"(){
		
		when: "user with username is fetched from service"
		def dtoUserInfo = userService.getByUsername(null)

		then:
		IllegalArgumentException exception = thrown()
		exception.message == "Paramater 'username' can't be null"
	}

	def "getByUsername:: user exists with username"(){
		
		given: "a customer exists with username"
		UserInfo entityUserInfo = new UserInfo()
		entityUserInfo.setUsername(usernamePassed)

		and: "repository always returns above entity"
		repo.findByUsername(entityUserInfo.getUsername()) >> entityUserInfo

		when: "user with username is fetched from service"
		def dtoUserInfo = userService.getByUsername(entityUserInfo.getUsername())

		then:
		assert dtoUserInfo.getUsername(), expected

		where:
		usernamePassed		| expected
		"umarali"			| "umarlai"
	}

	def "getByUsername:: user does not exist"(){
		
		when: "user with username is fetched from service"
		def dtoUserInfo = userService.getByUsername(usernamePassed)

		then:
		assert dtoUserInfo.getErrorMessage(), expected

		where:
		usernamePassed		| expected
		"umarali"			| "User umarali not found"
	}

	def "add:: user is added"(){
		
		given: "a customer is created with username, email, password and active"
		UserInfoDto dtoUserInfo = new UserInfoDto(null, username, email, password, active, null)

		and:
		UserInfo entUserInfo = userService.convertFromDtoToEntity(dtoUserInfo)
		entUserInfo.setId(id)

		when: "user is added using service"
		Optional<UserInfoDto> opt = userService.add(dtoUserInfo)

		then:
		1 * repo.save(_) >> entUserInfo
		opt.get().getUsername() == username
		
		where:
		id = 1
		username = "username"
		email = "username@email.com"
		password = "password"
		active = 1
	}

	def "add:: user is added twice with username"(){
		
		given: "a customer is created with username, email, password and active"
		UserInfoDto dtoUserInfo = new UserInfoDto(null, username, email, password, active, null)

		and:
		UserInfo entUserInfo = userService.convertFromDtoToEntity(dtoUserInfo)
		entUserInfo.setId(id)

		when: "user is added using service"
		Optional<UserInfoDto> opt = userService.add(dtoUserInfo)
		Optional<UserInfoDto> opt1 = userService.add(dtoUserInfo)

		then:
		2 * repo.save(_) >> entUserInfo
		opt.get().getUsername() == username
		
		where:
		id = 1
		username = "username"
		email = "username@email.com"
		password = "password"
		active = 1
	}

	def "add:: IllegalArgumentException is thrown when null parameter is passed"(){
		
		when: "user is added to server"
		def dtoUserInfo = userService.add(null)

		then:
		IllegalArgumentException exception = thrown()
		exception.message == "Paramater 'dto' can't be null"
	}
}
