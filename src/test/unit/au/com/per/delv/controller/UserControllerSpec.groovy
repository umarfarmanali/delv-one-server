package au.com.per.delv.controller

import spock.lang.Specification

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.MockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest

import au.com.per.delv.controller.UserController
import au.com.per.delv.service.IUserService

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(controllers = [UserController])  
class UserControllerSpec extends Specification {

	@Autowired
	protected MockMvc mvc;

	IUserService service = Mock(IUserService)

	def "/user/getAll:: non-empty or empty list is returned"() {
        
		expect: "Success message returned"
        mvc.perform(get('/user/getAll'))
          .andExpect(status().isOk())
          .andReturn()
          .response
          .contentAsString == "Hello world!"
    }
}