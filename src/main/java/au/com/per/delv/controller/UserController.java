package au.com.per.delv.controller;

import static au.com.per.delv.EnumError.ALL_OK;
import static au.com.per.delv.EnumError.ALREADY_EXIST;
import static au.com.per.delv.EnumError.EXCEPTION;
import static au.com.per.delv.EnumError.MISSING_RELATION;
import static au.com.per.delv.EnumError.NOT_FOUND;
import static au.com.per.delv.EnumError.PARAMETER_NULL;

import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import au.com.per.delv.controller.response.AbstractResponse;
import au.com.per.delv.persistence.entity.UserInfo;
import au.com.per.delv.service.IUserService;

@RestController
@RequestMapping("/user")
public class UserController {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	IUserService userService;
	
	@GetMapping(value = "/getAll")
	public AbstractResponse<UserInfo> getAll() {

		AbstractResponse<UserInfo> response = new AbstractResponse<>();
		response.add(userService.getAll());
		response.setSuccessMessage(ALL_OK.toString());
		
		return response;
	}
	
	@GetMapping(value = "/getByEmail/{email}")
	public AbstractResponse<UserInfo> getByEmail(@PathVariable("email") String email) {

		AbstractResponse<UserInfo> response = new AbstractResponse<>();
		
		if(Objects.isNull(email)) {
			response.setErrorMessage(PARAMETER_NULL.toString() + " | Email: " + email);
		} else {
			
			Optional<UserInfo> opt = userService.getByEmail(email);
			
			if(opt.isPresent()) {
				response.add(opt.get());
				response.setSuccessMessage(ALL_OK.toString());
			} else {
				response.setErrorMessage(NOT_FOUND.toString() + " | Email: " + email);
			}
		}
		return response;
	}

	@GetMapping(value = "/getByUsername/{username}")
	public AbstractResponse<UserInfo> getByUsername(@PathVariable("username") String username) {

		AbstractResponse<UserInfo> response = new AbstractResponse<>();
		
		if(Objects.isNull(username)) {
			response.setErrorMessage(PARAMETER_NULL.toString() + " | Username: " + username);
		} else {
			
			Optional<UserInfo> opt = userService.getByUsername(username);
			
			if(opt.isPresent()) {
				response.add(opt.get());
				response.setSuccessMessage(ALL_OK.toString());
			} else {
				response.setErrorMessage(NOT_FOUND.toString() + " | Username: " + username);
			}
		}
		return response;
	}

	@PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
	public AbstractResponse<UserInfo> add(@RequestBody UserInfo obj) {

		AbstractResponse<UserInfo> response = new AbstractResponse<>();
		
		if (Objects.isNull(obj)) {
			response.setErrorMessage(PARAMETER_NULL.toString() + " | Entity: " + obj);
			return response;
		}
		
		if (obj.getRoleSet().isEmpty()) {
			response.setErrorMessage(MISSING_RELATION.toString("role"));
			return response;
		}
		
		if(userService.getByEmail(obj.getEmail()).isPresent()) {
			response.setErrorMessage(ALREADY_EXIST.toString() + " | Email: " + obj.getEmail());
			return response;
		}
		
		if(userService.getByUsername(obj.getUsername()).isPresent()) {
			response.setErrorMessage(ALREADY_EXIST.toString() + " | Username: " + obj.getUsername());
			return response;
		}
		
		try {
			UserInfo userInfo = userService.add(obj);
			response.add(userInfo);
			response.setSuccessMessage(ALL_OK.toString());
		} catch (Exception e) {
			response.setErrorMessage(EXCEPTION.toString(e.getMessage()));
		}
		return response;
	}

	@DeleteMapping(value = "/deleteById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public AbstractResponse<UserInfo> deleteById(@PathVariable("id") Integer id) {

		AbstractResponse<UserInfo> response = new AbstractResponse<>();
		
		if (Objects.isNull(id)) {
			response.setErrorMessage(PARAMETER_NULL.toString() + " | Id: " + id);
			return response;
		}
		
		Optional<UserInfo> opt = userService.getById(id);
		
		if(!opt.isPresent()) {
			response.setErrorMessage(NOT_FOUND.toString() + " | Id: " + id);
			return response;
		}
		
		userService.deleteById(id);
			
		response.add(opt.get());
		response.setSuccessMessage(ALL_OK.toString());
		
		return response;
	}
	
	@DeleteMapping(value = "/deleteByUsername/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
	public AbstractResponse<UserInfo> deleteByUsername(@PathVariable("username") String username) {

		AbstractResponse<UserInfo> response = new AbstractResponse<>();
		
		if (Objects.isNull(username)) {
			response.setErrorMessage(PARAMETER_NULL.toString() + " | Username: " + username);
			return response;
		}
		
		Optional<UserInfo> opt = userService.getByUsername(username);
		
		if(!opt.isPresent()) {
			response.setErrorMessage(NOT_FOUND.toString() + " | Username: " + username);
			return response;
		}
		
		userService.deleteByUsername(username);
			
		response.add(opt.get());
		response.setSuccessMessage(ALL_OK.toString());
		
		return response;
	}
}
