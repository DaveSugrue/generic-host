package ie.sugrue.domain;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StatusTest {
	private Status			status;

	private final Logger	log	= LoggerFactory.getLogger(this.getClass());

	@Before
	public void setUp() throws Exception {
		status = new Status();
	}

	@After
	public void tearDown() throws Exception {
		status = null;
	}

	@Test
	public void testConstructorGeneric() {

		assertEquals(0, status.getCode());
		assertEquals("Success", status.getMessages().get(0));
	}

	@Test
	public void testConstructorParam() {
		Status status2 = new Status(1, "Test this");
		assertEquals(1, status2.getCode());
		assertEquals("Test this", status2.getMessages().get(0));
	}

	@Test
	public void testClearMessages() {
		status.addMessage("Test that");
		status.addMessage("Test again");
		assertEquals(3, status.getMessages().size());
		status.clearMessages();
		assertEquals(0, status.getMessages().size());
	}

	@Test
	public void testUpdateStatus() {
		status.updateStatus(0, "Test this");
		status.updateStatus(0, "Test that");
		assertEquals(0, status.getCode());
		assertEquals("Status [code=0, messages=[Success, Test this, Test that]]", status.toString());
		status.updateStatus(1, "Test this");
		assertEquals("Status [code=1, messages=[Test this]]", status.toString());
		status.updateStatus(0, "Test that");
		assertEquals("Status [code=1, messages=[Test this]]", status.toString());
		status.updateStatus(2, "Test this");
		assertEquals("Status [code=2, messages=[Test this]]", status.toString());
		status.updateStatus(2, "Test that");
		assertEquals("Status [code=2, messages=[Test this, Test that]]", status.toString());
		status.updateStatus(0, "Test this");
		assertEquals("Status [code=2, messages=[Test this, Test that]]", status.toString());
	}

	@Test
	public void testToString() {
		status.updateStatus(0, "Test this");
		status.updateStatus(0, "Test that");
		assertEquals(3, status.getMessages().size());
		assertEquals("Status [code=0, messages=[Success, Test this, Test that]]", status.toString());
	}

	@Test
	public void testEquals() {
		status.updateStatus(0, "Test this");

		Status otherStatusObj = new Status();
		otherStatusObj.updateStatus(0, "Test this");

		assertEquals(status, otherStatusObj);
	}

}
