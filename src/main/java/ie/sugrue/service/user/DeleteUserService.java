package ie.sugrue.service.user;

import ie.sugrue.domain.ResponseWrapper;

public interface DeleteUserService {

	ResponseWrapper deleteUser(ResponseWrapper resp, String id);

}
