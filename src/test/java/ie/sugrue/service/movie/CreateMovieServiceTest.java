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
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ie.sugrue.domain.Movie;
import ie.sugrue.domain.ResponseWrapper;
import ie.sugrue.repository.MovieRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class CreateMovieServiceTest {

	@Configuration
	static class MovieServiceTestContextConfiguration {
		@Bean
		public CreateMovieService createMovieService() {
			return new CreateMovieServiceImpl();
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
	private CreateMovieService	createMovieService;
	@Autowired
	private MovieRepository		movieRepo;

	private ResponseWrapper		resp;
	private Movie				movie1;
	private Movie				movie2;

	@Before
	public void setup() {
		resp = new ResponseWrapper();

		movie1 = new Movie(1, "ACTN", "Test Name 1", "B", "Testing this 1", 100, "test1.jpg", 75);
		movie2 = new Movie(2, "ACTN", "Test Name 2", "B", "Testing this 2", 110, "test2.jpg", 80);

		doNothing().when(movieRepo).createMovie(movie1);
		doThrow(new DuplicateKeyException(null)).when(movieRepo).createMovie(movie2);
	}

	@After
	public void tearDown() {
		Mockito.reset(movieRepo);
	}

	@Test
	public void testCreateMovieSuccess() {
		resp = createMovieService.createMovie(resp, movie1);
		assertThat(resp.getStatus().getCode(), is(equalTo(0)));
	}

	@Test
	public void testCreateMovieFailure() {
		resp = createMovieService.createMovie(resp, movie2);
		assertThat(resp.getStatus().getCode(), is(equalTo(1)));
	}
}
