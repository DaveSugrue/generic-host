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

public class GenreTest {
	private Genre			genre;
	private Genre			genre2;

	private final Logger	log	= LoggerFactory.getLogger(this.getClass());

	@Before
	public void setUp() throws Exception {
		genre = new Genre();
		genre2 = new Genre("ACTN", "Action", 2);
	}

	@After
	public void tearDown() throws Exception {
		genre = null;
		genre2 = null;
	}

	@Test
	public void testConstructorGeneric() {
		assertNull(genre.getId());
		assertNull(genre.getDescription());
		assertThat(genre.getOrderSequence(), is(equalTo(0)));
	}

	@Test
	public void testConstructorParam() {
		assertThat(genre2.getId(), is(equalTo("ACTN")));
		assertThat(genre2.getDescription(), is(equalTo("Action")));
		assertThat(genre2.getOrderSequence(), is(equalTo(2)));
	}

	@Test
	public void testToString() {
		assertEquals("Genre [id=ACTN, description=Action, orderSequence=2]", genre2.toString());
	}

	@Test
	public void testEquals() {
		Genre genre3 = new Genre("ACTN", "Action", 2);
		Genre genre4 = new Genre("ACTN", "Action", 3);
		assertThat(genre3, is(equalTo(genre2)));
		assertThat(genre3, not(equalTo(genre4)));
	}

}
