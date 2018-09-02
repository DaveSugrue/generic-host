package ie.sugrue.service.user;


import org.apache.commons.validator.routines.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import ie.sugrue.domain.ResponseWrapper;
import ie.sugrue.domain.Status;
import ie.sugrue.domain.User;
import ie.sugrue.repository.UserRepository;
import ie.sugrue.utils.Utils;

@Service("getUserService")
@Scope("prototype")
public class GetUserServiceImpl implements GetUserService {

	private final Logger	log	= LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserRepository	userRepo;

	@Override
	public User getUser(String id) {

		User user = new User();
		Long userId = 0l;
		
		if (Utils.isNotNull(id))
		{
			if (EmailValidator.getInstance().isValid(id)){
				return getUserByEmail(id);
			}
			else {
				try{
					userId = Long.valueOf(id);
					return getUserById(userId);
				} catch (NumberFormatException nfe){
					log.error("Cannot identify user based on id of {} to be retrieved from Database", id, nfe);
				}
			}
		} else {
			log.error("Cannot identify user based on id of {} to be retrieved from Database", id);
		}

		return user;
	}

	@Override
	public ResponseWrapper getUser(ResponseWrapper resp, String id) {

		Long userId = 0l;
		
		if (Utils.isNotNull(id))
		{
			if (EmailValidator.getInstance().isValid(id)){
				return getUserByEmail(resp, id);
			}
			else {
				try{
					userId = Long.valueOf(id);
					return getUserById(resp, userId);
				} catch (NumberFormatException nfe){
					log.error("Cannot identify user based on id of {} to be retrieved from Database", id, nfe);
					resp.getStatus().updateStatus(1, "I'm not sure what User you are trying to retrieve. Please try again.");
				}
			}
		} else {
			log.error("Cannot identify user based on id of {} to be retrieved from Database", id);
			resp.getStatus().updateStatus(1, "I'm not sure what User you are trying to retrieve. Please try again.");
		}

		return resp;
	}
	
	@Override
	public User getUserById(long id) {

		User user = new User();

		try {
			user = userRepo.getUser(id);
		} catch (EmptyResultDataAccessException erdae) {
			log.info("Problem occured getting user with id of {} from DB - ", id, erdae);
		} catch (Exception e) {
			log.error("Problem occured getting user with id of {} from DB - ", id, e);
		}

		return user;
	}

	@Override
	public ResponseWrapper getUserById(ResponseWrapper resp, long id) {

		try {
			User user = userRepo.getUser(id);
			resp.addObject(user);
		} catch (EmptyResultDataAccessException erdae) {
			log.info("Problem occured getting user with id of {} from DB - ", id, erdae);
			Status status = new Status(1, "User Does Not Exist");
			resp.setStatus(status);
		} catch (Exception e) {
			log.error("Problem occured getting user with id of {} from DB - ", id, e);
			Status status = new Status(2, "Problem getting User data from DB");
			resp.setStatus(status);
		}

		return resp;
	}

	@Override
	public User getUserByEmail(String email) {

		User user = new User();

		try {
			user = userRepo.getUser(email);
		} catch (EmptyResultDataAccessException erdae) {
			log.info("Problem occured getting user with email of {} from DB - ", email, erdae);
		} catch (Exception e) {
			log.error("Problem occured getting user with email of {} from DB - ", email, e);
		}

		return user;
	}

	@Override
	public ResponseWrapper getUserByEmail(ResponseWrapper resp, String email) {

		try {
			User user = userRepo.getUser(email);
			resp.addObject(user);
		} catch (EmptyResultDataAccessException erdae) {
			log.info("Problem occured getting user with email of {} from DB - ", email, erdae);
			Status status = new Status(1, "User Does Not Exist");
			resp.setStatus(status);
		} catch (Exception e) {
			log.error("Problem occured getting user with id of email from DB - ", email, e);
			Status status = new Status(2, "Problem getting User data from DB");
			resp.setStatus(status);
		}

		return resp;
	}

}
