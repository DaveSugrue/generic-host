package ie.sugrue.service.basket;

import ie.sugrue.domain.Basket;
import ie.sugrue.domain.ResponseWrapper;

public interface GetBasketService {

	public Basket getBasket(Long id);

	public ResponseWrapper getBasket(ResponseWrapper resp, long id);

	public ResponseWrapper getBasketForUser(ResponseWrapper resp, long userId);

	public Boolean basketAlreadyExistsForUser(long userId);

}
