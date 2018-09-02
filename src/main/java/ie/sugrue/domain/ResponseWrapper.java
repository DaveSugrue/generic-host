package ie.sugrue.domain;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Used by all services to wrap a response being sent back to a client. It always contains a status object and then an array of objects to
 * be returned to the client
 * 
 * @author Dave Sugrue
 *
 */
@Component
@Scope(value = "prototype")
public class ResponseWrapper {
	private Status			status	= new Status();
	private List			objects	= new ArrayList();

	private final Logger	log		= LoggerFactory.getLogger(this.getClass());

	public ResponseWrapper() {

	}
	
	public void updateStatus(int newCode, String message) {
		this.getStatus().updateStatus(newCode, message);
	}
	
	public ResponseWrapper complete() {
		if (this.getStatus().getCode() == 2) {
			log.error("");
			log.error("##################################################     ERROR     ##################################################");
			log.error("Error encountered :: {}", this);
			log.error("########################################     End     ########################################");
			log.error("");
		} else {
			log.info("Returning :: {}", this);
		}

		return this;
	}
	
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public List getObjects() {
		return objects;
	}

	public void setObjects(List objects) {
		this.objects = objects;
	}

	public void addObject(Object object) {
		this.objects.add(object);
	}
	
	@Override
	public String toString() {
		return "ResponseWrapper [status=" + status + ", objects=" + objects + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((objects == null) ? 0 : objects.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		ResponseWrapper other = (ResponseWrapper) obj;
		if (objects == null) {
			if (other.objects != null) {
				return false;
			}
		} else if (!objects.equals(other.objects)) {
			return false;
		}
		if (status == null) {
			if (other.status != null) {
				return false;
			}
		} else if (!status.equals(other.status)) {
			return false;
		}
		return true;
	}

}
