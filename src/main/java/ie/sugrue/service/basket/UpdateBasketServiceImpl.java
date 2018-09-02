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
import ie.sugrue.repository.BasketRepository;

@Service("updateBasketService")
@Scope("prototype")
public class UpdateBasketServiceImpl implements UpdateBasketService {

	private final Logger		log	= LoggerFactory.getLogger(this.getClass());

	@Autowired
	private BasketRepository	basketRepo;

	@Override
	public ResponseWrapper updateBasket(ResponseWrapper resp, Basket basket) {

		try {
			if (basket.getId() > 0) {
				resp.addObject(basketRepo.updateBasket(basket));
			} else if (basket.getUserId() > 0) {
				Basket tempBasket = basketRepo.getBasketForUser(basket.getUserId());
				basket.setId(tempBasket.getId());
				resp.addObject(basketRepo.updateBasket(basket));
			} else {
				log.error("Cannot identify the basket we are trying to update", basket);
				resp.getStatus().updateStatus(1, "We encountered a problem updating your basket. Please try again.");
			}
		} catch (EmptyResultDataAccessException erdae) {
			log.error("Cannot identify user to be updated when trying to update {} to Database", basket, erdae);
			resp.getStatus().updateStatus(1, "We encountered a problem saving the Basket details to our database. Please try again.");
		} catch (DataAccessException dae) {
			log.error("Data Access exception encountered trying to update {} to Database", basket, dae);
			resp.getStatus().updateStatus(2, "We encountered a problem saving the Basket details to our database. Please try again.");
		} catch (Exception e) {
			log.error("Unknown exception encountered trying to update {} to Database", basket, e);
			resp.getStatus().updateStatus(2, "We encountered a problem saving the Basket details to our database. Please try again.");
		}

		return resp;
	}

}
