package ie.sugrue.service.movie;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;

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
public class GetMovieServiceTest {

	@Configuration
	static class MovieServiceTestContextConfiguration {
		@Bean
		public GetMovieService getMovieService() {
			return new GetMovieServiceImpl();
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
	private GetMovieService		getMovieService;
	@Autowired
	private MovieRepository		movieRepo;

	private ResponseWrapper		resp1;
	private ResponseWrapper		resp2;
	private Movie				movie1;
	private Movie				movie2;
	private Movie				movie3;
	private ArrayList<Movie>	allMovies;
	private ArrayList<Movie>	allActionMovies;

	@Before
	public void setup() {
		resp1 = new ResponseWrapper();
		resp2 = new ResponseWrapper();

		movie1 = new Movie(1, "ACTN", "Test Name 1", "B", "Testing this 1", 100, "test1.jpg", 75);
		movie2 = new Movie(2, "ACTN", "Test Name 2", "B", "Testing this 2", 110, "test2.jpg", 80);
		movie3 = new Movie(3, "COMDY", "Test Name 3", "B", "Testing this 3", 120, "test3.jpg", 85);

		allMovies = new ArrayList<Movie>();
		allMovies.add(movie1);
		allMovies.add(movie2);
		allMovies.add(movie3);

		allActionMovies = new ArrayList<Movie>();
		allActionMovies.add(movie1);
		allActionMovies.add(movie2);

		Mockito.when(movieRepo.getMovie(1)).thenReturn(movie1);
		Mockito.when(movieRepo.getMovies()).thenReturn(allMovies);
		Mockito.when(movieRepo.getMovies("ACTN")).thenReturn(allActionMovies);
		Mockito.when(movieRepo.getMovie(4)).thenThrow(new EmptyResultDataAccessException(-1));
		Mockito.when(movieRepo.getMovies("NONE")).thenThrow(new EmptyResultDataAccessException(-1));
	}

	@After
	public void tearDown() {
		Mockito.reset(movieRepo);
	}

	@Test
	public void getMovieByIdTest() {
		assertThat(getMovieService.getMovie(1), is(equalTo(movie1)));
	}

	@Test
	public void getMovieByIdReturnResponseWrapperTest() {
		resp1.addObject(movie1);

		resp2 = getMovieService.getMovie(resp2, 1);

		assertThat(resp2, is(equalTo(resp1)));
	}

	@Test
	public void getMovieByNonExistantIdReturnResponseWrapperTest() {
		resp1 = getMovieService.getMovie(resp1, 4);

		assertThat(resp1.getStatus().getCode(), is(equalTo(1)));
		assertThat(resp1.getStatus().getMessages().get(0), is(equalTo("Movie Does Not Exist")));
	}

	@Test
	public void getMoviesTest() {
		assertThat(getMovieService.getMovies(), is(equalTo(allMovies)));
	}

	@Test
	public void getMoviesReturnResponseWrapperTest() {
		resp1.addObject(movie1);
		resp1.addObject(movie2);
		resp1.addObject(movie3);

		resp2 = getMovieService.getMovies(resp2);

		assertThat(resp2, is(equalTo(resp1)));
	}

	@Test
	public void getMoviesByGenreTest() {
		assertThat(getMovieService.getMovies("ACTN"), is(equalTo(allActionMovies)));
	}

	@Test
	public void getMoviesByGenresReturnResponseWrapperTest() {
		resp1.addObject(movie1);
		resp1.addObject(movie2);

		resp2 = getMovieService.getMovies(resp2, "ACTN");

		assertThat(resp2, is(equalTo(resp1)));
	}

	@Test
	public void getMoviesByNonExistantGenreReturnResponseWrapperTest() {
		resp1 = getMovieService.getMovies(resp1, "NONE");

		assertThat(resp1.getStatus().getCode(), is(equalTo(1)));
		assertThat(resp1.getStatus().getMessages().get(0), is(equalTo("No Movies found")));
	}

}
