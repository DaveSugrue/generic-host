package ie.sugrue.service.genre;

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

import ie.sugrue.domain.Genre;
import ie.sugrue.domain.ResponseWrapper;
import ie.sugrue.repository.GenreRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class GetGenreServiceTest {

	@Configuration
	static class GenreServiceTestContextConfiguration {
		@Bean
		public GetGenreService getGenreService() {
			return new GetGenreServiceImpl();
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
	private GetGenreService		getGenreService;
	@Autowired
	private GenreRepository		genreRepo;

	private ResponseWrapper		resp1;
	private ResponseWrapper		resp2;
	private Genre				genre1;
	private Genre				genre2;
	private Genre				genre3;
	private ArrayList<Genre>	allGenres;

	@Before
	public void setup() {
		resp1 = new ResponseWrapper();
		resp2 = new ResponseWrapper();

		genre1 = new Genre("ACTN", "Test Name 1", 0);
		genre2 = new Genre("COMDY", "Test Name 2", 1);
		genre3 = new Genre("ADVNT", "Test Name 3", 2);

		allGenres = new ArrayList<Genre>();
		allGenres.add(genre1);
		allGenres.add(genre2);
		allGenres.add(genre3);

		Mockito.when(genreRepo.getGenre("ACTN")).thenReturn(genre1);
		Mockito.when(genreRepo.getGenres()).thenReturn(allGenres);
		Mockito.when(genreRepo.getGenre("NONE")).thenThrow(new EmptyResultDataAccessException(-1));
	}

	@After
	public void tearDown() {
		Mockito.reset(genreRepo);
	}

	@Test
	public void getGenreByIdTest() {
		assertThat(getGenreService.getGenre("ACTN"), is(equalTo(genre1)));
	}

	@Test
	public void getGenreByIdReturnResponseWrapperTest() {
		resp1.addObject(genre1);

		resp2 = getGenreService.getGenre(resp2, "ACTN");

		assertThat(resp2, is(equalTo(resp1)));
	}

	@Test
	public void getGenreByNonExistantIdReturnResponseWrapperTest() {
		resp1 = getGenreService.getGenre(resp1, "NONE");

		assertThat(resp1.getStatus().getCode(), is(equalTo(1)));
		assertThat(resp1.getStatus().getMessages().get(0), is(equalTo("Genre Does Not Exist")));
	}

	@Test
	public void getGenresTest() {
		assertThat(getGenreService.getGenres(), is(equalTo(allGenres)));
	}

	@Test
	public void getGenresReturnResponseWrapperTest() {
		resp1.addObject(genre1);
		resp1.addObject(genre2);
		resp1.addObject(genre3);

		resp2 = getGenreService.getGenres(resp2);

		assertThat(resp2, is(equalTo(resp1)));
	}
}
