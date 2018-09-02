package ie.sugrue.service.basket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import ie.sugrue.domain.Basket;
import ie.sugrue.domain.ResponseWrapper;
import ie.sugrue.domain.Status;
import ie.sugrue.repository.BasketRepository;

@Service("getBasketService")
@Scope("prototype")
public class GetBasketServiceImpl implements GetBasketService {

	private final Logger		log	= LoggerFactory.getLogger(this.getClass());

	@Autowired
	private BasketRepository	basketRepo;

	@Override
	public Basket getBasket(Long id) {

		Basket basket = new Basket();

		try {
			basket = basketRepo.getBasket(id);
		} catch (EmptyResultDataAccessException erdae) {
			log.info("Problem occured getting basket with id of {} from DB - ", id, erdae);
		} catch (Exception e) {
			log.error("Problem occured getting basket with id of {} from DB - ", id, e);
		}

		return basket;
	}

	@Override
	public ResponseWrapper getBasket(ResponseWrapper resp, long id) {

		try {
			Basket basket = basketRepo.getBasket(id);
			resp.addObject(basket);
		} catch (EmptyResultDataAccessException erdae) {
			log.info("Problem occured getting basket with id of {} from DB - ", id, erdae);
			Status status = new Status(1, "Basket Does Not Exist");
			resp.setStatus(status);
		} catch (Exception e) {
			log.error("Problem occured getting basket with id of {} from DB - ", id, e);
			Status status = new Status(2, "Problem getting Basket data from DB");
			resp.setStatus(status);
		}

		return resp;
	}

	@Override
	public ResponseWrapper getBasketForUser(ResponseWrapper resp, long userId) {

		try {
			Basket basket = basketRepo.getBasketForUser(userId);
			resp.addObject(basket);
		} catch (EmptyResultDataAccessException erdae) {
			log.info("Problem occured getting basket for user id of {} from DB - ", userId, erdae);
			Status status = new Status(1, "Basket Does Not Exist");
			resp.setStatus(status);
		} catch (Exception e) {
			log.error("Problem occured getting basket for user id of {} from DB - ", userId, e);
			Status status = new Status(2, "Problem getting Basket data from DB");
			resp.setStatus(status);
		}

		return resp;
	}

	@Override
	public Boolean basketAlreadyExistsForUser(long userId) {
		try {
			Basket basket = basketRepo.getBasketForUser(userId);
			return true;
		} catch (EmptyResultDataAccessException erdae) {
			return false;
		}
	}

}
