package ie.sugrue.service.genre;

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

import ie.sugrue.domain.Genre;
import ie.sugrue.domain.ResponseWrapper;
import ie.sugrue.repository.GenreRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class UpdateGenreServiceTest {

	@Configuration
	static class GenreServiceTestContextConfiguration {
		@Bean
		public UpdateGenreService updateGenreService() {
			return new UpdateGenreServiceImpl();
		}

		@Bean
		public GenreRepository genreRepo() {
			return Mockito.mock(GenreRepository.class);
		}

		@Bean
		public ResponseWrapper resp() {
			return Mockito.mock(ResponseWrapper.class);
		}

		@Bean
		public GetGenreService getGenreService() {
			return Mockito.mock(GetGenreService.class);
		}
	}

	private final Logger		log	= LoggerFactory.getLogger(this.getClass());
	@Autowired
	private UpdateGenreService	updateGenreService;
	@Autowired
	private GenreRepository		genreRepo;
	@Autowired
	private GetGenreService		getGenreService;

	private ResponseWrapper		resp;
	private Genre				genre1;
	private Genre				genre2;

	@Before
	public void setup() {
		resp = new ResponseWrapper();

		genre1 = new Genre("ACTN", "Test Name 1", 0);
		genre2 = new Genre("COMDY", "Test Name 2", 1);

		doThrow(new EmptyResultDataAccessException(0)).when(genreRepo).updateGenre(genre2);

		Mockito.when(getGenreService.getGenre("ACTN")).thenReturn(genre1);
		Mockito.when(genreRepo.updateGenre(genre1)).thenReturn(genre1);
		Mockito.when(getGenreService.getGenre("COMDY")).thenReturn(genre2);
		doThrow(new EmptyResultDataAccessException(0)).when(genreRepo).updateGenre(genre2);

	}

	@After
	public void tearDown() {
		Mockito.reset(genreRepo);
	}

	@Test
	public void testUpdateGenreSuccess() {
		resp = updateGenreService.updateGenre(resp, genre1);
		assertThat(resp.getStatus().getCode(), is(equalTo(0)));
	}

	@Test
	public void testUpdateGenreFailure() {
		resp = updateGenreService.updateGenre(resp, genre2);
		assertThat(resp.getStatus().getCode(), is(equalTo(1)));
	}
}
