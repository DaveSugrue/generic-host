package ie.sugrue.service.login;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import ie.sugrue.domain.ResponseWrapper;
import ie.sugrue.domain.User;
import ie.sugrue.repository.UserRepository;

@Service("loginService")
@Scope("prototype")
public class LoginServiceImpl implements LoginService {

	private final Logger	log	= LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserRepository	userRepo;

	@Override
	public ResponseWrapper login(ResponseWrapper resp, User user) {

		try {
			User storedUser = userRepo.getUser(user.getEmail());

			if (validateLogin(user.getPw(), storedUser.getPw())) {
				resp.addObject(storedUser);
			} else {
				resp.getStatus().updateStatus(1, "Username or Password are incorrect. Please try again");
			}
		} catch (EmptyResultDataAccessException empty) {
			resp.getStatus().updateStatus(1, "User does not exist. Please try again");
		} catch (Exception e) {
			log.error("Unknown exception encountered trying to log {} in.", user, e);
			resp.getStatus().updateStatus(2, "We encountered a problem logging you in. Please try again.");
		}
		log.info("resp = " + resp.toString());
		return resp;
	}

	@Override
	public Boolean validateLogin(String password, String storedPassword) {
		return password.equals(storedPassword);
	}

}
