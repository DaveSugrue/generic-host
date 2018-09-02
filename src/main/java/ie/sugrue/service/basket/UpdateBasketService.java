package ie.sugrue.service.basket;

import ie.sugrue.domain.Basket;
import ie.sugrue.domain.ResponseWrapper;

public interface UpdateBasketService {

	ResponseWrapper updateBasket(ResponseWrapper resp, Basket basket);

}
