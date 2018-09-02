package ie.sugrue.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ie.sugrue.domain.ResponseWrapper;
import ie.sugrue.domain.User;
import ie.sugrue.service.login.LoginService;

@CrossOrigin(origins = "*", maxAge = 3600)

@RestController
@RequestMapping("/")
@Scope("request")
public class LoginController extends PrimaryController {

	@Autowired
	ResponseWrapper	resp;
	@Autowired
	LoginService	loginServiceImpl;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseWrapper login(@RequestBody User user) {
		System.out.println("validating for email = '" + user.getEmail() + "' and pw = '" + user.getPw() + "'");

		return loginServiceImpl.login(resp, user);
	}
}
