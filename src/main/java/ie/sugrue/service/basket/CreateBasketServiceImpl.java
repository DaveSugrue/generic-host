package ie.sugrue.service.basket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import ie.sugrue.domain.Basket;
import ie.sugrue.domain.ResponseWrapper;
import ie.sugrue.repository.BasketRepository;

@Service("createBasketService")
@Scope("prototype")
public class CreateBasketServiceImpl implements CreateBasketService {

	private final Logger		log	= LoggerFactory.getLogger(this.getClass());

	@Autowired
	private GetBasketService	getBasketService;
	@Autowired
	private BasketRepository	basketRepo;

	@Override
	public ResponseWrapper createBasket(ResponseWrapper resp, Basket basket) {

		try {

			if (getBasketService.basketAlreadyExistsForUser(basket.getUserId())) {
				resp.getStatus().updateStatus(1, "This user already has a basket.");
			} else {
				basketRepo.createBasket(basket);
			}
		} catch (DuplicateKeyException dk) {
			log.info("Duplicate key on id: {}", basket.getId());
			resp.getStatus().updateStatus(1, "The basket id '" + basket.getId() + "' is already in use.");
		} catch (DataIntegrityViolationException dive) {
			log.error("Data Integrity exception encountered trying to save {} to Database. Does the product or user exist?", basket, dive);
			resp.getStatus().updateStatus(2, "We encountered a problem saving the Basket details to our database. Please try again.");
		} catch (DataAccessException dae) {
			log.error("Data Access exception encountered trying to save {} to Database", basket, dae);
			resp.getStatus().updateStatus(2, "We encountered a problem saving the Basket details to our database. Please try again.");
		} catch (Exception e) {
			log.error("Unknown exception encountered trying to save {} to Database", basket, e);
			resp.getStatus().updateStatus(2, "We encountered a problem saving the Basket details to our database. Please try again.");
		}

		return resp;
	}

}
