package au.com.per.delv.controller;

import java.util.List;
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

import au.com.per.delv.dto.UserInfoDto;
import au.com.per.delv.service.IUserService;

@RestController
@RequestMapping("/user")
public class UserController {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	IUserService userService;

	@GetMapping(value = "/all")
	public List<UserInfoDto> getAll() {

		return userService.getList();
	}

	@GetMapping(value = "/byUsername/{username}")
	public UserInfoDto getByUsername(@PathVariable("username") String username) {

		if(!Objects.isNull(username)) {
			return userService.getByUsername(username);
		}
		return null;
	}

	@PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
	public UserInfoDto add(@RequestBody UserInfoDto obj) {

		if (!Objects.isNull(obj)) {
			Optional<UserInfoDto> optUserInfoDto = userService.add(obj);
			if (optUserInfoDto.isPresent()) {
				return optUserInfoDto.get();
			}
		}
		return null;
	}

	@DeleteMapping(value = "/delete/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
	public String deleteByUsername(@PathVariable("username") String username) {

		String returnMessage;
		try {
			if (username != null && !username.isEmpty()) {

				userService.deleteByUsername(username);
				returnMessage = "delete successful";
			} else {
				returnMessage = "delete un-successful | parameter is empty";
			}
		} catch (Exception e) {
			log.warn(e.getMessage());
			returnMessage = "delete un-successful | exception: " + e.getMessage();
		}
		return "{\"message\":\"" + returnMessage + "\"}";
	}
}
