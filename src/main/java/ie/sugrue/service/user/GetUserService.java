package ie.sugrue.service.user;

import ie.sugrue.domain.ResponseWrapper;
import ie.sugrue.domain.User;

public interface GetUserService {

	public User getUser(String id);

	public ResponseWrapper getUser(ResponseWrapper resp, String id);
	
	public User getUserById(long id);

	public ResponseWrapper getUserById(ResponseWrapper resp, long id);

	public User getUserByEmail(String email);

	public ResponseWrapper getUserByEmail(ResponseWrapper resp, String email);
	
}
