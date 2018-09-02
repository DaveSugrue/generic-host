package ie.sugrue.service.user;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

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
import ie.sugrue.repository.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class DeleteUserServiceTest {

	@Configuration
	static class UserServiceTestContextConfiguration {
		@Bean
		public DeleteUserService deleteUserService() {
			return new DeleteUserServiceImpl();
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

	private final Logger		log	= LoggerFactory.getLogger(this.getClass());
	@Autowired
	private DeleteUserService	deleteUserService;
	@Autowired
	private UserRepository		userRepo;

	private ResponseWrapper		resp;

	@Before
	public void setup() {
		resp = new ResponseWrapper();

		doNothing().when(userRepo).deleteUser(1);
		doThrow(new EmptyResultDataAccessException(0)).when(userRepo).deleteUser(2);
		doNothing().when(userRepo).deleteUser("john@doe.ie");
		doThrow(new EmptyResultDataAccessException(0)).when(userRepo).deleteUser("jane@doe.ie");
	}

	@After
	public void tearDown() {
		Mockito.reset(userRepo);
	}

	@Test
	public void testDeleteUserByIdSuccess() {
		resp = deleteUserService.deleteUser(resp, "1");
		assertEquals(0, resp.getStatus().getCode());
	}

	@Test
	public void testDeleteNonExistantUserById() {
		resp = deleteUserService.deleteUser(resp, "2");
		assertEquals(1, resp.getStatus().getCode());
	}

	@Test
	public void testDeleteUnknownUserById() {
		resp = deleteUserService.deleteUser(resp, null);
		assertEquals(1, resp.getStatus().getCode());
		assertEquals("I'm not sure what User you are trying to delete. Please try again.", resp.getStatus().getMessages().get(0));
	}

	@Test
	public void testDeleteUserByEmailSuccess() {
		resp = deleteUserService.deleteUser(resp, "john@doe.ie");
		assertEquals(0, resp.getStatus().getCode());
	}

	@Test
	public void testDeleteNonExistantUserByEmail() {
		resp = deleteUserService.deleteUser(resp, "jane@doe.ie");
		assertEquals(1, resp.getStatus().getCode());
	}
	
	@Test
	public void testDeleteUnknownUserByEmail() {
		resp = deleteUserService.deleteUser(resp, "jackdoe.ie");
		assertEquals(1, resp.getStatus().getCode());
		assertEquals("I'm not sure what User you are trying to delete. Please try again.", resp.getStatus().getMessages().get(0));
	}

}
