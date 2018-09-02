package ie.sugrue.service.basket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import ie.sugrue.domain.Basket;
import ie.sugrue.domain.ResponseWrapper;
import ie.sugrue.domain.User;
import ie.sugrue.repository.BasketRepository;

@Service("deleteBasketService")
@Scope("prototype")
public class DeleteBasketServiceImpl implements DeleteBasketService {

	private final Logger		log	= LoggerFactory.getLogger(this.getClass());

	@Autowired
	private BasketRepository	basketRepo;

	@Override
	public ResponseWrapper deleteBasket(ResponseWrapper resp, Basket basket) {

		try {
			if (basket.getId() > 0) {
				basketRepo.deleteBasket(basket.getId());
			} else {
				log.error("Cannot identify Basket to be deleted when trying to delete {} from Database", basket);
				resp.getStatus().updateStatus(1, "We encountered a problem deleting Basket details from our database. Please try again.");
			}
		} catch (EmptyResultDataAccessException erdae) {
			log.info("Problem occured deleting basket with id of {} from DB - ", basket.getId(), erdae);
			resp.getStatus().updateStatus(1, "Basket cannot be deleted as it does not exist.");
		} catch (DataAccessException dae) {
			log.error("Data Access exception encountered trying to delete {} from Database", basket, dae);
			resp.getStatus().updateStatus(2, "We encountered a problem deleting Basket details from our database. Please try again.");
		} catch (Exception e) {
			log.error("Unknown exception encountered trying to delete {} to Database", basket, e);
			resp.getStatus().updateStatus(2, "We encountered a problem deleting Basket details from our database. Please try again.");
		}

		return resp;
	}

	@Override
	public ResponseWrapper deleteBasketForUser(ResponseWrapper resp, User user) {

		try {
			if (user.getId() > 0) {
				basketRepo.deleteBasketForUser(user.getId());
			} else {
				log.error("Cannot identify User whose baskets are to be deleted when trying to delete {} from Database", user);
				resp.getStatus().updateStatus(1, "We encountered a problem deleting Basket details from our database. Please try again.");
			}
		} catch (EmptyResultDataAccessException erdae) {
			log.info("Problem occured deleting basket for user with id of {} from DB - ", user.getId(), erdae);
			resp.getStatus().updateStatus(1, "Basket cannot be deleted as it does not exist.");
		} catch (DataAccessException dae) {
			log.error("Data Access exception encountered deleting basket for user {} from Database", user, dae);
			resp.getStatus().updateStatus(2, "We encountered a problem deleting Basket details from our database. Please try again.");
		} catch (Exception e) {
			log.error("Unknown exception encountered deleting basket for user {} from Database", user, e);
			resp.getStatus().updateStatus(2, "We encountered a problem deleting Basket details from our database. Please try again.");
		}

		return resp;
	}
}
