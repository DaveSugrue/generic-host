package ie.sugrue.service.login;

import ie.sugrue.domain.ResponseWrapper;
import ie.sugrue.domain.User;

public interface LoginService {

	public ResponseWrapper login(ResponseWrapper resp, User user);

	public Boolean validateLogin(String password, String storedPassword);
}
