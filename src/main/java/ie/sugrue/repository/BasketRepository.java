package ie.sugrue.repository;

import ie.sugrue.domain.Basket;

public interface BasketRepository {

	public void createBasket(Basket basket);

	public Basket getBasket(long id);

	public Basket getBasketForUser(long userId);

	public void deleteBasket(long id);

	public void deleteBasketForUser(long userId);

	public Basket updateBasket(Basket basket);
}