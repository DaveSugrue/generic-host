package ie.sugrue.service.basket;

import ie.sugrue.domain.Basket;
import ie.sugrue.domain.ResponseWrapper;
import ie.sugrue.domain.User;

public interface DeleteBasketService {

	ResponseWrapper deleteBasket(ResponseWrapper resp, Basket basket);

	ResponseWrapper deleteBasketForUser(ResponseWrapper resp, User user);

}
