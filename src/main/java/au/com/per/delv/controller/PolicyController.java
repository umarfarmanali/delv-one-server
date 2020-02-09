package au.com.per.delv.controller;

import static au.com.per.delv.EnumError.ALL_OK;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import au.com.per.delv.controller.response.AbstractResponse;
import au.com.per.delv.persistence.entity.Policy;
import au.com.per.delv.service.IPolicyService;

@RestController
@RequestMapping("/policy")
public class PolicyController {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	IPolicyService policyService;

	@GetMapping(value = "/getAll")
	public AbstractResponse<Policy> getAll() {

		AbstractResponse<Policy> response = new AbstractResponse<>();
		response.add(policyService.getAll());
		response.setSuccessMessage(ALL_OK.toString());
		
		return response;
	}
}
