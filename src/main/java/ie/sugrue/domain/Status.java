package ie.sugrue.domain;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Status object is passed throughout the execution of a service and updated to the highest error code encountered along with a
 * corresponding message to the user. Error Code 0 - No errors encountered and service executed as expected. Error Code 1 - A business rule
 * prevented the successful completion of the service and the user is advised how to proceed. Error Code 2 - A technical issue prevented the
 * service from executing. Only message(s) relating to the highest error code are returned. Where multiple errors of the same (highest)
 * classification occur, all messages are returned to the user.
 * 
 * @author Dave Sugrue
 *
 */
public class Status {
	private int					code		= 0;
	private ArrayList<String>	messages	= new ArrayList<String>();
	private final Logger		log			= LoggerFactory.getLogger(this.getClass());

	public Status() {
		updateStatus(0, "Success");
	}

	public Status(int code, String message) {
		updateStatus(code, message);
	}

	/*
	 * If a issue higher than the existing code is encountered, update the status code, clear existing messages and add the new message. If
	 * of the same classification, simply add the message to the array.
	 */
	public void updateStatus(int newCode, String message) {
		if (newCode > getCode()) {
			setCode(newCode);
			clearMessages();
			addMessage(message);
		} else if (newCode == getCode()) {
			addMessage(message);
		}
	}

	public void clearMessages() {
		this.messages.clear();
	}

	public void addMessage(String message) {
		this.messages.add(message);
	}

	public int getCode() {
		return code;
	}

	// Never call directly, use recordError() above.
	private void setCode(int code) {
		this.code = code;
	}

	public ArrayList<String> getMessages() {
		return messages;
	}

	public void setMessages(ArrayList<String> messages) {
		this.messages = messages;
	}

	@Override
	public String toString() {
		return "Status [code=" + code + ", messages=" + messages + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + code;
		result = prime * result + ((messages == null) ? 0 : messages.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Status other = (Status) obj;
		if (code != other.code) {
			return false;
		}
		if (messages == null) {
			if (other.messages != null) {
				return false;
			}
		} else if (!messages.equals(other.messages)) {
			return false;
		}
		return true;
	}

}
