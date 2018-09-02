package ie.sugrue.service.genre;

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

import ie.sugrue.domain.Genre;
import ie.sugrue.domain.ResponseWrapper;
import ie.sugrue.repository.GenreRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class CreateGenreServiceTest {

	@Configuration
	static class GenreServiceTestContextConfiguration {
		@Bean
		public CreateGenreService createGenreService() {
			return new CreateGenreServiceImpl();
		}

		@Bean
		public GenreRepository genreRepo() {
			return Mockito.mock(GenreRepository.class);
		}

		@Bean
		public ResponseWrapper resp() {
			return Mockito.mock(ResponseWrapper.class);
		}
	}

	private final Logger		log	= LoggerFactory.getLogger(this.getClass());
	@Autowired
	private CreateGenreService	createGenreService;
	@Autowired
	private GenreRepository		genreRepo;

	private ResponseWrapper		resp;
	private Genre				genre1;
	private Genre				genre2;

	@Before
	public void setup() {
		resp = new ResponseWrapper();

		genre1 = new Genre("ACTN", "Action", 2);
		genre2 = new Genre("COMDY", "Comedy", 1);

		doNothing().when(genreRepo).createGenre(genre1);
		doThrow(new DuplicateKeyException(null)).when(genreRepo).createGenre(genre2);
	}

	@After
	public void tearDown() {
		Mockito.reset(genreRepo);
	}

	@Test
	public void testCreateMovieSuccess() {
		resp = createGenreService.createGenre(resp, genre1);
		assertThat(resp.getStatus().getCode(), is(equalTo(0)));
	}

	@Test
	public void testCreateMovieFailure() {
		resp = createGenreService.createGenre(resp, genre2);
		assertThat(resp.getStatus().getCode(), is(equalTo(1)));
	}
}
