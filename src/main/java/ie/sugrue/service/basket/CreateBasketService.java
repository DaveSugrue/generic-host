package ie.sugrue.service.basket;

import ie.sugrue.domain.Basket;
import ie.sugrue.domain.ResponseWrapper;

public interface CreateBasketService {

	ResponseWrapper createBasket(ResponseWrapper resp, Basket basket);

}
