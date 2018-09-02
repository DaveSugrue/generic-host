package ie.sugrue.service.movie;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
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

import ie.sugrue.domain.Movie;
import ie.sugrue.domain.ResponseWrapper;
import ie.sugrue.repository.MovieRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class UpdateMovieServiceTest {

	@Configuration
	static class MovieServiceTestContextConfiguration {
		@Bean
		public UpdateMovieService updateMovieService() {
			return new UpdateMovieServiceImpl();
		}

		@Bean
		public MovieRepository movieRepo() {
			return Mockito.mock(MovieRepository.class);
		}

		@Bean
		public ResponseWrapper resp() {
			return Mockito.mock(ResponseWrapper.class);
		}

		@Bean
		public GetMovieService getMovieService() {
			return Mockito.mock(GetMovieService.class);
		}
	}

	private final Logger		log	= LoggerFactory.getLogger(this.getClass());
	@Autowired
	private UpdateMovieService	updateMovieService;
	@Autowired
	private MovieRepository		movieRepo;
	@Autowired
	private GetMovieService		getMovieService;

	private ResponseWrapper		resp;
	private Movie				movie1;
	private Movie				movie2;
	private Movie				oldMovie;
	private Movie				movieWithNulls;
	private Movie				populatedMovie;

	@Before
	public void setup() {
		resp = new ResponseWrapper();

		movie1 = new Movie(1, "ACTN", "Test Name 1", "B", "Testing this 1", 100, "test1.jpg", 75);
		movie2 = new Movie(2, "ACTN", "Test Name 2", "B", "Testing this 2", 110, "test2.jpg", 80);

		oldMovie = new Movie(3, "ACTN", "Test Name 3", "B", "Testing this 3", 110, "test3.jpg", 80);
		movieWithNulls = new Movie(3, "ACTN", "Test Name 4", null, null, null, "test3.jpg", 80);
		populatedMovie = new Movie(3, "ACTN", "Test Name 4", "B", "Testing this 3", 110, "test3.jpg", 80);

		// doNothing().when(movieRepo).updateMovie(movie1);
		doThrow(new EmptyResultDataAccessException(0)).when(movieRepo).updateMovie(movie2);

		Mockito.when(getMovieService.getMovie(1)).thenReturn(movie1);
		Mockito.when(movieRepo.updateMovie(movie1)).thenReturn(movie1);
		Mockito.when(getMovieService.getMovie(2)).thenReturn(movie2);
		doThrow(new EmptyResultDataAccessException(0)).when(movieRepo).updateMovie(movie2);

		Mockito.when(getMovieService.getMovie(3)).thenReturn(oldMovie);
		Mockito.when(movieRepo.updateMovie(populatedMovie)).thenReturn(populatedMovie);

	}

	@After
	public void tearDown() {
		Mockito.reset(movieRepo);
	}

	@Test
	public void testUpdateMovieSuccess() {
		resp = updateMovieService.updateMovie(resp, movie1);
		assertThat(resp.getStatus().getCode(), is(equalTo(0)));
	}

	@Test
	public void testUpdateMovieFailure() {
		resp = updateMovieService.updateMovie(resp, movie2);
		assertThat(resp.getStatus().getCode(), is(equalTo(1)));
	}

	@Test
	public void testUpdateMovieWithNullsSuccess() {
		// Nulls should populate with values from oldMovie so now looks like populatedMovie
		resp = updateMovieService.updateMovie(resp, movieWithNulls);
		assertThat(resp.getStatus().getCode(), is(equalTo(0)));
		Movie movie3 = (Movie) resp.getObjects().get(0);
		assertThat(movie3, is(equalTo(populatedMovie)));
	}

}
