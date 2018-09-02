package ie.sugrue.service.user;

import ie.sugrue.domain.ResponseWrapper;
import ie.sugrue.domain.User;

public interface UpdateUserService {

	ResponseWrapper updateUser(ResponseWrapper resp, User user);

}
