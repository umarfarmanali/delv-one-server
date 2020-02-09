package au.com.per.delv.service

import spock.lang.Specification

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException;

import au.com.per.delv.service.IUserService
import au.com.per.delv.persistence.entity.UserInfo
import au.com.per.delv.persistence.rep.IUserInfoRepository
import au.com.per.delv.persistence.entity.builder.UserInfoBuilder

import java.util.Optional;

import static au.com.per.delv.EnumError.*

import java.lang.IllegalArgumentException

class UserServiceSpec extends Specification {

	@Autowired
	IUserService userService

	IUserInfoRepository repo = Mock(IUserInfoRepository)

	def setup(){
		userService = new UserServiceImpl(repo)
	}

	def "getAll:: non empty list is returned when users exist"(){
		
		given: "a list exists"
		UserInfo entityUserInfo1 = UserInfoBuilder.builder().setId(1).build()
		UserInfo entityUserInfo2 = UserInfoBuilder.builder().setId(2).build()

		List<UserInfo> toReturnUserInfoList = [entityUserInfo1, entityUserInfo2]

		and: "repository always returns above list"
		repo.findAll() >> toReturnUserInfoList

		when: "user with username is fetched from service"
		List<UserInfo> userInfoList = userService.getAll()

		then:
		userInfoList.size == 2
	}

	def "getAll:: empty list is returned when users don't exist"(){
		
		given: "an empty list exists"
		List<UserInfo> toReturnUserInfoList = []

		and: "repository always returns above list"
		repo.findAll() >> toReturnUserInfoList

		when: "user with username is fetched from service"
		List<UserInfo> userInfoList = userService.getAll()

		then:
		userInfoList.size == 0
	}

	def "getByEmail:: IllegalArgumentException is thrown when null parameter is passed"(){
		
		when: "user with email is fetched from service"
		Optional<UserInfo> opt = userService.getByEmail(null)

		then:
		IllegalArgumentException exception = thrown()
		exception.message.contains(PARAMETER_NULL.toString())
	}

	def "getByEmail:: user exists with email"(){
		
		given: "a customer exists with email"
		UserInfo entityUserInfo = UserInfoBuilder.builder().setEmail(emailPassed).build()

		and: "repository always returns above entity"
		repo.findByEmail(entityUserInfo.getEmail()) >> Optional.of(entityUserInfo)

		when: "user with email is fetched from service"
		Optional<UserInfo> opt = userService.getByEmail(entityUserInfo.getEmail())

		then:
		opt.get().getEmail() == expected

		where:
		emailPassed = "umarali@email.com"
		expected = "umarali@email.com"
	}

	def "getByEmail:: user does not exist"(){
		
		given: "repository always returns empty"
		repo.findByEmail(emailPassed) >> Optional.empty()

		when: "user with email is fetched from service"
		Optional<UserInfo> opt = userService.getByEmail(emailPassed)

		then:
		opt.isPresent() == false

		where:
		emailPassed = "umarali@email.com"
	}

	def "getByUsername:: IllegalArgumentException is thrown when null parameter is passed"(){
		
		when: "user with username is fetched from service"
		Optional<UserInfo> opt = userService.getByUsername(null)

		then:
		IllegalArgumentException exception = thrown()
		exception.message.contains(PARAMETER_NULL.toString())
	}

	def "getByUsername:: user exists with username"(){
		
		given: "a customer exists with username"
		UserInfo entityUserInfo = UserInfoBuilder.builder().setUsername(usernamePassed).build()

		and: "repository always returns above entity"
		repo.findByUsername(entityUserInfo.getUsername()) >> Optional.of(entityUserInfo)

		when: "user with username is fetched from service"
		Optional<UserInfo> opt = userService.getByUsername(entityUserInfo.getUsername())

		then:
		opt.get().getUsername() == expected

		where:
		usernamePassed = "umarali"
		expected = "umarali"
	}

	def "getByUsername:: user does not exist"(){
		
		given: "repository always returns empty"
		repo.findByUsername(usernamePassed) >> Optional.empty()
		
		when: "user with username is fetched from service"
		Optional<UserInfo> opt = userService.getByUsername(usernamePassed)

		then:
		opt.isPresent() == false

		where:
		usernamePassed = "umarali"
	}

	def "add:: user is added"(){
		
		given: "object is created with username, email, password and active"
		UserInfo userInfo = UserInfoBuilder.builder().setUsername(username).setPassword(password).build()

		and:
		repo.save(_) >> userInfo

		when: "user is added using service"
		UserInfo returnedUserInfo = userService.add(userInfo)
		
		then:
		returnedUserInfo != null
		returnedUserInfo.getUsername() != null
		returnedUserInfo.getUsername() == username
		
		where:
		username = "username"
		password = "password"
		email = "username@email.com"
	}

	def "add:: IllegalArgumentException is thrown when null parameter is passed"(){
		
		when: "user is added with null parameter"
		def dtoUserInfo = userService.add(null)

		then:
		IllegalArgumentException exception = thrown()
		exception.message.contains(PARAMETER_NULL.toString())
	}

	def "deleteById:: user is deleted by id"(){
		
		when: "delete method called with non-null parameter"
		userService.deleteById(id)

		then:
		1 * repo.deleteById(_)
		noExceptionThrown()

		where:
		id = 1
	}
	
	def "deleteById:: IllegalArgumentException is thrown when null parameter is passed"(){
		
		when: "delete method called with null parameter"
		userService.deleteById(null)

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