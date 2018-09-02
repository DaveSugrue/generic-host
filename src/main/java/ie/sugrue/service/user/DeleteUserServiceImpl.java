package ie.sugrue.service.user;

import org.apache.commons.validator.routines.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import ie.sugrue.domain.ResponseWrapper;
import ie.sugrue.repository.UserRepository;
import ie.sugrue.utils.Utils;

@Service("deleteUserService")
@Scope("prototype")
public class DeleteUserServiceImpl implements DeleteUserService {

	private final Logger	log	= LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserRepository	userRepo;

	@Override
	public ResponseWrapper deleteUser(ResponseWrapper resp, String id) {

		Long userId = 0l;
		
		try {
			if (Utils.isNotNull(id))
			{
				if (EmailValidator.getInstance().isValid(id)){
					String email = id;
					userRepo.deleteUser(email);
				}
				else {
					try{
						userId = Long.valueOf(id);
						userRepo.deleteUser(userId);
					} catch (NumberFormatException nfe){
						log.error("Cannot identify user based on id of {} to be deleted when trying to delete from Database", id, nfe);
						resp.getStatus().updateStatus(1, "I'm not sure what User you are trying to delete. Please try again.");
					}
				}
			} else {
				log.error("Cannot identify user based on id of {} to be deleted when trying to delete from Database", id);
				resp.getStatus().updateStatus(1, "I'm not sure what User you are trying to delete. Please try again.");
			}
		} catch (EmptyResultDataAccessException erdae) {
			log.info("Problem occured deleting user with id of {} from DB - ", id, erdae);
			resp.getStatus().updateStatus(1, "User cannot be deleted as it does not exist.");
		} catch (DataAccessException dae) {
			log.error("Data Access exception encountered trying to delete user with id of {} from Database", id, dae);
			resp.getStatus().updateStatus(2, "We encountered a problem deleting user details from our database. Please try again.");
		} catch (Exception e) {
			log.error("Unknown exception encountered trying to delete user with id of {} to Database", id, e);
			resp.getStatus().updateStatus(2, "We encountered a problem deleting user details from our database. Please try again.");
		}

		return resp;
	}
}
