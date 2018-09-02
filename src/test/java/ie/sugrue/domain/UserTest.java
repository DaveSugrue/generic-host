package ie.sugrue.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UserTest {

	private Date	dob;
	private User	user;

	@Before
	public void setUp() throws Exception {
		dob = Date.valueOf("1985-05-01");
		user = new User(1l, "Mike", "Cleary", dob, "mike@cleary.net", "111111");
	}

	@After
	public void tearDown() throws Exception {
		user = null;
	}

	@Test
	public void testUserLongStringStringDateStringString() {
		assertEquals(1l, user.getId());
		assertEquals("Mike", user.getFirstName());
		assertEquals("Cleary", user.getLastName());
		assertEquals(dob, user.getDOB());
		assertEquals("mike@cleary.net", user.getEmail());
		assertEquals("111111", user.getPw());
	}

	@Test
	public void testToString() {
		assertEquals("User [id=1, firstName=Mike, lastName=Cleary, email=mike@cleary.net, pw=111111, dob=1985-05-01]", user.toString());
	}

	@Test
	public void testEqualsWhereEqualNoNulls() {
		User user1 = new User(1l, "Mike", "Cleary", dob, "mike@cleary.net", "111111");
		User user2 = new User(1l, "Mike", "Cleary", dob, "mike@cleary.net", "111111");
		assertTrue(user1.equals(user2));
	}

	@Test
	public void testEqualsWhereEqualWithNulls() {
		User user1 = new User(1l, null, "Cleary", dob, "mike@cleary.net", "111111");
		User user2 = new User(1l, null, "Cleary", dob, "mike@cleary.net", "111111");
		assertTrue(user1.equals(user2));
	}

	@Test
	public void testEqualsWhereNotEqualNoNulls() {
		User user1 = new User(1l, "Mike", "Cleary", dob, "mike@cleary.net", "111111");
		User user2 = new User(1l, "John", "Cleary", dob, "mike@cleary.net", "111111");
		assertFalse(user1.equals(user2));
	}

	@Test
	public void testEqualsWhereNotEqualWithNulls() {
		User user1 = new User(1l, null, "Cleary", dob, "mike@cleary.net", "111111");
		User user2 = new User(1l, "Mike", "Cleary", dob, "mike@cleary.net", null);
		assertFalse(user1.equals(user2));
	}
}
