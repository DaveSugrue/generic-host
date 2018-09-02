package ie.sugrue.service.movie;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
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
import ie.sugrue.repository.MovieRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class DeleteMovieServiceTest {

	@Configuration
	static class MovieServiceTestContextConfiguration {
		@Bean
		public DeleteMovieService deleteMovieService() {
			return new DeleteMovieServiceImpl();
		}

		@Bean
		public MovieRepository movieRepo() {
			return Mockito.mock(MovieRepository.class);
		}

		@Bean
		public ResponseWrapper resp() {
			return Mockito.mock(ResponseWrapper.class);
		}
	}

	private final Logger		log	= LoggerFactory.getLogger(this.getClass());
	@Autowired
	private DeleteMovieService	deleteMovieService;
	@Autowired
	private MovieRepository		movieRepo;

	private ResponseWrapper		resp;

	@Before
	public void setup() {
		resp = new ResponseWrapper();

		doNothing().when(movieRepo).deleteMovie(1);
		doThrow(new EmptyResultDataAccessException(0)).when(movieRepo).deleteMovie(2);
	}

	@After
	public void tearDown() {
		Mockito.reset(movieRepo);
	}

	@Test
	public void testDeleteMovieSuccess() {
		resp = deleteMovieService.deleteMovie(resp, 1);
		assertThat(resp.getStatus().getCode(), is(equalTo(0)));
	}

	@Test
	public void testDeleteMovieWithNoIdFailure() {
		resp = deleteMovieService.deleteMovie(resp, 0);
		assertThat(resp.getStatus().getCode(), is(equalTo(1)));
		assertThat(resp.getStatus().getMessages().get(0), is(equalTo("I'm not sure what Movie you are trying to delete. Please try again.")));
	}

	@Test
	public void testDeleteNonExistantMovieFailure() {
		resp = deleteMovieService.deleteMovie(resp, 2);
		assertThat(resp.getStatus().getCode(), is(equalTo(1)));
		assertThat(resp.getStatus().getMessages().get(0), is(equalTo("Movie cannot be deleted as it does not exist.")));
	}
}
