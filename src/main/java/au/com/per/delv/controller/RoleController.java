package au.com.per.delv.controller;

import static au.com.per.delv.EnumError.ALL_OK;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import au.com.per.delv.controller.response.AbstractResponse;
import au.com.per.delv.persistence.entity.Role;
import au.com.per.delv.service.IRoleService;

@RestController
@RequestMapping("/role")
public class RoleController {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	IRoleService roleService;

	@GetMapping(value = "/getAll")
	public AbstractResponse<Role> getAll() {

		AbstractResponse<Role> response = new AbstractResponse<>();
		response.add(roleService.getAll());
		response.setSuccessMessage(ALL_OK.toString());
		
		return response;
	}
}
