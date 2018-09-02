package ie.sugrue.service.user;

import ie.sugrue.domain.ResponseWrapper;
import ie.sugrue.domain.User;

public interface CreateUserService {

	ResponseWrapper createUser(ResponseWrapper resp, User user);

}
