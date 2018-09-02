package ie.sugrue.service.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import ie.sugrue.domain.ResponseWrapper;
import ie.sugrue.domain.User;
import ie.sugrue.repository.UserRepository;

@Service("createUserService")
@Scope("prototype")
public class CreateUserServiceImpl implements CreateUserService {

	private final Logger	log	= LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserRepository	userRepo;
	@Autowired
	GetUserService			getUserService;

	@Override
	public ResponseWrapper createUser(ResponseWrapper resp, User user) {

		try {
			userRepo.createUser(user);
		} catch (DuplicateKeyException dk) {
			log.info("Duplicate key on email address: {}", user.getEmail());
			resp.getStatus().updateStatus(1, "The email address '" + user.getEmail() + "' is already in use.");
		} catch (DataAccessException dae) {
			log.error("Data Access exception encountered trying to save {} to Database", user, dae);
			resp.getStatus().updateStatus(2, "We encountered a problem saving your details to our database. Please try again.");
		} catch (Exception e) {
			log.error("Unknown exception encountered trying to save {} to Database", user, e);
			resp.getStatus().updateStatus(2, "We encountered a problem saving your details to our database. Please try again.");
		}

		// Now that the user is created, re-populate the object with the id and add it to the responseWrapper
		user = getUserService.getUser(user.getEmail());
		resp.addObject(user);
		return resp;
	}

}
