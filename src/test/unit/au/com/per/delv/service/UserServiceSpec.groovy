package au.com.per.delv.service

import spock.lang.Specification

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException;

import au.com.per.delv.dto.UserInfoDto
import au.com.per.delv.service.IUserService
import au.com.per.delv.persistence.entity.UserInfo
import au.com.per.delv.persistence.rep.IUserInfoRepository

import java.util.Optional;
import java.lang.IllegalArgumentException

import static au.com.per.delv.persistence.PersistenceError.*

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

	def "getByEmail:: IllegalArgumentException is thrown when null parameter is passed"(){
		
		when: "user with email is fetched from service"
		def dtoUserInfo = userService.getByEmail(null)

		then:
		IllegalArgumentException exception = thrown()
		exception.message.contains(PARAMETER_NULL.toString())
	}

	def "getByEmail:: user exists with email"(){
		
		given: "a customer exists with email"
		UserInfo entityUserInfo = new UserInfo()
		entityUserInfo.setEmail(emailPassed)

		and: "repository always returns above entity"
		repo.findByEmail(entityUserInfo.getEmail()) >> entityUserInfo

		when: "user with email is fetched from service"
		def dtoUserInfo = userService.getByEmail(entityUserInfo.getEmail())

		then:
		dtoUserInfo.getEmail() == expected

		where:
		emailPassed = "umarali@email.com"
		expected = "umarali@email.com"
	}

	def "getByEmail:: user does not exist"(){
		
		when: "user with email is fetched from service"
		def dtoUserInfo = userService.getByEmail(emailPassed)

		then:
		dtoUserInfo.getErrorMessage().contains(NOT_FOUND.toString())

		where:
		emailPassed = "umarali@email.com"
	}

	def "getByUsername:: IllegalArgumentException is thrown when null parameter is passed"(){
		
		when: "user with username is fetched from service"
		def dtoUserInfo = userService.getByUsername(null)

		then:
		IllegalArgumentException exception = thrown()
		exception.message.contains(PARAMETER_NULL.toString())
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
		dtoUserInfo.getUsername() == expected

		where:
		usernamePassed = "umarali"
		expected = "umarali"
	}

	def "getByUsername:: user does not exist"(){
		
		when: "user with username is fetched from service"
		def dtoUserInfo = userService.getByUsername(usernamePassed)

		then:
		dtoUserInfo.getErrorMessage().contains(NOT_FOUND.toString())

		where:
		usernamePassed = "umarali"
	}

	def "add:: user is added"(){
		
		given: "object is created with username, email, password and active"
		UserInfoDto dtoUserInfo = new UserInfoDto(null, username, email, password, active, null)

		and:
		UserInfo entUserInfo = userService.convertFromDtoToEntity(dtoUserInfo)
		entUserInfo.setId(id)

		and:
		repo.findByUsername(username) >> null

		and:
		repo.findByEmail(email) >> null

		and:
		repo.save(_) >> entUserInfo

		when: "user is added using service"
		Optional<UserInfoDto> opt = userService.add(dtoUserInfo)
		
		then:
		opt.get() != null
		opt.get().getUsername() != null
		opt.get().getUsername() == username
		
		where:
		id = 1
		username = "username"
		email = "username@email.com"
		password = "password"
		active = 1
	}

	def "add:: user is added with existing username"(){
		
		given: "object is created with username, email, password and active"
		UserInfoDto dtoUserInfo = new UserInfoDto(null, username, email, password, active, null)

		and:
		UserInfo entUserInfo = userService.convertFromDtoToEntity(dtoUserInfo)
		entUserInfo.setId(id)

		and: "object with username already exists"
		repo.findByUsername(username) >> entUserInfo
		
		when: "user is added using service"
		Optional<UserInfoDto> opt = userService.add(dtoUserInfo)
		
		then:
		opt.get() != null
		opt.get().getErrorMessage() == ALREADY_EXIST.toString()  + " | username: " + username
		
		where:
		id = 1
		username = "username"
		email = "username@email.com"
		password = "password"
		active = 1
	}

	def "add:: user is added with existing email"(){
		
		given: "object is created with username, email, password and active"
		UserInfoDto dtoUserInfo = new UserInfoDto(null, username, email, password, active, null)

		and:
		UserInfo entUserInfo = userService.convertFromDtoToEntity(dtoUserInfo)
		entUserInfo.setId(id)

		and: "object with username doesn't exist"
		repo.findByUsername(username) >> null

		and: "object with email already exists"
		repo.findByEmail(email) >> entUserInfo

		when: "user is added using service"
		Optional<UserInfoDto> opt = userService.add(dtoUserInfo)
		
		then:
		opt.get() != null
		opt.get().getErrorMessage() == ALREADY_EXIST.toString()  + " | email: " + email
		
		where:
		id = 1
		username = "username"
		email = "username@email.com"
		password = "password"
		active = 1
	}

	def "add:: IllegalArgumentException is thrown when null parameter is passed"(){
		
		when: "user is added with null parameter"
		def dtoUserInfo = userService.add(null)

		then:
		IllegalArgumentException exception = thrown()
		exception.message.contains(PARAMETER_NULL.toString())
	}

	def "delete:: user is deleted by id"(){
		
		when: "delete method called with non-null parameter"
		userService.delete(id)

		then:
		1 * repo.deleteById(_)
		noExceptionThrown()

		where:
		id = 1
	}
	
	def "delete:: IllegalArgumentException is thrown when null parameter is passed"(){
		
		when: "delete method called with null parameter"
		userService.delete(null)

		then:
		IllegalArgumentException exception = thrown()
		exception.message.contains(PARAMETER_NULL.toString())
	}

	def "deleteByUsername:: user is deleted by username"(){
		
		when: "delete method called with non-null parameter"
		userService.deleteByUsername(username)

		then:
		1 * repo.deleteByUsername(_)
		noExceptionThrown()

		where:
		username = "username"
	}
	
	def "deleteByUsername:: IllegalArgumentException is thrown when null parameter is passed"(){
		
		when: "delete method called with null parameter"
		userService.deleteByUsername(null)

		then:
		IllegalArgumentException exception = thrown()
		exception.message.contains(PARAMETER_NULL.toString())
	}
}