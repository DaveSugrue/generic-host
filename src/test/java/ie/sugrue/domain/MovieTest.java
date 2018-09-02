package ie.sugrue.domain;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MovieTest {
	private Movie			movie;
	private Movie			movie2;

	private final Logger	log	= LoggerFactory.getLogger(this.getClass());

	@Before
	public void setUp() throws Exception {
		movie = new Movie();
		movie2 = new Movie(1, "ACTN", "Test Name", "B", "Testing this", 100, "test.jpg", 75);
	}

	@After
	public void tearDown() throws Exception {
		movie = null;
		movie2 = null;
	}

	@Test
	public void testConstructorGeneric() {
		assertEquals(0, movie.getId());
		assertNull(movie.getGenreId());
		assertNull(movie.getName());
		assertNull(movie.getFormat());
		assertNull(movie.getNotes());
		assertNull(movie.getDuration());
		assertNull(movie.getImage());
		assertNull(movie.getRating());
	}

	@Test
	public void testConstructorParam() {
		assertEquals(1, movie2.getId());
		assertEquals("ACTN", movie2.getGenreId());
		assertEquals("Test Name", movie2.getName());
		assertEquals("B", movie2.getFormat());
		assertEquals("Testing this", movie2.getNotes());
		assertEquals((Integer) 100, movie2.getDuration());
		assertEquals("test.jpg", movie2.getImage());
		assertEquals((Integer) 75, movie2.getRating());
	}

	@Test
	public void testToString() {
		assertEquals("Movie [id=1, genreId=ACTN, name=Test Name, format=B, notes=Testing this, duration=100, image=test.jpg, rating=75]", movie2.toString());
	}

	@Test
	public void testEquals() {
		Movie movie3 = new Movie(1, "ACTN", "Test Name", "B", "Testing this", 100, "test.jpg", 75);
		Movie movie4 = new Movie(1, "ACTN", "Test Name", "B", "Testing this", 100, "test.jpg", 50);
		assertThat(movie3, is(equalTo(movie2)));
		assertThat(movie3, not(equalTo(movie4)));
	}

}
