package ie.sugrue.service.user;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
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
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ie.sugrue.domain.ResponseWrapper;
import ie.sugrue.domain.User;
import ie.sugrue.repository.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class CreateUserServiceTest {

	@Configuration
	static class UserServiceTestContextConfiguration {
		@Bean
		public CreateUserService createUserService() {
			return new CreateUserServiceImpl();
		}

		@Bean
		public UserRepository userRepo() {
			return Mockito.mock(UserRepository.class);
		}

		@Bean
		public ResponseWrapper resp() {
			return Mockito.mock(ResponseWrapper.class);
		}

		@Bean
		public GetUserService getUserService() {
			return Mockito.mock(GetUserService.class);
		}
	}

	private final Logger		log	= LoggerFactory.getLogger(this.getClass());
	@Autowired
	private CreateUserService	createUserService;
	@Autowired
	private UserRepository		userRepo;
	@Autowired
	private GetUserService		getUserService;

	private ResponseWrapper		resp;
	private User				user1;
	private User				user2;
	private Date				dob;

	@Before
	public void setup() {
		resp = new ResponseWrapper();

		dob = Date.valueOf("1985-05-01");
		user1 = new User(1, "John", "Doe", dob, "john@doe.ie", "123456");
		dob = Date.valueOf("1985-05-02");
		user2 = new User(2, "Jane", "Doe", dob, "jane@doe.ie", "123456");

		Mockito.when(getUserService.getUser("john@doe.ie")).thenReturn(user1);
		Mockito.when(getUserService.getUser("jane@doe.ie")).thenReturn(user2);

		doNothing().when(userRepo).createUser(user1);
		doThrow(new DuplicateKeyException("")).when(userRepo).createUser(user2);
	}

	@After
	public void tearDown() {
		Mockito.reset(userRepo);
	}

	@Test
	public void testCreateUserSuccess() {
		resp = createUserService.createUser(resp, user1);
		assertEquals(0, resp.getStatus().getCode());
		assertEquals(user1, (User) resp.getObjects().get(0));
	}

	@Test
	public void testCreateNonExistantUserById() {
		resp = createUserService.createUser(resp, user2);
		assertEquals(1, resp.getStatus().getCode());
		String errorMessage = "The email address '" + user2.getEmail() + "' is already in use.";
		assertEquals(errorMessage, resp.getStatus().getMessages().get(0));

	}
}
