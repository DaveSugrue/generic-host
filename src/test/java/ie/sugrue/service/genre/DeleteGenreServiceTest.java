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
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ie.sugrue.domain.ResponseWrapper;
import ie.sugrue.repository.GenreRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class DeleteGenreServiceTest {

	@Configuration
	static class GenreServiceTestContextConfiguration {
		@Bean
		public DeleteGenreService deleteGenreService() {
			return new DeleteGenreServiceImpl();
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
	private DeleteGenreService	deleteGenreService;
	@Autowired
	private GenreRepository		genreRepo;

	private ResponseWrapper		resp;

	@Before
	public void setup() {
		resp = new ResponseWrapper();

		doNothing().when(genreRepo).deleteGenre("ACTN");
		doThrow(new EmptyResultDataAccessException(0)).when(genreRepo).deleteGenre("CHICK");
	}

	@After
	public void tearDown() {
		Mockito.reset(genreRepo);
	}

	@Test
	public void testDeleteGenreSuccess() {
		resp = deleteGenreService.deleteGenre(resp, "ACTN");
		assertThat(resp.getStatus().getCode(), is(equalTo(0)));
	}

	@Test
	public void testDeleteGenreWithNoIdFailure() {
		resp = deleteGenreService.deleteGenre(resp, null);
		assertThat(resp.getStatus().getCode(), is(equalTo(1)));
		assertThat(resp.getStatus().getMessages().get(0), is(equalTo("I'm not sure what Genre you are trying to delete. Please try again.")));
	}

	@Test
	public void testDeleteNonExistantGenreFailure() {
		resp = deleteGenreService.deleteGenre(resp, "CHICK");
		assertThat(resp.getStatus().getCode(), is(equalTo(1)));
		assertThat(resp.getStatus().getMessages().get(0), is(equalTo("Genre cannot be deleted as it does not exist.")));
	}
}
