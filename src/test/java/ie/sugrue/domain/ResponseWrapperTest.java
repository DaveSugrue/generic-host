package ie.sugrue.domain;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResponseWrapperTest {
	private ResponseWrapper	resp;
	private ResponseWrapper	resp2;

	private final Logger	log	= LoggerFactory.getLogger(this.getClass());

	@Before
	public void setUp() throws Exception {
		resp = new ResponseWrapper();
		resp2 = new ResponseWrapper();
	}

	@After
	public void tearDown() throws Exception {
		resp = null;
	}

	@Test
	public void testConstructorGeneric() {
		assertThat(resp.getStatus().getCode(), is(equalTo(0)));
		assertThat(resp.getStatus().getMessages().get(0), is(equalTo("Success")));
		assertEquals(0, resp.getObjects().size());
	}

	@Test
	public void testUpdateStatus() {
		resp.updateStatus(1, "warning message");

		assertThat(resp.getStatus().getCode(), is(equalTo(1)));
		assertThat(resp.getStatus().getMessages().get(0), is(equalTo("warning message")));
	}

	@Test
	public void testToString() {
		Movie movie1 = new Movie(1, "ACTN", "Test Name", "B", "Testing this", 100, "test.jpg", 75);
		Movie movie2 = new Movie(2, "COMDY", "Test Name 2", "B", "Testing this again", 120, "test2.jpg", 85);
		resp.addObject(movie1);
		resp.addObject(movie2);

		assertEquals(
				"ResponseWrapper [status=Status [code=0, messages=[Success]], objects=[Movie [id=1, genreId=ACTN, name=Test Name, format=B, notes=Testing this, duration=100, image=test.jpg, rating=75], Movie [id=2, genreId=COMDY, name=Test Name 2, format=B, notes=Testing this again, duration=120, image=test2.jpg, rating=85]]]",
				resp.toString());
	}

	@Test
	public void testEquals() {
		resp.updateStatus(0, "Test this");
		resp2.updateStatus(0, "Test this");

		resp.addObject("Test");
		resp2.addObject("Test");

		assertEquals(resp, resp2);
	}

}
