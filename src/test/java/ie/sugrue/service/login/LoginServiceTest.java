package ie.sugrue.service.login;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doThrow;

import java.sql.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ie.sugrue.domain.ResponseWrapper;
import ie.sugrue.domain.User;
import ie.sugrue.repository.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class LoginServiceTest {

	@Configuration
	static class UserServiceTestContextConfiguration {
		@Bean
		public LoginService loginService() {
			return new LoginServiceImpl();
		}

		@Bean
		public UserRepository userRepo() {
			return Mockito.mock(UserRepository.class);
		}

		@Bean
		public ResponseWrapper resp() {
			return Mockito.mock(ResponseWrapper.class);
		}
	}

	private final Logger	log	= LoggerFactory.getLogger(this.getClass());
	@Autowired
	private LoginService	loginService;
	@Autowired
	private UserRepository	userRepo;

	private ResponseWrapper	resp;
	private User			logonUser;
	private User			storedUser;
	private User			nonUser;
	private Date			dob;

	@Before
	public void setup() {
		resp = new ResponseWrapper();

		dob = Date.valueOf("1985-05-01");
		logonUser = new User(1, "John", "Doe", dob, "john@doe.ie", "123456");
		storedUser = new User(1, "John", "Doe", dob, "john@doe.ie", "123456");
		dob = Date.valueOf("1985-05-02");
		nonUser = new User(2, "Jane", "Doe", dob, "jane@doe.ie", "112233");

		Mockito.when(userRepo.getUser("john@doe.ie")).thenReturn(storedUser);
		doThrow(new EmptyResultDataAccessException(0)).when(userRepo).getUser("jane@doe.ie");
	}

	@After
	public void tearDown() {
		Mockito.reset(userRepo);
	}

	@Test
	public void testLoginSuccess() {
		resp = loginService.login(resp, logonUser);
		assertEquals(0, resp.getStatus().getCode());
	}

	@Test
	public void testLoginFailure() {
		logonUser.setPw("ABCDEF");
		String errorMessage = "Username or Password are incorrect. Please try again";
		resp = loginService.login(resp, logonUser);

		assertEquals(1, resp.getStatus().getCode());
		assertEquals(errorMessage, resp.getStatus().getMessages().get(0));

	}

	@Test
	public void testLoginForNonExistantUser() {
		resp = loginService.login(resp, nonUser);
		String errorMessage = "User does not exist. Please try again";

		assertEquals(1, resp.getStatus().getCode());
		assertEquals(errorMessage, resp.getStatus().getMessages().get(0));
	}

	@Test
	public void testValidateLogin() {
		assertEquals(true, loginService.validateLogin("abc", "abc"));
		assertEquals(false, loginService.validateLogin("abc", "def"));
		assertEquals(false, loginService.validateLogin("abc", "ABC"));
	}

}
