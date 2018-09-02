package ie.sugrue.domain;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.sql.Date;

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
		Date dob1, dob2;
		dob1 = Date.valueOf("1995-05-01");
		dob2 = Date.valueOf("1996-06-02");
		User user1 = new User(1l, "John", "Doe", dob1, "john@doe.net", "111111");
		User user2 = new User(2l, "Jane", "Doe", dob2, "jane@doe.net", "abcdefg");
		resp.addObject(user1);
		resp.addObject(user2);

		log.info("resp = " + resp.toString());
		assertEquals(
				"ResponseWrapper [status=Status [code=0, messages=[Success]], objects=[User [id=1, firstName=John, lastName=Doe, email=john@doe.net, pw=111111, dob=1995-05-01], User [id=2, firstName=Jane, lastName=Doe, email=jane@doe.net, pw=abcdefg, dob=1996-06-02]]]",
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
